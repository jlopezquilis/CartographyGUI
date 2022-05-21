/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

//import java.awt.Color;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import poiupv.Poi;

/**
 * @author Ivaniko
 * @author suprimo keloke
 * @author Juanito
 */
public class FXMLDocumentController implements Initializable {
    

    //=======================================
    // hashmap para guardar los puntos de interes POI
    private final HashMap<String, Poi> hm = new HashMap<>();
    // ======================================
    // la variable zoomGroup se utiliza para dar soporte al zoom
    // el escalado se realiza sobre este nodo, al escalar el Group no mueve sus nodos
    private Group zoomGroup;

    private ListView<Poi> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;
    @FXML
    private ToggleButton markButton;
    private Circle circle;
    @FXML
    private ToggleGroup tool;
    @FXML
    private ColorPicker colorPick;
    @FXML
    private Slider sliderThick;
    @FXML
    private ToggleButton lineButton;
    
    private Text label;
    
    private Line myLine;
    private Circle myCircle;
    private double startXArc;
    //For moving the protractor
    private ImageView protractor;
    private double startTransX, startTransY;
    private double baseX, baseY;
    private int protractorOpen = 0;
    
    @FXML
    private MenuItem selectProblem;
    
    //Para checkear si se ha iniciado sesion:
    private static BooleanProperty isLoggedIn;
    
    
    @FXML
    private Menu accountMenu;
    @FXML
    private MenuItem selectRandomProblem;
    @FXML
    private Menu problemsMenu;
    @FXML
    private MenuItem logOutOption;
    @FXML
    private ToggleButton arcButton;
    @FXML
    private ToggleButton textButton;
    @FXML
    private ToggleButton clearButton;
    @FXML
    private ToggleGroup tool21;
    @FXML
    private MenuItem menuItemLogIn;
    @FXML
    private MenuItem menuItemSignUp;
    @FXML
    private ToggleButton protractorButton;
    @FXML
    private ToggleButton crossButton;
    @FXML
    private ToggleGroup tool1;
    
    
    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }
    
    
    @FXML
    private void handleMousePressed(MouseEvent event) {     
        //If tool selected is mark
        if (markButton.isSelected()) {
            //Create a point (small circunference)
            circle = new Circle(sliderThick.getValue());
            //Apply color and thickness to point (For developing in future)
            
            circle.setFill(colorPick.getValue());
            circle.setStrokeWidth(sliderThick.getValue());;
            
            zoomGroup.getChildren().add(circle);
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            
            //For deleting the point (context menu)
            circle.setOnContextMenuRequested(eventContext -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem deleteItem = new MenuItem("Delete");
                menuContext.getItems().add(deleteItem);
                //If the user selects the option, we delete the line
                deleteItem.setOnAction( eventMenu ->{
                    //we get the line form the original event, and delete it.
                    zoomGroup.getChildren().remove((Node)eventContext.getSource());
                    eventMenu.consume();
                });
                menuContext.show(circle, eventContext.getSceneX(), eventContext.getSceneY());
                eventContext.consume();
            });
        }
        
        //If tool selected is line
        else if (lineButton.isSelected()) {
            myLine = new Line(event.getX(), event.getY(), event.getX(), event.getY());
            myLine.setStrokeWidth(sliderThick.getValue());
            myLine.setStroke(colorPick.getValue());
            zoomGroup.getChildren().add(myLine);
            //For deleting the line (context menu)
            myLine.setOnContextMenuRequested(eventContext -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem deleteItem = new MenuItem("Delete");
                menuContext.getItems().add(deleteItem);
                //If the user selects the option, we delete the line
                deleteItem.setOnAction( eventMenu ->{
                    //we get the line form the original event, and delete it.
                    zoomGroup.getChildren().remove((Node)eventContext.getSource());
                    eventMenu.consume();
                });
                menuContext.show(myLine, eventContext.getSceneX(), eventContext.getSceneY());
                eventContext.consume();
            });

        }
        else if (arcButton.isSelected()) {
            myCircle = new Circle(1);
            myCircle.setStroke(colorPick.getValue());
            myCircle.setStrokeWidth(sliderThick.getValue());
            myCircle.setFill(Color.TRANSPARENT);
            myCircle.setCenterX(event.getX());
            myCircle. setCenterY(event.getY());
            zoomGroup.getChildren().add(myCircle);
            startXArc = event.getX();
            
            //For deleting the arc (context menu)
            myCircle.setOnContextMenuRequested(eventContext -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem deleteItem = new MenuItem("Delete");
                menuContext.getItems().add(deleteItem);
                //If the user selects the option, we delete the line
                deleteItem.setOnAction( eventMenu ->{
                    //we get the line form the original event, and delete it.
                    zoomGroup.getChildren().remove((Node)eventContext.getSource());
                    eventMenu.consume();
                });
                menuContext.show(myCircle, eventContext.getSceneX(), eventContext.getSceneY());
                eventContext.consume();
            });
        }
        else if (textButton.isSelected()) {
            TextField userText = new TextField();
            zoomGroup.getChildren().add(userText);
            userText.setLayoutX(event.getX());
            userText.setLayoutY(event.getY());
            userText.requestFocus();
            
            //OnAction
            userText.setOnAction(eventT ->{
                label = new Text(userText.getText());
                label.setX(userText.getLayoutX());
                label.setY(userText.getLayoutY());
                label.setFill(colorPick.getValue());
                //sliderThick.setValue(20);
                label.setFont(Font.font("Gafatar", sliderThick.getValue()));
                zoomGroup.getChildren().add(label);
                zoomGroup.getChildren().remove(userText);
                event.consume();
            });
            
            //For deleting the label (context menu) NO FUNCIONA
            label.setOnContextMenuRequested(eventContext -> {
                ContextMenu menuContext = new ContextMenu();
                MenuItem deleteItem = new MenuItem("Delete");
                menuContext.getItems().add(deleteItem);
                //If the user selects the option, we delete the line
                deleteItem.setOnAction( eventMenu ->{
                    //we get the line form the original event, and delete it.
                    zoomGroup.getChildren().remove((Node)eventContext.getSource());
                    eventMenu.consume();
                });
                menuContext.show(label, eventContext.getSceneX(), eventContext.getSceneY());
                eventContext.consume();
            });
        }
        else if (protractorButton.isSelected()) {
            if (protractorOpen == 0){
                protractor = new ImageView("resources/transportador.png");
                protractor.setOpacity(0.65);
                protractor.setX(event.getX());
                protractor.setY(event.getY());
                zoomGroup.getChildren().add(protractor);
                protractorOpen = 1;
            } 
            startTransX = event.getSceneX();
            startTransY = event.getSceneY();
            baseX = protractor.getTranslateX();
            baseY = protractor.getTranslateY();
            protractor.getScene().setCursor(Cursor.CROSSHAIR); //change the cursor
            event.consume();
        }
        else if (crossButton.isSelected()) {
            //Let's make 4 lines for creating the axis
            Line lineXP = new Line(170, event.getY(), event.getX(), event.getY());
            lineXP.setStroke(Color.RED);
            zoomGroup.getChildren().add(lineXP);
            Line linePX = new Line(event.getX(), event.getY(), 8610, event.getY());
            linePX.setStroke(Color.RED);
            zoomGroup.getChildren().add(linePX);
            Line lineYP = new Line(event.getX(), 280, event.getX(), event.getY());
            lineYP.setStroke(Color.RED);
            zoomGroup.getChildren().add(lineYP);
            Line linePY = new Line(event.getX(), event.getY(), event.getX(), 5515);
            linePY.setStroke(Color.RED);
            zoomGroup.getChildren().add(linePY);
        }
        
    }
    
    @FXML
    private void handleMouseDragged (MouseEvent event) {
        if (lineButton.isSelected()){
            myLine.setEndX(event.getX());
            myLine.setEndY(event.getY());
            event.consume();
        }
        else if (arcButton.isSelected()) {
            double radio = Math.abs(event.getX() - startXArc);
            myCircle.setRadius(radio);
            event.consume();
        }
        else if (protractorButton.isSelected()){
            double despX = event.getSceneX() - startTransX;
            double despY = event.getSceneY() - startTransY;
            protractor.setTranslateX(baseX + despX);
            protractor.setTranslateY(baseY + despY);
            event.consume();
        }
    }
    

    //Handler dado en el ejemplo. Podemos eliminarlo
    void listClicked(MouseEvent event) {
        Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();

        // Animación del scroll hasta la posicion del item seleccionado
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = itemSelected.getPosition().getX() / mapWidth;
        double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // movemos el objto map_pin hasta la posicion del POI
        double pinW = map_pin.getBoundsInLocal().getWidth();
        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setLayoutX(itemSelected.getPosition().getX());
        map_pin.setLayoutY(itemSelected.getPosition().getY());
        pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    }

    //Método dado en el ejemplo. Podemos eliminarlo
    private void initData() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initData();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        //zoomGroup.getChildren().add(circle);
        map_scrollpane.setContent(contentGroup);

        //Para controlar si el usuario ha iniciado sesion
        isLoggedIn = new SimpleBooleanProperty();
        isLoggedIn.setValue(Boolean.FALSE);
        
        problemsMenu.visibleProperty().bind(isLoggedIn);
        logOutOption.visibleProperty().bind(isLoggedIn);
        menuItemLogIn.visibleProperty().bind(Bindings.not(isLoggedIn));
        menuItemSignUp.visibleProperty().bind(Bindings.not(isLoggedIn));
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
        posicion.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
                + "         X: " + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    private void cerrarAplicacion(ActionEvent event) {
        ((Stage)zoom_slider.getScene().getWindow()).close();
    }

    private void acercaDe(ActionEvent event) {
        Alert mensaje= new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2022");
        mensaje.showAndWait();
    }

    @FXML
    private void handleOnActionLogIn(ActionEvent event) throws IOException{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLLogIn.fxml"));
        Pane root = (Pane) myLoader.load();
        
        FXMLLogInController controller = myLoader.<FXMLLogInController>getController();
        
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        controller.initMainWindow(stage);
        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
        
    }

    @FXML
    private void handleOnActionSignUp(ActionEvent event) throws IOException{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLSignUp.fxml"));
        Pane root = (Pane) myLoader.load();
        
        FXMLSignUpController controller = myLoader.<FXMLSignUpController>getController();
        
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        controller.initSU(stage, true);
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void handleOnActionAboutUs(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText("Creators of this splendid project");
        alert.setContentText("Iván Haro Limiñana\nJuan Francisco López Quilis\nPablo Pérez Martínez");
        alert.showAndWait();
    }

    @FXML
    private void handleOnActionPickRandom(ActionEvent event) throws IOException{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLQuestions.fxml"));
        Pane root = (Pane) myLoader.load();
        
        FXMLQuestionsController controller = myLoader.<FXMLQuestionsController>getController();
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        controller.initRandom(-1);
        stage.setTitle("Question");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    private void handleOnActionPickProblem(ActionEvent event) throws IOException {
        /*List<String> choices = new ArrayList<>();
        for (int i = 1; i < 20; i++) choices.add(String.format("%d", i)); //Pongo 20 como numero arbitrario
        ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);   //porque no se cuantos problemas hay
        dialog.setTitle("Choose problem");
        dialog.setHeaderText("Which problem do you want to solve?");
        dialog.setContentText("Choose a number");
        //Hacemos que el dialog se vea bonito
        DialogPane dp = dialog.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("LoginStyle.css").toExternalForm()); //Podemos crear un css propio para el alert
        Optional<String> result = dialog.showAndWait();                                     //pero eso segun vayamos de tiempo
        
        if (result.isPresent()) {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLQuestions.fxml"));
            Pane root = (Pane) myLoader.load();
        
            FXMLQuestionsController controller = myLoader.<FXMLQuestionsController>getController();
            Scene scene = new Scene (root);
            Stage stage = new Stage();
            stage.setScene(scene);
            controller.initRandom(Integer.parseInt(result.get()));
            stage.setTitle("Question");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }*/
        
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLSelectQuestion.fxml"));
        Pane root = (Pane) myLoader.load();
        
        FXMLSelectQuestionController controller = myLoader.<FXMLSelectQuestionController>getController();
        Scene scene = new Scene (root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Select Question");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
        
    }

    public static void setLoggedIn() {
        isLoggedIn.setValue(Boolean.TRUE);
        
    }

    @FXML
    private void handleLogOut(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Log Out");
        alert.setHeaderText("Caution!");
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            isLoggedIn.setValue(Boolean.FALSE);
        } else {
            alert.close();
        }
        
    }


}
