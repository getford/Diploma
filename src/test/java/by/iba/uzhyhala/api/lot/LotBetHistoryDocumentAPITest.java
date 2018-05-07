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

    private static final String UUID_LOT = "4da89f5a-1fed-40cc-afd5-22e8eecd97dd";

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init() throws Exception {
        initMocks(this);
        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/"));
        when(mockHttpServletRequest.getParameter("uuid_lot")).thenReturn(UUID_LOT);

        when(mockHttpServletRequest.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME))
                .thenReturn(VariablesUtil.TEST_API_KEY_NAME);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        List<BetHistoryTO> list = new ArrayList<>();
        BetHistoryTO to = new BetHistoryTO();
        to.setBet(10000);
        list.add(to);

        mockStatic(CommonUtil.class);
        when(CommonUtil.getHistoryBets(UUID_LOT)).thenReturn(list);
        when(CommonUtil.isApiKeyValid(VariablesUtil.TEST_API_KEY_NAME)).thenReturn(true);
    }

    @Test
    public void test() throws IOException {
        new LotBetHistoryDocumentAPI().doGet(mockHttpServletRequest, mockHttpServletResponse);
    }
}
