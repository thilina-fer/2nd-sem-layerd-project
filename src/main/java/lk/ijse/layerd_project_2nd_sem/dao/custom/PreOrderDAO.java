package lk.ijse.layerd_project_2nd_sem.dao.custom;

import lk.ijse.layerd_project_2nd_sem.dao.CrudDAO;
import lk.ijse.layerd_project_2nd_sem.dto.PreOrderDTO;
import lk.ijse.layerd_project_2nd_sem.entity.PreOrder;

import java.util.ArrayList;

public interface PreOrderDAO extends CrudDAO<PreOrder> {
    ArrayList<String> getAllUserId() throws Exception;
    ArrayList<String> getAllItemId() throws Exception;
}
