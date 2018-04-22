package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/api/infolot")
public class LotAddAPI extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LotAddAPI.class);
    private Session session = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(CommonUtil.getJsonBetBulk(session, req.getParameter("uuid")));
        } catch (Exception ex) {
            resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}