/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Navegacion;
import model.Problem;

/**
 * FXML Controller class
 *
 * @author ivanh
 */
public class FXMLSelectQuestionController implements Initializable {

    @FXML
    private ListView<String> listViewProblems;
    @FXML
    private Label labelEnunciado;
    @FXML
    private Button buttonSelectProblem;
    @FXML
    private Button buttonRandomProblem;
    @FXML
    private Button buttonCancel;
    
    private Navegacion nav;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {nav = Navegacion.getSingletonNavegacion();} catch (Exception e) {};
        ArrayList<Problem> lista = new ArrayList<>(nav.getProblems());
        ObservableList<Problem> myProblems = FXCollections.observableArrayList(lista);
        
        ArrayList<String> showList = new ArrayList<>();
        for(int i = 0; i < lista.size(); i++) {
            showList.add("Question " + i);
        }
        ObservableList<String> myShowList = FXCollections.observableArrayList(showList);
        
        listViewProblems.setItems(myShowList);
        
        buttonSelectProblem.disableProperty().bind(listViewProblems.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        
        listViewProblems.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> 
        {
            int index = listViewProblems.getSelectionModel().selectedIndexProperty().getValue();
            labelEnunciado.textProperty().setValue(myProblems.get(index).getText());
            labelEnunciado.visibleProperty().setValue(true);
        });
    }    

    @FXML
    private void handleOnActionButtonSelect(ActionEvent event) throws IOException {
        int index = listViewProblems.getSelectionModel().selectedIndexProperty().getValue();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLQuestions.fxml"));
        Pane root = (Pane) myLoader.load();
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLQuestionsController controller = myLoader.<FXMLQuestionsController>getController();
        Scene scene = new Scene (root);
        thisStage.setScene(scene);
        controller.initRandom(index);
        thisStage.setTitle("Question");
        thisStage.setResizable(true);
        thisStage.show();
    }

    @FXML
    private void handleOnActionButtonRandom(ActionEvent event) throws IOException {
        
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLQuestions.fxml"));
        Pane root = (Pane) myLoader.load();
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLQuestionsController controller = myLoader.<FXMLQuestionsController>getController();
        Scene scene = new Scene (root);
        thisStage.setScene(scene);
        controller.initRandom(-1);
        thisStage.setTitle("Question");
        thisStage.setResizable(true);
        thisStage.show();
    }

    @FXML
    private void handleOnActionButtonCancel(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}
