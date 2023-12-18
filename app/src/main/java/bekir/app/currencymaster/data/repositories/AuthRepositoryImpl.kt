package bekir.app.currencymaster.data.repositories

import bekir.app.currencymaster.data.source.models.AuthState
import bekir.app.currencymaster.data.source.models.User
import bekir.app.currencymaster.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signUp(user: User): AuthState {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            AuthState.Success(user)
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Sign up failed")
        }
    }

    override suspend fun login(email: String, password: String): AuthState {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthState.Success(User("done", "done", "done"))
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Login failed")
        }
    }
}
