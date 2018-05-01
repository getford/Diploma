package by.iba.uzhyhala.util;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class MailUtilTest {
    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Mock
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void init() throws Exception {
        initMocks(this);


//        when(mockHttpServletRequest.getParameter("uuid")).thenReturn("5a132e1d-10c5-486c-886b-756fb4b3a1f8");
//        when(mockHttpServletRequest.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME)).thenReturn(VariablesUtil.TEST_API_KEY_NAME);
//        when(mockHttpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
    }

    @Test
    public void test() throws IOException, ServletException {
    }
}
