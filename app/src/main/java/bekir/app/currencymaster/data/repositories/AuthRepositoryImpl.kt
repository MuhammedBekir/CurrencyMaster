package bekir.app.currencymaster.data.repositories

import bekir.app.currencymaster.data.source.models.User
import bekir.app.currencymaster.data.utils.AuthState
import bekir.app.currencymaster.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//هون منعمل العمليات اللي بتصير عن طريق الفايربيس
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    //هون عملية انشاء حسابات
    override suspend fun signUp(user: User): AuthState {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            AuthState.Success
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Sign up failed")
        }
    }

    //هون عملية تسجيل الدخول
    override suspend fun login(email: String, password: String): AuthState {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthState.Success
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Login failed")
        }
    }

}
