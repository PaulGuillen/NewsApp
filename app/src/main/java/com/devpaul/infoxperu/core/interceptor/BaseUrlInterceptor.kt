import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class BaseUrlInterceptor @Inject constructor(
    @Named("BaseUrlPeru") private val baseUrlPeru: HttpUrl,
    @Named("BaseUrlNews") private val baseUrlNews: HttpUrl
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalUrl = request.url

        // Verifica si la URL contiene "v2"
        val newUrl = if (originalUrl.encodedPath.contains("v2")) {
            baseUrlNews.newBuilder()
                .encodedPath(originalUrl.encodedPath)
                .query(originalUrl.query)
                .build()
        } else {
            baseUrlPeru.newBuilder()
                .encodedPath(originalUrl.encodedPath)
                .query(originalUrl.query)
                .build()
        }

        // Crea una nueva solicitud con la URL modificada
        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}
