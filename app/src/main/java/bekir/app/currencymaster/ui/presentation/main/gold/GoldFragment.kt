package bekir.app.currencymaster.ui.presentation.main.gold

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import bekir.app.currencymaster.R
import bekir.app.currencymaster.databinding.FragmentGoldBinding
import bekir.app.currencymaster.ui.presentation.main.MainViewModel
import bekir.app.currencymaster.ui.utils.FragmentExtensions.collectLatestWhenStarted
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class GoldFragment : Fragment(R.layout.fragment_gold) {
    private var _binding: FragmentGoldBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val topScreenAdapter by lazy {
        GroupieAdapter()
    }
    private val topScreenSection by lazy {
        Section()
    }
    private val goldAdapter by lazy {
        GroupieAdapter()
    }
    private val goldSection by lazy {
        Section()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTopCurrenciesRecyclerView()
        setupGoldValuesRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupGoldValuesRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.goldValuesRc.layoutManager = layoutManager
        binding.goldValuesRc.adapter = goldAdapter
        goldAdapter.updateAsync(listOf(goldSection))
        collectLatestWhenStarted(viewModel.goldStateFLow) {
            goldAdapter.update(it)
        }

    }

    private fun setupTopCurrenciesRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.topCurrenciesRc.layoutManager = layoutManager
        binding.topCurrenciesRc.adapter = topScreenAdapter
        topScreenAdapter.updateAsync(listOf(topScreenSection))
        collectLatestWhenStarted(viewModel.topCurrenciesStateFLow) {
            topScreenSection.update(it)
        }

    }
}