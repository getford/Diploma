package by.iba.uzhyhala.lot;

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
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class BetHandlerTest {
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

        when(mockHttpServletRequest.getParameter("uuid_lot")).thenReturn("4da89f5a-1fed-40cc-afd5-22e8eecd97dd");
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
    }

    @Test
    public void testBetLessThenBlitzCost() throws IOException {
        when(mockHttpServletRequest.getParameter("cost")).thenReturn("1000");
        new BetHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }


    @Test
    public void testBetMoreThenBlitzCost() throws IOException {
        when(mockHttpServletRequest.getParameter("cost")).thenReturn("100000");
        new BetHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
