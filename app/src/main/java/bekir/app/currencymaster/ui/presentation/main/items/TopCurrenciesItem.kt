package bekir.app.currencymaster.ui.presentation.main.items

import android.view.View
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.ItemTopCurrenciesBinding
import com.xwray.groupie.viewbinding.BindableItem

class TopCurrenciesItem(private val currencyCode: String, private val currencyValue: Double) :
    BindableItem<ItemTopCurrenciesBinding>() {
    override fun bind(viewBinding: ItemTopCurrenciesBinding, position: Int) {
        viewBinding.currencyTxtItem.text =
            viewBinding.root.context.getString(R.string.currency_to_usd, currencyCode)
        viewBinding.currencyValueItem.text = currencyValue.toString()
    }

    override fun getLayout(): Int {
        return R.layout.item_top_currencies
    }

    override fun initializeViewBinding(view: View): ItemTopCurrenciesBinding {
        return ItemTopCurrenciesBinding.bind(view)
    }

}