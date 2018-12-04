/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BomberMan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Win10
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button newGame,highScores;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    @FXML
    private void NewGame(MouseEvent e) throws IOException{
        MusicUtils.playClick();
        Parent root = FXMLLoader.load(getClass().getResource("SelectLevel.fxml"));
        Scene s = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();//lay thong tin cua so
        window.setScene(s);
        window.show();  
        s.getRoot().requestFocus();
    }
    @FXML
    private void HighScores(MouseEvent e) throws IOException{
        MusicUtils.playClick();
        Parent root = FXMLLoader.load(getClass().getResource("HighScores.fxml"));
        Scene s = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();//lay thong tin cua so
        window.setScene(s);
        window.show();  
        s.getRoot().requestFocus(); 
    }
    @FXML
    private synchronized void enterNewGame(){
        newGame.setStyle("-fx-background-color: #e7f50a");
        
    }
    @FXML
    private synchronized void exitNewGame(){
        newGame.setStyle("-fx-background-color: #321d04");
        
    }
     @FXML
    private synchronized void enterHighScores(){
        highScores.setStyle("-fx-background-color: #e7f50a");
        
    }
    @FXML
    private synchronized void exitHighScores(){
        highScores.setStyle("-fx-background-color: #321d04");
        
    }
}
