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
import lk.ijse.layerd_project_2nd_sem.bo.custom.UserBO;
import lk.ijse.layerd_project_2nd_sem.dto.UserDTO;
import lk.ijse.layerd_project_2nd_sem.view.UserTM;

import java.util.Optional;

public class ManageUserPageController {
    public AnchorPane ancUserPage;
    public TextField searchField;

    public Label lblUserId;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPassword;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtRole;

    public TableView<UserTM> tblUser;
    public TableColumn<UserTM , String> colId;
    public TableColumn<UserTM, String> colName;
    public TableColumn<UserTM , String> colEmail;
    public TableColumn<UserTM , String> colPassword;
    public TableColumn<UserTM , String> colContact;
    public TableColumn<UserTM , String> colAddress;
    public TableColumn<UserTM , String> colRole;


    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String userNamePattern = "^[A-Za-z0-9_ ]{3,}$";
    private final String passwordPattern = "^[A-Za-z0-9@#$%^&+=]{6,}$";

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            nextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText(null);
            txtEmail.setText(null);
            txtPassword.setText(null);
            txtContact.setText(null);
            txtAddress.setText(null);
            txtRole.setText(null);
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void nextId() throws Exception {
        String userId = userBO.generateUserId();
        lblUserId.setText(userId);
    }

    private void loadTableData() throws Exception {
        tblUser.setItems(FXCollections.observableArrayList(
                userBO.getAllIUser().stream().map(
                        userDTO -> new UserTM(
                                userDTO.getUserId(),
                                userDTO.getUserName(),
                                userDTO.getEmail(),
                                userDTO.getPassword(),
                                userDTO.getContact(),
                                userDTO.getAddress(),
                                userDTO.getRole()
                        )).toList()
        ));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String userId = lblUserId.getText();
        String userName = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String role = txtRole.getText();

        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);
        boolean isValidContact = contact.length() == 10;

        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0");
        txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: #7367F0");
        txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: #7367F0");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0");
        txtRole.setStyle(txtRole.getStyle() + ";-fx-border-color: #7367F0");

        if (!isValidEmail) txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        if (!isValidPassword) txtPassword.setStyle(txtPassword.getStyle() + ";-fx-border-color: red;");
        if (!isValidContact) txtContact.setStyle(txtContact.getStyle() + ";-fx-border-color: red;");

        try {
            userBO.saveUser(new UserDTO(
                    userId,
                    userName,
                    email,
                    password,
                    contact,
                    address,
                    role
            ));

            tblUser.getItems().add(new UserTM(
                    userId,
                    userName,
                    email,
                    password,
                    contact,
                    address,
                    role
            ));
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "User Saved Successfully ", ButtonType.OK).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String userId = lblUserId.getText();
        String userName = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String role = txtRole.getText();

        try {
            userBO.updateUser(new UserDTO(
                    userId,
                    userName,
                    email,
                    password,
                    contact,
                    address,
                    role
            ));

            tblUser.getItems().add(new UserTM(
                    userId,
                    userName,
                    email,
                    password,
                    contact,
                    address,
                    role
            ));
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } finally {
            resetPage();
            new Alert(Alert.AlertType.INFORMATION, "User Saved Successfully ", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this user?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            String userId = tblUser.getSelectionModel().getSelectedItem().getUserId();
            try {
                userBO.deleteUser(userId);
                tblUser.getItems().remove(tblUser.getSelectionModel().getSelectedItem());
                tblUser.getSelectionModel().clearSelection();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete the user " + userId, ButtonType.OK).show();
            }finally {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "User deleted successfully", ButtonType.OK).show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void navigateTo(String path) {
        try {
            ancUserPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancUserPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancUserPage.heightProperty());

            ancUserPage.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }
    }
    public void goToHomePage(ActionEvent actionEvent) {
        navigateTo("/DashboardPage.fxml");
    }
    public void onClickTable(MouseEvent mouseEvent) {
        UserTM selectedUser = tblUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            lblUserId.setText(selectedUser.getUserId());
            txtName.setText(selectedUser.getUserName());
            txtEmail.setText(selectedUser.getEmail());
            txtPassword.setText(selectedUser.getPassword());
            txtContact.setText(selectedUser.getContact());
            txtAddress.setText(selectedUser.getAddress());
            txtRole.setText(selectedUser.getRole());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    public void search(KeyEvent keyEvent) {
    }




}
