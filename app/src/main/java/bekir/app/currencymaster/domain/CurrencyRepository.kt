package bekir.app.currencymaster.domain

import bekir.app.currencymaster.data.source.models.currency_response.CurrencyResponse

//هون الانترقيس ومنحط فيها الدالة اللي منجيب عن طريقها المعلومات
interface CurrencyRepository {

    suspend fun getCurrencies(): CurrencyResponse
}