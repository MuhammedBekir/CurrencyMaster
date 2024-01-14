package bekir.app.currencymaster.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Extension functions for Fragment related operations.
 */
object FragmentExtensions {

    /**
     * Collects the latest values from a Flow when the Fragment is in the started state.
     */
    fun <T> Fragment.collectLatestWhenStarted(flow: Flow<T>, block: suspend (value: T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            flow.flowWithLifecycle(lifecycle).collectLatest(block)
        }
    }
}
