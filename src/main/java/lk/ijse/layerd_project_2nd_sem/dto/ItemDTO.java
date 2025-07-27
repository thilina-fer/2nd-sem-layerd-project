package lk.ijse.layerd_project_2nd_sem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ItemDTO {
    private String itemId;
    private String itemName;
    private int quantity;
    private double buyPrice;
    private double sellPrice;
}
