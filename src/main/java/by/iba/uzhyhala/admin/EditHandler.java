package by.iba.uzhyhala.admin;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/edit")
public class EditHandler extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(EditHandler.class);
    private static final long serialVersionUID = -1727543333317873296L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("doPost");
/*        try {
*//*            if (req.getParameter("type").equals(LOT)) {
// TODO:
            } else if (req.getParameter("type").equals(USER)) {

            }*//*
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
        }*/
    }
}
