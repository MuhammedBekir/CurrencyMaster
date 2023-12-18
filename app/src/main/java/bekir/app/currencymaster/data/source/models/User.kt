package bekir.app.currencymaster.data.source.models

data class User(val fullName: String, val email: String, val password: String)
sealed class AuthState {
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}