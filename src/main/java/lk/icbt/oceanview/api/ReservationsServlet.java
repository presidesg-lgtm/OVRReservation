package lk.icbt.oceanview.api;

import com.google.gson.Gson;
import lk.icbt.oceanview.dto.ApiResponse;
import lk.icbt.oceanview.dto.CreateReservationRequest;
import lk.icbt.oceanview.dto.UpdateReservationRequest;
import lk.icbt.oceanview.exception.NotFoundException;
import lk.icbt.oceanview.exception.ValidationException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationsServlet extends HttpServlet {

    private final Gson gson = new Gson();

    // manual wiring (no frameworks) - Factory pattern
    private final lk.icbt.oceanview.factory.ServiceFactory serviceFactory =
            new lk.icbt.oceanview.factory.ServiceFactory(new lk.icbt.oceanview.factory.DAOFactory());

    private final lk.icbt.oceanview.service.ReservationService service =
            serviceFactory.createReservationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        json(resp);
        try {
            CreateReservationRequest body = gson.fromJson(req.getReader(), CreateReservationRequest.class);
            service.createReservation(body);

            resp.setStatus(201);
            resp.getWriter().write(gson.toJson(new ApiResponse(true, "Reservation created")));

        } catch (ValidationException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, "Server error: " + e.getMessage())));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        json(resp);
        try {
            String id = req.getParameter("id");

            if (id != null && !id.trim().isEmpty()) {
                resp.setStatus(200);
                resp.getWriter().write(gson.toJson(service.getReservationById(id)));
                return;
            }

            resp.setStatus(200);
            resp.getWriter().write(gson.toJson(service.getAllReservations()));

        } catch (ValidationException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (NotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, "Server error: " + e.getMessage())));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        json(resp);
        try {
            String id = req.getParameter("id");
            UpdateReservationRequest body = gson.fromJson(req.getReader(), UpdateReservationRequest.class);

            service.updateReservation(id, body);

            resp.setStatus(200);
            resp.getWriter().write(gson.toJson(new ApiResponse(true, "Reservation updated: " + id)));

        } catch (ValidationException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (NotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, "Server error: " + e.getMessage())));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        json(resp);
        try {
            String id = req.getParameter("id");

            service.deleteReservation(id);

            resp.setStatus(200);
            resp.getWriter().write(gson.toJson(new ApiResponse(true, "Reservation deleted: " + id)));

        } catch (ValidationException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (NotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, "Server error: " + e.getMessage())));
        }
    }

    private void json(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }
}