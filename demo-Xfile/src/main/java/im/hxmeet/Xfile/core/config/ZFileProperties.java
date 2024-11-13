package im.hxmeet.Xfile.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
/**这个注解来自 Lombok 库，它自动为类生成常用的方法，如 getters、setters、toString、equals 和 hashCode 等。这简化了代码，使得我们不需要手动编写这些方法。*/
@EnableConfigurationProperties
@Component
/**这个注解指定类将绑定到应用程序的配置文件（如 application.properties 或 application.yml）中以 zfile 为前缀的属性上。例如，如果有 zfile.debug=true，则该值将被自动映射到 debug 字段。*/
@ConfigurationProperties(prefix = "zfile")
public class ZFileProperties {
    private Boolean debug;

}
