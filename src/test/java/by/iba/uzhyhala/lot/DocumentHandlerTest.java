package by.iba.uzhyhala.lot;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import java.util.ArrayList;

import static by.iba.uzhyhala.util.CommonUtil.*;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class DocumentHandlerTest {

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

        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer(TEST_URL));
        whenNew(MockHttpServletResponse.class).withAnyArguments().thenReturn(mockHttpServletResponse);
    }

    @Test
    public void testDoPost() {
        String UUID_LOT = "2ee7fa10-6c2a-4980-bd8e-be174c273d53";

        getHistoryBets(UUID_LOT);
        prepareFileForAttach(new XSSFWorkbook(), "File name",
                EXCEL_EXTENSION_XLSX);

        createExcelFile(new ArrayList<>(), new ArrayList<>(), "Sheet name");

        when(mockHttpServletRequest.getParameter("uuid")).thenReturn(UUID_LOT);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(PDF);
        when(mockHttpServletRequest.getParameter("send-mail")).thenReturn("true");
        new DocumentHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(PDF);
        when(mockHttpServletRequest.getParameter("send-mail")).thenReturn("false");
        new DocumentHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(EXCEL);
        when(mockHttpServletRequest.getParameter("send-mail")).thenReturn("true");
        new DocumentHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(EXCEL);
        when(mockHttpServletRequest.getParameter("send-mail")).thenReturn("false");
        new DocumentHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void testGenerateExcelDocLots() {
        new DocumentHandler().generateExcelDocLots(QUERY_SELECT_ALL_LOT, EXCEL_EXTENSION_XLSX);
    }
}
