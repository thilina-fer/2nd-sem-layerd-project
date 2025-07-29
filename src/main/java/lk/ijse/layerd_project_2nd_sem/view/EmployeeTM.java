package lk.ijse.layerd_project_2nd_sem.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeTM {
    private String employeeId;
    private String employeeName;
    private String employeeContact;
    private String employeeAddress;
    private String employeeNic;
    private int employeeAge;
    private double salary;
}
