package by.iba.uzhyhala.util;

import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.mail.Transport;

import static by.iba.uzhyhala.util.VariablesUtil.EMAIL_SUPPORT;
import static by.iba.uzhyhala.util.VariablesUtil.TEST_URL;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Transport.class})
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*"})
public class MailUtilTest {

    @Mock
    private MockHttpServletRequest mockHttpServletRequest;

    @Before
    public void init() {
        initMocks(this);
        when(mockHttpServletRequest.getRequestURL()).thenReturn(new StringBuffer(TEST_URL));
    }

    @Test
    public void test() {
        new MailUtil().sendErrorMail("");
        new MailUtil().sendSimpleHtmlMail(EMAIL_SUPPORT, "", "");
        new MailUtil().sendSimplePlainMail(EMAIL_SUPPORT, "", "");
    }
}
