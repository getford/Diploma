package by.iba.uzhyhala.util;

import by.iba.uzhyhala.lot.to.BetHistoryTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.struts.mock.MockHttpServletRequest;
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

import java.util.ArrayList;
import java.util.List;

import static by.iba.uzhyhala.util.CommonUtil.*;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "java.lang.*"})
public class CommonUtilTest {
    private static final String UUID_LOT = "2ee7fa10-6c2a-4980-bd8e-be174c273d53";
    private static final String UUID_USER = "293f3466-ae17-4b47-ab64-77bf85006c1d";

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

    @Before
    public void init() {
        initMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        sessionFactory = configuration.buildSessionFactory();
    }

    @Test
    public void testTranslateLotStatus() {
        translateLotStatus(STATUS_LOT_ACTIVE);
        translateLotStatus(STATUS_LOT_SALES);
        translateLotStatus(STATUS_LOT_CLOSE);
        translateLotStatus(STATUS_LOT_WAIT);
        translateLotStatus("");
    }

    @Test
    public void testChangeRate() {
        changeRate(UUID_LOT, RATE_PLUS, LOT);
        changeRate(UUID_LOT, RATE_MINUS, LOT);

        changeRate(UUID_USER, RATE_PLUS, USER);
        changeRate(UUID_USER, RATE_MINUS, USER);
    }

    @SuppressFBWarnings("UC_USELESS_OBJECT")
    @Test
    public void testGetCurrentCost() {
        BetHistoryTO to = new BetHistoryTO();
        to.setBet(10000);
        List<BetHistoryTO> betHistoryTOList = new ArrayList<>();
        betHistoryTOList.add(to);
        getCurrentCost(betHistoryTOList);
    }

    @Test
    public void testGetAllUser() {
        getAllUser();
    }

    @Test
    public void testGetCategoryById() {
        getCategoryById(1);
    }

    @Test
    public void testGetUserEmailByUUID() {
        getUserEmailByUUID(UUID_USER);
        getUserEmailByUUID(null);
    }

    @Test
    public void testGetUUIDUserByUUIDLot() {
        getUUIDUserByUUIDLot(UUID_LOT);
    }

    @Test
    public void testGetHistoryBets() {
        getHistoryBets(UUID_LOT);
    }

    @Test
    public void testIsApiKeyValid() {
        isApiKeyValid(TEST_API_KEY_NAME);
    }

    @Test
    public void testGetCategories() {
        getCategories();
    }

/*    @Test
    public void testSaveUploadFile() throws IOException, ServletException {
        // when(mockHttpServletRequest.getServletContext().getRealPath("")).thenReturn("/");
        saveUploadFile(mockHttpServletRequest);
    }*/
}
