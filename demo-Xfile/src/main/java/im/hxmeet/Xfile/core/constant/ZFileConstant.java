package im.hxmeet.Xfile.core.constant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * ZFile 常量
 */
@Configuration
public class ZFileConstant {

    /**PATH_SEPARATOR_CHAR 和 PATH_SEPARATOR: 定义了一个路径分隔符，通常用于构建文件路径。
     * PATH_SEPARATOR_CHAR 是字符类型，而 PATH_SEPARATOR 是字符串类型。两者均表示相同的值 '/'。*/

    public static final Character PATH_SEPARATOR_CHAR = '/';
    public static final String PATH_SEPARATOR = "/";


    /**
     * 最大支持文本文件大小为 ? KB 的文件内容.
     */

    //静态公共变量，表示支持的文本文件的最大大小，单位是 KB。默认值设置为 100 KB。
    public static Long TEXT_MAX_FILE_SIZE_KB = 100L;

    /**
     * Autowired 注解使得方法setTextMaxFileSizeKB在 Spring 管理的 Bean 中被自动调用。
     * required = false 的意思是如果没有合适的 Bean 存在，该方法可以安全地不被调用。*/
    @Autowired(required = false)
    /** @Value("512") Long maxFileSizeKB 注解用于指示从 Spring 的配置文件中读取值。在此示例中，它试图读取配置项 zfile.preview.text.maxFileSizeKb 的值，
     * 并将其赋给参数 maxFileSizeKb。*/
    public void setTextMaxFileSizeKB(@Value("512") Long maxFileSizeKB) {
        ZFileConstant.TEXT_MAX_FILE_SIZE_KB = maxFileSizeKB;
    }
}


