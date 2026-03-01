package lk.icbt.oceanview.api;

import com.google.gson.Gson;
import lk.icbt.oceanview.dao.ReservationDAO;
import lk.icbt.oceanview.dto.ApiResponse;
import lk.icbt.oceanview.dto.CreateReservationRequest;
import lk.icbt.oceanview.service.ReservationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationsServlet extends HttpServlet {

    private final Gson gson = new Gson();

    // manual wiring (no frameworks)
    private final lk.icbt.oceanview.factory.ServiceFactory serviceFactory =
            new lk.icbt.oceanview.factory.ServiceFactory(new lk.icbt.oceanview.factory.DAOFactory());

    private final lk.icbt.oceanview.service.ReservationService service =
            serviceFactory.createReservationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            CreateReservationRequest body =
                    gson.fromJson(req.getReader(), CreateReservationRequest.class);

            service.createReservation(body);

            resp.setStatus(201);
            resp.getWriter().write(gson.toJson(new ApiResponse(true, "Reservation created")));
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, "Server error: " + e.getMessage())));
        }
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req,
                         javax.servlet.http.HttpServletResponse resp) throws java.io.IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String id = req.getParameter("id");

            // if id is provided -> return single reservation
            if (id != null && !id.trim().isEmpty()) {
                java.util.Map<String, Object> one = service.getReservationById(id);

                if (one == null) {
                    resp.setStatus(404);
                    resp.getWriter().write(gson.toJson(
                            new lk.icbt.oceanview.dto.ApiResponse(false, "Reservation not found: " + id)
                    ));
                    return;
                }

                resp.getWriter().write(gson.toJson(one));
                return;
            }

            // otherwise -> return list (existing behavior)
            resp.getWriter().write(gson.toJson(service.getAllReservations()));

        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new lk.icbt.oceanview.dto.ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(
                    new lk.icbt.oceanview.dto.ApiResponse(false, "Server error: " + e.getMessage())
            ));
        }
    }

    @Override
    protected void doDelete(javax.servlet.http.HttpServletRequest req,
                            javax.servlet.http.HttpServletResponse resp) throws java.io.IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String id = req.getParameter("id");

            boolean deleted = service.deleteReservation(id);
            if (!deleted) {
                resp.setStatus(404);
                resp.getWriter().write(gson.toJson(
                        new lk.icbt.oceanview.dto.ApiResponse(false, "Reservation not found: " + id)
                ));
                return;
            }

            resp.getWriter().write(gson.toJson(
                    new lk.icbt.oceanview.dto.ApiResponse(true, "Reservation deleted: " + id)
            ));

        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new lk.icbt.oceanview.dto.ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(
                    new lk.icbt.oceanview.dto.ApiResponse(false, "Server error: " + e.getMessage())
            ));
        }
    }

    @Override
    protected void doPut(javax.servlet.http.HttpServletRequest req,
                         javax.servlet.http.HttpServletResponse resp) throws java.io.IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String id = req.getParameter("id");
            lk.icbt.oceanview.dto.UpdateReservationRequest body =
                    gson.fromJson(req.getReader(), lk.icbt.oceanview.dto.UpdateReservationRequest.class);

            boolean updated = service.updateReservation(id, body);

            if (!updated) {
                resp.setStatus(404);
                resp.getWriter().write(gson.toJson(
                        new lk.icbt.oceanview.dto.ApiResponse(false, "Reservation not found: " + id)
                ));
                return;
            }

            resp.getWriter().write(gson.toJson(
                    new lk.icbt.oceanview.dto.ApiResponse(true, "Reservation updated: " + id)
            ));

        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new lk.icbt.oceanview.dto.ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(
                    new lk.icbt.oceanview.dto.ApiResponse(false, "Server error: " + e.getMessage())
            ));
        }
    }
}
