package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommonUtil.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class LotBetHistoryDocumentAPITest {

    public static final String UUID_LOT = "4da89f5a-1fed-40cc-afd5-22e8eecd97dd";

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init() throws Exception {
        initMocks(this);
        mockStatic(CommonUtil.class);

        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer(VariablesUtil.TEST_URL));
        when(mockHttpServletRequest.getParameter("uuid_lot")).thenReturn(UUID_LOT);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        List<BetHistoryTO> list = new ArrayList<>();
        BetHistoryTO to = new BetHistoryTO();
        to.setBet(10000);
        list.add(to);

        when(CommonUtil.getHistoryBets(UUID_LOT)).thenReturn(list);
        when(CommonUtil.isApiKeyValid(VariablesUtil.TEST_API_KEY_NAME)).thenReturn(true);
    }

    @Test
    public void test() throws IOException {
        when(mockHttpServletRequest.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME))
                .thenReturn(VariablesUtil.TEST_API_KEY_NAME);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(VariablesUtil.PDF);
        new LotBetHistoryDocumentAPI().doGet(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(VariablesUtil.EXCEL);
        new LotBetHistoryDocumentAPI().doGet(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter("type")).thenReturn("");
        new LotBetHistoryDocumentAPI().doGet(mockHttpServletRequest, mockHttpServletResponse);
    }
}
