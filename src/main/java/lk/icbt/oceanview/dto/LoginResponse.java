package lk.icbt.oceanview.dto;

public class LoginResponse {
    public boolean success;
    public String message;
    public String username;
    public String role;

    public LoginResponse(boolean success, String message, String username, String role) {
        this.success = success;
        this.message = message;
        this.username = username;
        this.role = role;
    }
}
