package bekir.app.currencymaster.ui.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.ActivityMainBinding
import bekir.app.currencymaster.ui.presentation.auth.AuthActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeValues()
        initDrawerAndActionBar()
        initClickListeners()

    }

    private fun observeValues() {
        //هون منعمل العمليات المسؤولة هن تغيير الشاشة لما نكبس ع وحدة من الخيارات اللي تحت الشاشة
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentScreen.collectLatest {
                    val transaction: FragmentTransaction =
                        supportFragmentManager.beginTransaction().setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    transaction.replace(
                        binding.navHostFragment.id, it.fragment
                    ).commit()
                    changeButtonsColor(it)
                }
            }
        }
        //هون منشوف اذا المستخدم عامل تسجيل دخون فما منظهرلو زر تسجيل الدخول بالقائمة عاليسار
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isUserSignedIn.collectLatest { isSignedIn ->
                    val navHeaderBinding = binding.navigationView.getHeaderView(0)
                    val loginLayout = navHeaderBinding.findViewById<LinearLayout>(R.id.login_layout)
                        .apply { isVisible = !isSignedIn }
                    navHeaderBinding.findViewById<TextView>(R.id.username_txt)
                        .apply {
                            isVisible = isSignedIn
                            val email = viewModel.firebaseAuth.currentUser?.email
                            text = email
                        }
                    loginLayout.setOnClickListener {
                        if (!isSignedIn) {
                            val intent = Intent(this@MainActivity, AuthActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun initClickListeners() {
        //هون منحدد شو يصير لما نكبس على واحد من العناصر الموجودين بالbar اللي تحت
        binding.drawerImg.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        binding.currencyImg.setOnClickListener {
            viewModel.changeScreen(Screens.CurrencyFragment)
        }
        binding.goldImg.setOnClickListener {
            viewModel.changeScreen(Screens.GoldFragment)
        }
        binding.converterImg.setOnClickListener {
            viewModel.changeScreen(Screens.ConverterFragment)
        }
    }

    // هون عم نعمل  initialize لل drawer (اللي هو القائمة اللي بتظهر من عاليسار)
    private fun initDrawerAndActionBar() {
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navigationView: NavigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    //هاد بغير لون العنصر اللي منكبس عليه من الbar اللي تحت
    private fun changeButtonsColor(screen: Screens) {
        val currencyColor = ContextCompat.getColor(
            this,
            if (screen == Screens.CurrencyFragment) R.color.blue else R.color.grey
        )
        val goldColor = ContextCompat.getColor(
            this,
            if (screen == Screens.GoldFragment) R.color.blue else R.color.grey
        )
        val converterColor = ContextCompat.getColor(
            this,
            if (screen == Screens.ConverterFragment) R.color.blue else R.color.grey
        )


        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.currencyImg.drawable),
            currencyColor
        )
        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.goldImg.drawable),
            goldColor
        )
        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.converterImg.drawable),
            converterColor
        )
        binding.currencyTxt.setTextColor(currencyColor)
        binding.goldTxt.setTextColor(goldColor)
        binding.converterTxt.setTextColor(converterColor)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item1 -> {
                viewModel.changeScreen(Screens.CurrencyFragment)
            }

            R.id.menu_item2 -> {
                viewModel.changeScreen(Screens.GoldFragment)
            }

            R.id.menu_item3 -> {
                viewModel.changeScreen(Screens.ConverterFragment)
            }

            R.id.menu_item4 -> {
                if (viewModel.isUserSignedIn.value) {
                    viewModel.firebaseAuth.signOut()
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToast("you're not logged in")
                }
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //منتحكم شو بصير لما ينكبس زر الرجوع
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //هي دالة منستدعيها لما بدنا نطالع رسالة للمستخدم
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}