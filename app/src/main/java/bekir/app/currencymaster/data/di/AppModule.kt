package bekir.app.currencymaster.data.di

import bekir.app.currencymaster.data.repositories.AuthRepositoryImpl
import bekir.app.currencymaster.data.repositories.CurrencyRepositoryImpl
import bekir.app.currencymaster.domain.AuthRepository
import bekir.app.currencymaster.domain.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    //هون منعمل DI للكلاس اللي هو AuthRepository
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository


    @Binds
    @Singleton
    //هون منعمل DI للكلاس اللي هو CurrencyRepository

    abstract fun bindCurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}