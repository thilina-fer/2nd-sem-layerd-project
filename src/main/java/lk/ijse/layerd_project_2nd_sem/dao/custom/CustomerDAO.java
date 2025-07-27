package lk.ijse.layerd_project_2nd_sem.dao.custom;

import lk.ijse.layerd_project_2nd_sem.dao.CrudDAO;
import lk.ijse.layerd_project_2nd_sem.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
    ArrayList<Customer> search(String searchText) throws SQLException, ClassNotFoundException;
}
