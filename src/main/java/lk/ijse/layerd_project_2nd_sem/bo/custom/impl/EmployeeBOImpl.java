package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.EmployeeBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.EmployeeDAO;
import lk.ijse.layerd_project_2nd_sem.dto.EmployeeDTO;
import lk.ijse.layerd_project_2nd_sem.entity.Employee;

import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws Exception {
       ArrayList<Employee> entity = employeeDAO.getAll();
       ArrayList<EmployeeDTO> employeeDto = new ArrayList<>();
       for (Employee e : entity) {
           employeeDto.add(new EmployeeDTO(
                     e.getEmployeeId(),
                     e.getEmployeeName(),
                     e.getEmployeeContact(),
                     e.getEmployeeAddress(),
                     e.getEmployeeNic(),
                     e.getEmployeeAge(),
                     e.getSalary()
           ));
       }
       return employeeDto;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws Exception {
        return employeeDAO.save(new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getEmployeeName(),
                employeeDTO.getEmployeeContact(),
                employeeDTO.getEmployeeAddress(),
                employeeDTO.getEmployeeNic(),
                employeeDTO.getEmployeeAge(),
                employeeDTO.getSalary()
        ));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws Exception {
        return employeeDAO.update(new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getEmployeeName(),
                employeeDTO.getEmployeeContact(),
                employeeDTO.getEmployeeAddress(),
                employeeDTO.getEmployeeNic(),
                employeeDTO.getEmployeeAge(),
                employeeDTO.getSalary()
        ));
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws Exception {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public String generateEmployeeId() throws Exception {
        return employeeDAO.generateNewId();
    }
}
