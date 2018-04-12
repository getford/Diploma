package by.iba.uzhyhala.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CommonUtilTest {

    @Mock
    private Session session = HibernateUtil.getSessionFactory().openSession();

    @Test
    public void loginOrEmail() {
        String cred = "test@test.test";
        String expected = "email";
        Assert.assertEquals(expected, CommonUtil.loginOrEmail(cred));
    }

    @Test
    public void getIdUserByLoginEmail() {
        String cred = "test@test.test";
        String type = "email";
        int expected = 2;
        Assert.assertEquals(expected, CommonUtil.getIdUserByLoginEmail(session, cred, type));

        SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
        Session session = Mockito.mock(Session.class);
        Query query = Mockito.mock(Query.class);
        }
}
