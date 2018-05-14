package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.util.VariablesUtil;
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

import javax.net.ssl.HttpsURLConnection;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class LotStatusTest {

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

    @Mock
    private HttpsURLConnection httpsURLConnection;

    @Before
    public void init() throws Exception {
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();

        when(mockHttpServletRequest.getParameter("uuid")).thenReturn("5a132e1d-10c5-486c-886b-756fb4b3a1f8");
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        URL url = mock(URL.class);
        whenNew(URL.class).withArguments(VariablesUtil.TEST_URL).thenReturn(url);
    }

    @Test
    public void test() {
        new LotStatus().doGet(mockHttpServletRequest, mockHttpServletResponse);
    }
}
