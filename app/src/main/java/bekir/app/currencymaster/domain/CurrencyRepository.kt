package bekir.app.currencymaster.domain

import bekir.app.currencymaster.data.source.models.currency_response.CurrencyResponse

interface CurrencyRepository {

    suspend fun getCurrencies(): CurrencyResponse
}