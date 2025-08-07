package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws Exception;
    boolean saveItem(ItemDTO itemDTO) throws Exception;
    boolean updateItem(ItemDTO itemDTO) throws Exception;
    boolean deleteItem(String itemId) throws Exception;
    String generateItemId() throws Exception;
    ArrayList<ItemDTO> searchItem(String text) throws Exception;
    boolean existItem(String id) throws SQLException, ClassNotFoundException;
}
