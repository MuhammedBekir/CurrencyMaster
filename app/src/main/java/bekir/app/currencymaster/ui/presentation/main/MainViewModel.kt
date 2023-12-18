package bekir.app.currencymaster.ui.presentation.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bekir.app.currencymaster.data.source.models.currency_response.ConversionRates
import bekir.app.currencymaster.data.source.models.currency_response.CurrencyResponse
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
//        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyResponse = repository.getCurrencies()

            val currencyItems = prepareCurrencyItems(currencyResponse)
            val topCurrenciesItem = prepareTopCurrencyItems(currencyResponse)

            _topCurrenciesStateFLow.emit(topCurrenciesItem)
            _currenciesStateFLow.emit(currencyItems)


        }

    }

    private fun prepareCurrencyItems(currencyResponse: CurrencyResponse): List<MainScreenItem> {
        return listOf(
            MainScreenItem(
                "TRY",
                currencyResponse.conversionRates.TRY,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "EUR",
                currencyResponse.conversionRates.EUR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "AED",
                currencyResponse.conversionRates.AED,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "GBP",
                currencyResponse.conversionRates.GBP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "EGP",
                currencyResponse.conversionRates.EGP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "AUD",
                currencyResponse.conversionRates.AUD,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "SEK",
                currencyResponse.conversionRates.SEK,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "JPY",
                currencyResponse.conversionRates.JPY,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "CAD",
                currencyResponse.conversionRates.CAD,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "QAR",
                currencyResponse.conversionRates.QAR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "SHP",
                currencyResponse.conversionRates.SHP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "XDR",
                currencyResponse.conversionRates.XDR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "MRU",
                currencyResponse.conversionRates.MRU,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "OMR",
                currencyResponse.conversionRates.OMR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "GIP",
                currencyResponse.conversionRates.GIP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "CHF",
                currencyResponse.conversionRates.CHF,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "COP",
                currencyResponse.conversionRates.COP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "BDT",
                currencyResponse.conversionRates.BDT,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "PKR",
                currencyResponse.conversionRates.PKR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "ZWL",
                currencyResponse.conversionRates.ZWL,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
        )
    }

    private fun prepareTopCurrencyItems(currencyResponse: CurrencyResponse): List<TopCurrenciesItem> {
        return listOf(
            TopCurrenciesItem("TRY", currencyResponse.conversionRates.TRY),
            TopCurrenciesItem("EUR", currencyResponse.conversionRates.EUR),
            TopCurrenciesItem("AED", currencyResponse.conversionRates.AED),
            TopCurrenciesItem("EGP", currencyResponse.conversionRates.EGP),
            TopCurrenciesItem("QAR", currencyResponse.conversionRates.QAR)
        )
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
