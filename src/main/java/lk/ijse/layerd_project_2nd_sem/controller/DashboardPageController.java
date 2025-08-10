package lk.ijse.layerd_project_2nd_sem.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {
    public AnchorPane ancDashboard;
    public Label alpha;
    public Label alpha1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animateLabelZoomIn();
        animateLabelZoomIn2();

    }

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

    public void btnSupplierOnAction(ActionEvent actionEvent) {
        navigateTo("/SupplierPage.fxml");
    }

    public void btnUserOnAction(ActionEvent actionEvent) {
        navigateTo("/UserPage.fxml");
    }

    public void btnPreOrderOnAction(ActionEvent actionEvent) {
        navigateTo("/PreOrderPage.fxml");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        navigateTo("/OrderPage.fxml");
    }

    private void animateLabelZoomIn() {
        String loginText = alpha.getText();
        alpha.setText(loginText);

        // Set initial scale and opacity
        alpha.setScaleX(0.5);
        alpha.setScaleY(0.5);
        alpha.setOpacity(0);

        // Create scale transition (zoom effect)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1500), alpha);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        // Create fade-in transition
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), alpha);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Play both transitions together
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
        parallelTransition.play();
    }

    private void animateLabelZoomIn2() {
        String loginText = alpha1.getText();
        alpha1.setText(loginText);

        // Set initial scale and opacity
        alpha1.setScaleX(0.5);
        alpha1.setScaleY(0.5);
        alpha1.setOpacity(0);

        // Create scale transition (zoom effect)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1500), alpha1);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        // Create fade-in transition
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), alpha1);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Play both transitions together
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
        parallelTransition.play();
    }
}
