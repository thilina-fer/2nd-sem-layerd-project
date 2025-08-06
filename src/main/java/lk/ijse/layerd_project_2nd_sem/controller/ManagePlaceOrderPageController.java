package lk.ijse.layerd_project_2nd_sem.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.layerd_project_2nd_sem.bo.BOFactory;
import lk.ijse.layerd_project_2nd_sem.bo.custom.PlaceOrderBO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.CustomerDAO;
import lk.ijse.layerd_project_2nd_sem.dto.CustomerDTO;
import lk.ijse.layerd_project_2nd_sem.dto.ItemDTO;
import lk.ijse.layerd_project_2nd_sem.dto.OrderDetailDTO;
import lk.ijse.layerd_project_2nd_sem.entity.Customer;
import lk.ijse.layerd_project_2nd_sem.view.OrderDetailTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManagePlaceOrderPageController {

    public AnchorPane ancDashboard;
    public Button btnPlaceOrder;
    public Label lblCustomerName;
    public Label lblItemName;
    public TextField txtAddToCartQty;
    public Button btnAddToCart;
    public Label lblItemPrice;
    public ComboBox<String> cmbCustomerContact;
    public ComboBox<String> cmbItemId;
    public Label lblItemQty;
    public Label lblOrderId;
    public Label lblorderDate;
    public Label lblTotal;
    private String orderId;


    public TableView<OrderDetailTM> tblOrder;
    public TableColumn<OrderDetailTM, String> colContact;
    public TableColumn<OrderDetailTM, String> colItemId;
    public TableColumn<OrderDetailTM, String> colItemName;
    public TableColumn<OrderDetailTM, Integer> colQty;
    public TableColumn<OrderDetailTM, Double> colPrice;
    public TableColumn<OrderDetailTM, Double> colTotal;
    public TableColumn<?, ?> colAction;


    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);

    public void  initialize() {
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerContact"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrder.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM , Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrder.getColumns().get(6);

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
        lblorderDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(false);
        lblCustomerName.setFocusTraversable(false);
        //lblCustomerName.setEditable(false);
        lblItemName.setFocusTraversable(false);
        //lblItemName

        cmbCustomerContact.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*search customer*/
                    try {
                        if(existCustomer(newValue + "")){
                            new Alert(Alert.AlertType.ERROR, "Customer not found !").show();
                        }

                        CustomerDTO customerDTO = placeOrderBO.searchCustomer(newValue+"");
                        lblCustomerName.setText(customerDTO.getCustomerName());

                    }catch (Exception e){
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Failed to load customer details.").show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to load customer details.").show();
                }
            }else {
                lblCustomerName.setText("");
            }
        });
        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                try {
                    /*search item*/
                    try {
                        if(existItem(newValue + "")){
                            new Alert(Alert.AlertType.ERROR, "Item not found!").show();
                        }
                        ItemDTO itemDTO = placeOrderBO.searchItem(newValue+"");
                        lblItemName.setText(itemDTO.getItemName());
                        lblItemPrice.setText(String.valueOf(itemDTO.getSellPrice()));

                        Optional<OrderDetailTM> optionalDetail = tblOrder.getItems().stream().filter(detail -> detail.getItemId().equals(newValue)).findFirst();
                        lblItemQty.setText(optionalDetail.isPresent() ? String.valueOf(itemDTO.getQuantity() - optionalDetail.get().getQuantity()) : itemDTO.getQuantity() + "");

                    }catch (Exception e){
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Failed to load item details.").show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to load item details.").show();
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
                btnPlaceOrder.setText("update");
                txtAddToCartQty.setText(Integer.parseInt(txtAddToCartQty.getText()) + selectedOrderDetail.getQuantity() + "");
                lblItemQty.setText(String.valueOf(selectedOrderDetail.getQuantity()));
            }else {
                btnPlaceOrder.setText("Add");
                cmbItemId.setDisable(false);
                cmbItemId.getSelectionModel().clearSelection();
                txtAddToCartQty.clear();
            }
        });
        loadAllCustomerIds();
        loadAllItemCodes();
    }

    private boolean existCustomer(String customerId) throws SQLException, ClassNotFoundException {
     return placeOrderBO.existCustomer(customerId);
    }

    private boolean existItem(String itemId) throws SQLException, ClassNotFoundException {
        return placeOrderBO.existItem(itemId);
    }

    public String generateNewOrderId() {
        try {
            return placeOrderBO.generateOrderId();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate new order ID.").show();

        }
        return "O001";
    }


    private void loadAllCustomerIds() {
        try{
            ArrayList<CustomerDTO> customerDTOS = placeOrderBO.getAllCustomer();
            for (CustomerDTO customerDTO : customerDTOS) {
                cmbCustomerContact.getItems().add(customerDTO.getCustomerContact());
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load customer contacts.").show();
        }
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> itemDTOS = placeOrderBO.getAllItem();
            for (ItemDTO itemDTO : itemDTOS) {
                cmbItemId.getItems().add(itemDTO.getItemId());
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load item IDs.").show();
        }
    }



    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        if(!txtAddToCartQty.getText().matches("\\d+") || Integer.parseInt(txtAddToCartQty.getText()) <= 0 ||
                Integer.parseInt(txtAddToCartQty.getText()) > Integer.parseInt(lblItemQty.getText())){

            new Alert(Alert.AlertType.ERROR, "Invalid quantity!").show();
            return;
        }
        String customerContact = cmbCustomerContact.getSelectionModel().getSelectedItem();
        String itemId = cmbItemId.getSelectionModel().getSelectedItem();
        String name = lblItemName.getText();
        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        int qty = Integer.parseInt(txtAddToCartQty.getText());
        double total = unitPrice * qty;


        boolean exists = tblOrder.getItems().stream().anyMatch(detail -> detail.getItemId().equals(itemId));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrder.getItems().stream().filter(detail -> detail.getItemId().equals(itemId)).findFirst().get();

            if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQuantity(qty);
                orderDetailTM.setTotal(total);
                tblOrder.getSelectionModel().clearSelection();

            } else {

                orderDetailTM.setQuantity(orderDetailTM.getQuantity() + qty);
                total = orderDetailTM.getQuantity() * unitPrice;
                orderDetailTM.setTotal(total);
            }

            tblOrder.refresh();

        } else {
            tblOrder.getItems().add(new OrderDetailTM( customerContact , itemId, name , qty, unitPrice, total));
        }

        cmbItemId.getSelectionModel().clearSelection();
        cmbItemId.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerContact.getSelectionModel().getSelectedItem() != null && !tblOrder.getItems().isEmpty()));

    }

    private void calculateTotal() {
        double total = 0.0;

        for (OrderDetailTM detail : tblOrder.getItems()) {
            //total = total.add(detail.getTotal());
            total += detail.getTotal();
        }
        lblTotal.setText("Total: " +total);

    }

    public void btnPlaceOrderOnAAction(ActionEvent actionEvent) {

        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerContact.getValue(),
                tblOrder.getItems().stream().map(tm -> new OrderDetailDTO(orderId,tm.getItemId(), tm.getQuantity(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        //lblId.setText("Order Id: " + orderId);
        cmbCustomerContact.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        tblOrder.getItems().clear();
        //lblItemQty.clear();
        calculateTotal();
    }

    private boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        try {
            return placeOrderBO.placeOrder(orderId, orderDate, customerId, orderDetails);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void OnClickTable(MouseEvent mouseEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
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

    public void goToHomePage(ActionEvent actionEvent) {
        navigateTo("/DashboardPage.fxml");
    }
}
