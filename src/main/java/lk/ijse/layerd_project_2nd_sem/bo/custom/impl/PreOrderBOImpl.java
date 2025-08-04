package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.PreOrderBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.PreOrderDAO;
import lk.ijse.layerd_project_2nd_sem.dto.PreOrderDTO;
import lk.ijse.layerd_project_2nd_sem.entity.PreOrder;

import java.util.ArrayList;

public class PreOrderBOImpl implements PreOrderBO {

    PreOrderDAO preOrderDAO = (PreOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PRE_ORDER);

    @Override
    public ArrayList<PreOrderDTO> getAllPreOrder() throws Exception {
        ArrayList<PreOrder> preOrders = preOrderDAO.getAll();
        ArrayList<PreOrderDTO> preOrderDTOS = new ArrayList<>();
        for (PreOrder preOrder : preOrders) {
            preOrderDTOS.add(new PreOrderDTO(
                    preOrder.getPreOrderId(),
                    preOrder.getUserId(),
                    preOrder.getItemId(),
                    preOrder.getAdvance()
            ));
        }
        return preOrderDTOS;
    }

    @Override
    public boolean savePreOrder(PreOrderDTO preOrderDTO) throws Exception {
        return preOrderDAO.save(new PreOrder(
                preOrderDTO.getPreOrderId(),
                preOrderDTO.getUserId(),
                preOrderDTO.getItemId(),
                preOrderDTO.getAdvance()
        ));
    }

    @Override
    public boolean updatePreOrder(PreOrderDTO preOrderDTO) throws Exception {
        return preOrderDAO.update(new PreOrder(
                preOrderDTO.getPreOrderId(),
                preOrderDTO.getUserId(),
                preOrderDTO.getItemId(),
                preOrderDTO.getAdvance()
        ));
    }

    @Override
    public boolean deletePreOrder(String preOrderId) throws Exception {
        return preOrderDAO.delete(preOrderId);
    }

    @Override
    public String generatePreOrderId() throws Exception {
        return preOrderDAO.generateNewId();
    }

    @Override
    public ArrayList<PreOrderDTO> searchPreOrder(String text) throws Exception {
        ArrayList<PreOrder> preOrders = preOrderDAO.search(text);
        ArrayList<PreOrderDTO> preOrderDTOS = new ArrayList<>();
        for (PreOrder preOrder : preOrders) {
            preOrderDTOS.add(new PreOrderDTO(
                    preOrder.getPreOrderId(),
                    preOrder.getUserId(),
                    preOrder.getItemId(),
                    preOrder.getAdvance()
            ));
        }
        return preOrderDTOS;
    }


    @Override
    public ArrayList<String> getAllItemId() throws Exception {
        return preOrderDAO.getAllItemId();
    }

    @Override
    public ArrayList<String> getAllUserId() throws Exception {
       return preOrderDAO.getAllUserId();
    }
}
