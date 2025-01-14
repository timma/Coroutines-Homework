package otus.homework.coroutines

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    companion object{
        val FIFTEEN : Long = 5L
    }

    private val okHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .connectTimeout(FIFTEEN, TimeUnit.SECONDS)
            .callTimeout(FIFTEEN, TimeUnit.SECONDS)
            .readTimeout(FIFTEEN, TimeUnit.SECONDS)
            .writeTimeout(FIFTEEN, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofitCats by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://cat-fact.herokuapp.com/facts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val serviceCats by lazy { retrofitCats.create(CatsService::class.java) }

    private val retrofitPhoto by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://aws.random.cat/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val servicePhoto by lazy { retrofitPhoto.create(PhotoService::class.java) }
}