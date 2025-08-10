package lk.ijse.layerd_project_2nd_sem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupOrderDetails {
    private String orderId;
    private String itemId;
    private int quantity;
    private double unitPrice;
}
