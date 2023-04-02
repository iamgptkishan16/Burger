package brd.session;

import brd.dao.entity.MasterCustomer;
import brd.dao.entity.TemporaryCustomer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
    private static final SessionUtil instance = new SessionUtil();
    private final SessionFactory sessionFactory;

    public static SessionUtil getInstance() {
        return instance;
    }

    private SessionUtil() {
        Configuration cfg = new Configuration();
        cfg.configure("HibernateAssign.cfg.xml");
        cfg.addAnnotatedClass(TemporaryCustomer.class);
        cfg.addAnnotatedClass(MasterCustomer.class);
        sessionFactory = cfg.buildSessionFactory();
    }

    public static Session getSession() {
        return getInstance().sessionFactory.openSession();
    }
}