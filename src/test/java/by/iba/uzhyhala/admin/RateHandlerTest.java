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

import java.io.IOException;

import static by.iba.uzhyhala.util.VariablesUtil.LOT;
import static by.iba.uzhyhala.util.VariablesUtil.RATE_PLUS;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class RateHandlerTest {
    private static final String UUID = "27a68e9a-6927-4503-822b-3186af513e89";

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

        when(mockHttpServletRequest.getParameter("uuid_")).thenReturn(UUID);
        when(mockHttpServletRequest.getParameter("goal")).thenReturn(LOT);
        when(mockHttpServletRequest.getParameter("type")).thenReturn(RATE_PLUS);
    }

    @Test
    public void test() throws IOException {
        new RateHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
