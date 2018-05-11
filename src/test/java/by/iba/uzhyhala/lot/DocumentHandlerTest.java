package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.api.lot.LotBetHistoryDocumentAPITest;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtil.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class DocumentHandlerTest {

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private File file;

    @Mock
    private FileInputStream fileInputStream;

    @Before
    public void init() throws Exception {
        initMocks(this);

        List<BetHistoryTO> list = new ArrayList<>();
        BetHistoryTO to = new BetHistoryTO();
        to.setBet(10000);
        list.add(to);

        mockStatic(CommonUtil.class);
        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/"));

//        whenNew(File.class).withAnyArguments().thenReturn(file);
//        whenNew(FileInputStream.class).withArguments(Matchers.anyString()).thenReturn(fileInputStream);

        when(CommonUtil.getHistoryBets(LotBetHistoryDocumentAPITest.UUID_LOT)).thenReturn(list);
        when(CommonUtil.prepareFileForAttach(new Object(), "File name",
                VariablesUtil.EXCEL_EXTENSION_XLSX)).thenReturn(File.createTempFile("prefix", "suffix"));

        List<Map<String, String>> dateList = new ArrayList<>();
        List<String> columnList = new ArrayList<>();
        when(CommonUtil.createExcelFile(dateList, columnList, "Sheet name")).thenReturn(new XSSFWorkbook());
    }

    @Test
    public void testGenerateDocHistoryBetExcel() {
        new DocumentHandler().generateExcelDocHistoryBet(mockHttpServletRequest, LotBetHistoryDocumentAPITest.UUID_LOT, VariablesUtil.EXCEL_EXTENSION_XLSX, true);
        //      new DocumentHandler().generateExcelDocHistoryBet(mockHttpServletRequest, LotBetHistoryDocumentAPITest.UUID_LOT, VariablesUtil.EXCEL_EXTENSION_XLSX, false);
    }

    @Test
    public void testGenerateExcelDocLots() {
        new DocumentHandler().generateExcelDocLots(VariablesUtil.QUERY_SELECT_ALL_LOT, VariablesUtil.EXCEL_EXTENSION_XLSX);
    }
}
