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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLLogInController implements Initializable {
    
    //For setting it as main stage
    private Stage primaryStage;
    @FXML
    private Hyperlink toSignUp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initMainWindow(Stage stage) {
        primaryStage = stage;
    }
    
    @FXML
    private void handleOnActionSignUp(ActionEvent event) throws IOException {
        toSignUp.setText("funciono");
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLSignUp.fxml"));
        Parent root = myLoader.load();
        FXMLSignUpController SUController = myLoader.<FXMLSignUpController>getController();
        
        SUController.initSU(primaryStage);
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Navigation Tool - Sign Up");
        primaryStage.show();
        
        
        
    }

    private void handleOnActionButtonClose(ActionEvent event) {
        primaryStage.close();
    }
    
}
