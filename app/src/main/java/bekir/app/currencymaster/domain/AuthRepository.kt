package bekir.app.currencymaster.domain

import bekir.app.currencymaster.data.source.AuthState
import bekir.app.currencymaster.data.source.User

interface AuthRepository {
    suspend fun signUp(user: User): AuthState
    suspend fun login(email: String, password: String): AuthState
}
