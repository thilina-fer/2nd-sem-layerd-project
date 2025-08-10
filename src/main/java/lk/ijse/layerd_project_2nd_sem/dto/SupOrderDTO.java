package lk.ijse.layerd_project_2nd_sem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupOrderDTO {
    private String orderId;
    private String supplierId;
    private String date;
    private String supplierName;
}
