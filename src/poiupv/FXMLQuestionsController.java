/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.Answer;
import model.Navegacion;
import model.Problem;

/**
 * FXML Controller class
 *
 * @author jlopc
 */
public class FXMLQuestionsController implements Initializable {

    @FXML
    private RadioButton answer1;
    @FXML
    private ToggleGroup answers;
    @FXML
    private RadioButton answer2;
    @FXML
    private RadioButton answer3;
    @FXML
    private RadioButton answer4;
    @FXML
    private Label labelNumber;
    @FXML
    private Label labelQuestion;
    @FXML
    private Button buttonCheckAnswer;
    @FXML
    private Button buttonNextQuest;
    
    
    // Diferentes atributos para que todo sea más cómodo :)
    private ArrayList<Problem> ListProblems;
    private int indexOfProblem = -1;
    private Navegacion nav;
    private ArrayList<Answer> ListAnswers;
    private BooleanProperty checkedAnswers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {nav = Navegacion.getSingletonNavegacion();} catch (Exception e) {};
        //checkedAnswers.setValue(Boolean.FALSE);
        //buttonNextQuest.disableProperty().bind(checkedAnswers);
    }  
    
    public void initRandom(int i) {
        indexOfProblem = i;
        if(indexOfProblem == -1) {  // Si el indice del problema es -1, significa que hemos escogido la opcion de problema aleatorio.
            // Cogemos la lista con todos los problemas y escogemos uno al azar.
            labelNumber.textProperty().setValue("Random Question");
            ListProblems = new ArrayList<>(nav.getProblems());
            int index = new Random().nextInt(ListProblems.size());
            Problem problem = ListProblems.get(index);
            // Ponemos el enunciado del problema.
            labelQuestion.textProperty().setValue(problem.getText());
            
            // Cogemos la lista de las respuestas y las desordenamos para que nunca salgan en el mismo orden.
            ListAnswers = new ArrayList<>(problem.getAnswers());
            Collections.shuffle(ListAnswers);
            // Ponemos el texto en cada una de las respuestas.
            answer1.textProperty().setValue(ListAnswers.get(0).getText());
            answer2.textProperty().setValue(ListAnswers.get(1).getText());
            answer3.textProperty().setValue(ListAnswers.get(2).getText());
            answer4.textProperty().setValue(ListAnswers.get(3).getText());
        } else {    // En este caso el indice del problema se saca de la pantalla de seleccionar problema y se lo pasaríamos.
            // Escogemos el problema seleccionado y lo mostramos por pantalla con sus respectivas respuestas.
            labelNumber.textProperty().setValue("Question " + indexOfProblem);
            ListProblems = new ArrayList<>(nav.getProblems());
            Problem problem = ListProblems.get(indexOfProblem);
            labelQuestion.textProperty().setValue(problem.getText());
            
            ListAnswers = new ArrayList<>(problem.getAnswers());
            Collections.shuffle(ListAnswers);
            answer1.textProperty().setValue(ListAnswers.get(0).getText());
            answer2.textProperty().setValue(ListAnswers.get(1).getText());
            answer3.textProperty().setValue(ListAnswers.get(2).getText());
            answer4.textProperty().setValue(ListAnswers.get(3).getText());
        }
    }

    @FXML
    private void handleOnActionButtonChechAnswer(ActionEvent event) {
        //checkedAnswers.setValue(Boolean.TRUE);
    }

    @FXML
    private void handleOnActionButtonNextQuest(ActionEvent event) {
        //Aquí faltaría añadir el código para meter en la base de datos si la respuesta era correcta y eso
        if(indexOfProblem == -1) {  // Si el indice del problema es -1, significa que hemos escogido la opcion de problema aleatorio.
            // Cogemos la lista con todos los problemas y escogemos uno al azar.
            labelNumber.textProperty().setValue("Random Question");
            ListProblems = new ArrayList<>(nav.getProblems());
            int index = new Random().nextInt(ListProblems.size());
            Problem problem = ListProblems.get(index);
            // Ponemos el enunciado del problema.
            labelQuestion.textProperty().setValue(problem.getText());
            
            // Cogemos la lista de las respuestas y las desordenamos para que nunca salgan en el mismo orden.
            ListAnswers = new ArrayList<>(problem.getAnswers());
            Collections.shuffle(ListAnswers);
            // Ponemos el texto en cada una de las respuestas.
            answer1.textProperty().setValue(ListAnswers.get(0).getText());
            answer2.textProperty().setValue(ListAnswers.get(1).getText());
            answer3.textProperty().setValue(ListAnswers.get(2).getText());
            answer4.textProperty().setValue(ListAnswers.get(3).getText());
        } else {    // En este caso el indice del problema se saca de la pantalla de seleccionar problema y se lo pasaríamos.
            // Escogemos el problema seleccionado y lo mostramos por pantalla con sus respectivas respuestas.
            indexOfProblem++;
            labelNumber.textProperty().setValue("Question " + indexOfProblem);
            ListProblems = new ArrayList<>(nav.getProblems());
            Problem problem = ListProblems.get(indexOfProblem);
            labelQuestion.textProperty().setValue(problem.getText());
            
            ListAnswers = new ArrayList<>(problem.getAnswers());
            Collections.shuffle(ListAnswers);
            answer1.textProperty().setValue(ListAnswers.get(0).getText());
            answer2.textProperty().setValue(ListAnswers.get(1).getText());
            answer3.textProperty().setValue(ListAnswers.get(2).getText());
            answer4.textProperty().setValue(ListAnswers.get(3).getText());
        }
        
    }
    
}
