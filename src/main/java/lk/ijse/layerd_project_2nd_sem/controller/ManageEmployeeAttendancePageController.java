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
import lk.ijse.layerd_project_2nd_sem.bo.custom.EmployeeAttendanceBO;
import lk.ijse.layerd_project_2nd_sem.dao.custom.EmployeeAttendanceDAO;
import lk.ijse.layerd_project_2nd_sem.dto.EmployeeAttendanceDTO;
import lk.ijse.layerd_project_2nd_sem.view.EmployeeAttendanceTM;

import java.util.Optional;

public class ManageEmployeeAttendancePageController {
    public Label lblAttendanceId;
    public TextField txtNic;
    public TextField txtDate;
    public TextField txtAttendTime;
    public TextField txtDuration;

    public TableView<EmployeeAttendanceTM> tblAttendance;
    public TableColumn<EmployeeAttendanceTM , String> colId;
    public TableColumn<EmployeeAttendanceTM , String> colNic;
    public TableColumn<EmployeeAttendanceTM , String> colDate;
    public TableColumn<EmployeeAttendanceTM , String> colAttendTime;
    public TableColumn<EmployeeAttendanceTM , String> colDuration;
    
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TextField searchField;

    private final String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
    private final String timePattern = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
    private final String durationPattern = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
    public AnchorPane ancAttendancePage;

    EmployeeAttendanceBO employeeAttendanceBO = (EmployeeAttendanceBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE_ATTENDANCE);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("employeeNic"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAttendTime.setCellValueFactory(new PropertyValueFactory<>("attendTime"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

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

            txtNic.setText(null);
            txtDate.setText(java.time.LocalDate.now().toString());
            txtAttendTime.setText(null);
            txtDuration.setText(null);

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void loadNextId() throws Exception {
        String nextId = employeeAttendanceBO.generateEmployeeAttendanceId();
        lblAttendanceId.setText(nextId);
    }

    private void loadTableData() throws Exception {
        tblAttendance.setItems(FXCollections.observableArrayList(
                employeeAttendanceBO.getAllEmployeeAttendance().stream().map(
                        attendance -> new EmployeeAttendanceTM(
                                attendance.getAttendanceId(),
                                attendance.getEmployeeNic(),
                                attendance.getDate(),
                                attendance.getAttendTime(),
                                attendance.getDuration()
                        )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String attendanceId = lblAttendanceId.getText();
        String employeeNic = txtNic.getText();
        String date = txtDate.getText();
        String attendTime = txtAttendTime.getText();
        String duration = txtDuration.getText();

        boolean isValidNic = employeeNic.length() == 12 && employeeNic.charAt(9) == '-';
        boolean isValidDate = date.matches(datePattern);
        boolean isValidAttTime = attendTime.matches(timePattern);
        boolean isValidDuration = duration.matches(durationPattern);

        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;-fx-border-radius: 20;");
        txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: #7367F0; -fx-border-radius: 20;");
        txtAttendTime.setStyle(txtAttendTime.getStyle() + ";-fx-border-color: #7367F0;-fx-border-radius: 20;");
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: #7367F0;-fx-border-radius: 20;");

        if (!isValidNic) txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
        if (!isValidDate) txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: red;");
        if (!isValidAttTime) txtAttendTime.setStyle(txtAttendTime.getStyle() + ";-fx-border-color: red;");
        if (!isValidDuration) txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red;");

        try {
            employeeAttendanceBO.saveEmployeeAttendance(new EmployeeAttendanceDTO(
                    attendanceId,
                    employeeNic,
                    date,
                    attendTime,
                    duration
            ));
            tblAttendance.getItems().add(new EmployeeAttendanceTM(
                    attendanceId,
                    employeeNic,
                    date,
                    attendTime,
                    duration
            ));
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed Save Attendance").show();
        }finally {
            new Alert(Alert.AlertType.INFORMATION, "Successfully saved!").show();
            resetPage();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String attendanceId = lblAttendanceId.getText();
        String employeeNic = txtNic.getText();
        String date = txtDate.getText();
        String attendTime = txtAttendTime.getText();
        String duration = txtDuration.getText();

        try {
            employeeAttendanceBO.updateEmployeeAttendance(new EmployeeAttendanceDTO(
                    attendanceId,
                    employeeNic,
                    date,
                    attendTime,
                    duration
            ));
            tblAttendance.getItems().add(new EmployeeAttendanceTM(
                    attendanceId,
                    employeeNic,
                    date,
                    attendTime,
                    duration
            ));
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update attendance").show();
        } finally {
            new Alert(Alert.AlertType.INFORMATION, "Attendance Updated Successfully").show();
            resetPage();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this attendance record?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String attendanceId = tblAttendance.getSelectionModel().getSelectedItem().getAttendanceId();
            try {
                employeeAttendanceBO.deleteEmployeeAttendance(attendanceId);
                tblAttendance.getItems().remove(tblAttendance.getSelectionModel().getSelectedItem());
                tblAttendance.getSelectionModel().clearSelection();
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete attendance record").show();
            } finally {
                new Alert(Alert.AlertType.INFORMATION, "Attendance record deleted successfully").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        EmployeeAttendanceTM selectedRow = tblAttendance.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            lblAttendanceId.setText(selectedRow.getAttendanceId());
            txtNic.setText(selectedRow.getEmployeeNic());
            txtDate.setText(selectedRow.getDate());
            txtAttendTime.setText(selectedRow.getAttendTime());
            txtDuration.setText(selectedRow.getDuration());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void navigateTo(String path) {
        try {
            ancAttendancePage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancAttendancePage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancAttendancePage.heightProperty());

            ancAttendancePage.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }
    public void goToHomePage(ActionEvent actionEvent) {
        navigateTo("/DashboardPage.fxml");
    }

    public void search(KeyEvent keyEvent) {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            try {
                loadTableData();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load attendance data").show();
            }
        } else {
            try {
                tblAttendance.setItems(FXCollections.observableArrayList(
                        employeeAttendanceBO.searchAttendance(searchText).stream().map(
                                attendance -> new EmployeeAttendanceTM(
                                        attendance.getAttendanceId(),
                                        attendance.getEmployeeNic(),
                                        attendance.getDate(),
                                        attendance.getAttendTime(),
                                        attendance.getDuration()
                                )).toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search attendance data").show();
            }
        }
    }
}
