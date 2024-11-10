package im.hxmeet.Xfile.core.config;

import cn.hutool.core.util.StrUtil;
import lombok.val;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Locale;
//注解解释
/*
@DependsOn
这个注解表示 FlywayDbInitializer 类依赖于名为 myBatisPlusConfig 的 bean。
在 Spring 容器启动时，会确保先初始化 myBatisPlusConfig，然后再初始化 FlywayDbInitializer。

@Configuration:
这个注解表明该类是一个 Spring 配置类，Spring 会自动检测并注册其中的 bean。

@Resource:
这个注解用于依赖注入，表示将 dataSource 属性注入一个 DataSource 类型的 bean。DataSource 是用于获取数据库连接的接口。

@PostConstruct:
这个注解表示 migrateFlyway() 方法将在 bean 初始化后自动调用。它通常用于执行一些初始化操作，比如数据库迁移。
*/
@DependsOn("myBatisPlusConfig")
@Configuration
public class FlywayDbInitializer {
    public static final String[] SUPPORT_DB_TYPE = new String[]{"mysql", "sqlite"};

    @Resource
    private DataSource dataSource;
    /**
     * 启动时根据当前数据库类型执行数据库初始化
     */
    @PostConstruct
    public void migrateFlyway() {
        try{

            //通过 dataSource 获取数据库连接，并获取数据库的产品名称（如 MySQL、SQLite 等）。
            String databaseProductName = dataSource.getConnection().getMetaData().getDatabaseProductName();
            //将数据库产品名称转换为小写，以便后续比较。
            String dbType = databaseProductName.toLowerCase(Locale.ROOT);

            // 检查当前数据库类型是否支持
            if (!StrUtil.equalsAnyIgnoreCase(dbType, SUPPORT_DB_TYPE)) {

                throw new RuntimeException("不支持的数据库类型 [" + dbType + "]");
            }

            //使用 Flyway 配置数据库迁移。dataSource 用于连接数据库，outOfOrder(true) 允许迁移脚本的执行顺序不是严格的顺序，
            // locations("db/migration-" + dbType) 指定了迁移脚本的位置，路径取决于数据库类型。
            Flyway load = Flyway.configure().dataSource(dataSource).outOfOrder(true).locations("db/migration-" + dbType).load();
            //执行数据库迁移，应用所有待执行的迁移脚本。
            load.migrate();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
