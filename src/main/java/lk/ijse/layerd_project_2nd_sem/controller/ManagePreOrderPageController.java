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
import lk.ijse.layerd_project_2nd_sem.bo.custom.PreOrderBO;
import lk.ijse.layerd_project_2nd_sem.bo.custom.UserBO;
import lk.ijse.layerd_project_2nd_sem.dto.PreOrderDTO;
import lk.ijse.layerd_project_2nd_sem.view.PreOrderTM;

import java.util.Optional;

public class ManagePreOrderPageController {

    public AnchorPane ancPreOrderPage;
    public Label lblPreOrderId;
    public ComboBox comboUserId;
    public ComboBox comboItemId;
    public TextField txtAdvance;

    public TableView<PreOrderTM> tblPreOrder;
    public TableColumn<PreOrderTM , String> colPreId;
    public TableColumn<PreOrderTM , String> colUserId;
    public TableColumn<PreOrderTM , String> colItemId;
    public TableColumn<PreOrderTM , Double> colAdvance;


    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TextField searchField;

    PreOrderBO preOrderBO = (PreOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRE_ORDER);

    public void initialize() {
        colPreId.setCellValueFactory(new PropertyValueFactory<>("preOrderId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("advance"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK).show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            loadUserId();
            loadItemDetils();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            comboUserId.getSelectionModel().clearSelection();
            comboItemId.getSelectionModel().clearSelection();
            txtAdvance.setText(null);

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = preOrderBO.generatePreOrderId();
        lblPreOrderId.setText(nextId);
    }

    private void loadItemDetils() {
        try {
            comboItemId.setItems(FXCollections.observableArrayList(preOrderBO.getAllItemId()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load item IDs: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    private void loadUserId() {
        try {
            comboUserId.setItems(FXCollections.observableArrayList(preOrderBO.getAllUserId()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load user IDs: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    private void loadTableData() throws Exception {
        tblPreOrder.setItems(FXCollections.observableArrayList(
                preOrderBO.getAllPreOrder().stream().map(
                        preOrderDTO -> new PreOrderTM(
                                preOrderDTO.getPreOrderId(),
                                preOrderDTO.getUserId(),
                                preOrderDTO.getItemId(),
                                preOrderDTO.getAdvance()
                        )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String preOrderId = lblPreOrderId.getText();
        String userId = (String) comboUserId.getValue();
        String itemId = (String) comboItemId.getValue();
        double advance = Double.parseDouble(txtAdvance.getText());

        try {
            preOrderBO.savePreOrder(new PreOrderDTO(
                    preOrderId,
                    userId,
                    itemId,
                    advance
            ));

            tblPreOrder.getItems().add(new PreOrderTM(
                    preOrderId,
                    userId,
                    itemId,
                    advance
            ));
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed Save Order"+ e.getMessage(),ButtonType.OK).show();
        }finally {
            new Alert(Alert.AlertType.INFORMATION,"Successfully saved order",ButtonType.OK).show();
            resetPage();

        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String preOrderId = lblPreOrderId.getText();
        String userId = (String) comboUserId.getValue();
        String itemId = (String) comboItemId.getValue();
        double advance = Double.parseDouble(txtAdvance.getText());

        try {
            preOrderBO.updatePreOrder(new PreOrderDTO(
                    preOrderId,
                    userId,
                    itemId,
                    advance
            ));

            tblPreOrder.getItems().add(new PreOrderTM(
                    preOrderId,
                    userId,
                    itemId,
                    advance
            ));
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed Update Order"+ e.getMessage(),ButtonType.OK).show();
        }finally {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION,"Successfully updated order",ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this pre-order?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String preOrderId = lblPreOrderId.getText();
            try {
                preOrderBO.deletePreOrder(preOrderId);
                tblPreOrder.getItems().removeIf(preOrderTM -> preOrderTM.getPreOrderId().equals(preOrderId));
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Successfully deleted pre-order", ButtonType.OK).show();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete pre-order: " + e.getMessage(), ButtonType.OK).show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PreOrderTM selectedItem = tblPreOrder.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblPreOrderId.setText(selectedItem.getPreOrderId());
            comboUserId.getSelectionModel().select(selectedItem.getUserId());
            comboItemId.getSelectionModel().select(selectedItem.getItemId());
            txtAdvance.setText(String.valueOf(selectedItem.getAdvance()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void navigateTo(String path) {
        try {
            ancPreOrderPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancPreOrderPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPreOrderPage.heightProperty());

            ancPreOrderPage.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void goToHomePage(ActionEvent actionEvent) {
        navigateTo("/DashboardPage.fxml");
    }

    public void gotoItemPage(MouseEvent mouseEvent) {
        navigateTo("/ItemPage.fxml");
    }

    public void search(KeyEvent keyEvent) {
        String searchText = searchField.getText();
        try {
            tblPreOrder.setItems(FXCollections.observableArrayList(
                    preOrderBO.searchPreOrder(searchText).stream().map(
                            preOrderDTO -> new PreOrderTM(
                                    preOrderDTO.getPreOrderId(),
                                    preOrderDTO.getUserId(),
                                    preOrderDTO.getItemId(),
                                    preOrderDTO.getAdvance()
                            )).toList()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to search: " + e.getMessage(), ButtonType.OK).show();
        }
    }










}
