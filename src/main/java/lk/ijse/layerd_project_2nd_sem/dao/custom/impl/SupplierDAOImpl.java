package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.SupplierDAO;
import lk.ijse.layerd_project_2nd_sem.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM supplier");
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while (resultSet.next()) {
            Supplier entity = new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            suppliers.add(entity);
        }
        return suppliers;
    }

    @Override
    public boolean save(Supplier customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO supplier VALUES(?,?,?,?)",
                customerDTO.getSupplierId(),
                customerDTO.getSupplierName(),
                customerDTO.getSupplierContact(),
                customerDTO.getSupplierAddress()
        );
    }

    @Override
    public boolean update(Supplier customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE supplier SET supplier_name = ? , supplier_contact = ? , supplier_address = ? WHERE supplier_id = ?",
                customerDTO.getSupplierName(),
                customerDTO.getSupplierContact(),
                customerDTO.getSupplierAddress(),
                customerDTO.getSupplierId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM supplier WHERE supplier_id = ?",
                id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");
        char tableChartacter = 'S';

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
    public ArrayList<Supplier> search(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> dtos = new ArrayList<>();
        String sql = "SELECT * FROM supplier WHERE supplier_id LIKE ? OR supplier_name LIKE ? OR supplier_contact LIKE ? OR supplier_address LIKE ? ";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);

        while (resultSet.next()) {
            Supplier supplier = new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            dtos.add(supplier);
        }
        return dtos;
    }
}
