package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.api.lot.LotBetHistoryDocumentAPITest;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class DocumentHandlerTest {

    @Before
    public void init() {
        initMocks(this);

        List<BetHistoryTO> list = new ArrayList<>();
        BetHistoryTO to = new BetHistoryTO();
        to.setBet(10000);
        list.add(to);

        mockStatic(CommonUtil.class);
        when(CommonUtil.getHistoryBets(LotBetHistoryDocumentAPITest.UUID_LOT)).thenReturn(list);
    }

    @Test
    public void testGenerateDocHistoryBetExcel() {
        new DocumentHandler().generateExcelDocHistoryBet(LotBetHistoryDocumentAPITest.UUID_LOT, VariablesUtil.EXCEL_EXTENSION_XLSX);
    }

    @Test
    public void testGenerateExcelDocLots() {
        new DocumentHandler().generateExcelDocLots(VariablesUtil.QUERY_SELECT_ALL_LOT, VariablesUtil.EXCEL_EXTENSION_XLSX);
    }
}
