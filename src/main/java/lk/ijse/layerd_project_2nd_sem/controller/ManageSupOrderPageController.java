package lk.ijse.layerd_project_2nd_sem.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.layerd_project_2nd_sem.bo.BOFactory;
import lk.ijse.layerd_project_2nd_sem.bo.custom.SupOrderBO;
import lk.ijse.layerd_project_2nd_sem.dto.*;
import lk.ijse.layerd_project_2nd_sem.view.SupOrderDetailTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageSupOrderPageController {
    public Label lblOrderId;
    public Label orderDate;
    public ComboBox cmbSupplierId;
    public Label lblSupplierName;
    public ComboBox cmbItemId;
    public Label lblItemName;
    public TextField txtAddToCartQty;
    public Label lblItemQty;
    public Label lblItemPrice;
    private String orderId;
    public Button btnPlaceOrder;
    public Button btnAddToCart;
    public Label lblTotal;


    public TableView<SupOrderDetailTM> tblOrder;
    public TableColumn<SupOrderDetailTM , String > colSupId;
    public TableColumn<SupOrderDetailTM , String > colItemId;
    public TableColumn<SupOrderDetailTM , String > colItemName;
    public TableColumn<SupOrderDetailTM , Integer > colQty;
    public TableColumn<SupOrderDetailTM , Double > colPrice;
    public TableColumn<SupOrderDetailTM , Double > colTotal;
    public TableColumn<? , ? > colAction;

    SupOrderBO supOrderBO = (SupOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUP_ORDER);

    public AnchorPane ancDashboard;

    public void initialize(){
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<SupOrderDetailTM, Button> lastCol = (TableColumn<SupOrderDetailTM, Button>) tblOrder.getColumns().get(6);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Remove");

            btnDelete.setOnAction(event -> {
                tblOrder.getItems().remove(param.getValue());
                tblOrder.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);
        orderDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(false);
        lblSupplierName.setFocusTraversable(false);
        //lblCustomerName.setEditable(false);
        lblItemName.setFocusTraversable(false);
        //lblItemName

        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {

                    try {
                        if(!existSupplier(newValue + "")){
                            //new Alert(Alert.AlertType.ERROR, "Customer not found !" + newValue + "").show();
                        }

                       SupplierDTO supplierDTO = supOrderBO.searchSupplier(newValue + "");
                        if (supplierDTO != null && supplierDTO.getSupplierName() != null) {
                            lblSupplierName.setText(supplierDTO.getSupplierName());
                        } else {
                           lblSupplierName.setText(""); // or show an error
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Failed to load supplier details." + newValue + "" + e).show();
                    }
                }catch (Exception throwable) {
                    throwable.printStackTrace();
                }
            }else {
                lblSupplierName.setText("");
            }
        });


        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemId) -> {
            txtAddToCartQty.setEditable(newItemId != null);
            btnAddToCart.setDisable(newItemId == null);

            if (newItemId != null) {
                try {


                    try {
                        if(!existItem(newItemId + "")){

                        }
                        ItemDTO itemDTO = supOrderBO.searchItem(newItemId+"");
                        lblItemName.setText(itemDTO.getItemName());
                        lblItemPrice.setText(itemDTO.getSellPrice() + "");

                        Optional<SupOrderDetailTM> optionalDetail = tblOrder.getItems().stream().filter(detail -> detail.getItemId().equals(newItemId)).findFirst();
                        lblItemQty.setText((optionalDetail.isPresent() ? itemDTO.getQuantity() - optionalDetail.get().getQuantity() : itemDTO.getQuantity())+"");

                    }catch (SQLException throwables){
                        throwables.printStackTrace();

                    }
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }

            }else {

                lblItemName.setText("");
                lblItemPrice.setText("");
                lblItemQty.setText("");
            }
        });


        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                cmbItemId.setDisable(true);
                cmbItemId.setValue(selectedOrderDetail.getItemId());
                // btnPlaceOrder.setText("update");
                txtAddToCartQty.setText(Integer.parseInt(txtAddToCartQty.getText()) + selectedOrderDetail.getQuantity() + "");
                lblItemQty.setText(selectedOrderDetail.getQuantity() + "");

            }else {
                //btnPlaceOrder.setText("Add");
                cmbItemId.setDisable(false);
                cmbItemId.getSelectionModel().clearSelection();
                txtAddToCartQty.clear();
            }
        });
        loadAllSupplierIds();
        loadAllItemIds();
    }

    private void loadAllSupplierIds() {
        try {
            ArrayList<SupplierDTO> allSuppliers = supOrderBO.getAllSupplier();
            for (SupplierDTO supplierDTO : allSuppliers) {
                cmbSupplierId.getItems().add(supplierDTO.getSupplierId());
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load supplier IDs.").show();
        }
    }

    private void loadAllItemIds() {
        try {
            ArrayList<ItemDTO> itemDTOS = supOrderBO.getAllItem();
            for (ItemDTO itemDTO : itemDTOS) {
                cmbItemId.getItems().add(itemDTO.getItemId());
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load item IDs.").show();
        }
    }

    private boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return supOrderBO.existItem(id);
    }

    private boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supOrderBO.existSupplier(id);
    }

    public String generateNewOrderId() {
        try {
            return supOrderBO.generateOrderId();
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate new order ID.").show();

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return "SO001";
    }


    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        if(!lblItemQty.getText().matches("\\d+") ||
                Integer.parseInt(lblItemQty.getText()) <= 0 ||
                Integer.parseInt(txtAddToCartQty.getText()) > Integer.parseInt(lblItemQty.getText())){ // Fixed condition
            new Alert(Alert.AlertType.ERROR, "Invalid quantity!").show();
            txtAddToCartQty.requestFocus(); // Changed focus to quantity input
            return;
        }

        String supId = cmbSupplierId.getSelectionModel().getSelectedItem() + "";
        String itemId = cmbItemId.getSelectionModel().getSelectedItem()+"";
        String name = lblItemName.getText();
        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        int qty = Integer.parseInt(txtAddToCartQty.getText());
        double total = unitPrice * qty;


        boolean exists = tblOrder.getItems().stream().anyMatch(detail -> detail.getItemId().equals(itemId));

        if (exists) {
            SupOrderDetailTM supOrderDetailTM = tblOrder.getItems().stream().filter(detail -> detail.getItemId().equals(itemId)).findFirst().get();

            if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                supOrderDetailTM.setQuantity(qty);
                supOrderDetailTM.setTotal(total);
                tblOrder.getSelectionModel().clearSelection();

            } else {

                supOrderDetailTM.setQuantity(supOrderDetailTM.getQuantity() + qty);
                total = supOrderDetailTM.getQuantity() * unitPrice;
                supOrderDetailTM.setTotal(total);
            }


            tblOrder.refresh();

        } else {
            tblOrder.getItems().add(new SupOrderDetailTM( supId , itemId, name , qty, unitPrice, total));
        }

        cmbItemId.getSelectionModel().clearSelection();
        cmbItemId.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbSupplierId.getSelectionModel().getSelectedItem() != null && !tblOrder.getItems().isEmpty()));

    }

    private void calculateTotal() {
        double total = 0.0;

        for (SupOrderDetailTM detail : tblOrder.getItems()) {
            total += detail.getTotal();
        }
        lblTotal.setText("Total: " +total);

    }

    private boolean saveOrder(String orderId, LocalDate orderDate, String supplierId, List<SupOrderDetailDTO> supOrderDetail) {
        try {
            return supOrderBO.placeOrder(orderId, orderDate, supplierId, supOrderDetail);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(orderId, LocalDate.now(), String.valueOf(cmbSupplierId.getValue()),
                tblOrder.getItems().stream().map(tm -> new SupOrderDetailDTO(orderId,tm.getItemId(), tm.getQuantity(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        //lblId.setText("Order Id: " + orderId);
        cmbSupplierId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        tblOrder.getItems().clear();
        //lblItemQty.clear();
        calculateTotal();
    
    }

    public void navigateTo(String path) {
        try {
            ancDashboard.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashboard.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashboard.heightProperty());

            ancDashboard.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void gotoDashboard(MouseEvent mouseEvent) {
        navigateTo("/DashboardPage.fxml");
    }

}
