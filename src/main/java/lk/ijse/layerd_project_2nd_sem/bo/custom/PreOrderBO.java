package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.EmployeeDTO;
import lk.ijse.layerd_project_2nd_sem.dto.PreOrderDTO;

import java.util.ArrayList;

public interface PreOrderBO extends SuperBO {
    ArrayList<PreOrderDTO> getAllPreOrder() throws Exception;
    boolean savePreOrder(PreOrderDTO preOrderDTO) throws Exception;
    boolean updatePreOrder(PreOrderDTO preOrderDTO) throws Exception;
    boolean deletePreOrder(String preOrderId) throws Exception;
    String generatePreOrderId() throws Exception;
    ArrayList<PreOrderDTO> searchPreOrder(String text) throws Exception;
    ArrayList<String> getAllUserId() throws Exception;
    ArrayList<String> getAllItemId() throws Exception;

}
