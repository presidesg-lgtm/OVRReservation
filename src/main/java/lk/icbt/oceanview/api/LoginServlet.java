package lk.icbt.oceanview.api;

import com.google.gson.Gson;
import lk.icbt.oceanview.dao.UserDAO;
import lk.icbt.oceanview.dto.LoginRequest;
import lk.icbt.oceanview.dto.LoginResponse;
import lk.icbt.oceanview.model.User;
import lk.icbt.oceanview.service.AuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final lk.icbt.oceanview.factory.ServiceFactory serviceFactory =
            new lk.icbt.oceanview.factory.ServiceFactory(new lk.icbt.oceanview.factory.DAOFactory());

    private final lk.icbt.oceanview.service.AuthService authService =
            serviceFactory.createAuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            LoginRequest body = gson.fromJson(req.getReader(), LoginRequest.class);
            User user = authService.authenticate(body);

            if (user == null) {
                resp.setStatus(401);
                resp.getWriter().write(gson.toJson(
                        new LoginResponse(false, "Invalid username or password", null, null)
                ));
                return;
            }

            resp.getWriter().write(gson.toJson(
                    new LoginResponse(true, "Login success", user.getUsername(), user.getRole())
            ));

        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(
                    new LoginResponse(false, e.getMessage(), null, null)
            ));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(
                    new LoginResponse(false, "Server error: " + e.getMessage(), null, null)
            ));
        }
    }
}
