package im.hxmeet.Xfile.core.config;

import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * XFile Web 相关配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 添加自定义枚举格式化器.
     * @see StorageTypeEnum
     */

    /*该方法用于注册一个自定义的枚举格式化器。它使用了 FormatterRegistry 来添加一个新的转换器工厂 StringToEnumConverterFactory，
    这使得在请求中将字符串转换为指定枚举类型变得更加容易。这在处理 REST API 时非常有用，可以直接将 URL 中的字符串参数转换为相应的枚举类型。*/
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());

    }

    /**
     * 支持 url 中传入 <>[\]^`{|} 这些特殊字符.
     */
    /**功能: 这个方法创建了一个 TomcatServletWebServerFactory 的 Bean，并自定义了 Tomcat 的连接器。
    特殊字符支持: 通过设置 relaxedPathChars 和 relaxedQueryChars 属性，允许在 URL 中使用一些特殊字符，
    这在某些情况下，如需要传递特定的参数时，可以避免 URL 编码问题。*/
    @Bean
    public ServletWebServerFactory WebServerFactory() {
        TomcatServletWebServerFactory webServerFactory = new TomcatServletWebServerFactory();

        //添加对URL中特殊符号的支持
        webServerFactory.addConnectorCustomizers(connector -> {
            connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}%[]");
            connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}%[]");
        });
        return webServerFactory;
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
    /**
     * 这个方法是一个自定义的 Web 服务器工厂定制器，用于设置错误页面。*/
        return factory -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            ErrorPage error200Page = new ErrorPage(HttpStatus.OK, "/index.html");
            Set<ErrorPage> errorPages = new HashSet<>();
            errorPages.add(error404Page);;
            errorPages.add(error200Page);
            factory.setAddress((InetAddress) errorPages);
        };

    }
}
