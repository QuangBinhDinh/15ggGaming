/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BomberMan;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Win10
 */
public class mainRun extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        scene.getRoot().requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            HighScoreUtils.readData();
        } catch (IOException ex) {}
        MusicUtils.playMenuMusic();
        launch(args);
        
    }
    
}
