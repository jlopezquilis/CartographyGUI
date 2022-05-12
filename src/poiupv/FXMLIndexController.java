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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLIndexController implements Initializable {

    @FXML
    private Button chooseProblem;
    @FXML
    private Button randomProblem;
    @FXML
    private Button showResults;
    @FXML
    private Button modifyProfile;
    @FXML
    private Button logOut;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleChooseProblem(ActionEvent event) {
    }

    @FXML
    private void handleRandomProblem(ActionEvent event) {
    }

    @FXML
    private void handleShowResults(ActionEvent event) {
    }

    @FXML
    private void handleModifyProfile(ActionEvent event) throws IOException {
        /*FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLModifyProfile.fxml"));
        Parent root = myLoader.load();
        FXMLModifyProfileController SUController = myLoader.<FXMLModifyProfileController>getController();
                                                        //NO ENCUENTRA EL CONTROLLER PORQUE TODAVIA NO ESTA CREADA
        SUController.initSU(primaryStage);              //LA VENTANA, ESTO ES MAS QUE NADA POR TENERLO YA ESCRITO
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Navigation Tool - Modify Profile");
        primaryStage.show();*/
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLLogIn.fxml"));
        Parent root = myLoader.load();
        FXMLLogInController LoginController = myLoader.<FXMLLogInController>getController();
        
        LoginController.initMainWindow(primaryStage);
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Navigation Tool - Log In");
        primaryStage.show();
    }
    
}
