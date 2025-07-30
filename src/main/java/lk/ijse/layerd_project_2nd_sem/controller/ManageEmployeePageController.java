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
import lk.ijse.layerd_project_2nd_sem.bo.custom.EmployeeBO;
import lk.ijse.layerd_project_2nd_sem.dto.EmployeeDTO;
import lk.ijse.layerd_project_2nd_sem.view.EmployeeTM;

import java.util.ArrayList;
import java.util.Optional;

public class ManageEmployeePageController {
    public AnchorPane ancEmployeePage;
    public TextField searchField;
    public Label lblEmpId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtSalary;
    public TextField txtNic;

    public TableView<EmployeeTM> tblEmployee;
    public TableColumn<EmployeeTM, String> colId;
    public TableColumn<EmployeeTM, String> colName;
    public TableColumn<EmployeeTM, String> colContact;
    public TableColumn<EmployeeTM, String> colAddress;
    public TableColumn<EmployeeTM, String> colNic;
    public TableColumn<EmployeeTM, Integer> colAge;
    public TableColumn<EmployeeTM, Double> colSalary;


    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String agePattern = "^(\\d+)$";
    private final String salaryPattern = "^(\\d+)$";

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("employeeNic"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("employeeAge"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            btnReset.setDisable(false);

            txtName.setText(null);
            txtContact.setText(null);
            txtAddress.setText(null);
            txtAge.setText(null);
            txtSalary.setText(null);
            txtNic.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void loadTableData() throws Exception {
        tblEmployee.setItems(FXCollections.observableArrayList(
                employeeBO.getAllEmployee().stream().map(
                        employeeDto -> new EmployeeTM(
                                employeeDto.getEmployeeId(),
                                employeeDto.getEmployeeName(),
                                employeeDto.getEmployeeContact(),
                                employeeDto.getEmployeeAddress(),
                                employeeDto.getEmployeeNic(),
                                employeeDto.getEmployeeAge(),
                                employeeDto.getSalary()
                        )).toList()
        ));
    }

    private void loadNextId() throws Exception {
        String nextId = employeeBO.generateEmployeeId();
        lblEmpId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent event) {
        String employeeId = lblEmpId.getText();
        String employeeName = txtName.getText();
        String employeeContact = txtContact.getText();
        String employeeAddress = txtAddress.getText();
        String employeeNic = txtNic.getText();
        String employeeAge = txtAge.getText();
        String employeeSalary = txtSalary.getText();

        boolean isValidContact = employeeContact.length() == 10;
        // boolean isValidNic = employeeNic.length() == 12 && employeeNic.charAt(9) == '-';
        boolean isValidAge = employeeAge.matches(agePattern);
        boolean isValidSalary = employeeSalary.matches(salaryPattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0");
        txtAge.setStyle(txtAge.getStyle() + ";-fx-border-color: #7367F0");
        txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color: #7367F0");

        if (!isValidContact) txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");
        // if (!isValidNic) txtNic.setStyle(txtNic.getStyle()+";-fx-border-color: red;");
        if (!isValidAge) txtAge.setStyle(txtAge.getStyle() + ";-fx-border-color: red;");
        if (!isValidSalary) txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color: red;");

        int parsedAge = Integer.parseInt(employeeAge);
        double parsedSalary = Double.parseDouble(employeeSalary);

        if (!isValidContact ||/* !isValidNic ||*/ !isValidAge || !isValidSalary) {
            new Alert(Alert.AlertType.ERROR, "Please fill the fields correctly!").show();
            return;
        }
        try {
            employeeBO.saveEmployee(new EmployeeDTO(
                    employeeId,
                    employeeName,
                    employeeContact,
                    employeeAddress,
                    employeeNic,
                    parsedAge,
                    parsedSalary
            ));

            tblEmployee.getItems().add(new EmployeeTM(
                    employeeId,
                    employeeName,
                    employeeContact,
                    employeeAddress,
                    employeeNic,
                    parsedAge,
                    parsedSalary
            ));

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Save Employee").show();
        } finally {
            new Alert(Alert.AlertType.INFORMATION, "Employee Saved Successfully").show();
            resetPage();
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String employeeId = lblEmpId.getText();
        String employeeName = txtName.getText();
        String employeeContact = txtContact.getText();
        String employeeAddress = txtAddress.getText();
        String employeeNic = txtNic.getText();
        String employeeAge = txtAge.getText();
        String employeeSalary = txtSalary.getText();

        int parsedAge = Integer.parseInt(employeeAge);
        double parsedSalary = Double.parseDouble(employeeSalary);
        try {
            employeeBO.updateEmployee(new EmployeeDTO(
                    employeeId,
                    employeeName,
                    employeeContact,
                    employeeAddress,
                    employeeNic,
                    parsedAge,
                    parsedSalary
            ));

            tblEmployee.getItems().add(new EmployeeTM(
                    employeeId,
                    employeeName,
                    employeeContact,
                    employeeAddress,
                    employeeNic,
                    parsedAge,
                    parsedSalary
            ));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Update Employee").show();
        } finally {
            new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully").show();
            resetPage();
        }
    }

    public void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this employee?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String employeeId = tblEmployee.getSelectionModel().getSelectedItem().getEmployeeId();
            try {
                employeeBO.deleteEmployee(employeeId);
                tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
                tblEmployee.getSelectionModel().clearSelection();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete employee").show();
            } finally {
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully").show();
                resetPage();
            }
        }
    }

    public void btnResetOnAction(ActionEvent event) {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        EmployeeTM selectedRow = tblEmployee.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            lblEmpId.setText(selectedRow.getEmployeeId());
            txtName.setText(selectedRow.getEmployeeName());
            txtContact.setText(selectedRow.getEmployeeContact());
            txtAddress.setText(selectedRow.getEmployeeAddress());
            txtNic.setText(selectedRow.getEmployeeNic());
            txtAge.setText(String.valueOf(selectedRow.getEmployeeAge()));
            txtSalary.setText(String.valueOf(selectedRow.getSalary()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnReset.setDisable(false);
        }
    }

    public void goToEmployeeAttendancePage(MouseEvent mouseEvent) {
    }


    public void navigateTo(String path) {
        try {
            ancEmployeePage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancEmployeePage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancEmployeePage.heightProperty());

            ancEmployeePage.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void gotoHomePage(ActionEvent event) {
        navigateTo("/DashboardPage.fxml");
    }

    public void goToAttendancePage(ActionEvent actionEvent) {
        navigateTo("/EmployeeAttendancePage.fxml");
    }

    public void search(KeyEvent event) {
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load Customers").show();
            }
        } else {
            try {
                ArrayList<EmployeeDTO> customerList = employeeBO.searchEmployee(searchText);
                tblEmployee.setItems(FXCollections.observableArrayList(
                        customerList.stream()
                                .map(employeeDto -> new EmployeeTM(
                                        employeeDto.getEmployeeId(),
                                        employeeDto.getEmployeeName(),
                                        employeeDto.getEmployeeContact(),
                                        employeeDto.getEmployeeAddress(),
                                        employeeDto.getEmployeeNic(),
                                        employeeDto.getEmployeeAge(),
                                        employeeDto.getSalary()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search customers").show();
            }
        }
    }
}
