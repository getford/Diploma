package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.api.lot.LotBetHistoryDocumentAPITest;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.iba.uzhyhala.util.VariablesUtil.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtil.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class DocumentHandlerTest {

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init() throws Exception {
        initMocks(this);

        List<BetHistoryTO> list = new ArrayList<>();
        BetHistoryTO to = new BetHistoryTO();
        to.setBet(10000);
        list.add(to);

        mockStatic(CommonUtil.class);
        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer(TEST_URL));

        whenNew(MockHttpServletResponse.class).withAnyArguments().thenReturn(mockHttpServletResponse);

        when(CommonUtil.getHistoryBets(LotBetHistoryDocumentAPITest.UUID_LOT)).thenReturn(list);
        when(CommonUtil.prepareFileForAttach(new Object(), "File name",
                EXCEL_EXTENSION_XLSX)).thenReturn(File.createTempFile("prefix", "suffix"));

        List<Map<String, String>> dateList = new ArrayList<>();
        List<String> columnList = new ArrayList<>();
        when(CommonUtil.createExcelFile(dateList, columnList, "Sheet name")).thenReturn(new XSSFWorkbook());
    }

    @Test
    public void testDoPost() {
        when(mockHttpServletRequest.getParameter("uuid")).thenReturn(LotBetHistoryDocumentAPITest.UUID_LOT);

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
