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
import lk.ijse.layerd_project_2nd_sem.bo.custom.SupplierBO;
import lk.ijse.layerd_project_2nd_sem.dto.SupplierDTO;
import lk.ijse.layerd_project_2nd_sem.view.SupplierTM;

import java.util.Optional;

public class ManageSupplierPageController {
    public Label lblSupplierId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;
    public AnchorPane ancSupplierPage;

    public TableView<SupplierTM> tblSupplier;
    public TableColumn<SupplierTM , String> colId;
    public TableColumn<SupplierTM , String> colName;
    public TableColumn<SupplierTM , String> colContact;
    public TableColumn<SupplierTM , String> colAddress;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public TextField searchField;


    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));

        try {
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
            btnSave.setDisable(false);

            txtName.setText(null);
            txtContact.setText(null);
            txtAddress.setText(null);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = supplierBO.generateSupplierId();
        lblSupplierId.setText(nextId);
    }

    private void loadTableData() throws Exception {
        tblSupplier.setItems(FXCollections.observableArrayList(
                supplierBO.getAllISupplier().stream().map(
                        supplierDTO -> new SupplierTM(
                                supplierDTO.getSupplierId(),
                                supplierDTO.getSupplierName(),
                                supplierDTO.getSupplierContact(),
                                supplierDTO.getSupplierAddress()
                        )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String supplierId = lblSupplierId.getText();
        String supplierName = txtName.getText();
        String supplierContact = txtContact.getText();
        String supplierAddress = txtAddress.getText();

        boolean isValidContact = supplierContact.length() == 10;

        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0; -fx-border-radius: 20;");

        if (!isValidContact) txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");

        try {
            supplierBO.saveSupplier(new SupplierDTO(
                    supplierId,
                    supplierName,
                    supplierContact,
                    supplierAddress
            ));

            tblSupplier.getItems().add(new SupplierTM(
                    supplierId,
                    supplierName,
                    supplierContact,
                    supplierAddress
            ));
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }finally {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully", ButtonType.OK).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String supplierId = lblSupplierId.getText();
        String supplierName = txtName.getText();
        String supplierContact = txtContact.getText();
        String supplierAddress = txtAddress.getText();

        try {
            supplierBO.updateSupplier(new SupplierDTO(
                    supplierId,
                    supplierName,
                    supplierContact,
                    supplierAddress
            ));

            tblSupplier.getItems().add(new SupplierTM(
                    supplierId,
                    supplierName,
                    supplierContact,
                    supplierAddress
            ));
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this supplier?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String supplierId = lblSupplierId.getText();
            try {
            supplierBO.deleteSupplier(supplierId);
            tblSupplier.getItems().remove(tblSupplier.getSelectionModel().getSelectedItem().getSupplierId());
            tblSupplier.getSelectionModel().clearSelection();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
            } finally {
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully", ButtonType.OK).show();
                resetPage();
            }
        }
    }
    public void search(KeyEvent keyEvent) {
    }



    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        SupplierTM selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            lblSupplierId.setText(selectedSupplier.getSupplierId());
            txtName.setText(selectedSupplier.getSupplierName());
            txtContact.setText(selectedSupplier.getSupplierContact());
            txtAddress.setText(selectedSupplier.getSupplierAddress());

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }
    }
    public void navigateTo(String path) {
        try {
            ancSupplierPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancSupplierPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancSupplierPage.heightProperty());

            ancSupplierPage.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void goTHomePage(ActionEvent actionEvent) {
        navigateTo("/DashboardPage.fxml");
    }
}
