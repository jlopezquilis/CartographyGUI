/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

/*import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;*/

//Imports copiados del fxml document controller:
import java.net.URL;
import java.time.LocalDate; //Import que he añadido con alt + enter, comprobar que sea el bueno
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import poiupv.Poi;
//Estos d abajo tb hacen falta para que funcione en este caso
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image; //Otro import que he añadido con el alt + enter, hay que comprobar que esté bien
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import model.*;//Esto lo he añadido con lo d alt + enter, no se si
                         //es la clase que hay que importar para que funcione
/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLSignUpController implements Initializable {
    
    //For managing with Stages
    private Stage previousStage;
    private Scene previousScene;
    private String previousTitle;
    private Navegacion nav;
    
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

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Declaramos objeto Navegacion para base de datos
        try {
            nav = Navegacion.getSingletonNavegacion();
        } catch (Exception e) {

        }
    }   
    
    //For managin with stages
    public void initSU(Stage stage) {
        previousStage = stage;
        previousScene = previousStage.getScene();
        previousTitle = previousStage.getTitle();
    }

    @FXML
    private void handleAcceptButton(ActionEvent event) throws Exception { //Sin el throws no funciona el singleton
        
        //Checking user
        if(!User.checkNickName(usernameTextfield.textProperty().getValue())) {
            //si el usuario es incorrecto se muestra un mensaje que te dice el error.
            usernameLabel.visibleProperty().setValue(true);
        } else if (nav.exitsNickName(usernameTextfield.textProperty().getValue())) {
            //si el usuario ya existe, se muestra un mensaje que te lo dice.
            usernameLabel.textProperty().setValue("This user already exists");
            usernameLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            usernameLabel.visibleProperty().setValue(false);
        }
        
        //Checking email
        if(!User.checkEmail(emailTextfield.textProperty().getValueSafe())) {
            //mostrar label diciendo que el formato no es correcto.
            emailLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            emailLabel.visibleProperty().setValue(false);
        }
        
        //Checking password
        if(!User.checkPassword(passwordTextfield.textProperty().getValue())) {
            //mostrar label diciendo que el formato no es correcto.
            passwordLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            passwordLabel.visibleProperty().setValue(false);
        }
        
        //Checking repeat password
        
        if(!passwordTextfield.textProperty().getValue().equals(repeatPasswordTextfield.textProperty().getValue())) {
            //mostrar label diciendo que no son iguales ambas contraseñas
            repeatPasswordLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            repeatPasswordLabel.visibleProperty().setValue(false);
        }
        
        //Checking birthdate
        
        //Asumimos que todos los parametros son correctos pq lo habremos
        //comprobado en sus respectivos metodos:
        if(todoCorrecto()) {
            String username = usernameTextfield.textProperty().getValue();
            String email = emailTextfield.textProperty().getValue();
            String password = passwordTextfield.textProperty().getValue();
            Image avatar = avatarImage.getImage();
            LocalDate birthday = birthdayDatepicker.getValue(); //Ni idea d si la fecha se saca con esto jsjs

            nav.registerUser(username, email, password, avatar, birthday);
        } else {
            everythingCorrectLabel.visibleProperty().setValue(true);
            //mostrar mensaje de rellenar todos los campos correctamente.
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        previousStage.setScene(previousScene);
        previousStage.setTitle(previousTitle);
    }


    @FXML
    private void handleSelectImage(ActionEvent event) {
        
    }

    private boolean todoCorrecto() throws Exception{
        //metodo en el que comprobar a la vez si todos los campos son correctos
        
        return passwordTextfield.textProperty().getValue().equals(repeatPasswordTextfield.textProperty().getValue()) &&
               User.checkPassword(passwordTextfield.textProperty().getValue()) &&
               User.checkEmail(emailTextfield.textProperty().getValue()) &&
               User.checkNickName(usernameTextfield.textProperty().getValue()) &&
               !nav.exitsNickName(usernameTextfield.textProperty().getValue());
    }

    private void handleChangeEmail(KeyEvent event) {
        if(!User.checkEmail(emailTextfield.textProperty().getValueSafe())) {
            //mostrar label diciendo que el formato no es correcto.
            emailLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            emailLabel.visibleProperty().setValue(false);
        }
    }

    private void handleChangeUsername(KeyEvent event) throws Exception{
        
        if(!User.checkNickName(usernameTextfield.textProperty().getValue())) {
            //si el usuario es incorrecto se muestra un mensaje que te dice el error.
            usernameLabel.visibleProperty().setValue(true);
        } else if (nav.exitsNickName(usernameTextfield.textProperty().getValue())) {
            //si el usuario ya existe, se muestra un mensaje que te lo dice.
            usernameLabel.textProperty().setValue("This user already exists");
            usernameLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            usernameLabel.visibleProperty().setValue(false);
        }
    }

    private void handleChangePassword(KeyEvent event) {
        if(!User.checkPassword(passwordTextfield.textProperty().getValue())) {
            //mostrar label diciendo que el formato no es correcto.
            passwordLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            passwordLabel.visibleProperty().setValue(false);
        }
    }

    private void handleChangeRepeatPassword(KeyEvent event) {
        if(!passwordTextfield.textProperty().getValue().equals(repeatPasswordTextfield.textProperty().getValue())) {
            //mostrar label diciendo que no son iguales ambas contraseñas
            repeatPasswordLabel.visibleProperty().setValue(true);
        } else {
            //deshabilitar la label para que no siga apareciendo si está bien
            repeatPasswordLabel.visibleProperty().setValue(false);
        }
    }

}
