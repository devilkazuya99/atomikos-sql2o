package ong.ternchow.app;

import org.sql2o.Connection;
import java.util.List;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import com.atomikos.icatch.jta.UserTransactionManager;

import org.sql2o.Sql2o;
import lombok.extern.slf4j.Slf4j;
import ong.ternchow.app.bean.Fruit;

/**
 * Hello world!
 *
 */
@Slf4j
public class App {

    static Sql2o sql2o;
    static final String APPLE = "APPLE";
    static final String BANANA = "BANANA";
    static final String MANGO = "MANGO";

    public static void main(String[] args) throws Exception {

        UserTransactionManager utm = new UserTransactionManager();
        utm.setForceShutdown(true);
        utm.init();
        TransactionManager tm = utm;

        DataSource dataSource = DatasourceFactory.getAtomikosDatasource();
        // DataSource dataSource = DatasourceFactory.getDatasource();
        sql2o = new Sql2o(dataSource);

        try {
            tm.begin();
            howManyFruit(APPLE);
            putInSomeFruit(APPLE);
            tm.commit();
            tm.begin(); // need to start a new one after commit()
            showMeTheFruits(APPLE);
        } catch (Exception e) {
            tm.rollback();
            log.error("", e);
        } finally {
            utm.close();
        }
    }

    private static void howManyFruit(String fruitName) {
        String query = "select count(*) from " + fruitName;
        log.info(query);
        Connection con = sql2o.open(); // only allowed to open but not close.
        Integer count = con.createQuery(query).executeScalar(Integer.class);
        log.info("count = {}", count);
    }

    private static void putInSomeFruit(String fruitName) {
        String query = "INSERT INTO " + fruitName + "(name) VALUES(:name)";
        log.info(query);
        Connection con = sql2o.beginTransaction();
        for (int i = 1; i <= 5; i++) {
            con.createQuery(query).addParameter("name", fruitName + " " + i).executeUpdate();
        }
        // con.commit(); // can't commit here :(
    }

    private static void showMeTheFruits(String fruitName) {
        String query = "select * from " + fruitName;
        log.info(query);
        Connection con = sql2o.open();
        con.createQuery(query).executeAndFetch(Fruit.class).forEach(f -> {
            log.info("{}", f);
        });
    }

}
