package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.CustomerDAO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM customer");
        ArrayList<Customer> customers = new ArrayList<>();

        while (resultSet.next()) {
            Customer entity = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            customers.add(entity);
        }
        return customers;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO customer VALUES(?,?,?,?)",
                entity.getCustomerId(),
                entity.getCustomerName(),
                entity.getCustomerContact(),
                entity.getCustomerAddress()
                );

    }

    @Override
    public boolean update(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE customer SET customer_name = ? , customer_contact = ? , customer_address = ? WHERE customer_id = ?",
                customerDTO.getCustomerName(),
                customerDTO.getCustomerContact(),
                customerDTO.getCustomerAddress(),
                customerDTO.getCustomerId()
        );
    }


    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM customer WHERE customer_id = ?",
                customerId);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");
        char tableChartacter = 'C';

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChartacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableChartacter + "001";
    }

    @Override
    public ArrayList<Customer> search(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE customer_id LIKE ? OR customer_name LIKE ? OR customer_contact LIKE ? OR customer_address LIKE ?";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql , pattern , pattern , pattern , pattern);

        while (resultSet.next()) {
           Customer customer= new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Customer find(String contact) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM customer WHERE customer_contact = ?", contact);
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("customer_contact"),
                    resultSet.getString("customer_address"));

        }
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT customer_id FROM customer WHERE customer_id = ?", id);
        return resultSet.next();
    }
}
