package jettytest.dbservices;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.hibernate.Session;


public class TableService {

    private MessageDigest messageDigest;



    private final Session session;

    public TableService(Session session) {
       // this.currentClazz = clazz;
        this.session = session;
    }


    private String encryptedPassword( String password){
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

    int persist(User entity){
        session.beginTransaction();
        int result =  (Integer) session.save(entity);
        Balance balance = entity.getBalance();
        balance.setUser(entity);
        session.update(balance);
        session.getTransaction().commit();
        return result;
    }
    void update(User entity){
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    User findById(int id){
        session.beginTransaction();
        User result =  session.get(User.class, id);
        session.getTransaction().commit();
        return result;
    }

    void delete(User entity){
        session.beginTransaction();
        session.delete(entity);
        session.delete(entity.getBalance());
        session.getTransaction().commit();
    }

    List<User> getAll(){
        session.beginTransaction();
        List<User> result = null;
        String hql = "from User";
        result = session.createQuery(hql, User.class).list();
        session.getTransaction().commit();
        return result;
    }
}
