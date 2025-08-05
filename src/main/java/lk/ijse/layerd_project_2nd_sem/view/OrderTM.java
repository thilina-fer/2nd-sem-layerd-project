package lk.ijse.layerd_project_2nd_sem.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderTM {
    private String orderId;
    private String customerContact;
    private String customerName;
    private String date;
    private Double OrderTotal;
}
