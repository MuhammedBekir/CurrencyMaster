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

/**
 * Fragment to display gold-related information.
 */
class GoldFragment : Fragment(R.layout.fragment_gold) {
    private var _binding: FragmentGoldBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    // Adapter for the top gold-related information
    private val topScreenAdapter by lazy {
        GroupieAdapter()
    }

    // Section for the top gold-related information
    private val topScreenSection by lazy {
        Section()
    }

    // Adapter for the gold values list
    private val goldAdapter by lazy {
        GroupieAdapter()
    }

    // Section for the gold values
    private val goldSection by lazy {
        Section()
    }

    /**
     * Sets up and returns the user interface for the screen.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoldBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the interface is created, managing interactions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTopCurrenciesRecyclerView()
        setupGoldValuesRecyclerView()
    }

    /**
     * Cleans up resources when the view is destroyed to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Sets up the gold values RecyclerView.
     */
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

    /**
     * Sets up the top gold-related information RecyclerView.
     */
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
