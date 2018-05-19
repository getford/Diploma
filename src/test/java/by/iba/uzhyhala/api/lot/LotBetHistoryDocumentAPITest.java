package by.iba.uzhyhala.api.lot;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static by.iba.uzhyhala.util.CommonUtil.getHistoryBets;
import static by.iba.uzhyhala.util.CommonUtil.isApiKeyValid;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class LotBetHistoryDocumentAPITest {

    public static final String UUID_LOT = "2ee7fa10-6c2a-4980-bd8e-be174c273d53";

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init() throws Exception {
        initMocks(this);

        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer(TEST_URL));
        when(mockHttpServletRequest.getParameter("uuid_lot")).thenReturn(UUID_LOT);
        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
    }

    @Test
    public void test() throws IOException {
        getHistoryBets(UUID_LOT);
        isApiKeyValid(TEST_API_KEY_NAME);

        when(mockHttpServletRequest.getParameter(PARAMETER_API_KEY_NAME))
                .thenReturn(TEST_API_KEY_NAME);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(PDF);
        new LotBetHistoryDocumentAPI().doGet(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter("type")).thenReturn(EXCEL);
        new LotBetHistoryDocumentAPI().doGet(mockHttpServletRequest, mockHttpServletResponse);

        when(mockHttpServletRequest.getParameter("type")).thenReturn("");
        new LotBetHistoryDocumentAPI().doGet(mockHttpServletRequest, mockHttpServletResponse);
    }
}
