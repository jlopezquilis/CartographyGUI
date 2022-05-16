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
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
