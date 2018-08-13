package jettytest.dbservices;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableServiceTest {

    SessionFactory factory;

    @Before
    public void init() {
        factory = SessionFactoryKeeper.initH2Configuration();
    }
    @Test
    public void CascadeInteractionTest(){
        Session session = factory.openSession();

        TableService dbService = new TableService(session);
        Balance balance = new Balance();
        balance.setBalance(150.14);
        User user1 = new User();
        user1.setBalance(balance);
        user1.setPhoneNumber("89016548732");
        user1.setEncryptedPassword("145369");
        TableService service = new TableService(session);
        service.persist(user1);

        User resultUser = service.findById(1);
        session.close();
        Assert.assertEquals(resultUser, user1);
    }
}
