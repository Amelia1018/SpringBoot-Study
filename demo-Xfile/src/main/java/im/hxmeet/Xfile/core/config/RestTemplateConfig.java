package im.hxmeet.Xfile.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    /**
     * OneDrive 请求 RestTemplate.
     * 获取 header 中的 storageId 来判断到底是哪个存储源 ID, 在请求头中添加 Bearer: Authorization {token} 信息, 用于 API 认证.
     */
    @Bean
    public RestTemplate oneDriveRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new  XFileOkHttp3ClientHttpRequestFactory());
        return restTemplate;
    }
}
//这行代码设置了RestTemplate的请求工厂。XFileOkHttp3ClientHttpRequestFactory是一个自定义的请求工厂，
// 它可能是基于OkHttp3客户端实现的，用于处理HTTP请求。这个自定义工厂允许RestTemplate使用OkHttp3的特性，比如异步请求、连接池管理等。