package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/changerate")
public class RateHandler extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RateHandler.class);
    private static final long serialVersionUID = 8577765665413524404L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("doPost method");
        CommonUtil.changeRate(req.getParameter("uuid_"), req.getParameter("goal"), req.getParameter("type"));
        switch (req.getParameter("type")) {
            case VariablesUtil.LOT:
                resp.sendRedirect("/pages/lot.jsp?uuid=" + req.getParameter("uuid_"));
                break;
            case VariablesUtil.USER:
                resp.sendRedirect("/pages/profile.jsp?user=" + req.getParameter("login_or_email"));
                break;
        }
    }
}
