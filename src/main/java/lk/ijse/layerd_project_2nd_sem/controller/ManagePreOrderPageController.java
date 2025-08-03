package lk.ijse.layerd_project_2nd_sem.controller;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ManagePreOrderPageController {

    public AnchorPane ancPreOrderPage;
    public Label lblPreOrderId;
    public ComboBox comboUserId;
    public ComboBox comboItemId;
    public TextField txtAdvance;

    public TableView<PreOrderTm> tblPayment;
    public TableColumn<PreOrderTm , String> colPreId;
    public TableColumn<PreOrderTm , String> colUserId;
    public TableColumn<PreOrderTm , String> colItemId;
    public TableColumn<PreOrderTm , Double> colAdvance;


    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TextField searchField;

}
