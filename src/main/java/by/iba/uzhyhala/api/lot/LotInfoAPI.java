package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/api/lot/info")
public class LotInfoAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotInfoAPI.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String message;
        String uuidLot = req.getParameter("uuid");
        try {
            if (CommonUtil.isApiKeyValid(req.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME))) {
                LOGGER.info("uuid lot: " + uuidLot + ", api_key: " + req.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME));
                String bulk = CommonUtil.getJsonBetBulk(session, uuidLot);
                if (!StringUtils.isBlank(bulk))
                    message = bulk;
                else
                    message = "{\"uuid_lot\": \"" + uuidLot + "\",\"exception\": \"Info not found, please check uuid lot\"}";
            } else {
                message = "{\"exception\":\"Api key isnt valid\"}";
            }
            resp.getWriter().write(message);
        } catch (Exception ex) {
            resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}