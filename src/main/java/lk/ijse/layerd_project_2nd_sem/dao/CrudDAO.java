package lk.ijse.layerd_project_2nd_sem.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    boolean save(T customerDTO) throws SQLException, ClassNotFoundException;
    boolean update(T customerDTO) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    String generateNewId() throws SQLException, ClassNotFoundException;
    ArrayList<T> search (String searchText) throws SQLException, ClassNotFoundException;
}
