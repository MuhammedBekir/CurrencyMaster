package bekir.app.currencymaster.ui.presentation.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bekir.app.currencymaster.data.source.models.currency_response.CurrencyResponse
import bekir.app.currencymaster.domain.CurrencyRepository
import bekir.app.currencymaster.ui.presentation.main.convert.ConverterFragment
import bekir.app.currencymaster.ui.presentation.main.currency.CurrencyFragment
import bekir.app.currencymaster.ui.presentation.main.gold.GoldFragment
import bekir.app.currencymaster.ui.presentation.main.items.MainScreenItem
import bekir.app.currencymaster.ui.presentation.main.items.TopCurrenciesItem
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.xwray.groupie.Group


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _currentScreen = MutableStateFlow<Screens>(Screens.CurrencyFragment)
    val currentScreen = _currentScreen.asStateFlow()

    private val _currenciesStateFLow = MutableStateFlow<List<Group>>(listOf())
    val currenciesStateFLow = _currenciesStateFLow.asStateFlow()

    private val _topCurrenciesStateFLow = MutableStateFlow<List<Group>>(listOf())
    val topCurrenciesStateFLow = _topCurrenciesStateFLow.asStateFlow()


    private val _goldStateFLow = MutableStateFlow<List<Group>>(listOf())
    val goldStateFLow = _goldStateFLow.asStateFlow()

    private val _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn = _isUserSignedIn.asStateFlow()

    init {
        _isUserSignedIn.value = firebaseAuth.currentUser != null
//        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyResponse = repository.getCurrencies()

            val currencyItems = prepareCurrencyItems(currencyResponse)
            val topCurrenciesItem = prepareTopCurrencyItems(currencyResponse)
            val goldItems = prepareGoldItems(currencyResponse)
            _topCurrenciesStateFLow.emit(topCurrenciesItem)
            _currenciesStateFLow.emit(currencyItems)
            _goldStateFLow.emit(goldItems)

        }

    }

    //currencies items
    private fun prepareCurrencyItems(currencyResponse: CurrencyResponse): List<MainScreenItem> {
        return listOf(
            MainScreenItem(
                "USD",
                "TRY",
                currencyResponse.conversionRates.TRY,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "EUR",
                currencyResponse.conversionRates.EUR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "AED",
                currencyResponse.conversionRates.AED,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "GBP",
                currencyResponse.conversionRates.GBP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "EGP",
                currencyResponse.conversionRates.EGP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "AUD",
                currencyResponse.conversionRates.AUD,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "SEK",
                currencyResponse.conversionRates.SEK,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "JPY",
                currencyResponse.conversionRates.JPY,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "CAD",
                currencyResponse.conversionRates.CAD,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "QAR",
                currencyResponse.conversionRates.QAR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "SHP",
                currencyResponse.conversionRates.SHP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "XDR",
                currencyResponse.conversionRates.XDR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "MRU",
                currencyResponse.conversionRates.MRU,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "OMR",
                currencyResponse.conversionRates.OMR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "GIP",
                currencyResponse.conversionRates.GIP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "CHF",
                currencyResponse.conversionRates.CHF,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "COP",
                currencyResponse.conversionRates.COP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "BDT",
                currencyResponse.conversionRates.BDT,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "PKR",
                currencyResponse.conversionRates.PKR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "ZWL",
                currencyResponse.conversionRates.ZWL,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
        )
    }

    //top currencies items
    private fun prepareTopCurrencyItems(currencyResponse: CurrencyResponse): List<TopCurrenciesItem> {
        return listOf(
            TopCurrenciesItem("TRY", currencyResponse.conversionRates.TRY),
            TopCurrenciesItem("EUR", currencyResponse.conversionRates.EUR),
            TopCurrenciesItem("AED", currencyResponse.conversionRates.AED),
            TopCurrenciesItem("EGP", currencyResponse.conversionRates.EGP),
            TopCurrenciesItem("QAR", currencyResponse.conversionRates.QAR)
        )
    }

    //gold items
    private fun prepareGoldItems(currencyResponse: CurrencyResponse): List<MainScreenItem> {
        return listOf(
            MainScreenItem(
                "GOLD",
                "TRY",
                currencyResponse.conversionRates.TRY,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "XAU",
                "USD",
                currencyResponse.conversionRates.EUR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "USD",
                "KG",
                currencyResponse.conversionRates.AED,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "EUR",
                "KG",
                currencyResponse.conversionRates.GBP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "GRAM",
                "GOLD",
                currencyResponse.conversionRates.EGP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "GOLD",
                "SILVER",
                currencyResponse.conversionRates.AUD,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "YENI",
                "YARIM",
                currencyResponse.conversionRates.SEK,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "ESKI",
                "YARIM",
                currencyResponse.conversionRates.JPY,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "YENI",
                "TAM",
                currencyResponse.conversionRates.CAD,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "YENI",
                "ATA",
                currencyResponse.conversionRates.QAR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "YENI",
                "ATA5",
                currencyResponse.conversionRates.SHP,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "SILVER",
                "TL",
                currencyResponse.conversionRates.XDR,
                currencyResponse.timeLastUpdateUnix.toLong()
            ),
            MainScreenItem(
                "PLATINUM",
                "USD",
                currencyResponse.conversionRates.PKR,
                currencyResponse.timeLastUpdateUnix.toLong()
            )
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
