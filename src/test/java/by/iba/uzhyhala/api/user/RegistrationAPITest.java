package by.iba.uzhyhala.api.user;

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

import java.io.*;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class RegistrationAPITest {
    private static final String REQUEST_BODY = "{\n" +
            "  \"login\": \"test\",\n" +
            "  \"email\": \"test@test.test\",\n" +
            "  \"password\": \"test\"\n" +
            "}";

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
    public void init() throws Exception {
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();

        when(mockHttpServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(REQUEST_BODY)));
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
    }

    @Test
    public void test() throws IOException {
        new RegistrationAPI().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
