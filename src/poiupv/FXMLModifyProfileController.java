/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.User;

/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLModifyProfileController implements Initializable {

    @FXML
    private ImageView profilePicture;
    @FXML
    private Hyperlink changePictureLink;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    
    private User user;
    
    private boolean email;
    private boolean password;
    private boolean bBirthday;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label birthdayLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usernameTextfield.disableProperty().setValue(Boolean.TRUE);
        acceptButton.disableProperty().setValue(Boolean.TRUE);
        emailLabel.visibleProperty().setValue(Boolean.FALSE);
        passwordLabel.visibleProperty().setValue(Boolean.FALSE);
        birthdayLabel.visibleProperty().setValue(Boolean.FALSE);
        
        emailTextfield.focusedProperty().addListener((observable, oldVal, newVal) -> 
        {
            if(!newVal) {
                String sEmail = emailTextfield.textProperty().getValue();
                if(!User.checkEmail(sEmail)) {
                    acceptButton.disableProperty().setValue(true);
                    emailLabel.visibleProperty().setValue(true);
                    email = false;
                } else {
                    emailLabel.visibleProperty().setValue(false);
                    email = true;
                    if(todoCorrecto()) {acceptButton.disableProperty().setValue(false);}
                }
            }
        });
        
        passwordTextfield.focusedProperty().addListener((observable, oldVal, newVal) -> 
        {
            if(!newVal) {
                String pass =  passwordTextfield.textProperty().getValue();
                if(!User.checkPassword(pass)) {
                    acceptButton.disableProperty().setValue(true);
                    passwordLabel.visibleProperty().setValue(true);
                    password = false;
                } else {
                    passwordLabel.visibleProperty().setValue(false);
                    password = true;
                    if(todoCorrecto()) {acceptButton.disableProperty().setValue(false);}
                }
            }
        });
        
        birthdatePicker.focusedProperty().addListener((observable, oldVal, newVal) -> 
        {
            if(!newVal) {
                LocalDate birthday = birthdatePicker.getValue();
                LocalDate today = LocalDate.now();
                if (birthday.compareTo(today.minusYears(12)) > 0) {
                    acceptButton.disableProperty().setValue(true);
                    birthdayLabel.visibleProperty().setValue(true);
                    bBirthday = false;
                } else {
                    birthdayLabel.visibleProperty().setValue(false);
                    bBirthday = true;
                    if(todoCorrecto()) {acceptButton.disableProperty().setValue(false);}
                }
            }
        });
    }    

    @FXML
    private void handleChangePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(
                ((Node)event.getSource()).getScene().getWindow());
        profilePicture.setImage(new Image(selectedFile.toURI().toString()));
    }

    @FXML
    private void handleAcceptButton(ActionEvent event) throws NavegacionDAOException {
        //Aqui todos los metodos set del usuario
        user.setEmail(emailTextfield.getText());
        user.setPassword(passwordTextfield.getText());
        user.setBirthdate(birthdatePicker.getValue());
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    public void initUser(User u) {
        user = u;
        usernameTextfield.textProperty().setValue(u.getNickName());
        profilePicture.setImage(u.getAvatar());
    }
    
    private boolean todoCorrecto() {
        //metodo en el que comprobar a la vez si todos los campos son correctos
        return password &&  email && bBirthday;
    }
}