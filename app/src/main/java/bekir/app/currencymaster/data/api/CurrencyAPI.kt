package bekir.app.currencymaster.data.api

import bekir.app.currencymaster.data.source.models.currency_response.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyAPI {

    @GET("latest/USD")
    suspend fun getCurrencies(
    ): Response<CurrencyResponse>
}