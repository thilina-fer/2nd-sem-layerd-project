package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.UserBO;
import lk.ijse.layerd_project_2nd_sem.dto.SupplierDTO;
import lk.ijse.layerd_project_2nd_sem.dto.UserDTO;

import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    @Override
    public ArrayList<SupplierDTO> getAllIUser() throws Exception {
        return null;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws Exception {
        return false;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws Exception {
        return false;
    }

    @Override
    public boolean deleteUser(String userId) throws Exception {
        return false;
    }

    @Override
    public String generateUserId() throws Exception {
        return "";
    }
}
