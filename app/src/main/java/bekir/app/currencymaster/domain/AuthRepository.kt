package bekir.app.currencymaster.domain

import bekir.app.currencymaster.data.source.models.User
import bekir.app.currencymaster.data.utils.AuthState

interface AuthRepository {
    suspend fun signUp(user: User): AuthState
    suspend fun login(email: String, password: String): AuthState
}
