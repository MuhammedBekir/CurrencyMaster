package bekir.app.currencymaster.data.di

import bekir.app.currencymaster.data.utils.Config.CURRENCY_BASE_URL
import bekir.app.currencymaster.data.api.CurrencyAPI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//هاد الكلاس المسؤول عند الdependency injection
object APIModule {


    @Singleton
    @Provides
    // هون عم نعمل dependency injection لل retrofit
    //الretrofit هي المكتبة اللي عم نستخدمها لنعمل الrequest للموقع اللي بيعطينا اسعار العملات
    fun provideRetrofitApi(
        retrofit: Retrofit
    ): CurrencyAPI = retrofit.create()


    @Singleton
    @Provides
    //كمان هون متل اللي فوقا، عم نعمل DI لل retrofit
    fun provideRetrofitCall(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CURRENCY_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    //هون عملنا DI لمكتبة فينا عن طريقها نحط timeout للوقت اللي بيسترغقو الapi ليجيب النتائج
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    //هون منعمل DI للفايربيس FirebaseAuth، منستخدم هي لنعمل حسابات جديدة
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    //هون منعمل DI للفايربيس FirebaseDatabase، كمان منستخدم هي لنعمل حسابات جديدة
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance().reference
}