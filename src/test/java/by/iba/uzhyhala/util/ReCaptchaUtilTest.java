package by.iba.uzhyhala.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*",
        "com.sun.mail.*", "javax.mail.internet.MailUtil.*", "javax.mail.*"})
public class ReCaptchaUtilTest {

    @Mock
    private HttpsURLConnection httpsURLConnection;

    @Test
    public void testIf() {
        ReCaptchaUtil.verify(null);
    }

    @Test
    public void test() throws Exception {
        URL url = mock(URL.class);
        whenNew(URL.class).withArguments(ReCaptchaUtil.URL).thenReturn(url);
        when(url.openConnection()).thenReturn(httpsURLConnection);
        ReCaptchaUtil.verify("true");
        ReCaptchaUtil.verify("false");
    }
}
