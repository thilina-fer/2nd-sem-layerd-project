package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.UserBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.UserDAO;
import lk.ijse.layerd_project_2nd_sem.dto.SupplierDTO;
import lk.ijse.layerd_project_2nd_sem.dto.UserDTO;
import lk.ijse.layerd_project_2nd_sem.entity.User;

import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public ArrayList<UserDTO> getAllIUser() throws Exception {
        ArrayList<User> users = userDAO.getAll();
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getContact(),
                    user.getAddress(),
                    user.getRole()
            ));
        }
        return  userDTOs;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws Exception {
        return userDAO.save(new User(
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getContact(),
                userDTO.getAddress(),
                userDTO.getRole()
        ));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws Exception {
        return userDAO.update(new User(
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getContact(),
                userDTO.getAddress(),
                userDTO.getRole()
        ));
    }

    @Override
    public boolean deleteUser(String userId) throws Exception {
        return userDAO.delete(userId);
    }

    @Override
    public String generateUserId() throws Exception {
        return userDAO.generateNewId();
    }

    @Override
    public ArrayList<UserDTO> searchUser(String text) throws Exception {
        ArrayList<User> users = userDAO.search(text);
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getContact(),
                    user.getAddress(),
                    user.getRole()
            ));
        }
        return userDTOs;
    }
}
