package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.dto.ItemDTO;
import lk.ijse.layerd_project_2nd_sem.dto.OrderDetailDTO;
import lk.ijse.layerd_project_2nd_sem.dto.SupplierDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface SupOrderBO extends SuperBO {
    SupplierDTO searchSupplier(String contact) throws SQLException,ClassNotFoundException;
    ItemDTO searchItem(String id) throws SQLException,ClassNotFoundException;
    boolean existSupplier(String id) throws SQLException, ClassNotFoundException;
    boolean existItem(String id) throws SQLException, ClassNotFoundException;
    String generateOrderId() throws SQLException,ClassNotFoundException;
    ArrayList<CustomerDTO> getAllSupplier() throws SQLException, ClassNotFoundException;
    ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;
    boolean placeOrder(String orderId, LocalDate orderDate, String supplierId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
    ItemDTO findItem(String id) throws SQLException, ClassNotFoundException;

}
