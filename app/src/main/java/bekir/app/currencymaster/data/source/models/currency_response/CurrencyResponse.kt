package bekir.app.currencymaster.data.source.models.currency_response

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base_code")
    val baseCode: String = "USD",
    @SerializedName("conversion_rates")
    val conversionRates: ConversionRates = ConversionRates(),
    val documentation: String = "",
    val result: String = "",
    @SerializedName("terms_of_use")
    val termsOfUse: String = "",
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Int = 0,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String = "",
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Int = 0,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String = ""
)