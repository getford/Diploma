package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class RateHandlerTest {
    private static final String UUID = "5c6b7e7c-aa1a-4e9a-94eb-31e9fe81f4c2";

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;


    @Before
    public void init() {
        when(mockHttpServletRequest.getParameter("uuid_")).thenReturn(UUID);
        when(mockHttpServletRequest.getParameter("goal")).thenReturn(VariablesUtil.LOT);
        when(mockHttpServletRequest.getParameter("type")).thenReturn(VariablesUtil.RATE_PLUS);
    }

    @Test
    public void test() throws IOException {
        new RateHandler().doPost(mockHttpServletRequest, mockHttpServletResponse);
    }
}
