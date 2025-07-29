package lk.ijse.layerd_project_2nd_sem.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeAttendanceTM {
    private String attendanceId;
    private String employeeNic;
    private String date;
    private String attendTime;
    private String duration;
}
