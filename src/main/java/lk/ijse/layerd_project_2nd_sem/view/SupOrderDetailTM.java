package lk.ijse.layerd_project_2nd_sem.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupOrderDetailTM {
    private String supplierId;
    private String itemId;
    private String itemName;
    private int quantity;
    private double unitPrice;
}
