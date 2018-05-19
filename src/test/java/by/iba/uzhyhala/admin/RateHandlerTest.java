package by.iba.uzhyhala.admin;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import static by.iba.uzhyhala.util.VariablesUtil.LOT;
import static by.iba.uzhyhala.util.VariablesUtil.RATE_PLUS;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class RateHandlerTest {
    
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Configuration configuration;

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init() {
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();

        when(mockHttpServletRequest.getParameter("uuid_")).thenReturn("2ee7fa10-6c2a-4980-bd8e-be174c273d53");
        when(mockHttpServletRequest.getParameter("goal")).thenReturn(LOT);
        when(mockHttpServletRequest.getParameter("type")).thenReturn(RATE_PLUS);
    }

    @Test
    public void test() {
        new RateHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
