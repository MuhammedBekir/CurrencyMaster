package bekir.app.currencymaster.ui.presentation.main.items

import android.view.View
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.ItemTopCurrenciesBinding
import com.xwray.groupie.viewbinding.BindableItem
/**
 * Data item representing a top currency on the main screen.
 */
class TopCurrenciesItem(private val currencyCode: String, private val currencyValue: Double) :
    BindableItem<ItemTopCurrenciesBinding>() {

    /**
     * Binds the data to the view.
     */
    override fun bind(viewBinding: ItemTopCurrenciesBinding, position: Int) {
        viewBinding.currencyTxtItem.text =
            viewBinding.root.context.getString(R.string.currency_to_usd, currencyCode)
        viewBinding.currencyValueItem.text = currencyValue.toString()
    }

    /**
     * Returns the layout resource ID.
     */
    override fun getLayout(): Int {
        return R.layout.item_top_currencies
    }

    /**
     * Initializes the view binding.
     */
    override fun initializeViewBinding(view: View): ItemTopCurrenciesBinding {
        return ItemTopCurrenciesBinding.bind(view)
    }
}
