package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtil.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class DocumentBetHistoryTest {

    private static final String UUID_LOT = "4da89f5a-1fed-40cc-afd5-22e8eecd97dd";
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

        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/"));
        when(mockHttpServletRequest.getParameter("uuid_lot")).thenReturn(UUID_LOT);

        List<BetHistoryTO> list = new ArrayList<>();
        BetHistoryTO to = new BetHistoryTO();
        to.setBet(10000);
        list.add(to);
        
        mockStatic(CommonUtil.class);
        when(CommonUtil.getHistoryBets(UUID_LOT)).thenReturn(list);
    }

    @Test
    public void testBetLessThenBlitzCost() {
        new DocumentBetHistory().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
