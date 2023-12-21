package bekir.app.currencymaster.ui.presentation.main.items

import android.view.View
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.CurrencyExchangeItemBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainScreenItem(
    private val baseCode: String,
    private val currencyCode: String,
    private val currencyValue: Double,
    private val date: Long
) :
    BindableItem<CurrencyExchangeItemBinding>() {

    override fun bind(viewBinding: CurrencyExchangeItemBinding, position: Int) {

        viewBinding.lastUpdateDateTxt.text = getFormattedDate()
        viewBinding.fromToText.text = viewBinding.root.context.getString(R.string.base_to_currency, baseCode, currencyCode)
        viewBinding.buyValueTxt.text = currencyValue.toString()
        viewBinding.sellValueTxt.text = getSellValue()
    }

    override fun getLayout(): Int {
        return R.layout.currency_exchange_item
    }

    override fun initializeViewBinding(view: View): CurrencyExchangeItemBinding {
        return CurrencyExchangeItemBinding.bind(view)
    }

    private fun getFormattedDate(): String {
        val date = Date(date * 1000)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

    private fun getSellValue(): String {
        return currencyValue.toString().map {
            if (it.isDigit()) {
                ((it.toString().toInt() + 1) % 10).toString()
            } else {
                it.toString()
            }
        }.joinToString("")
    }
}