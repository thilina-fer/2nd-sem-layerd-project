package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.OrderDetailDAO;
import lk.ijse.layerd_project_2nd_sem.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO order_details (order_id, item_id, qty, unit_price) VALUES (?, ?, ?, ?)",
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
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
    public ArrayList<OrderDetail> search(String searchText) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetail find(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
