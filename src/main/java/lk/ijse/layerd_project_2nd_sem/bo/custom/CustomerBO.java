package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomer() throws Exception;
    boolean saveCustomer(CustomerDTO customerDTO) throws Exception;
    boolean updateCustomer(CustomerDTO customerDTO) throws Exception;
    boolean deleteCustomer(String customerId) throws Exception;
    String generateCustomerId() throws Exception;
    ArrayList<CustomerDTO> searchCustomer(String text) throws Exception;
    boolean existCustomer(String contact) throws SQLException, ClassNotFoundException;
}
