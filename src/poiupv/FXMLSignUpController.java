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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image; //Otro import que he añadido con el alt + enter, hay que comprobar que esté bien
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import model.Navegacion; //Esto lo he añadido con lo d alt + enter, no se si
                         //es la clase que hay que importar para que funcione
/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLSignUpController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAcceptButton(ActionEvent event) throws Exception { //Sin el throws no funciona el singleton
        //Asumimos que todos los parametros son correctos pq lo habremos
        //comprobado en sus respectivos metodos:
        String username = usernameTextfield.textProperty().getValue();
        String email = emailTextfield.textProperty().getValue();
        String password = passwordTextfield.textProperty().getValue();
        Image avatar = avatarImage.getImage();
        LocalDate birthday = birthdayDatepicker.getValue(); //Ni idea d si la fecha se saca con esto jsjs
        
        Navegacion nav = Navegacion.getSingletonNavegacion();
        nav.registerUser(username, email, password, avatar, birthday);
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
    }

    @FXML
    private void handleChangeUsername(InputMethodEvent event) {
    }

    @FXML
    private void handleChangeBirthdate(InputMethodEvent event) {
    }

    @FXML
    private void handleChangeEmail(InputMethodEvent event) {
    }

    @FXML
    private void handleChangePassword(InputMethodEvent event) {
    }

    @FXML
    private void handleChangeRepeatPassword(InputMethodEvent event) {
    }

    @FXML
    private void handleSelectImage(ActionEvent event) {
    }
    
}
