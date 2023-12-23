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

    var usdToTlExchangeRate: Double = 0.0

    init {
        _isUserSignedIn.value = firebaseAuth.currentUser != null
//        getCurrencies()
    }


    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyResponse = repository.getCurrencies()
            usdToTlExchangeRate = currencyResponse.conversionRates.TRY
            val currencyItems = prepareCurrencyItems(currencyResponse)
            val topCurrenciesItem = prepareTopCurrencies(currencyResponse)
            val goldItems = prepareGoldItems(currencyResponse)
            _topCurrenciesStateFLow.emit(topCurrenciesItem)
            _currenciesStateFLow.emit(currencyItems)
            _goldStateFLow.emit(goldItems)

        }

    }

    //currencies items
    private fun prepareCurrencyItems(currencyResponse: CurrencyResponse): List<MainScreenItem> {
        return currencyResponse.conversionRates.javaClass.declaredFields.map { field ->
            field.isAccessible = true
            MainScreenItem(
                "USD",
                field.name,
                field.get(currencyResponse.conversionRates) as Double,
                currencyResponse.timeLastUpdateUnix.toLong()
            )
        }
    }


    //top currencies items
    private fun prepareTopCurrencies(currencyResponse: CurrencyResponse): List<TopCurrenciesItem> {
        val currencyRates = currencyResponse.conversionRates

        return listOf("TRY", "EUR", "AED", "EGP", "QAR").map { currencyCode ->
            val field = currencyRates.javaClass.getDeclaredField(currencyCode)
            field.isAccessible = true
            val rate = field.get(currencyRates) as Double
            TopCurrenciesItem(currencyCode, rate)
        }
    }

    //gold items
    private fun prepareGoldItems(currencyResponse: CurrencyResponse): List<MainScreenItem> {
        val currencyRates = currencyResponse.conversionRates

        val goldItemMappings = listOf(
            Triple("GOLD", "TRY", currencyRates.TRY),
            Triple("XAU", "USD", currencyRates.EUR),
            Triple("USD", "KG", currencyRates.AED),
            Triple("EUR", "KG", currencyRates.GBP),
            Triple("GRAM", "GOLD", currencyRates.EGP),
            Triple("GOLD", "SILVER", currencyRates.AUD),
            Triple("YENI", "YARIM", currencyRates.SEK),
            Triple("ESKI", "YARIM", currencyRates.JPY),
            Triple("YENI", "TAM", currencyRates.CAD),
            Triple("YENI", "ATA", currencyRates.QAR),
            Triple("YENI", "ATA5", currencyRates.SHP),
            Triple("SILVER", "TL", currencyRates.XDR),
            Triple("PLATINUM", "USD", currencyRates.PKR)
        )

        return goldItemMappings.map { (from, to, rate) ->
            MainScreenItem(from, to, rate, currencyResponse.timeLastUpdateUnix.toLong())
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
