package bekir.app.currencymaster.ui.presentation.main.convert

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.FragmentConverterBinding
import bekir.app.currencymaster.databinding.FragmentGoldBinding
import bekir.app.currencymaster.ui.presentation.main.MainViewModel
import bekir.app.currencymaster.ui.presentation.main.currency.collectLatestWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!
    private var isTryDefault = true
    private var isUserTypingLeft = true
    private var isUserTypingRight = true
    private val viewModel: MainViewModel by activityViewModels()

    var exchangeRateBetweenTRYandUSD: Double = 0.0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchangeRateBetweenTRYandUSD = viewModel.usdToTlExchangeRate
        handleSwitchCurrenciesClick()
        handleExchangeRatesEditTexts()


    }

    private fun handleExchangeRatesEditTexts() {
        binding.leftEditTxt.apply {
            setOnClickListener {
                binding.leftPen.visibility = View.VISIBLE
                binding.rightPen.visibility = View.GONE
            }
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (isUserTypingLeft) {
                        // Convert the text to a double
                        val inputValue = s.toString().toDoubleOrNull() ?: 0.0
                        // Update the rightEditTxt with the converted value
                        isUserTypingRight = false
                        val convertedValue = inputValue * exchangeRateBetweenTRYandUSD
                        val formattedValue = "%.3f".format(convertedValue)
                        binding.rightEditTxt.setText(if (convertedValue != 0.0) formattedValue else "")
                        isUserTypingRight = true
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NONE) {
                    view?.let { activity?.hideKeyboard(it) }
                    true
                } else {
                    false
                }
            }
        }

        binding.rightEditTxt.apply {
            setOnClickListener {
                binding.rightPen.visibility = View.VISIBLE
                binding.leftPen.visibility = View.GONE
            }
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (isUserTypingRight) {
                        // Convert the text to a double
                        val inputValue = s.toString().toDoubleOrNull() ?: 0.0
                        // Update the leftEditTxt with the converted value
                        isUserTypingLeft = false
                        val convertedValue = inputValue / exchangeRateBetweenTRYandUSD
                        val formattedValue = "%.3f".format(convertedValue)
                        binding.leftEditTxt.setText(if (convertedValue != 0.0) formattedValue else "")
                        isUserTypingLeft = true
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NONE) {
                    view?.let { activity?.hideKeyboard(it) }
                    true
                } else {
                    false
                }
            }
        }
    }


    private fun handleSwitchCurrenciesClick() {
        binding.switchCurrencies.setOnClickListener {
            if (isTryDefault) {
                binding.tryTxt.text = getString(R.string.usd_us_dollar)
                binding.usdTxt.text = getString(R.string.try_turkish_lira)
                isTryDefault = false
            } else {
                binding.tryTxt.text = getString(R.string.try_turkish_lira)
                binding.usdTxt.text = getString(R.string.usd_us_dollar)
                isTryDefault = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}