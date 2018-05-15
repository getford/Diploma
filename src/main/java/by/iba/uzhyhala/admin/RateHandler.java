package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.util.CommonUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.iba.uzhyhala.util.VariablesUtil.LOT;
import static by.iba.uzhyhala.util.VariablesUtil.USER;

@WebServlet(urlPatterns = "/changerate")
public class RateHandler extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RateHandler.class);
    private static final long serialVersionUID = 8577765665413524404L;

    @SuppressFBWarnings({"SF_SWITCH_NO_DEFAULT", "HRS_REQUEST_PARAMETER_TO_HTTP_HEADER"})
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("doPost method");
        CommonUtil.changeRate(req.getParameter("uuid_"), req.getParameter("goal"), req.getParameter("type"));
        switch (req.getParameter("type")) {
            case LOT:
                resp.sendRedirect("/pages/lot.jsp?uuid=" + req.getParameter("uuid_"));
                break;
            case USER:
                resp.sendRedirect("/pages/profile.jsp?user=" + req.getParameter("login_or_email"));
                break;
        }
    }
}
