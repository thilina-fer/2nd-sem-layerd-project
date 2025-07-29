package lk.ijse.layerd_project_2nd_sem.bo;

import lk.ijse.layerd_project_2nd_sem.bo.custom.impl.CustomerBOImpl;
import lk.ijse.layerd_project_2nd_sem.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.layerd_project_2nd_sem.bo.custom.impl.ItemBOImpl;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? new BOFactory() : boFactory;
    }
    public enum BOTypes {
        CUSTOMER,ITEM,EMPLOYEE
    }
    public SuperBO getBO(BOTypes boType) {
        switch (boType) {
            case CUSTOMER:
                return new CustomerBOImpl();

            case ITEM:
                   return new ItemBOImpl();

            case EMPLOYEE:
                return new EmployeeBOImpl();

            default:
                return null;
        }
    }
}
