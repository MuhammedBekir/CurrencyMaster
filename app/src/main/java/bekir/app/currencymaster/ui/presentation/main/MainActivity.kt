package bekir.app.currencymaster.ui.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

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

}