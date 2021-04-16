package ong.ternchow.app;

import java.util.Properties;
import javax.sql.DataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.commons.dbcp2.BasicDataSource;

public class DatasourceFactory {

    static final String JDBC_USER = "sa";
    static final String JDBC_PASSWORD = "sa";
    static final String JDBC_URL = "jdbc:h2:~/atom2sql-db;INIT=RUNSCRIPT FROM 'classpath:init.sql'";

    public static DataSource getDatasource() {
        BasicDataSource ds = new BasicDataSource(); 
        ds.setInitialSize(3);
        ds.setUsername(JDBC_USER);
        ds.setPassword(JDBC_PASSWORD);
        ds.setUrl(JDBC_URL);
        return ds;
    }

    public static DataSource getAtomikosDatasource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName("H2-DATASOURCE");
        ds.setXaDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        Properties p = new Properties();
        p.setProperty("user", JDBC_USER);
        p.setProperty("password", JDBC_PASSWORD);
        p.setProperty("URL", JDBC_URL);
        ds.setXaProperties(p);
        ds.setPoolSize(3);
        return ds;

    }
}
