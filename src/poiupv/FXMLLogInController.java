/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLLogInController implements Initializable {
    
    //For setting it as main stage
    private Stage logInStage;
    @FXML
    private Hyperlink toSignUp;
    @FXML
    private Button buttonCancel;
    @FXML
    private Label labelUsername;
    @FXML
    private Button buttonLogIn;
    @FXML
    private TextField textfieldUsername;
    @FXML
    private PasswordField textfieldPassword;
    
    private Navegacion nav;
    private User user;
    private boolean bUser;
    private boolean bPassword;
    @FXML
    private Label labelPassword;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {nav = Navegacion.getSingletonNavegacion();} catch (Exception e) {}
        
        buttonLogIn.disableProperty().setValue(true);
        
        textfieldUsername.focusedProperty().addListener((observable, oldVal, newVal) -> 
        {
            if(!newVal) {
                String sUser = textfieldUsername.textProperty().getValue();
                if(!nav.exitsNickName(sUser)){
                    labelUsername.visibleProperty().setValue(true);
                    buttonLogIn.disableProperty().setValue(true);
                    bUser = false;
                } else {
                    labelUsername.visibleProperty().setValue(false);
                    bUser = true;
                    if(todoCorrecto()) {buttonLogIn.disableProperty().setValue(false);}
                }
            }
        });
        
        textfieldPassword.focusedProperty().addListener((observable, oldVal, newVal) -> //Tenemos que revisar esto pq no va del todo bien
        {
            String sPass =  textfieldPassword.textProperty().getValue();
            if(sPass.equals("")) {
                buttonLogIn.disableProperty().setValue(true);
                bPassword = false;
            } else {
                bPassword = true;
                if(todoCorrecto()) {buttonLogIn.disableProperty().setValue(false);}
            }
        });
        
        
    }    

    public void initMainWindow(Stage stage) {
        logInStage = stage;
    }
    
    @FXML
    private void handleOnActionSignUp(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLSignUp.fxml"));
        Pane root = (Pane) myLoader.load();
        FXMLSignUpController SUController = myLoader.<FXMLSignUpController>getController();
        
        SUController.initSU(logInStage, false);
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        logInStage.setScene(scene);
        logInStage.setTitle("Navigation Tool - Sign Up");
        logInStage.show();
    }

    @FXML
    private void handleOnActionButtonCancel(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void handleOnActionButtonLogIn(ActionEvent event) throws IOException{
        FXMLDocumentController.setLoggedIn();
        String nickname = textfieldUsername.textProperty().getValue();
        String password = textfieldPassword.textProperty().getValue();
        user = nav.loginUser(nickname, password);
        if(user == null) {
            labelPassword.visibleProperty().setValue(true);
        } else {
            //cerramos el login, faltaría pasarle el usuario a la ventana principal.
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }
    
    private boolean todoCorrecto() {
        return bUser && bPassword;
    }
}
