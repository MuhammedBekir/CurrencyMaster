package bekir.app.currencymaster.ui.presentation.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import bekir.app.currencymaster.data.source.AuthState
import bekir.app.currencymaster.data.source.User
import bekir.app.currencymaster.databinding.FragmentLoginBinding
import bekir.app.currencymaster.ui.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginLayout.setOnClickListener {
            val email = binding.emailIt.text.toString()
            val password = binding.passwordEt.text.toString()
            viewModel.login(email, password)
        }
        binding.signupTxt.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())

        }

        viewLifecycleOwner.lifecycleScope.launch {
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
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()

    }

    private fun showSuccess() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}