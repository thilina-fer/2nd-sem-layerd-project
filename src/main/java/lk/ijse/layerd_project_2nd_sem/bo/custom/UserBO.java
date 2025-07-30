package lk.ijse.layerd_project_2nd_sem.bo.custom;

import lk.ijse.layerd_project_2nd_sem.bo.SuperBO;
import lk.ijse.layerd_project_2nd_sem.dto.UserDTO;

import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<UserDTO> getAllIUser() throws Exception;
    boolean saveUser(UserDTO userDTO) throws Exception;
    boolean updateUser(UserDTO userDTO) throws Exception;
    boolean deleteUser(String userId) throws Exception;
    String generateUserId() throws Exception;
    ArrayList<UserDTO> searchUser(String text) throws Exception;
}
