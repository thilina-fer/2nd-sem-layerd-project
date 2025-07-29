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
import lk.ijse.layerd_project_2nd_sem.bo.custom.CustomerBO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.view.CustomerTM;

import java.util.ArrayList;
import java.util.Optional;

public class ManageCustomerPageController {
    public Label lblCustomerId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;
    public AnchorPane ancCustomerPage;
    public TextField searchField;

    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM , String> colId;
    public TableColumn<CustomerTM , String> colName;
    public TableColumn<CustomerTM , String> colContact;
    public TableColumn<CustomerTM , String> colAddress;


    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String addressPattern = "^[A-Za-z ]+$";

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));

        try {
            rersetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void rersetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText(null);
            txtContact.setText(null);
            txtAddress.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = customerBO.generateCustomerId();
        lblCustomerId.setText(nextId);
        
    }

    private void loadTableData() throws Exception {
        tblCustomer.setItems(FXCollections.observableArrayList(
                customerBO.getAllCustomer().stream().map(
                        customerDTO -> new CustomerTM(
                                customerDTO.getCustomerId(),
                                customerDTO.getCustomerName(),
                                customerDTO.getCustomerContact(),
                                customerDTO.getCustomerAddress()
                        )).toList()
        ));
    }

    public void goToDashboard(MouseEvent mouseEvent) {
    }

    public void search(KeyEvent event) {
        /*String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load customers").show();
            }
        } else {
            try {
                ArrayList<CustomerDTO> searchResults = customerBO.searchCustomer(searchText);
                tblCustomer.setItems(FXCollections.observableArrayList(
                        searchResults.stream().map(
                                CustomerDTO -> new CustomerTM(
                                        CustomerDTO.getCustomerId(),
                                        CustomerDTO.getCustomerName(),
                                        CustomerDTO.getCustomerContact(),
                                        CustomerDTO.getCustomerAddress()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search customers").show();
            }
        }*/
    }

    public void btnSaveOnAction(ActionEvent event) {
        String cutomerId = lblCustomerId.getText();
        String customerName = txtName.getText();
        String customerContact = txtContact.getText();
        String customerAddress = txtAddress.getText();

        boolean isValidContact = customerContact.matches(contactPattern);

        txtName.setStyle(txtAddress.getStyle()+ ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidContact) txtContact.setStyle("-fx-border-color: red;");

        try {
            customerBO.saveCustomer(new CustomerDTO(cutomerId,customerName,customerContact,customerAddress));
            tblCustomer.getItems().add(new CustomerTM(cutomerId,customerName,customerContact,customerAddress));
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully").show();
            rersetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed Save Customer").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String cutomerId = lblCustomerId.getText();
        String customerName = txtName.getText();
        String customerContact = txtContact.getText();
        String customerAddress = txtAddress.getText();
        try {
            customerBO.updateCustomer(new CustomerDTO(
                    cutomerId,
                    customerName,
                    customerContact,
                    customerAddress
            ));

            tblCustomer.getItems().add(new CustomerTM(
                    cutomerId,
                    customerName,
                    customerContact,
                    customerAddress
            ));

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update customer").show();
        } finally {
            rersetPage();
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this customer?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String customerId = tblCustomer.getSelectionModel().getSelectedItem().getCustomerId();
            try {
                customerBO.deleteCustomer(customerId);
                tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
                tblCustomer.getSelectionModel().clearSelection();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete customer").show();
            } finally {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfully").show();
                rersetPage();
            }
        }

    }

    public void btnResetOnAction(ActionEvent event) {
        rersetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM selectedRow = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            lblCustomerId.setText(selectedRow.getCustomerId());
            txtName.setText(selectedRow.getCustomerName());
            txtContact.setText(selectedRow.getCustomerContact());
            txtAddress.setText(selectedRow.getCustomerAddress());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    public void navigateTo(String path) {
        try {
            ancCustomerPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancCustomerPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCustomerPage.heightProperty());

            ancCustomerPage.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }
    public void goToHomePage(ActionEvent actionEvent) {
        navigateTo("/DashboardPage.fxml");
    }
}
