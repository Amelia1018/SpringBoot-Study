package im.hxmeet.Xfile.core.httpclient;

import okhttp3.OkHttpClient;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;

/**
 * 自建 OkHTTP3 客户端工厂类, 增加日志输出拦截器.
 */
public class ZFileOkHttp3ClientHttpRequestFactory extends OkHttp3ClientHttpRequestFactory {

    public ZFileOkHttp3ClientHttpRequestFactory() {
        // 使用 OkHttp3 作为底层请求库, 并设置重试机制和日志拦截器
        super(new OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEBUG)
                        .setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .build());
    }

}
