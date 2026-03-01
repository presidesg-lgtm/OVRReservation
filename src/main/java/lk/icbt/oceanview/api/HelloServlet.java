package lk.icbt.oceanview.api;

import com.google.gson.Gson;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class HelloServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> payload = Map.of(
                "message", "Hello from Java EE (Servlet)",
                "status", "ok"
        );

        resp.getWriter().write(gson.toJson(payload));
    }
}