package by.iba.uzhyhala.util;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CommonUtil.class, Query.class})
public class CommonUtilTest {

    @Mock
    private Session session;

    @Test
    public void testLoginOrEmail() {
        String cred = "test@test.test";
        String expected = "email";
        Assert.assertEquals((expected), CommonUtil.loginOrEmail(cred));
    }

    @Test
    public void testgetUserLoginByUUID(){

    }
}
