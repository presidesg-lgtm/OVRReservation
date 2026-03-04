package lk.icbt.oceanview.service;

import lk.icbt.oceanview.dao.UserDAO;
import lk.icbt.oceanview.dto.LoginRequest;
import lk.icbt.oceanview.exception.AuthException;
import lk.icbt.oceanview.exception.ValidationException;
import lk.icbt.oceanview.model.User;

public class AuthService {

    private final UserDAO dao;

    public AuthService(UserDAO dao) {
        this.dao = dao;
    }

    public User authenticate(LoginRequest req) throws Exception {
        if (req == null) throw new ValidationException("Request body is missing");
        if (isBlank(req.username)) throw new ValidationException("username is required");
        if (isBlank(req.password)) throw new ValidationException("password is required");

        User user = dao.findByUsername(req.username.trim());
        if (user == null) {
            throw new AuthException("Invalid username or password");
        }

        // simple check for now (later we can hash)
        if (!req.password.equals(user.getPasswordHash())) {
            throw new AuthException("Invalid username or password");
        }

        return user;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
