/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author peybo
 */
public class FXMLMapController implements Initializable {

    @FXML
    private MenuItem loginMenuOption;
    @FXML
    private MenuItem modifyMenuOption;
    @FXML
    private MenuItem logoutMenuOption;
    @FXML
    private MenuItem informationMenu;
    @FXML
    private Hyperlink hyperLink;
    @FXML
    private ColorPicker colorPicker;
    
    BooleanProperty isLoggedIn;
    
    private Stage primaryStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //isLoggedIn.setValue(Boolean.FALSE); //Nada más entrar a la pantalla principal aún no estamos loggeados
    
        //Añadimos un listener para cambiar el texto del hyperlink:
        /*hyperLink.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isLoggedIn.getValue() == Boolean.FALSE) {
                hyperLink.textProperty().setValue("Log in");
            }
            //else hyperLink.textProperty().setValue(); //Aquí hay que pasar como parámetro la variable nombre del usuario
        });*/
    }    
    
    public void initMainWindow(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private void handleLoginMenu(ActionEvent event) {
    }

    @FXML
    private void handleModifyMenu(ActionEvent event) {
    }

    @FXML
    private void handleLogoutMenu(ActionEvent event) {
    }

    @FXML
    private void handleInformationMenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("About us");
        alert.setHeaderText("Creators of this splendid project");

        alert.setContentText("Iván Haro Limiñana\nJuan Francisco López Quilis\nPablo Pérez Martínez");
        alert.showAndWait(); 
    }

    @FXML
    private void handleHyperLink(ActionEvent event) {
        
    }
    
}
