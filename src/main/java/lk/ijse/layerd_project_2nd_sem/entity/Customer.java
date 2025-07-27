package lk.ijse.layerd_project_2nd_sem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Customer {
    private String customerId;
    private String customerName;
    private String customerContact;
    private String customerAddress;
}
