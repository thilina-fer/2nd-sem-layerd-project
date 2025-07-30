package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.SupplierBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.SupplierDAO;
import lk.ijse.layerd_project_2nd_sem.dto.SupplierDTO;
import lk.ijse.layerd_project_2nd_sem.entity.Supplier;

import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public ArrayList<SupplierDTO> getAllISupplier() throws Exception {
        ArrayList<Supplier> supplier = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();
        for (Supplier s : supplier) {
            supplierDTOs.add(new SupplierDTO(
                   s.getSupplierId(),
                    s.getSupplierName(),
                    s.getSupplierContact(),
                    s.getSupplierAddress()
            ));
        }
        return  supplierDTOs;
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws Exception {
        return supplierDAO.save(new Supplier(
                supplierDTO.getSupplierId(),
                supplierDTO.getSupplierName(),
                supplierDTO.getSupplierContact(),
                supplierDTO.getSupplierAddress()
        ));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws Exception {
        return supplierDAO.update(new Supplier(
                supplierDTO.getSupplierId(),
                supplierDTO.getSupplierName(),
                supplierDTO.getSupplierContact(),
                supplierDTO.getSupplierAddress()
        ));
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws Exception {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public String generateSupplierId() throws Exception {
        return supplierDAO.generateNewId();
    }

    @Override
    public ArrayList<SupplierDTO> searchSupplier(String text) throws Exception {
        ArrayList<Supplier> suppliers = supplierDAO.search(text);
        ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();
        for (Supplier s : suppliers) {
            supplierDTOs.add(new SupplierDTO(
                    s.getSupplierId(),
                    s.getSupplierName(),
                    s.getSupplierContact(),
                    s.getSupplierAddress()
            ));
        }
        return supplierDTOs;
    }
}
