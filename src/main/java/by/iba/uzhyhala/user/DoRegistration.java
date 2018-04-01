package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.to.UserTO;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "/registration")
@RequestScoped
public class DoRegistration extends HttpServlet implements IParseJsonString {
    private static final Logger logger = Logger.getLogger(DoRegistration.class);

    private Session session;
    private Gson gson;

    public DoRegistration() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        doRegistration(req.getParameter("login"), req.getParameter("password"), req.getParameter("email"));
    }

    public void doRegistration(String login, String password, String email) {
        logger.debug(this.getClass().getName() + ", method: doRegistration");
        List<UserTO> list = handleInputString(prepareInputString(login, password, email));

        System.err.println(list.get(0).toString());

        try {
            if (isLoginAndEmailEmpty(list.get(0).getLogin(), list.get(0).getEmail())) {
                AuthInfoEntity authInfoEntity = new AuthInfoEntity();
                authInfoEntity.setLogin(list.get(0).getLogin());
                authInfoEntity.setPassword(list.get(0).getPassword());
                authInfoEntity.setEmail(VariablesUtil.EMAIL_SUPPORT);
                authInfoEntity.setRole(VariablesUtil.ROLE_USER);
                authInfoEntity.setUuid(UUID.randomUUID().toString());

                session.save(authInfoEntity);
                session.getTransaction().commit();
            } else {
                logger.debug("Login isn't empty");
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    private boolean isLoginAndEmailEmpty(String login, String email) {
        boolean flag = false;
        Query query = session.createQuery("SELECT a.login FROM " +
                VariablesUtil.ENTITY_AUTH_INFO + " a WHERE login = :login and email = :email");
        query.setParameter("login", login);
        query.setParameter("email", email);
        if (query.list().isEmpty())
            flag = true;

        return flag;
    }

    @Override
    public String prepareInputString(String login, String password, String email) {
        Map<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("password", password);
        map.put("email", email);
        return gson.toJson(map, Map.class);
    }

    @Override
    public List<UserTO> handleInputString(String json) {
        return gson.fromJson("[" + json + "]", new TypeToken<List<UserTO>>() {
        }.getType());
    }
}