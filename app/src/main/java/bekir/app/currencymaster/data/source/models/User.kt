package bekir.app.currencymaster.data.source.models

data class User(val fullName: String, val email: String, val password: String)
sealed class AuthState {
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}