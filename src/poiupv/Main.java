/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jose
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        //Empezamos con el Login. De ahi podrá derivarse a SignUp o bien al iniciar sesion, al programa.
        //Ayuda: Practica 3.2 va sobre cambio de stages y ventanas.
        FXMLLoader loader= new  FXMLLoader(getClass().getResource("FXMLLogIn.fxml"));
        Parent root = loader.load();
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        // 2.2- Codigo para configurar el paso de un stage a otro
        FXMLLogInController mainController = loader.<FXMLLogInController>getController();
        //Set current stage as main stage
        mainController.initMainWindow(stage);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setScene(scene);
        
        stage.setTitle("Navigation Tool - Main window");
        stage.setResizable(false);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
