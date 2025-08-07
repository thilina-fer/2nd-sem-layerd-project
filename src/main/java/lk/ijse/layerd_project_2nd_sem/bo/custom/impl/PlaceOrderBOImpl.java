package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.layerd_project_2nd_sem.bo.custom.PlaceOrderBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.CustomerDAO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.ItemDAO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.OrderDAO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.OrderDetailDAO;
import lk.ijse.layerd_project_2nd_sem.db.DBConnection;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.dto.ItemDTO;
import lk.ijse.layerd_project_2nd_sem.dto.OrderDetailDTO;
import lk.ijse.layerd_project_2nd_sem.entity.Customer;
import lk.ijse.layerd_project_2nd_sem.entity.Item;
import lk.ijse.layerd_project_2nd_sem.entity.Order;
import lk.ijse.layerd_project_2nd_sem.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public CustomerDTO searchCustomer(String contact) throws SQLException, ClassNotFoundException {
        Customer entity = customerDAO.find(contact);
        return new CustomerDTO(
                entity.getCustomerId(),
                entity.getCustomerName(),
                entity.getCustomerContact(),
                entity.getCustomerAddress()
                );
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        Item entity = itemDAO.find(id);
        return new ItemDTO(
                entity.getItemId(),
                entity.getItemName(),
                entity.getQuantity(),
                entity.getBuyPrice(),
                entity.getSellPrice()
        );
    }

    @Override
    public boolean existCustomer(String contact) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(contact);
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
       return itemDAO.exist(id);
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOs.add(new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerContact(),
                    customer.getCustomerAddress()
            ));
        }
        return customerDTOs;
    }

    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            itemDTOs.add(new ItemDTO(
                    item.getItemId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getBuyPrice(),
                    item.getSellPrice()
            ));
        }
        return itemDTOs;
    }

    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerContact, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();

        boolean b1 = orderDAO.exist(orderId);

        if (b1){
            return false;
        }
        connection.setAutoCommit(false);
        boolean b2 = orderDAO.save(new Order(
                orderId ,
                customerContact,
                orderDate
        ));

        // save orders

        if (!b2){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        // save order details

        for (OrderDetailDTO orderDetail : orderDetails) {
            boolean b3 = orderDetailDAO.save(new OrderDetail(
                    orderDetail.getOrderId(),
                    orderDetail.getItemId(),
                    orderDetail.getQuantity(),
                    orderDetail.getUnitPrice()
            ));

            if (!b3){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            // Search & Update Item
            ItemDTO item = findItem(orderDetail.getItemId());
            item.setQuantity(item.getQuantity() - orderDetail.getQuantity());

            // Update Item

            boolean b4 = itemDAO.update(new Item(
                    item.getItemId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getBuyPrice(),
                    item.getSellPrice()
            ));

            if (!b4){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;

        }

    @Override
    public ItemDTO findItem(String id) throws SQLException, ClassNotFoundException {
       try {
           Item item = itemDAO.find(id);
           return new ItemDTO(
                   item.getItemId(),
                     item.getItemName(),
                     item.getQuantity(),
                     item.getBuyPrice(),
                     item.getSellPrice()
           );
       }catch (Exception e){
              e.printStackTrace();
              throw new RuntimeException("Failed to find item with id: " + id, e);
       }
    }

    @Override
    public CustomerDTO findCustomer(String contact) throws SQLException, ClassNotFoundException {
        try {
            Customer customer = customerDAO.find(contact);
            return new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerContact(),
                    customer.getCustomerAddress()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
