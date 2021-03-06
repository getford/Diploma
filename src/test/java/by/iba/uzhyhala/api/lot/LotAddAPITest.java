package by.iba.uzhyhala.api.lot;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

import static by.iba.uzhyhala.util.VariablesUtil.PARAMETER_API_KEY_NAME;
import static by.iba.uzhyhala.util.VariablesUtil.TEST_API_KEY_NAME;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@SuppressFBWarnings({"ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD"})
@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class LotAddAPITest {

    private static String REQUEST_BODY = "{\n" +
            "  \"uuid_user_seller\": \"87ff415e-b8ea-481b-964d-c23815e97cb5\",\n" +
            "  \"name\": \"Lot name\",\n" +
            "  \"information\": \"info lot\",\n" +
            "  \"cost\": \"1000\",\n" +
            "  \"blitz_cost\": \"10000\",\n" +
            "  \"step_cost\": \"500\",\n" +
            "  \"date_start\": \"2018-04-22\",\n" +
            "  \"time_start\": \"00:00\",\n" +
            "  \"id_category\": 1\n" +
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
    public void init() {
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();
    }

    @Test
    public void test() throws IOException {
        when(mockHttpServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(REQUEST_BODY)));
        when(mockHttpServletRequest.getParameter(PARAMETER_API_KEY_NAME)).thenReturn(TEST_API_KEY_NAME);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        new LotAddAPI().doPost(mockHttpServletRequest, mockHttpServletResponse);
        new LotAddAPI().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void testEmptyName() throws IOException {
        REQUEST_BODY = "{\n" +
                "  \"uuid_user_seller\": \"\",\n" +
                "  \"name\": \"\",\n" +
                "  \"information\": \"\",\n" +
                "  \"cost\": \"\",\n" +
                "  \"blitz_cost\": \"\",\n" +
                "  \"step_cost\": \"\",\n" +
                "  \"date_start\": \"\",\n" +
                "  \"time_start\": \"\",\n" +
                "  \"id_category\": \n" +
                "}";

        when(mockHttpServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(REQUEST_BODY)));
        when(mockHttpServletRequest.getParameter(PARAMETER_API_KEY_NAME)).thenReturn(TEST_API_KEY_NAME);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        new LotAddAPI().doPost(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter(PARAMETER_API_KEY_NAME)).thenReturn("");
        new LotAddAPI().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
