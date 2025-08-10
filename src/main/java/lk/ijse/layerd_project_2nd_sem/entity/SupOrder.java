package lk.ijse.layerd_project_2nd_sem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupOrder {
    private String orderId;
    private String supplierId;
    private String date;
    private String supplierName;

    public SupOrder(String orderId, String supplierId, LocalDate orderDate) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.date = orderDate.toString();
    }
}
