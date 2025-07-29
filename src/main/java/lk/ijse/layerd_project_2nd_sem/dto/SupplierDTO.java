package lk.ijse.layerd_project_2nd_sem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupplierDTO {
    private String supplierId;
    private String supplierName;
    private String supplierContact;
    private String supplierAddress;
}
