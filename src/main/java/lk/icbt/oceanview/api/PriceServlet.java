package lk.icbt.oceanview.api;

import com.google.gson.Gson;
import lk.icbt.oceanview.dao.RoomRateDAO;
import lk.icbt.oceanview.dto.ApiResponse;
import lk.icbt.oceanview.pricing.PricingStrategyFactory;
import lk.icbt.oceanview.service.PricingService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PriceServlet extends HttpServlet {

    private final Gson gson = new Gson();

    private final PricingService pricingService =
            new PricingService(new RoomRateDAO(), new PricingStrategyFactory());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String roomType = req.getParameter("roomType");
            String checkIn = req.getParameter("checkIn");
            String checkOut = req.getParameter("checkOut");

            resp.getWriter().write(gson.toJson(
                    pricingService.calculatePrice(roomType, checkIn, checkOut)
            ));
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, "Server error: " + e.getMessage())));
        }
    }
}
