package by.iba.uzhyhala.util;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class CookieUtilTest {

    @Mock
    HttpServletRequest request;

    @Test
    public void idAdmin() throws UnsupportedEncodingException {
        Cookie[] cookies = new Cookie[]{};
        String token = "";
        boolean expected = true;
        Assert.assertEquals(expected, new CookieUtil(request).isAdmin(cookies));
    }
}
