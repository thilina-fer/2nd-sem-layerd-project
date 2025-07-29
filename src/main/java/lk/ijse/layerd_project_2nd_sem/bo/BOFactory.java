package lk.ijse.layerd_project_2nd_sem.bo;

import lk.ijse.layerd_project_2nd_sem.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? new BOFactory() : boFactory;
    }
    public enum BOTypes {
        CUSTOMER,
        ITEM,
        EMPLOYEE,
        SUPPLIER,
        USER,
        EMPLOYEE_ATTENDANCE
    }
    public SuperBO getBO(BOTypes boType) {
        switch (boType) {
            case CUSTOMER:
                return new CustomerBOImpl();

            case ITEM:
                   return new ItemBOImpl();

            case EMPLOYEE:
                return new EmployeeBOImpl();

            case SUPPLIER:
                return new SupplierBOImpl();

            case USER:
                return new UserBOImpl();

            case EMPLOYEE_ATTENDANCE:
                return new EmployeeAttendanceBOImpl();

            default:
                return null;
        }
    }
}
