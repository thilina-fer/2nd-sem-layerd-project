package lk.ijse.layerd_project_2nd_sem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupOrderDetailDTO {
    private String orderId;
    private String itemId;
    private int quantity;
    private double unitPrice;
}
