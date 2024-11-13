package im.hxmeet.Xfile.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
/**这个注解来自 Lombok 库，它自动为类生成常用的方法，如 getters、setters、toString、equals 和 hashCode 等。这简化了代码，使得我们不需要手动编写这些方法。*/
@EnableConfigurationProperties
@Component
@ConfigurationProperties(prefix = "zfile")
public class ZFileProperties {
    private Boolean debug;

}
