package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.OrderDAO;
import lk.ijse.layerd_project_2nd_sem.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO orders VALUES(?, ?, ?)",
                entity.getOrderId(),
                entity.getCustomerContact(),
                entity.getDate()
        );
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
        if (rs.next()) {
            String lastId = rs.getString(1);
            int id = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("O%03d", id);
        } else {
            return "O001";
        }
    }

    @Override
    public ArrayList<Order> search(String searchText) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order find(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT order_id FROM orders WHERE order_id = ?", id).next();
    }
}
