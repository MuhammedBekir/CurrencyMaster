package bekir.app.currencymaster.ui.presentation.main.items

import android.view.View
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.CurrencyExchangeItemBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
/**
 * Data item representing a currency exchange rate on the main screen.
 */
class MainScreenItem(
    private val baseCode: String,
    private val currencyCode: String,
    private val currencyValue: Double,
    private val date: Long
) :
    BindableItem<CurrencyExchangeItemBinding>() {

    /**
     * Binds the data to the view.
     */
    override fun bind(viewBinding: CurrencyExchangeItemBinding, position: Int) {
        viewBinding.lastUpdateDateTxt.text = getFormattedDate()
        viewBinding.fromToText.text =
            viewBinding.root.context.getString(R.string.fom_to, baseCode, currencyCode)
        viewBinding.buyValueTxt.text = currencyValue.toString()
        viewBinding.sellValueTxt.text = getSellValue()
    }

    /**
     * Returns the layout resource ID.
     */
    override fun getLayout(): Int {
        return R.layout.currency_exchange_item
    }

    /**
     * Initializes the view binding.
     */
    override fun initializeViewBinding(view: View): CurrencyExchangeItemBinding {
        return CurrencyExchangeItemBinding.bind(view)
    }

    /**
     * Formats the date to a readable string.
     */
    private fun getFormattedDate(): String {
        val date = Date(date * 1000)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

    /**
     * Alters the sell value for display purposes.
     */
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
