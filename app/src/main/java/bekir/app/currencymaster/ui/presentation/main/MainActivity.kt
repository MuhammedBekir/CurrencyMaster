package bekir.app.currencymaster.ui.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.ActivityMainBinding
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
        val navHeaderBinding = binding.navigationView.getHeaderView(0)
        val loginLayout = navHeaderBinding.findViewById<LinearLayout>(R.id.login_layout)
        loginLayout.setOnClickListener {
            showToast("Login Layout Clicked")
        }
        val navigationView: NavigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun changeButtonsColor(screen: Screens) {
        val coversColor = ContextCompat.getColor(
            this,
            if (screen == Screens.CurrencyFragment) R.color.blue else R.color.grey
        )
        val voicesColor = ContextCompat.getColor(
            this,
            if (screen == Screens.GoldFragment) R.color.blue else R.color.grey
        )
        val settingsColor = ContextCompat.getColor(
            this,
            if (screen == Screens.ConverterFragment) R.color.blue else R.color.grey
        )


        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.currencyImg.drawable),
            coversColor
        )
        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.goldImg.drawable),
            voicesColor
        )
        DrawableCompat.setTint(
            DrawableCompat.wrap(binding.converterImg.drawable),
            settingsColor
        )
        binding.currencyTxt.setTextColor(coversColor)
        binding.goldTxt.setTextColor(voicesColor)
        binding.converterTxt.setTextColor(settingsColor)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item1 -> {
                showToast("Item 1 clicked")
            }

            R.id.menu_item2 -> {
                showToast("Item 2 clicked")
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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}