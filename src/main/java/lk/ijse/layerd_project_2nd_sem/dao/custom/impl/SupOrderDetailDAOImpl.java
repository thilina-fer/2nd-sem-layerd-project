package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.SupOrderDetailDAO;
import lk.ijse.layerd_project_2nd_sem.dto.SupOrderDetailDTO;
import lk.ijse.layerd_project_2nd_sem.entity.OrderDetail;
import lk.ijse.layerd_project_2nd_sem.entity.SupOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupOrderDetailDAOImpl implements SupOrderDetailDAO {


    @Override
    public ArrayList<SupOrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(SupOrderDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO sup_order_details (sup_order_id, item_id, qty, unit_price) VALUES (?, ?, ?, ?)",
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    @Override
    public boolean update(SupOrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }



    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<SupOrderDetails> search(String searchText) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SupOrderDetails find(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
