package lk.ijse.layerd_project_2nd_sem.dao.custom.impl;

import lk.ijse.layerd_project_2nd_sem.dao.SQLUtil;
import lk.ijse.layerd_project_2nd_sem.dao.custom.EmployeeAttendanceDAO;
import lk.ijse.layerd_project_2nd_sem.entity.EmployeeAttendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeAttendanceDAOImpl implements EmployeeAttendanceDAO {
    @Override
    public ArrayList<EmployeeAttendance> getAll() throws SQLException, ClassNotFoundException {
            ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM employee_attendance");
            ArrayList<EmployeeAttendance> attendance = new ArrayList<>();

            while (resultSet.next()) {
                EmployeeAttendance entity = new EmployeeAttendance(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                attendance.add(entity);
            }
            return attendance;
    }

    @Override
    public boolean save(EmployeeAttendance customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO employee_attendance VALUES(?,?,?,?,?)",
                customerDTO.getAttendanceId(),
                customerDTO.getEmployeeNic(),
                customerDTO.getDate(),
                customerDTO.getAttendTime(),
                customerDTO.getAttendTime()

        );
    }

    @Override
    public boolean update(EmployeeAttendance customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE employee_attendance SET emp_nic = ? , date = ? , attend_time = ? , duration = ? WHERE attendance_id = ?",
                customerDTO.getEmployeeNic(),
                customerDTO.getDate(),
                customerDTO.getAttendTime(),
                customerDTO.getDuration(),
                customerDTO.getAttendanceId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM employee_attendance WHERE attendance_id = ?",
                id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT attendance_id FROM employee_attendance ORDER BY attendance_id DESC LIMIT 1");
        char tableCharacter = 'A';
        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    @Override
    public ArrayList<EmployeeAttendance> search(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeAttendance> dtos = new ArrayList<>();
        String sql = "SELECT * FROM employee_attendance WHERE attendance_id LIKE ? OR emp_nic LIKE ? OR date LIKE ? OR attend_time LIKE ? OR duration LIKE ?";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql , pattern , pattern , pattern , pattern , pattern);

        while (resultSet.next()){
            EmployeeAttendance  employeeAttendance = new EmployeeAttendance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            dtos.add(employeeAttendance);
        }
        return dtos;
    }
}
