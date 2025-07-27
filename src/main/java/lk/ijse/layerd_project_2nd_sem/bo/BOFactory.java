package lk.ijse.layerd_project_2nd_sem.bo;

import lk.ijse.layerd_project_2nd_sem.bo.custom.impl.CustomerBOImpl;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? new BOFactory() : boFactory;
    }
    public enum BOTypes {
        CUSTOMER
    }
    public SuperBO getBO(BOTypes boType) {
        switch (boType) {
            case CUSTOMER:
                return new CustomerBOImpl();
            default:
                return null;
        }
    }
}
