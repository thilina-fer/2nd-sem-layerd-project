package lk.ijse.layerd_project_2nd_sem.dao;

import lk.ijse.layerd_project_2nd_sem.dao.custom.impl.*;

import static lk.ijse.layerd_project_2nd_sem.bo.BOFactory.BOTypes.ITEM;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}
    public static DAOFactory getInstance() {
        return (daoFactory==null)?new DAOFactory():daoFactory;
    }
    public enum DAOTypes {
        CUSTOMER,
        ITEM,
        EMPLOYEE,
        SUPPLIER,
        USER,
        EMPLOYEE_ATTENDANCE,
        PRE_ORDER,
        ORDER,
        ORDER_DETAIL


    }
    public SuperDAO getDAO(DAOTypes daoType) {
        switch(daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();

            case ITEM:
                return new ItemDAOImpl();

            case EMPLOYEE:
                return new EmployeeDAOImpl();

            case SUPPLIER:
                return new SupplierDAOImpl();

            case USER:
                return new UserDAOImpl();

            case EMPLOYEE_ATTENDANCE:
                return new EmployeeAttendanceDAOImpl();

            case PRE_ORDER:
                    return new PreOrderDAOImpl();

            case ORDER:
                return new OrderDAOImpl();

            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
