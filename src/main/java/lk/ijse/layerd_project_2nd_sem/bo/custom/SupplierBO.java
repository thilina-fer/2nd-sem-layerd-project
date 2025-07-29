package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.ItemDTO;
import lk.ijse.layerd_project_2nd_sem.dto.SupplierDTO;

import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDTO> getAllISupplier() throws Exception;
    boolean saveSupplier(SupplierDTO supplierDTO) throws Exception;
    boolean updateSupplier(SupplierDTO supplierDTO) throws Exception;
    boolean deleteSupplier(String supplierId) throws Exception;
    String generateSupplierId() throws Exception;
}
