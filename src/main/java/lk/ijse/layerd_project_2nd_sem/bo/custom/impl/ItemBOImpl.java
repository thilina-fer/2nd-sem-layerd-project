package lk.ijse.layerd_project_2nd_sem.bo.custom.impl;

import lk.ijse.layerd_project_2nd_sem.bo.custom.ItemBO;
import lk.ijse.layerd_project_2nd_sem.dao.DAOFactory;
import lk.ijse.layerd_project_2nd_sem.dao.custom.ItemDAO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.dto.ItemDTO;
import lk.ijse.layerd_project_2nd_sem.entity.Item;

import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws Exception {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : items) {
            itemDTOS.add(new ItemDTO(
                    item.getItemId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getBuyPrice(),
                    item.getSellPrice()
            ));
        }
        return itemDTOS;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws Exception {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws Exception {
        return itemDAO.update(new Item(itemDTO.getItemId(),
                itemDTO.getItemName(),
                itemDTO.getQuantity(),
                itemDTO.getBuyPrice(),
                itemDTO.getSellPrice()
                ));
    }

    @Override
    public boolean deleteItem(String itemId) throws Exception {
        return itemDAO.delete(itemId);
    }

    @Override
    public String generateItemId() throws Exception {
        return itemDAO.generateNewId();
    }
}
