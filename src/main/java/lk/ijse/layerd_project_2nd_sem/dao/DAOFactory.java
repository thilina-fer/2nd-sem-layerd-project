package lk.ijse.layerd_project_2nd_sem.dao;

import lk.ijse.layerd_project_2nd_sem.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.layerd_project_2nd_sem.dao.custom.impl.ItemDAOImpl;

import static lk.ijse.layerd_project_2nd_sem.bo.BOFactory.BOTypes.ITEM;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}
    public static DAOFactory getInstance() {
        return (daoFactory==null)?new DAOFactory():daoFactory;
    }
    public enum DAOTypes {
        CUSTOMER,
        ITEM


    }
    public SuperDAO getDAO(DAOTypes daoType) {
        switch(daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();

            case ITEM:
                return new ItemDAOImpl();

            default:
                return null;
        }
    }
}
