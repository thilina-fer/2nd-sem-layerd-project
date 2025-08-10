package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.SupOrderDAO;
import lk.ijse.layerd_project_2nd_sem.entity.SupOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupIrderDAOImpl implements SupOrderDAO {
    @Override
    public ArrayList<SupOrder> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(SupOrder customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO supplier_orders VALUES(?,?,?)",
                customerDTO.getOrderId(),
                customerDTO.getSupplierId(),
                customerDTO.getDate()
        );
    }

    @Override
    public boolean update(SupOrder customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT sup_order_id FROM supplier_orders ORDER BY sup_order_id DESC LIMIT 1");
        String  tableString = "SO";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(tableString.length());
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableString + "001";
    }


    @Override
    public ArrayList<SupOrder> search(String searchText) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SupOrder find(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT order_id FROM orders WHERE order_id = ?",
                id).next();
    }
}
