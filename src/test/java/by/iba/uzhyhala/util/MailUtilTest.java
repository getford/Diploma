package by.iba.uzhyhala.util;

import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class MailUtilTest {
    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Before
    public void init() {
        initMocks(this);
        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/"));
    }

    @Test
    public void test() {
        new MailUtil().sendErrorMailForAdmin("");
        new MailUtil().sendMailRegistration("", "", "", mockHttpServletRequest);
        new MailUtil().sendMailChangeLotStatus("", "", "", mockHttpServletRequest);
    }
}
