package by.iba.uzhyhala.user;

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

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*",
        "javax.net.ssl.*", "com.sun.mail.smtp.*"})
public class PasswordHandlerTest {

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
    public void testForgetPasswordTrue() throws IOException {
        initVariables("true");
        new PasswordHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void testForgetPasswordFalse() throws IOException {
        initVariables("false");
        new PasswordHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }

    private void initVariables(String fp) {
        when(mockHttpServletRequest.getParameter("uuid")).thenReturn("e0f11600-fa75-44cc-8a05-056bcb1042bb");
        when(mockHttpServletRequest.getParameter("fp")).thenReturn(fp);
        when(mockHttpServletRequest.getParameter("old_password")).thenReturn("eee");
        when(mockHttpServletRequest.getParameter("new_password")).thenReturn("eee");
        when(mockHttpServletRequest.getParameter("new_password_")).thenReturn("ee");
    }
}
