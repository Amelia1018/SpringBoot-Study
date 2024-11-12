package im.hxmeet.Xfile.core.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
//定义一个名为 MyDatabaseIdProvider 的公共类，继承自 DatabaseIdProvider 接口，表示该类用于提供数据库 ID。
public class MyDatabaseIdProvider implements DatabaseIdProvider {
    private static final String DATABASE_MYSQL = "MySQL";
    private static final String DATABASE_SQLITE = "SQLite";

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        Connection conn = dataSource.getConnection();
        String dbName = conn.getMetaData().getDatabaseProductName();
        //初始化一个字符串变量 dbAlias，用于存储对应的数据库 ID。
        String daAlias = "";
        switch (dbName) {
            case DATABASE_MYSQL:
                daAlias = "MYSQL";
                break;
            case DATABASE_SQLITE:
                daAlias = "SQLite";
                break;
            default:
                  break;

        }
        return daAlias;
    }
}
