/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.io.File;
import java.net.URL;
import java.time.LocalDate; 
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.image.Image; 
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.*;
/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLSignUpController implements Initializable {
    
    //For managing with Stages
    private Stage previousStage;
    private Scene logInScene;
    private String logInTitle;
    private Navegacion nav;
    private File selectedFile;
    
    // Propiedades booleanas para saber si los campos están bien completados
    private boolean user;
    private boolean email;
    private boolean password;
    private boolean repeatedPassword;
    private boolean bBirthday;
    private boolean mainMenu;
    
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private DatePicker birthdayDatepicker;
    @FXML
    private TextField emailTextfield;
    @FXML
    private PasswordField passwordTextfield;
    @FXML
    private PasswordField repeatPasswordTextfield;
    @FXML
    private ImageView avatarImage;
    @FXML
    private Button selectImage;
    @FXML
    private Label everythingCorrectLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label repeatPasswordLabel;
    @FXML
    private Label birthdayLabel;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Declaramos objeto Navegacion para base de datos
        try {nav = Navegacion.getSingletonNavegacion();} catch (Exception e) {}
        
        usernameTextfield.focusedProperty().addListener((observable, oldVal, newVal) -> 
        {
            if(!newVal) {
                String nick = usernameTextfield.textProperty().getValue();
                if(nav.exitsNickName(nick)) {
                    acceptButton.disableProperty().setValue(true);
                    usernameLabel.textProperty().setValue("This user already exists.");
                    usernameLabel.visibleProperty().setValue(true);
                    user = false;
                } else if (!User.checkNickName(nick)) {
                    acceptButton.disableProperty().setValue(true);
                    usernameLabel.textProperty().setValue("The format is not correct (between 6 and 15 characters without spaces). You can also use hyphens or sub-dashes.");
                    usernameLabel.visibleProperty().setValue(true);
                    user = false;
                } else {
                    usernameLabel.visibleProperty().setValue(false);
                    user = true;
                    if(todoCorrecto()) {acceptButton.disableProperty().setValue(false);}
                }
            }
        });
        
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
        
        repeatPasswordTextfield.focusedProperty().addListener((observable, oldVal, newVal) -> 
        {
            if(!newVal) {
                String pass =  passwordTextfield.textProperty().getValue();
                String repeatPass = repeatPasswordTextfield.textProperty().getValue();
                if(!pass.equals(repeatPass)) {
                   acceptButton.disableProperty().setValue(true);
                   repeatPasswordLabel.visibleProperty().setValue(true);
                   repeatedPassword = false;
                } else {
                   repeatPasswordLabel.visibleProperty().setValue(false);
                   repeatedPassword = true;
                   if(todoCorrecto()) {acceptButton.disableProperty().setValue(false);}
                }
            }
        });
        
        birthdayDatepicker.focusedProperty().addListener((observable, oldVal, newVal) -> 
        {
            if(!newVal) {
                LocalDate birthday = birthdayDatepicker.getValue();
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
    
    //For managin with stages
    public void initSU(Stage stage, boolean mainMenu) {
        this.mainMenu = mainMenu;
        previousStage = stage;
        logInScene = previousStage.getScene();
        logInTitle = previousStage.getTitle();
    }

    @FXML
    private void handleAcceptButton(ActionEvent event) throws Exception { //Sin el throws no funciona el singleton
                    
        //Asumimos que todos los parametros son correctos pq lo habremos
        //comprobado en sus respectivos metodos:
        if(todoCorrecto()) {
            String username = usernameTextfield.textProperty().getValue();
            String email = emailTextfield.textProperty().getValue();
            String password = passwordTextfield.textProperty().getValue();
            Image avatar = avatarImage.getImage();
            LocalDate birthday = birthdayDatepicker.getValue();

            nav.registerUser(username, email, password, avatar, birthday);
        } else {
            everythingCorrectLabel.visibleProperty().setValue(true);
            //mostrar mensaje de rellenar todos los campos correctamente.
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Information");
        String nick = usernameTextfield.textProperty().getValue();
        alert.setHeaderText("User " + nick + " has been created.\nNow you can log in");
        alert.showAndWait();
        
        if(!mainMenu) {
            previousStage.setScene(logInScene);
            previousStage.setTitle(logInTitle);
        } else {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLLogIn.fxml"));
            Pane root = (Pane) myLoader.load();
            Scene scene = new Scene (root);
            previousStage.setScene(scene);
            previousStage.setTitle("Log In");
        }
    }
    
    @FXML
    private void onSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        selectedFile = fileChooser.showOpenDialog(
                ((Node)event.getSource()).getScene().getWindow());
        avatarImage.setImage(new Image(selectedFile.toURI().toString()));
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        if(!mainMenu) {
            previousStage.setScene(logInScene);
            previousStage.setTitle(logInTitle);
        } else {
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }



    private boolean todoCorrecto() {
        //metodo en el que comprobar a la vez si todos los campos son correctos
        return user && password && repeatedPassword && email && bBirthday;
    }

}
