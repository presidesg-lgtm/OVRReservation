package lk.icbt.oceanview.factory;

import lk.icbt.oceanview.service.AuthService;
import lk.icbt.oceanview.service.ReservationService;

public class ServiceFactory {

    private final DAOFactory daoFactory;

    public ServiceFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public ReservationService createReservationService() {
        return new ReservationService(daoFactory.createReservationDAO());
    }

    public AuthService createAuthService() {
        return new AuthService(daoFactory.createUserDAO());
    }
}
