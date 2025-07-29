package lk.ijse.layerd_project_2nd_sem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String supplierContact;
    private String supplierAddress;
}
