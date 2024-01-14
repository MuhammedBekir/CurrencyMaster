package bekir.app.currencymaster.ui.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import bekir.app.currencymaster.R
import bekir.app.currencymaster.data.source.models.User
import bekir.app.currencymaster.data.utils.AuthState
import bekir.app.currencymaster.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment(R.layout.fragment_signup) {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()


     // يقوم بإعداد وإرجاع واجهة المستخدم للشاشة.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createAccountLayout.setOnClickListener {
            val fullName = binding.fullNameEt.text.toString()
            val email = binding.emailIt.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.confirmPasswordEt.text.toString()

            if (password == confirmPassword) {
                viewModel.signUp(User(fullName, email, password))

            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            //هون منشوف شو النتائج بعد ما كبست على تسجيل الدخول، اذا نجح تسجيل الدخول او لا
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authState.collectLatest { state ->
                    when (state) {
                        is AuthState.Loading -> showLoading()
                        is AuthState.Success -> showSuccess()
                        is AuthState.Error -> showError(state.message)
                        else -> {}
                    }
                }
            }
        }
        binding.alreadyHaveAnAccountTxt.setOnClickListener {
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
        }
    }

    //هي الدالة بتورجي رسالة انو الحساب عم ينعمل
    private fun showLoading() {
        Toast.makeText(requireContext(), "processing", Toast.LENGTH_SHORT).show()
    }

    private fun showSuccess() {
        findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())

    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}