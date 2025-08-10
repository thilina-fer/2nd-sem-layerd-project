package lk.ijse.layerd_project_2nd_sem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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

    public TableView<SupOrderCartTm> tblOrder;
    public TableColumn<SupOrderCartTm , String > colSupId;
    public TableColumn<SupOrderCartTm , String > colItemId;
    public TableColumn<SupOrderCartTm , String > colItemName;
    public TableColumn<SupOrderCartTm , Integer > colQty;
    public TableColumn<SupOrderCartTm , Double > colPrice;
    public TableColumn<SupOrderCartTm , Double > colTotal;
    public TableColumn<? , ? > colAction;


    public AnchorPane ancDashboard;
}
