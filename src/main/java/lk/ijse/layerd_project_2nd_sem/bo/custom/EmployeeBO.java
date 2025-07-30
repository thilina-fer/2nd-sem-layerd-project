package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.dto.EmployeeDTO;

import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getAllEmployee() throws Exception;
    boolean saveEmployee(EmployeeDTO employeeDTO) throws Exception;
    boolean updateEmployee(EmployeeDTO employeeDTO) throws Exception;
    boolean deleteEmployee(String employeeId) throws Exception;
    String generateEmployeeId() throws Exception;
    ArrayList<EmployeeDTO> searchEmployee(String text) throws Exception;
}
