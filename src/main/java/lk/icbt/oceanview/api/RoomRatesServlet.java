package lk.icbt.oceanview.api;

import com.google.gson.Gson;
import lk.icbt.oceanview.dao.RoomRateDAO;
import lk.icbt.oceanview.dto.ApiResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoomRatesServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final RoomRateDAO dao = new RoomRateDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJson(resp);

        try {
            resp.setStatus(200);
            writeJson(resp, dao.getAllRates());

        } catch (Exception e) {
            resp.setStatus(500);
            writeJson(resp, new ApiResponse(false, "Database error: " + e.getMessage()));
        }
    }

    private void setJson(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private void writeJson(HttpServletResponse resp, Object obj) throws IOException {
        resp.getWriter().write(gson.toJson(obj));
    }
}