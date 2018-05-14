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
@PowerMockIgnore({"javax.xml.*", "org.xml.*", "org.w3c.*", "javax", "com.sun.org.apache.xerces.*", "javax.net.ssl.*"})
public class ReCaptchaUtilTest {
    private static final String URL = "https://www.google.com/recaptcha/api/siteverify";

    @Mock
    private HttpsURLConnection httpsURLConnection;

    @Test
    public void testIf() {
        ReCaptchaUtil.verify(null);
    }

    @Test
    public void test() throws Exception {
        URL url = mock(URL.class);
        whenNew(URL.class).withArguments(URL).thenReturn(url);
        when(url.openConnection()).thenReturn(httpsURLConnection);
        ReCaptchaUtil.verify("true");
        ReCaptchaUtil.verify("false");
    }
}
