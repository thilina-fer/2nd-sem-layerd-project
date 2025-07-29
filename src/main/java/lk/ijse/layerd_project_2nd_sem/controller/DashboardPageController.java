package lk.ijse.layerd_project_2nd_sem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DashboardPageController {
    public AnchorPane ancDashboard;
    public Button btnCustomer;
    public Button btnItem;
    public Button btnEmployee;

    void navigateTo(String path) {
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

    public void btnCustomerOnAction(ActionEvent event) {
        navigateTo("/CustomerPage.fxml");
    }

    public void btnItemOnAction(ActionEvent event) {
        navigateTo("/ItemPage.fxml");
    }

    public void btnEmployeeOnAction(ActionEvent event) {
        navigateTo("/EmployeePage.fxml");
    }
}
