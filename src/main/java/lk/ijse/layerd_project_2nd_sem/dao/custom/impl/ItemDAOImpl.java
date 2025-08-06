package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.ItemDAO;
import lk.ijse.layerd_project_2nd_sem.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM item");
        ArrayList<Item> items = new ArrayList<>();

        while (resultSet.next()) {
            Item entity = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
            items.add(entity);
        }
        return items;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO item VALUES(?,?,?,?,?)",
                entity.getItemId(),
                entity.getItemName(),
                entity.getQuantity(),
                entity.getBuyPrice(),
                entity.getSellPrice()
        );
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE item SET item_name = ? , quantity = ? , buying_price = ? , selling_price = ? WHERE item_id = ?",
                entity.getItemName(),
                entity.getQuantity(),
                entity.getBuyPrice(),
                entity.getSellPrice(),
                entity.getItemId()
        );
    }

    @Override
    public boolean delete(String itemId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM item WHERE item_id = ?",
                itemId);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1");
        char tableChartacter = 'I';

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
    public ArrayList<Item> search(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Item> dtos = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE item_id LIKE ? OR item_name LIKE ? OR quantity LIKE ? OR buying_price LIKE ? OR selling_price LIKE ?";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql , pattern , pattern , pattern , pattern , pattern);

        while (resultSet.next()){
          Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
            dtos.add(item);
        }
        return dtos;
    }

    @Override
    public Item find(String id) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM item WHERE item_id = ?", id);
       resultSet.next();
       return new Item(id,
               resultSet.getString("item_name"),
               resultSet.getInt("quantity"),
               resultSet.getDouble("buying_price"),
               resultSet.getDouble("selling_price")
               );
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT item_id FROM item WHERE item_id = ?", id);
        return resultSet.next();
    }
}
