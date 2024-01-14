package bekir.app.currencymaster.domain

import bekir.app.currencymaster.data.source.models.User
import bekir.app.currencymaster.data.utils.AuthState

//هون الانترفيس اللي منحط فيها الدوال تبع تسجيل الدخول وانشاء الحسابات
interface AuthRepository {
    suspend fun signUp(user: User): AuthState
    suspend fun login(email: String, password: String): AuthState
}
