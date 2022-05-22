/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleChangePicture(ActionEvent event) {
    }

    @FXML
    private void handleAcceptButton(ActionEvent event) {
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
    }
    
}
