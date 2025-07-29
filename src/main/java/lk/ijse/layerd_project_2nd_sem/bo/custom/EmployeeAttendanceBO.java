package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.dto.EmployeeAttendanceDTO;

import java.util.ArrayList;

public interface EmployeeAttendanceBO extends SuperBO {
    ArrayList<EmployeeAttendanceDTO> getAllEmployeeAttendance() throws Exception;
    boolean saveEmployeeAttendance(EmployeeAttendanceDTO employeeAttendanceDTO) throws Exception;
    boolean updateEmployeeAttendance(EmployeeAttendanceDTO employeeAttendanceDTO) throws Exception;
    boolean deleteEmployeeAttendance(String attendanceId) throws Exception;
    String generateEmployeeAttendanceId() throws Exception;
}
