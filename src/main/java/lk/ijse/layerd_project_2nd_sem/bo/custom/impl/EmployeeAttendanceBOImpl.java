package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.EmployeeAttendanceBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.EmployeeAttendanceDAO;
import lk.ijse.layerd_project_2nd_sem.dto.EmployeeAttendanceDTO;
import lk.ijse.layerd_project_2nd_sem.entity.EmployeeAttendance;

import java.util.ArrayList;

public class EmployeeAttendanceBOImpl implements EmployeeAttendanceBO {

    EmployeeAttendanceDAO employeeAttendanceDAO = (EmployeeAttendanceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE_ATTENDANCE);
    @Override
    public ArrayList<EmployeeAttendanceDTO> getAllEmployeeAttendance() throws Exception {
       ArrayList<EmployeeAttendance> attendances = employeeAttendanceDAO.getAll();
       ArrayList<EmployeeAttendanceDTO> employeeAttendanceDTOS = new ArrayList<>();
         for (EmployeeAttendance attendance : attendances) {
              employeeAttendanceDTOS.add(new EmployeeAttendanceDTO(
                     attendance.getAttendanceId(),
                     attendance.getEmployeeNic(),
                     attendance.getDate(),
                     attendance.getAttendTime(),
                     attendance.getDuration()
              ));
         }
         return employeeAttendanceDTOS;
    }

    @Override
    public boolean saveEmployeeAttendance(EmployeeAttendanceDTO employeeAttendanceDTO) throws Exception {
        return employeeAttendanceDAO.save(new EmployeeAttendance(
               employeeAttendanceDTO.getAttendanceId(),
               employeeAttendanceDTO.getEmployeeNic(),
               employeeAttendanceDTO.getDate(),
               employeeAttendanceDTO.getAttendTime(),
               employeeAttendanceDTO.getDuration()
        ));
    }

    @Override
    public boolean updateEmployeeAttendance(EmployeeAttendanceDTO employeeAttendanceDTO) throws Exception {
        return employeeAttendanceDAO.update(new EmployeeAttendance(
                employeeAttendanceDTO.getAttendanceId(),
                employeeAttendanceDTO.getEmployeeNic(),
                employeeAttendanceDTO.getDate(),
                employeeAttendanceDTO.getAttendTime(),
                employeeAttendanceDTO.getDuration()
        ));
    }

    @Override
    public boolean deleteEmployeeAttendance(String attendanceId) throws Exception {
        return employeeAttendanceDAO.delete(attendanceId);
    }

    @Override
    public String generateEmployeeAttendanceId() throws Exception {
        return employeeAttendanceDAO.generateNewId();
    }
}
