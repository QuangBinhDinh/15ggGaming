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
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Win10
 */
public class SelectLevelController implements Initializable {

    @FXML
    private Rectangle Rect1,Rect2,Rect3;
    @FXML
    private Label Label1,Label2,Label3;
    @FXML
    private Button Return;
    @FXML
    private Polygon Star11, Star12, Star13, Star21, Star22, Star23, Star31, Star32, Star33;
    public void setStar(){
        try {
            HighScoreUtils.readData();
        } catch (IOException ex) {}
        String[] data = HighScoreUtils.getList();
        switch(data[1]){
            case "1":
                Star11.setStyle("-fx-fill: #f1fa02");
                break;
            case "2":
                Star11.setStyle("-fx-fill: #f1fa02");
                Star12.setStyle("-fx-fill: #f1fa02");
                break;
            case "3":
                Star11.setStyle("-fx-fill: #f1fa02");
                Star12.setStyle("-fx-fill: #f1fa02");
                Star13.setStyle("-fx-fill: #f1fa02");
                break;
            default:
                break;
        }
        switch(data[3]){
            case "1":
                Star21.setStyle("-fx-fill: #f1fa02");
                break;
            case "2":
                Star21.setStyle("-fx-fill: #f1fa02");
                Star12.setStyle("-fx-fill: #f1fa02");
                break;
            case "3":
                Star21.setStyle("-fx-fill: #f1fa02");
                Star22.setStyle("-fx-fill: #f1fa02");
                Star23.setStyle("-fx-fill: #f1fa02");
                break;
            default:
                break;
        }
        switch(data[5]){
            case "1":
                Star31.setStyle("-fx-fill: #f1fa02");
                break;
            case "2":
                Star31.setStyle("-fx-fill: #f1fa02");
                Star32.setStyle("-fx-fill: #f1fa02");
                break;
            case "3":
                Star31.setStyle("-fx-fill: #f1fa02");
                Star32.setStyle("-fx-fill: #f1fa02");
                Star33.setStyle("-fx-fill: #f1fa02");
                break;
            default:
                break;
        }
    }
   
    @FXML
    private void Level1(MouseEvent e) throws IOException{
        MusicUtils.playClick();
        FXMLDocumentController.setLevel(0);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));//lay thong tin file fxml
        Scene s = new Scene(root);//tao moi scene theo thong tin file fxml
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();//lay thong tin cua so
        window.setScene(s);//dat sence vao trong cua so
        window.show(); 
        s.getRoot().requestFocus();
    }
    @FXML
    private void Level2(MouseEvent e) throws IOException{
        MusicUtils.playClick();
        FXMLDocumentController.setLevel(1);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));//lay thong tin file fxml
        Scene s = new Scene(root);//tao moi scene theo thong tin file fxml
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();//lay thong tin cua so
        window.setScene(s);//dat sence vao trong cua so
        window.show(); 
        s.getRoot().requestFocus();
    }
    @FXML
    private void Level3(MouseEvent e) throws IOException{
        MusicUtils.playClick();
        FXMLDocumentController.setLevel(2);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));//lay thong tin file fxml
        Scene s = new Scene(root);//tao moi scene theo thong tin file fxml
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();//lay thong tin cua so
        window.setScene(s);//dat sence vao trong cua so
        window.show(); 
        s.getRoot().requestFocus();
    }
    @FXML
    private void enterLv1(){
        Rect1.setVisible(true);
        Label1.setStyle("-fx-text-fill: #f600ff");
    }
    @FXML
    private void exitLv1(){
        Rect1.setVisible(false);
        Label1.setStyle("-fx-text-fill: #d3f018");
    }
    @FXML
    private void enterLv2(){
        Rect2.setVisible(true);
        Label2.setStyle("-fx-text-fill: #f600ff");
    }
    @FXML
    private void exitLv2(){
        Rect2.setVisible(false);
        Label2.setStyle("-fx-text-fill: #d3f018");
    }
    @FXML
    private void enterLv3(){
        Rect3.setVisible(true);
        Label3.setStyle("-fx-text-fill: #f600ff");
    }
    @FXML
    private void exitLv3(){
        Rect3.setVisible(false);
        Label3.setStyle("-fx-text-fill: #d3f018");
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
        // TODO
        setStar();
    }
}
