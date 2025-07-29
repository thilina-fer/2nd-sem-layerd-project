package lk.ijse.layerd_project_2nd_sem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeAttendanceDTO {
    private String attendanceId;
    private String employeeNic;
    private String date;
    private String attendTime;
    private String duration;
}
