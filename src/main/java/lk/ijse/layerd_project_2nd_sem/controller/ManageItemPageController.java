package lk.ijse.layerd_project_2nd_sem.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.layerd_project_2nd_sem.bo.BOFactory;
import lk.ijse.layerd_project_2nd_sem.bo.custom.ItemBO;
import lk.ijse.layerd_project_2nd_sem.dto.ItemDTO;
import lk.ijse.layerd_project_2nd_sem.view.ItemTM;

import java.util.Optional;

public class ManageItemPageController {
    public Label lblItemId;
    public TextField txtName;
    public TextField txtQuantity;
    public TextField txtBuyingPrice;
    public TextField txtSellingPrice;
    public AnchorPane ancItemPage;
    public TextField searchField;

    public TableView<ItemTM> tblItem;
    public TableColumn<ItemTM , String> colId;
    public TableColumn<ItemTM , String> colName;
    public TableColumn<ItemTM , Integer> colQuantity;
    public TableColumn<ItemTM , Double> colBuyPrice;
    public TableColumn<ItemTM , Double> colSellPrice;
    

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String quantityPattern = "^\\d+$";
    private final String buyingPricePattern = "^\\d+(\\.\\d{1,2})?$";
    private final String sellingPricePattern = "^\\d+(\\.\\d{1,2})?$";
    public Hyperlink goToHomePage;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colBuyPrice.setCellValueFactory(new PropertyValueFactory<>("BuyPrice"));
        colSellPrice.setCellValueFactory(new PropertyValueFactory<>("SellPrice"));

        try {
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void loadTableData() throws Exception {
        tblItem.setItems(FXCollections.observableArrayList(
                itemBO.getAllItems().stream().map(
                         itemDTO -> new ItemTM(
                               itemDTO.getItemId(),
                                itemDTO.getItemName(),
                                itemDTO.getQuantity(),
                                itemDTO.getBuyPrice(),
                                itemDTO.getSellPrice()
                        )).toList()
        ));
    }

    private void resetPage() throws Exception {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText(null);
            txtQuantity.setText(null);
            txtBuyingPrice.setText(null);
            txtSellingPrice.setText(null);
        } catch (Exception e) {
            throw new Exception("Failed to reset the page: " + e.getMessage());
        }
    }

    private void loadNextId() throws Exception {
        String id = itemBO.generateItemId();
        lblItemId.setText(id);
    }

    public void btnSaveOnAction(ActionEvent event) throws Exception {
        String itemId = lblItemId.getText();
        String itemName = txtName.getText();
        String qty = txtQuantity.getText();
        String buyingPrice = txtBuyingPrice.getText();
        String sellingPrice = txtSellingPrice.getText();

        boolean isValidQuantity = qty.matches(quantityPattern);
        boolean isValidBuyPrice = buyingPrice.matches(buyingPricePattern);
        boolean isValidSellPrice = sellingPrice.matches(sellingPricePattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0");
        txtBuyingPrice.setStyle(txtBuyingPrice.getStyle() + ";-fx-border-color: #7367F0");
        txtSellingPrice.setStyle(txtSellingPrice.getStyle() + ";-fx-border-color: #7367F0");

        if (!isValidQuantity) txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        if (!isValidBuyPrice) txtBuyingPrice.setStyle(txtBuyingPrice.getStyle() + ";-fx-border-color: red;");
        if (!isValidSellPrice) txtSellingPrice.setStyle(txtSellingPrice.getStyle() + ";-fx-border-color: red;");

        int presedQuantity = Integer.parseInt(qty);
        double presedBuyPrice = Double.parseDouble(buyingPrice);
        double presedSellPrice = Double.parseDouble(sellingPrice);

        try {
            itemBO.saveItem(new ItemDTO(
                    itemId,
                    itemName,
                    presedQuantity,
                    presedBuyPrice,
                    presedSellPrice
            ));

            tblItem.getItems().add(new ItemTM(
                    itemId,
                    itemName,
                    presedQuantity,
                    presedBuyPrice,
                    presedSellPrice
            ));
            resetPage();
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save item: " + e.getMessage()).show();
        }finally {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "Item saved successfully").show();
        }
    }

    public void search(KeyEvent event) {
    }



    public void btnUpdateOnAction(ActionEvent event) throws Exception {
        String itemId = lblItemId.getText();
        String itemName = txtName.getText();
        String qty = txtQuantity.getText();
        String buyingPrice = txtBuyingPrice.getText();
        String sellingPrice = txtSellingPrice.getText();

        int presedQuantity = Integer.parseInt(qty);
        double presedBuyPrice = Double.parseDouble(buyingPrice);
        double presedSellPrice = Double.parseDouble(sellingPrice);

        try {
            itemBO.updateItem(new ItemDTO(
                    itemId,
                    itemName,
                    presedQuantity,
                    presedBuyPrice,
                    presedSellPrice
            ));

            tblItem.getItems().add(new ItemTM(
                    itemId,
                    itemName,
                    presedQuantity,
                    presedBuyPrice,
                    presedSellPrice
            ));
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update item: " + e.getMessage()).show();
        }finally {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "Item updated successfully").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this item?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String itemId = lblItemId.getText();
            try {
                itemBO.deleteItem(itemId);
               tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem().getItemId());
               tblItem.getSelectionModel().clearSelection();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete item: " + e.getMessage()).show();
            }finally {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully").show();
                resetPage();
            }
        }
    }

    public void btnResetOnAction(ActionEvent event) throws Exception {
        resetPage();
    }

    public void btnReportOnAction(ActionEvent event) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblItemId.setText(selectedItem.getItemId());
            txtName.setText(selectedItem.getItemName());
            txtQuantity.setText(String.valueOf(selectedItem.getQuantity()));
            txtBuyingPrice.setText(String.valueOf(selectedItem.getBuyPrice()));
            txtSellingPrice.setText(String.valueOf(selectedItem.getSellPrice()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void navigateTo(String path) {
        try {
            ancItemPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancItemPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancItemPage.heightProperty());

            ancItemPage.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }
    public void goToHomePage(ActionEvent actionEvent) {
        navigateTo("/DashboardPage.fxml");
    }
}
