package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.SupOrderBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.ItemDAO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.SupOrderDAO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.SupOrderDetailDAO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.SupplierDAO;
import lk.ijse.layerd_project_2nd_sem.db.DBConnection;
import lk.ijse.layerd_project_2nd_sem.dto.*;
import lk.ijse.layerd_project_2nd_sem.entity.Item;
import lk.ijse.layerd_project_2nd_sem.entity.SupOrder;
import lk.ijse.layerd_project_2nd_sem.entity.SupOrderDetails;
import lk.ijse.layerd_project_2nd_sem.entity.Supplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupOrderBOImpl implements SupOrderBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);
    SupOrderDAO supOrderDAO = (SupOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUP_ORDER);
    SupOrderDetailDAO supOrderDetailDAO = (SupOrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUP_ORDER_DETAIL);

    @Override
    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier entity = supplierDAO.find(id);
        return new SupplierDTO(
                entity.getSupplierId(),
                entity.getSupplierName(),
                entity.getSupplierContact(),
                entity.getSupplierAddress()
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
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supOrderDAO.exist(id);
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return supOrderDAO.generateNewId();
    }

    @Override
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
       ArrayList<Supplier> suppliers = supplierDAO.getAll();
       ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();
         for (Supplier supplier : suppliers) {
            supplierDTOs.add(new SupplierDTO(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getSupplierContact(),
                    supplier.getSupplierAddress()
            ));
         }
         return supplierDTOs;
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
    public boolean placeOrder(String orderId, LocalDate orderDate, String supplierId, List<SupOrderDetailDTO> supOrderDetail) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();

        boolean b1 = supOrderDAO.exist(orderId);
        if (b1) {
            return false;
        }
        connection.setAutoCommit(false);
        boolean b2 = supOrderDAO.save(new SupOrder(
                orderId,
                supplierId,
                orderDate

        ));

        if (!b2){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

       for (SupOrderDetailDTO supOrderDetailDTO : supOrderDetail) {
           boolean b3 = supOrderDetailDAO.save(new SupOrderDetails(
                     supOrderDetailDTO.getOrderId(),
                     supOrderDetailDTO.getItemId(),
                     supOrderDetailDTO.getQuantity(),
                     supOrderDetailDTO.getUnitPrice()
           ));

           if (!b3) {
               connection.rollback();
               connection.setAutoCommit(true);
               return false;
           }
           ItemDTO item = findItem(supOrderDetailDTO.getItemId());
           item.setQuantity(item.getQuantity() + supOrderDetailDTO.getQuantity());

           boolean b4 = itemDAO.update(new Item(
                   item.getItemId(),
                   item.getItemName(),
                   item.getQuantity(),
                   item.getBuyPrice(),
                   item.getSellPrice()
           ));
           if (!b4) {
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
}
