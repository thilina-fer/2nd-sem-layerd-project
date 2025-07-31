package lk.ijse.layerd_project_2nd_sem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PreOrderDTO {
    private String preOrderId;
    private String userId;
    private String itemId;
    private double advance;
}
