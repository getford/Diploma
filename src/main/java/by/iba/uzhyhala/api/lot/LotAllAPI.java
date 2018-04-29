package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.api.to.LotFullFieldTOAPI;
import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/api/lot/all")
public class LotAllAPI extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LotAllAPI.class);
    private Session session = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<LotAllAPI> to = session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT).list();

            System.err.println(new Gson().toJson(to, LotFullFieldTOAPI.class));

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // resp.getWriter().write(CommonUtil.getJsonBetBulk(session, req.getParameter("uuid")));
        } catch (Exception ex) {
            resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // TODO: RETURN INFO ALL LOTS
    public void test() {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object[] to = session.createSQLQuery("SELECT " +
                    "uuid," +
                    "uuid_user_seller," +
                    "name," +
                    "information," +
                    "cost," +
                    "blitz_cost," +
                    "step_cost," +
                    "date_add," +
                    "date_start," +
                    "date_end," +
                    "time_start," +
                    "time_end," +
                    "uuid_user_client," +
                    "id_category," +
                    "status FROM lot").list().toArray();

            System.err.println(new Gson().toJson(to, LotEntity.class));

        } catch (Exception ex) {
            System.err.println("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
