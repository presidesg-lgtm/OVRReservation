package lk.icbt.oceanview.api;

import com.google.gson.Gson;
import lk.icbt.oceanview.dao.RoomRateDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoomRatesServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final RoomRateDAO dao = new RoomRateDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            resp.getWriter().write(gson.toJson(dao.getAllRates()));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(
                    java.util.Map.of("error", "Database error", "details", e.getMessage())
            ));
        }
    }
}