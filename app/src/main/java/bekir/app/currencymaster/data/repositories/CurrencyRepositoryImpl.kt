package bekir.app.currencymaster.data.repositories

import bekir.app.currencymaster.data.api.CurrencyAPI
import bekir.app.currencymaster.data.source.models.currency_response.CurrencyResponse
import bekir.app.currencymaster.domain.CurrencyRepository
import javax.inject.Inject

//هون منستعدي الدالة اللي منعمل عن طريقا طلب المعلومات من الموقع
class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyAPI

) : CurrencyRepository {
    //منعمل طلب للمعلومات بهي الدالة
    override suspend fun getCurrencies(): CurrencyResponse {
        val currenciesResponse = api.getCurrencies()
        return if (currenciesResponse.isSuccessful) {
            currenciesResponse.body() ?: CurrencyResponse()
        } else {
            CurrencyResponse()
        }
    }
}