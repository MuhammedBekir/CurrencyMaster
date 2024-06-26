package bekir.app.currencymaster.ui.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bekir.app.currencymaster.data.source.models.User
import bekir.app.currencymaster.data.utils.AuthState
import bekir.app.currencymaster.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//هون الviewmodel اللي شغلتو يربط بين ui وبين الrepository
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState?>(null)
    val authState = _authState.asStateFlow()

    fun signUp(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = AuthState.Loading
            _authState.value = authRepository.signUp(user)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = AuthState.Loading
            _authState.value = authRepository.login(email, password)
        }
    }
}
