package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.CustomerBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.CustomerDAO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.entity.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public ArrayList<CustomerDTO> getAllCustomer() throws Exception {
       ArrayList<Customer> entity = customerDAO.getAll();
       ArrayList<CustomerDTO> customerDTO = new ArrayList<>();
       for (Customer c : entity) {
           customerDTO.add(new CustomerDTO(
                   c.getCustomerId(),
                   c.getCustomerName(),
                   c.getCustomerContact(),
                   c.getCustomerAddress()
           ));
       }
       return customerDTO;
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws Exception {
        return customerDAO.save(new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getCustomerName(),
                customerDTO.getCustomerContact(),
                customerDTO.getCustomerAddress()
        ));
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws Exception {
        return customerDAO.update(new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getCustomerName(),
                customerDTO.getCustomerContact(),
                customerDTO.getCustomerAddress()
        ));
    }
    public boolean deleteCustomer(String customerId) throws Exception {
    return customerDAO.delete(customerId);
    }
    public String generateCustomerId() throws Exception {
        return customerDAO.generateNewId();
    }

    @Override
    public ArrayList<CustomerDTO> searchCustomer(String text) throws Exception {
        customerDAO.search(text);
        ArrayList<Customer> entity = customerDAO.search(text);
        ArrayList<CustomerDTO> customerDTO = new ArrayList<>();
        for (Customer c : entity) {
            customerDTO.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getCustomerName(),
                    c.getCustomerContact(),
                    c.getCustomerAddress()
            ));
    }
        return customerDTO;
    }
}
