/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BomberMan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Win10
 */
public class HighScoresController implements Initializable {
    @FXML
    private Label Score1, Score2, Score3, Star1, Star2, Star3;
    @FXML
    private Button Return;
    public void setLabel(){
        try {
            HighScoreUtils.readData();
        } catch (IOException ex) { }
        String[] data = HighScoreUtils.getList();
        Score1.setText(data[0]);
        Score2.setText(data[2]);
        Score3.setText(data[4]);
        Star1.setText(data[1]);
        Star2.setText(data[3]);
        Star3.setText(data[5]);
    }
    @FXML
    private void returnMenu(MouseEvent e) throws IOException{
        MusicUtils.playClick();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene s = new Scene(root);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();//lay thong tin cua so
        window.setScene(s);
        window.show();  
        s.getRoot().requestFocus(); 
    }
    @FXML
    private void enterMenu(){
        Return.setStyle("-fx-background-color: #e7f50a");
    }
    @FXML
    private void exitMenu(){
        Return.setStyle("-fx-background-color: #321d04");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLabel();
    }    
    
}
