package Helpers;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBHelper {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            //读取环境变量配置
            configuration.setProperty("hibernate.connection.url", System.getenv("MYSQL_URL"));
            configuration.setProperty("hibernate.connection.username", System.getenv("MYSQL_USER_NAME"));
            configuration.setProperty("hibernate.connection.password", System.getenv("MYSQL_USER_PASSWORD"));

            configuration.setProperty("hibernate.cache.use_second_level_cache", System.getenv("HIBERNATE_CACHE"));
            configuration.setProperty("hibernate.cache.use_query_cache", System.getenv("HIBERNATE_CACHE"));
            configuration.setProperty("hibernate.cache.region.factory_class", org.hibernate.cache.redis.hibernate52.SingletonRedisRegionFactory.class.getName());
            configuration.setProperty("hibernate.cache.region_prefix", "hibernate");
            configuration.setProperty("hibernate.cache.provider_configuration_file_resource_path", "hibernate-redis.properties");

            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * 获取数据库操作线程
     * @return
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
}