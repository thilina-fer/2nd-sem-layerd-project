package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.UserDAO;
import lk.ijse.layerd_project_2nd_sem.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM users");
        ArrayList<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User entity = new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
            users.add(entity);
        }
        return users;
    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO users VALUES(?,?,?,?,?,?,?)",
                entity.getUserId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getContact(),
                entity.getAddress(),
                entity.getRole()
        );
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE users SET user_name = ?, password = ?, email = ?, contact = ?, address = ?, role = ? WHERE user_id = ?",
                entity.getUserName(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getContact(),
                entity.getAddress(),
                entity.getRole(),
                entity.getUserId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM users WHERE user_id = ?",
                id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");
        char tableChartacter = 'U';

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
    public ArrayList<User> search(String searchText) throws SQLException, ClassNotFoundException {
        return null;
    }
}
