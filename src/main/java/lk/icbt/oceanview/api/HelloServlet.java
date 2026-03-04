package lk.icbt.oceanview.api;

import com.google.gson.Gson;
import lk.icbt.oceanview.dto.ApiResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("message", "Hello from Java EE (Servlet)");
            payload.put("status", "ok");

            resp.setStatus(200);
            resp.getWriter().write(gson.toJson(payload));

        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(
                    new ApiResponse(false, "Server error: " + e.getMessage())
            ));
        }
    }
}