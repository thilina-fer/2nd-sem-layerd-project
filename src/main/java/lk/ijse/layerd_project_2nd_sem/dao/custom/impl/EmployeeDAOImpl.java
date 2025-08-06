package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.EmployeeDAO;
import lk.ijse.layerd_project_2nd_sem.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM employee");
        ArrayList<Employee> employees = new ArrayList<>();

        while(resultSet.next()){
            Employee entity = new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getDouble(7)
            );
            employees.add(entity);
        }
        return employees;
    }

    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO employee VALUES(?,?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getEmployeeName(),
                entity.getEmployeeContact(),
                entity.getEmployeeAddress(),
                entity.getEmployeeNic(),
                entity.getEmployeeAge(),
                entity.getSalary()
                );
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE employee SET emp_name = ? , emp_contact = ? , emp_address = ? , emp_nic = ? , emp_age = ? , salary = ? WHERE emp_id = ?",
                entity.getEmployeeName(),
                entity.getEmployeeContact(),
                entity.getEmployeeAddress(),
                entity.getEmployeeNic(),
                entity.getEmployeeAge(),
                entity.getSalary(),
                entity.getEmployeeId()
                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM employee WHERE emp_id = ?",
                id
                );
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1");
        char tableChartacter = 'E';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChartacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableChartacter + "001";
    }

    @Override
    public ArrayList<Employee> search(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> dtos = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE emp_id LIKE ? OR emp_name LIKE ? OR emp_contact LIKE ? OR emp_address LIKE ? OR emp_age LIKE ? OR salary LIKE ?";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern, pattern, pattern);

        while (resultSet.next()) {
            Employee employee = new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getDouble(7)
            );
            dtos.add(employee);
        }
        return dtos;
    }

    @Override
    public Employee find(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
