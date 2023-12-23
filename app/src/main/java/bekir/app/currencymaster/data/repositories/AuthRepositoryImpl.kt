package bekir.app.currencymaster.data.repositories

import bekir.app.currencymaster.data.source.models.User
import bekir.app.currencymaster.data.utils.AuthState
import bekir.app.currencymaster.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signUp(user: User): AuthState {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            AuthState.Success
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Sign up failed")
        }
    }

    override suspend fun login(email: String, password: String): AuthState {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthState.Success
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Login failed")
        }
    }

}
