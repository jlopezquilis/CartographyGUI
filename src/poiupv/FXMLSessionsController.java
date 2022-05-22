/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLSessionsController implements Initializable {

    @FXML
    private ListView<String> listViewSessions;
    @FXML
    private Button buttonGeneralStatistics;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelHits;
    @FXML
    private Label labelFaults;
    
    private User myUser;
    private ObservableList<String> myRes;
    private ObservableList<Session> mySessions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*listViewSessions.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> 
        {
            int index = listViewSessions.getSelectionModel().selectedIndexProperty().getValue();
            if (index >= 0) {
              
                labelDate.textProperty().setValue("Date: " + myRes.get(index));
                labelHits.textProperty().setValue("Hits: " + mySessions.get(index).getHits());
                labelFaults.textProperty().setValue("Faults: " + mySessions.get(index).getFaults());
            }
        });*/
        
    }    
    
    public void initSessions(User user) {
        myUser = user;
        ArrayList<Session> sesiones = new ArrayList<>(myUser.getSessions());
        mySessions = FXCollections.observableArrayList(sesiones);
        
        int size = mySessions.size();
        ArrayList<String> res = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            String s = sesiones.get(i).getLocalDate().format(DateTimeFormatter.ISO_DATE);
            res.add(s);
        }
        myRes = FXCollections.observableArrayList(res);
        listViewSessions.setItems(myRes);
        
        listViewSessions.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> 
        {
            int index = listViewSessions.getSelectionModel().selectedIndexProperty().getValue();
            if (index >= 0) {
              
                labelDate.textProperty().setValue("Date: " + myRes.get(index));
                labelHits.textProperty().setValue("Hits: " + mySessions.get(index).getHits());
                labelFaults.textProperty().setValue("Faults: " + mySessions.get(index).getFaults());
            }
        });
        
    }

    @FXML
    private void handleOnActionGeneralStatistics(ActionEvent event) {
        int genHits = 0;
        int genFaults = 0;
        for (int i = 0; i < mySessions.size(); i++) {
            genHits += mySessions.get(i).getHits();
            genFaults += mySessions.get(i).getFaults();
        }
        labelDate.textProperty().setValue("Date: All Time");
        labelHits.textProperty().setValue("Hits: " + genHits);
        labelFaults.textProperty().setValue("Faults: " + genFaults);
        
        listViewSessions.getSelectionModel().select(-1);
    }
    
}
