package bekir.app.currencymaster.ui.presentation.main.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.FragmentCurrencyBinding
import bekir.app.currencymaster.ui.presentation.main.MainViewModel
import bekir.app.currencymaster.ui.utils.FragmentExtensions.collectLatestWhenStarted
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class CurrencyFragment : Fragment(R.layout.fragment_currency) {
    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()


    private val topScreenAdapter by lazy {
        GroupieAdapter()
    }
    private val adapter by lazy {
        GroupieAdapter()
    }
    private val mainScreenSection by lazy {
        Section()
    }

    private val topScreenSection by lazy {
        Section()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMainScreenRecyclerView()
        setupTopCurrenciesRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMainScreenRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.currenciesValuesRc.layoutManager = layoutManager
        binding.currenciesValuesRc.adapter = adapter
        adapter.updateAsync(listOf(mainScreenSection))
        collectLatestWhenStarted(viewModel.currenciesStateFLow) {
            mainScreenSection.update(it)
        }

    }

    private fun setupTopCurrenciesRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.topCurrenciesRc.layoutManager = layoutManager
        binding.topCurrenciesRc.adapter = topScreenAdapter
        topScreenAdapter.updateAsync(listOf(topScreenSection))
        collectLatestWhenStarted(viewModel.topCurrenciesStateFLow) {
            topScreenSection.update(it)
        }

    }
}