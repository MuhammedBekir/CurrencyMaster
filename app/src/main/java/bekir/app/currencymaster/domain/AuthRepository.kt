package bekir.app.currencymaster.domain

import bekir.app.currencymaster.data.source.models.AuthState
import bekir.app.currencymaster.data.source.models.User

interface AuthRepository {
    suspend fun signUp(user: User): AuthState
    suspend fun login(email: String, password: String): AuthState
}
