package im.hxmeet.Xfile.core.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * mybatis-plus 配置类
 *
 * @author hxm
 */
@Slf4j
@Configuration
public class MyBatisPlusConfig {

    //@Resource: 这个注解从 Spring 容器中注入一个 DataSource 实例。DataSource 是用于连接数据库的对象。
    @Resource
    private DataSource dataSource;

    //注入配置文件中 spring.datasource.driver-class-name 的值，获取数据库驱动类的名称（例如 SQLite 的驱动类是 org.sqlite.JDBC）。
    @Value( "org.sqlite.JDBC")
    private String datasourceDriveClassName;

    //注入配置文件中 spring.datasource.url 的值，获取数据库连接的 URL。
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    /**
     * 如果是 sqlite 数据库，自动创建数据库文件所在目录
     */
    //这个方法在 bean 初始化后自动调用。在这里用于执行 SQLite 的特定初始化逻辑。
    @PostConstruct
    public void init() {
        if(StrUtil.equals(datasourceDriveClassName, "org.sqlite.JDBC")){
            //从 URL 中提取出数据库文件的具体路径，去掉了 JDBC 前缀。
            String path = datasourceUrl.replace("jdbc:sqlite:","");
            //获取文件的父目录路径。
            String folderPath = FileUtil.getParent(path,1);
            log.info("SQLite 数据库文件所在目录: [{}]", folderPath);
            if (!FileUtil.exist(folderPath)) {
                FileUtil.mkdir(folderPath);
                log.info("检测到 SQLite 数据库文件所在目录不存在, 已自动创建.");
            } else {
                log.info("检测到 SQLite 数据库文件所在目录已存在, 无需自动创建.");
            }

        }

    }

    /**
     * mybatis plus 分页插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() throws SQLException {
        //实例化 MyBatis-Plus 的拦截器。
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        String databaseProductName = dataSource.getConnection().getMetaData().getDatabaseProductName();
        DbType dbType = DbType.getDbType(databaseProductName);
        //为拦截器添加一个分页拦截器，用于支持 MyBatis-Plus 的分页功能。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(dbType));
        return interceptor;
    }


}
