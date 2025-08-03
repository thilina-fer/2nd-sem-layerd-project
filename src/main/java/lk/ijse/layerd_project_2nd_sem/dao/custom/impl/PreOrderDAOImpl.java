package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.PreOrderDAO;
import lk.ijse.layerd_project_2nd_sem.entity.PreOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreOrderDAOImpl implements PreOrderDAO {

    @Override
    public ArrayList<PreOrder> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM pre_order");
        ArrayList<PreOrder> preOrders = new ArrayList<>();
        while (resultSet.next()) {
            PreOrder preOrder = new PreOrder(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(5)
            );
            preOrders.add(preOrder);
        }
        return preOrders;
    }

    @Override
    public boolean save(PreOrder customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO pre_order_manage VALUES(?,?,?,?)",
                customerDTO.getPreOrderId(),
                customerDTO.getUserId(),
                customerDTO.getItemId(),
                customerDTO.getAdvance()
        );
    }

    @Override
    public boolean update(PreOrder customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE pre_order_manage SET user_id = ? , item_id = ? , advance_payment = ? WHERE  pre_order_id = ? ",
                customerDTO.getUserId(),
                customerDTO.getItemId(),
                customerDTO.getAdvance(),
                customerDTO.getPreOrderId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM pre_order_manage WHERE pre_order_id = ?",
                id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT pre_order_id FROM pre_order_manage ORDER BY pre_order_id DESC LIMIT 1");
        String tableChartacter = "PR";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(tableChartacter.length());
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChartacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableChartacter + "001";
    }

    @Override
    public ArrayList<PreOrder> search(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<PreOrder> dtos = new ArrayList<>();
        String sql = "SELECT * FROM pre_order_manage WHERE pre_order_id LIKE ? OR user_id LIKE ? OR item_id LIKE ? OR advance_payment LIKE ?";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql , pattern , pattern , pattern , pattern);

        while (resultSet.next()) {
            PreOrder dto = new PreOrder(
                    resultSet.getString("pre_order_id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("item_id"),
                    resultSet.getDouble("advance_payment")
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
