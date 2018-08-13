package jettytest.dbservices;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryKeeper {
    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory initH2Configuration(){
        if (sessionFactory == null) {
            sessionFactory = configureH2Connection()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    private static Configuration configureH2Connection(){
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Balance.class)
                //.addClass(org.hibernate.auction.Bid.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "bang");
        configuration.setProperty("hibernate.connection.password", "bang");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        return configuration;
    }
}
