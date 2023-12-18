package bekir.app.currencymaster.ui.presentation.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bekir.app.currencymaster.domain.CurrencyRepository
import bekir.app.currencymaster.ui.presentation.main.items.MainScreenItem
import bekir.app.currencymaster.ui.presentation.main.items.TopCurrenciesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.xwray.groupie.Group


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {
    private val _currentScreen = MutableStateFlow<Screens>(Screens.CurrencyFragment)
    val currentScreen = _currentScreen.asStateFlow()

    private val _currenciesStateFLow = MutableStateFlow<List<Group>>(listOf())
    val currenciesStateFLow = _currenciesStateFLow.asStateFlow()

    private val _topCurrenciesStateFLow = MutableStateFlow<List<Group>>(listOf())
    val topCurrenciesStateFLow = _topCurrenciesStateFLow.asStateFlow()


    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyRepository = repository.getCurrencies()
            val conversionRates = currencyRepository.conversionRates

            val currencyItems = conversionRates.let {
                listOf(
                    MainScreenItem("TRY", it.TRY, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("EUR", it.EUR, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("AED", it.AED, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("GBP", it.GBP, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("EGP", it.EGP, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("AUD", it.AUD, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("SEK", it.SEK, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("JPY", it.JPY, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("CAD", it.CAD, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("QAR", it.QAR, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("SHP", it.SHP, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("XDR", it.XDR, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("MRU", it.MRU, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("OMR", it.OMR, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("GIP", it.GIP, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("CHF", it.CHF, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("COP", it.COP, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("BDT", it.BDT, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("PKR", it.PKR, currencyRepository.timeLastUpdateUnix.toLong()),
                    MainScreenItem("ZWL", it.ZWL, currencyRepository.timeLastUpdateUnix.toLong()),
                )
            }
            val topCurrenciesItem = conversionRates.let {
                listOf(
                    TopCurrenciesItem("TRY", it.TRY),
                    TopCurrenciesItem("EUR", it.EUR),
                    TopCurrenciesItem("AED", it.AED),
                    TopCurrenciesItem("EGP", it.EGP),
                    TopCurrenciesItem("QAR", it.QAR)
                    )
            }
            _topCurrenciesStateFLow.emit(topCurrenciesItem)
            _currenciesStateFLow.emit(currencyItems)


        }

    }

    fun changeScreen(screen: Screens) {
        if (_currentScreen.value != screen)
            _currentScreen.value = screen
    }

}

sealed class Screens(val fragment: Fragment) {
    data object CurrencyFragment : Screens(CurrencyFragment())
    data object GoldFragment : Screens(GoldFragment())
    data object ConverterFragment : Screens(ConverterFragment())

}
