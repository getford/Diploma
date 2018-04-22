package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/status")
public class LotStatus extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotStatus.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug(prepareUpdateStatus(req.getParameter("uuid"), req));
    }

    private boolean prepareUpdateStatus(String uuidLot, HttpServletRequest request) {
        boolean isChangeStatus;
        if (CommonUtil.getHistoryBets(uuidLot).size() > 1) {
            isChangeStatus = CommonUtil.updateLotStatus(VariablesUtil.STATUS_LOT_SALES, uuidLot, request);
        } else {
            isChangeStatus = CommonUtil.updateLotStatus(VariablesUtil.STATUS_LOT_CLOSE, uuidLot, request);
        }
        LOGGER.info(getClass().getName() + "\t" + isChangeStatus);
        return isChangeStatus;
    }
}
