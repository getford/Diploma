package by.iba.uzhyhala.user;

import by.iba.uzhyhala.TestHibernate;
import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.to.UserTO;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.hibernate.JDBCException;
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
    private static final Logger logger = Logger.getLogger(TestHibernate.class);

    private String errorMessage = null;
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
        doRegistration(req.getParameter("login"), req.getParameter("password"));
    }

    private void doRegistration(String login, String password) {
        logger.debug(this.getClass().getName() + ", method: doRegistration");
        List<UserTO> list = handleInputString(prepareInputString(login, password));

        System.err.println(list.get(0).toString());

        try {
            if (isLoginEmpty(list.get(0).getLogin())) {
                AuthInfoEntity authInfoEntity = new AuthInfoEntity();
                authInfoEntity.setLogin(list.get(0).getLogin());
                authInfoEntity.setPassword(list.get(0).getPassword());
                authInfoEntity.setRole(VariablesUtil.USER_ROLE);
                authInfoEntity.setUuid(UUID.randomUUID().toString());

                session.save(authInfoEntity);
                session.getTransaction().commit();
            } else {
                this.errorMessage = "Login isn't empty";
            }
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    private boolean isLoginEmpty(String login) {
        boolean flag = false;
        Query query = session.createQuery("SELECT a.login FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE login = :login");
        query.setParameter("login", login);
        if (query.list().isEmpty())
            flag = true;

        return flag;
    }

    @Override
    public String prepareInputString(String login, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("password", password);
        return gson.toJson(map, Map.class);
    }

    @Override
    public List<UserTO> handleInputString(String json) {
        return gson.fromJson("[" + json + "]", new TypeToken<List<UserTO>>() {
        }.getType());
    }
}