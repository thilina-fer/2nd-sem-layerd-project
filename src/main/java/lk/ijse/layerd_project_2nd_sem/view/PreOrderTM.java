package lk.ijse.layerd_project_2nd_sem.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PreOrderTM {
    private String preOrderId;
    private String userId;
    private String itemId;
    private double advance;
}
