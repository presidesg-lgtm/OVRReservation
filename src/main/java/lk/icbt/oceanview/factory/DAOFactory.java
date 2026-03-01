package lk.icbt.oceanview.factory;

import lk.icbt.oceanview.dao.ReservationDAO;
import lk.icbt.oceanview.dao.RoomRateDAO;
import lk.icbt.oceanview.dao.UserDAO;

public class DAOFactory {

    public ReservationDAO createReservationDAO() {
        return new ReservationDAO();
    }

    public RoomRateDAO createRoomRateDAO() {
        return new RoomRateDAO();
    }

    public UserDAO createUserDAO() {
        return new UserDAO();
    }
}