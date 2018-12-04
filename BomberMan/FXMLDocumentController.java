/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BomberMan;


import java.io.IOException;
import static java.lang.Math.abs;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.*;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 *
 * @author Win10
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private ArrayList<ImageView> WallList;//mang chua cac wall co dinh
    @FXML
    private ArrayList<ImageView> DustList;//mang chua cac wall co the pha
    @FXML
    private ImageView background, Bomb,Bomb1,Bomb2;//background va bomb
    @FXML
    private ImageView Player;//player
    @FXML
    private ImageView LaserLeft,LaserRight,LaserUp,LaserDown,LaserCenter;
    @FXML
    private ImageView LaserLeft1,LaserRight1,LaserUp1,LaserDown1,LaserCenter1;
    @FXML
    private ImageView LaserLeft2,LaserRight2,LaserUp2,LaserDown2,LaserCenter2;
    @FXML
    private ImageView IceCube, IceCube1, IceCube2,  IceCube3, IceCube4, IceCube5, IceCube6, IceCube7, IceCube8, IceCube9;
    
    private  ImageView[] freeList = new ImageView[10];
    public static void setLevel(int lv){
        level = lv;
    }
    @FXML
    private ArrayList<ImageView> mobList ;
    @FXML
    private ArrayList<ImageView> flowerList;
    @FXML
    private ArrayList<ImageView> iceGroundList;
    @FXML
    private ImageView Heart, Heart1, Heart2;
    @FXML
    private Label Point,Info,Level, TimeCount, LvScore, LvTimeLeft;
    @FXML
    private Polygon Star1, Star2, Star3;
    @FXML
    private Button NextLv,ReturnMenu;
    @FXML
    private ImageView Door,BombBuff, Boot ,Ice;
    @FXML
    private ImageView Flower;
    private RotateTransition[] rot;
    private PathTransition[] pah;
    private ParallelTransition[] par;
    //luu y: tag FMXL giu nguyen toan bo thuoc tinh cua cac bien o trong SceneBuilder
    //tuc la cac bien tren da dc khai bao san trong SceneBuilder va duoc truyen vao day
    private int realX = 50,realY = 50;//toa do cua Player so voi map
    //luu y o day co 2 toa do. Toa do Player.getLayOut() la so voi cua so game, con toa do realX realY la so voi ca map
    private boolean isPlaced,isPlaced1,isPlaced2;
    private boolean canFreeze = true, freezing = false;
    private boolean[] mobFreeze = new boolean[3];
    private Timeline time;
    private PathTransition path[] = new PathTransition[8];
    private PathTransition[] path1;
    private int[] mobLv1,mobLv2;//mang chua mang cua mob
    private Media md;
    private MediaPlayer mp;
    private boolean mobLv1Move[] = new boolean[8];
    private int pos,pos1;
    private int sec;//thoi gian cua tung lv
    private static int LvTime;
    public static void setLvTime(int time){
        LvTime = time;
    }//ham set thoi gian cho moi lv
    @FXML
    private ImageView mob;
    private int lifeLeft,ii,score = 0;//lifeLeft la so mang cua Player, Point la so diem gianh duoc
    private boolean processing ;//bien processing de kiem tra vong while cua ham ben duoi (xem line 1087)
    private boolean isPlayerDead ;//cho biet Player da chet hay chua
    private boolean stageNotClear;//cho biet Stage da clear hay chua
    private boolean completed, halfTime, noLifeLost;
    private int mapMove = 0;//khoang cach ma map da di chuyen
    private int mobMax = 8 ;//so mob to da(cu moi lv se tang len 1)
    private int mobLeft ;//so mob con lai tren map
    private int bombLevel , speedLevel;
    private static int level = 0;
    private int star;
    private boolean isPaused;
    private ArrayList<ImageView> listMob2;
    private AnimationTimer ani;
    private boolean left,right,up,down;
    @FXML
    private AnchorPane ScoreBoard, Result;
    @FXML
    private Pane myPane;
    @FXML
    private ImageView Slime, Slime1, Slime2;
    @FXML
    private ImageView LaserLeftLeft,LaserLeftLeft1,LaserLeftLeft2,LaserUpUp,LaserUpUp1,LaserUpUp2,LaserRightRight,LaserRightRight1,LaserRightRight2,LaserDownDown,LaserDownDown1,LaserDownDown2;
    @FXML
    public synchronized void keyPressed(KeyEvent e){
        if(isPlayerDead == false){
        switch(e.getCode()){
            case UP:
                up = true;
                break;
            case DOWN:
                down = true;
                break;
            case LEFT:
                left = true;
                break;
            case RIGHT:
                right = true;
                break;
            case SPACE:
                if(isPlaced == true && isPlaced1 == true && isPlaced2 == true) break;
                else if((isPlaced == false && isPlaced1 == false && isPlaced2 == false) || (isPlaced == false && isPlaced1 == false && isPlaced2 == true) || (isPlaced == false && isPlaced1 == true && isPlaced2 == true)){
                    isPlaced = true;
                    doBomb();
                    break;
                }else if((isPlaced == true && isPlaced1 == false && isPlaced2 == false) || (isPlaced == true && isPlaced1 == false && isPlaced2 == true)){
                    isPlaced1 = true;
                    doBomb1();
                    break;
                }else if((isPlaced == true && isPlaced1 == true && isPlaced2 == false) || (isPlaced == false && isPlaced1 == true && isPlaced2 == false)){
                    isPlaced2 = true;
                    doBomb2();
                    break;
                }
            case ESCAPE:
                if(isPaused == false){
                    pauseGame();
                }
                //truong hop nhan ESC thi game se dung lai
                break;
            case ENTER:
                System.out.println(realX + " " + Player.getLayoutX());
                break;
            case F:
                if(canFreeze){
                    canFreeze = false;
                    freeze();
                }
                break;
            default:
                break;
        }
        }      
    }//ham cho player di chuyen bang cac mui ten
    @FXML
    public synchronized void keyReleased(KeyEvent e){
        switch(e.getCode()){
            case UP:
                up = false;
                break;
            case DOWN:
                down = false;
                break;
            case LEFT:
                left = false;
                break;
            case RIGHT:
                right = false;
                break;
        }
    }
    public synchronized void moveAnimation(){//ham chuyen dong 
        //ham di chuyen trong game, su dung KeyEvent
        /* Giai thich cach chuyen dong map:
         * Trong game neu di chuyen ve phia ria map, thi toan map se dung yen, chi co toa do cua nhan vat la thay doi. Con khi player di chuyen
         * o giua map, thi nhan vat se dung yen, thay vao do toan bo map va cac object khac se chuyen dong nguoc voi huong mui ten di chuyen cua 
         * ban phim (dieu nay tao ra hieu ung nhan vat di chuyen that, nhung thuc chat la khong)
         * Do do moi co 2 loai toa do : realX,Y la toa do that cua nhan vat, con Player.getLayout chi la toa do so voi cua so games
         * 
         */
        ani = new AnimationTimer(){
            @Override
            public void handle(long now){
                int move = 5 * speedLevel;
                if(left && !isPlayerDead){
                    if(realX > 360 && realX <= 1110){
                      //truong hop nhan vat dang o giua map
                      //luc nay nhan vat se co dinh o giua man hinh game, thay vao do toan bo map se di chuyen
                        if(!Utils.totalBlockLeft(Player, WallList, DustList)){
                            allMoveRight();//di chuyen toan bo map
                            realX -= move;//update lai toa do cua player so voi map
                        }
                    }else if((realX > 50 && realX <= 360) || (realX > 1100 && realX <= 1450 - Player.getFitWidth()) ){
                        //truong hop nhan vat o ria map
                        //luc nay nhan vat se di chuyen va di ve ria man hinh game
                        if(!Utils.totalBlockLeft(Player, WallList, DustList)){
                            Player.setLayoutX(Player.getLayoutX() - move);
                            realX -= move;
                        }
                    }
                }
                if(right && !isPlayerDead){
                    //tuong tu truong hop LEFT
                    if(realX >= 360 && realX < 1110){
                        if(!Utils.totalBlockRight(Player, WallList, DustList)){
                            allMoveLeft();
                            realX += move;
                        }
                    }else if((realX >= 50 && realX < 360) || (realX >= 1100 && realX < 1450 - Player.getFitWidth()) ){
                        if(!Utils.totalBlockRight(Player, WallList, DustList)){
                            Player.setLayoutX(Player.getLayoutX() + move);
                            realX += move;
                        }
                    }
                }
                if(up && !isPlayerDead){
                    if(!Utils.totalBlockUp(Player, WallList, DustList)){
                        //kiem tra xem co player co bi chan tren boi wall nao khong
                        //neu khong bi chan thi thuc hien di chuyen nhan vat
                        Player.setLayoutY(Player.getLayoutY() - move);
                        realY -= move;//update lai toa do that cua nhan vat(toa do so voi toan map
                    }
                }
                if(down && !isPlayerDead){
                    if(!Utils.totalBlockDown(Player, WallList, DustList)){
                        //tuong tu nhu truong hop UP
                        Player.setLayoutY(Player.getLayoutY() + move);
                        realY += move;
                    }
                }
            }
        };
        ani.start();
    }
    public synchronized void MobCantMove(ImageView Bomb){
        //ham nay de khong cho Mob di chuyen qua Bomb
        Iterator it = mobList.iterator();
        pos = 0;
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            double x = imi.getLayoutX() + imi.getTranslateX();
            double y = imi.getLayoutY() + imi.getTranslateY();
            double r1,r2;
            r1 = x % 100; r2 = y % 100;
            if(r1 >= 0 && r1 <= 9) x = x - r1;
            else if(r1 >= 10 && r1 <= 19) x = x -r1 +10;
            else if(r1 >= 20 && r1 <= 29) x = x -r1 +20;
            else if(r1 >= 30 && r1 <= 39) x = x -r1 +30;
            else if(r1 >= 40 && r1 <= 49) x = x -r1 +40;
            else if(r1 >= 50 && r1 <= 59) x = x -r1 +50;
            else if(r1 >= 60 && r1 <= 69) x = x -r1 +60;
            else if(r1 >= 70 && r1 <= 79) x = x -r1 +70;
            else if(r1 >= 80 && r1 <= 89) x = x -r1 +80;
            else if(r1 >= 90 && r1 <= 99) x = x -r1 +90;
            if(r2 >= 0 && r2 <= 9) y = y - r2;
            else if(r2 >= 10 && r2 <= 19) y = y - r2 +10;
            else if(r2 >= 20 && r2 <= 29) y = y - r2 +20;
            else if(r2 >= 30 && r2 <= 39) y = y - r2 +30;
            else if(r2 >= 40 && r2 <= 49) y = y - r2 +40;
            else if(r2 >= 50 && r2 <= 59) y = y - r2 +50;
            else if(r2 >= 60 && r2 <= 69) y = y - r2 +60;
            else if(r2 >= 70 && r2 <= 79) y = y - r2 +70;
            else if(r2 >= 80 && r2 <= 89) y = y - r2 +80;
            else if(r2 >= 90 && r2 <= 99) y = y - r2 +90;
            if(((x > Bomb.getLayoutX() -50 && x <= Bomb.getLayoutX() && abs(y - Bomb.getLayoutY()) < 5) || (x >= Bomb.getLayoutX() + 50 && x < Bomb.getLayoutX() + 100 && abs(y - Bomb.getLayoutY()) < 5) || (y > Bomb.getLayoutY() - 50 && y<= Bomb.getLayoutY() && abs(x - Bomb.getLayoutX()) < 5) || (y >= Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 100 && abs(x - Bomb.getLayoutX()) < 5)) && mobLv1Move[pos] == false){
                path[pos].stop();//cho tam dung hieu ung voi path[pos] tuong ung
                mobLv1Move[pos] = true;
                imi.setTranslateX(0.0);imi.setTranslateY(0.0);
                imi.setLayoutX(x); imi.setLayoutY(y);
                if(!Utils.totalBlockRight(imi, WallList, DustList) &&(x < Bomb.getLayoutX() - 50 || x > Bomb.getLayoutX())){
                    Line line = new Line(25,25,40,25);
                    path[pos].setPath(line);
                    path[pos].setCycleCount(2);
                    path[pos].setAutoReverse(true);
                    path[pos].setDuration(Duration.seconds(0.8));
                    path[pos].play();
                    path[pos].setOnFinished((e)->{
                        mobLv1Move[pos] = false;
                    });
                }else if(!Utils.totalBlockLeft(imi, WallList, DustList) && (x < Bomb.getLayoutX() + 50 || x > Bomb.getLayoutX() + 100)){
                    Line line = new Line(25,25,10,25);
                    path[pos].setPath(line);
                    path[pos].setCycleCount(2);
                    path[pos].setAutoReverse(true);
                    path[pos].setDuration(Duration.seconds(0.8));
                    path[pos].play();
                    path[pos].setOnFinished((e)->{
                        mobLv1Move[pos] = false;
                    });
                }else if(!Utils.totalBlockUp(imi, WallList, DustList) &&(y < Bomb.getLayoutY() + 50 || y> Bomb.getLayoutY() + 100)){
                    Line line = new Line(25,25,25,10);
                    path[pos].setPath(line);
                    path[pos].setCycleCount(2);
                    path[pos].setAutoReverse(true);
                    path[pos].setDuration(Duration.seconds(0.8));
                    path[pos].play(); 
                    path[pos].setOnFinished((e)->{
                        mobLv1Move[pos] = false;
                    });
                }else if(!Utils.totalBlockDown(imi, WallList, DustList) && (y < Bomb.getLayoutY() - 50 || y > Bomb.getLayoutY())){
                    Line line = new Line(25,25,25,40);
                    path[pos].setPath(line);
                    path[pos].setCycleCount(2);
                    path[pos].setAutoReverse(true);
                    path[pos].setDuration(Duration.seconds(0.8));
                    path[pos].play();
                    path[pos].setOnFinished((e)->{
                        mobLv1Move[pos] = false;
                    });
                }   
            }
            if(pos < 7) ++pos;
        } 
    }//ham cho mob dung lai khi dung gan bomb
    public synchronized void doBomb(){
        if(Player.getLayoutX() == 360){
            //truong hop nhan vat o toa do co dinh
            //o toa do nay, nhan vat se dung yen va map se chuyen dong(truong hop nhan vat o giu
            placeBombIfMove(Bomb);//dat bomb trong truong hop map chuyen dong
        }else placeBombIfStand(Bomb);//dat bomb trong truong hop map dung yen
        Thread t = new Thread(()->{
            for(int i=0;i < 10;i++){
                Platform.runLater(()->{
                    MobCantMove(Bomb);
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                   
                }
            }
            
        });
        t.start();
        Thread t1 = new Thread(new Runnable() {
        //tao ra 1 luong moi de thuc hien nhieu cong viec cung luc
        @Override
        public void run(){
            try {
                Thread.sleep(2000);//cho luong ngu dong 2s
                //tuc la sau 2s dat bom thi bom moi no
            } catch (InterruptedException ex) {
                 System.out.println("An error has occured");
                 System.out.println(ex.getMessage());
            }
            Platform.runLater(() ->{
                breakWall();
            });
        } 
        });
        t1.start();//cho luong moi nay chay
    }//3 ham duoi thuc hien viec dat bomb
    public synchronized  void doBomb1(){
        if(Player.getLayoutX() == 360){
            //truong hop nhan vat o toa do co dinh
            //o toa do nay, nhan vat se dung yen va map se chuyen dong(truong hop nhan vat o giu
            placeBombIfMove(Bomb1);//dat bomb trong truong hop map chuyen dong
        }else placeBombIfStand(Bomb1);//dat bomb trong truong hop map dung yen
        Thread t = new Thread(()->{
            for(int i=0;i < 10;i++){
                Platform.runLater(()->{
                    MobCantMove(Bomb1);
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    
                }
            }
            
        });
        t.start();
        Thread t1 = new Thread(new Runnable() {
        //tao ra 1 luong moi de thuc hien nhieu cong viec cung luc
        @Override
        public void run(){
            try {
                Thread.sleep(2000);//cho luong ngu dong 2s
                //tuc la sau 2s dat bom thi bom moi 
                
            } catch (InterruptedException ex) {
                 System.out.println("An error has occured");
                 System.out.println(ex.getMessage());
            }
            Platform.runLater(() ->{
                breakWall1();//ham cho bom no
            });
        } 
        });
        t1.start();//cho luong moi nay chay
    }
    public synchronized void  doBomb2(){
        if(Player.getLayoutX() == 360){
            //truong hop nhan vat o toa do co dinh
            //o toa do nay, nhan vat se dung yen va map se chuyen dong(truong hop nhan vat o giu
            placeBombIfMove(Bomb2);//dat bomb trong truong hop map chuyen dong
        }else placeBombIfStand(Bomb2);//dat bomb trong truong hop map dung yen
        Thread t = new Thread(()->{
            for(int i=0;i < 10;i++){
                Platform.runLater(()->{
                    MobCantMove(Bomb2);
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    
                }
            }
            
        });
        t.start();
        Thread t1 = new Thread(new Runnable() {
        //tao ra 1 luong moi de thuc hien nhieu cong viec cung luc
        @Override
        public void run(){
            try {
                Thread.sleep(2000);//cho luong ngu dong 2s
                //tuc la sau 2s dat bom thi bom moi no
              
            } catch (InterruptedException ex) {
                 System.out.println("An error has occured");
                 System.out.println(ex.getMessage());
            }
            Platform.runLater(() ->{
                breakWall2();//ham cho bom no
            });
        } 
        });
        t1.start();//cho luong moi nay chay
    }
    @FXML
    private void getPlayerPos(KeyEvent e){
        switch(e.getCode()){
            case Z:
                for(int i =0;i<8;i++){
                    path[i].stop();
                }
                System.out.println("Stopped");
                break;
            default:
                break;
                
        } 
    }
    public synchronized void allMoveLeft(){
        //ham di chuyen toan bo object cua map sang ben trai
        int move = 5 * speedLevel;
        Iterator it = WallList.iterator();//di chuyen wall co dinh
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            imi.setLayoutX(imi.getLayoutX() - move);
        }
        Iterator itt = DustList.iterator();//di chuyen wall co the pha
        while(itt.hasNext()){
            ImageView imi2 = (ImageView) itt.next();
            imi2.setLayoutX(imi2.getLayoutX() - move);
        }
        for(int i=0;i<10;i++){
            freeList[i].setLayoutX(freeList[i].getLayoutX() -move);
        }
        Bomb.setLayoutX(Bomb.getLayoutX() -move);Bomb1.setLayoutX(Bomb1.getLayoutX() -move);Bomb2.setLayoutX(Bomb2.getLayoutX() -move);
        LaserLeft.setLayoutX(LaserLeft.getLayoutX() - move);LaserLeft1.setLayoutX(LaserLeft1.getLayoutX() - move);LaserLeft2.setLayoutX(LaserLeft2.getLayoutX() - move);
        LaserRight.setLayoutX(LaserRight.getLayoutX() - move);LaserRight1.setLayoutX(LaserRight1.getLayoutX() - move);LaserRight2.setLayoutX(LaserRight2.getLayoutX() - move);
        LaserUp.setLayoutX(LaserUp.getLayoutX() - move);LaserUp1.setLayoutX(LaserUp1.getLayoutX() - move);LaserUp2.setLayoutX(LaserUp2.getLayoutX() - move);
        LaserDown.setLayoutX(LaserDown.getLayoutX() - move);LaserDown1.setLayoutX(LaserDown1.getLayoutX() - move);LaserDown2.setLayoutX(LaserDown2.getLayoutX() - move);
        LaserCenter.setLayoutX(LaserCenter.getLayoutX() - move);LaserCenter1.setLayoutX(LaserCenter1.getLayoutX() - move);LaserCenter2.setLayoutX(LaserCenter2.getLayoutX() - move);
        Iterator ite = mobList.iterator();
        while(ite.hasNext()){
            ImageView imi1 = (ImageView) ite.next();
            imi1.setLayoutX(imi1.getLayoutX() - move);
        }
        if(level > 1){
            Iterator itn = listMob2.iterator();
            while(itn.hasNext()){
                ImageView imi3 = (ImageView) itn.next();
                imi3.setLayoutX(imi3.getLayoutX() - move);
            }
        }
        BombBuff.setLayoutX(BombBuff.getLayoutX() - move); Door.setLayoutX(Door.getLayoutX() - move);Boot.setLayoutX(Boot.getLayoutX() - move); Ice.setLayoutX(Ice.getLayoutX() - move);
        LaserLeftLeft.setLayoutX(LaserLeftLeft.getLayoutX() -move);LaserRightRight.setLayoutX(LaserRightRight.getLayoutX() -move);LaserUpUp.setLayoutX(LaserUpUp.getLayoutX() -move);LaserDownDown.setLayoutX(LaserDownDown.getLayoutX() -move);
        LaserLeftLeft1.setLayoutX(LaserLeftLeft1.getLayoutX() -move);LaserRightRight1.setLayoutX(LaserRightRight1.getLayoutX() -move);LaserUpUp1.setLayoutX(LaserUpUp1.getLayoutX() -move);LaserDownDown1.setLayoutX(LaserDownDown1.getLayoutX() -move);
        LaserLeftLeft2.setLayoutX(LaserLeftLeft2.getLayoutX() -move);LaserRightRight2.setLayoutX(LaserRightRight2.getLayoutX() -move);LaserUpUp2.setLayoutX(LaserUpUp2.getLayoutX() -move);LaserDownDown2.setLayoutX(LaserDownDown2.getLayoutX() -move);
        mapMove += move;
    }//ham cho toan map di chuyen sang trai
    public synchronized void allMoveRight(){
        //tuong tu nhu tren
        int move = 5 * speedLevel;
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            imi.setLayoutX(imi.getLayoutX() + move);
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi2 = (ImageView) itt.next();
            imi2.setLayoutX(imi2.getLayoutX() + move);
        }   
        for(int i=0;i<10;i++){
            freeList[i].setLayoutX(freeList[i].getLayoutX() +move);
        }
        Bomb.setLayoutX(Bomb.getLayoutX() +move);Bomb1.setLayoutX(Bomb1.getLayoutX() +move);Bomb2.setLayoutX(Bomb2.getLayoutX() +move);
        LaserLeft.setLayoutX(LaserLeft.getLayoutX() + move);LaserLeft1.setLayoutX(LaserLeft1.getLayoutX() + move);LaserLeft2.setLayoutX(LaserLeft2.getLayoutX() + move);
        LaserRight.setLayoutX(LaserRight.getLayoutX() + move);LaserRight1.setLayoutX(LaserRight1.getLayoutX() + move);LaserRight2.setLayoutX(LaserRight2.getLayoutX() + move);
        LaserUp.setLayoutX(LaserUp.getLayoutX() + move);LaserUp1.setLayoutX(LaserUp1.getLayoutX() + move);LaserUp2.setLayoutX(LaserUp2.getLayoutX() + move);
        LaserDown.setLayoutX(LaserDown.getLayoutX() + move);LaserDown1.setLayoutX(LaserDown1.getLayoutX() + move);LaserDown2.setLayoutX(LaserDown2.getLayoutX() + move);
        LaserCenter.setLayoutX(LaserCenter.getLayoutX() + move);LaserCenter1.setLayoutX(LaserCenter1.getLayoutX() + move);LaserCenter2.setLayoutX(LaserCenter2.getLayoutX() + move);
        Iterator ite = mobList.iterator();
        while(ite.hasNext()){
            ImageView imi1 = (ImageView) ite.next();
            imi1.setLayoutX(imi1.getLayoutX() + move);
        }
        if(level > 1){
            Iterator itn = listMob2.iterator();
            while(itn.hasNext()){
                ImageView imi3 = (ImageView) itn.next();
                imi3.setLayoutX(imi3.getLayoutX() + move);
            }
        }
        BombBuff.setLayoutX(BombBuff.getLayoutX() + move); Door.setLayoutX(Door.getLayoutX() + move);Boot.setLayoutX(Boot.getLayoutX() + move); Ice.setLayoutX(Ice.getLayoutX() + move);
        LaserLeftLeft.setLayoutX(LaserLeftLeft.getLayoutX() +move);LaserRightRight.setLayoutX(LaserRightRight.getLayoutX() +move);LaserUpUp.setLayoutX(LaserUpUp.getLayoutX() +move);LaserDownDown.setLayoutX(LaserDownDown.getLayoutX() +move);
        LaserLeftLeft1.setLayoutX(LaserLeftLeft1.getLayoutX() +move);LaserRightRight1.setLayoutX(LaserRightRight1.getLayoutX() +move);LaserUpUp1.setLayoutX(LaserUpUp1.getLayoutX() +move);LaserDownDown1.setLayoutX(LaserDownDown1.getLayoutX() +move);
        LaserLeftLeft2.setLayoutX(LaserLeftLeft2.getLayoutX() +move);LaserRightRight2.setLayoutX(LaserRightRight2.getLayoutX() +move);LaserUpUp2.setLayoutX(LaserUpUp2.getLayoutX() +move);LaserDownDown2.setLayoutX(LaserDownDown2.getLayoutX() + move);
        mapMove -= move;
    }//ham cho toan map di chuyen sang phai
    public synchronized void allReturn(){
        //ham reset lai map, Player se tro ve vi tri ban dau
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            imi.setLayoutX(imi.getLayoutX() + mapMove);
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi2 = (ImageView) itt.next();
            imi2.setLayoutX(imi2.getLayoutX() + mapMove);
        } 
        for(int i=0;i<10;i++){
            freeList[i].setLayoutX(freeList[i].getLayoutX() +mapMove);
        }
        Bomb.setLayoutX(Bomb.getLayoutX() + mapMove);Bomb1.setLayoutX(Bomb1.getLayoutX() +mapMove);Bomb2.setLayoutX(Bomb2.getLayoutX() +mapMove);
        LaserLeft.setLayoutX(LaserLeft.getLayoutX() + mapMove);LaserLeft1.setLayoutX(LaserLeft1.getLayoutX() + mapMove);LaserLeft2.setLayoutX(LaserLeft2.getLayoutX() +mapMove);
        LaserRight.setLayoutX(LaserRight.getLayoutX() + mapMove);LaserRight1.setLayoutX(LaserRight1.getLayoutX() + mapMove);LaserRight2.setLayoutX(LaserRight2.getLayoutX() + mapMove);
        LaserUp.setLayoutX(LaserUp.getLayoutX() + mapMove);LaserUp1.setLayoutX(LaserUp1.getLayoutX() + mapMove);LaserUp2.setLayoutX(LaserUp2.getLayoutX() + mapMove);
        LaserDown.setLayoutX(LaserDown.getLayoutX() + mapMove);LaserDown1.setLayoutX(LaserDown1.getLayoutX() + mapMove);LaserDown2.setLayoutX(LaserDown2.getLayoutX() + mapMove);
        LaserCenter.setLayoutX(LaserCenter.getLayoutX() + mapMove);LaserCenter1.setLayoutX(LaserCenter1.getLayoutX() + mapMove);LaserCenter2.setLayoutX(LaserCenter2.getLayoutX() + mapMove);
        Iterator ite = mobList.iterator();
        while(ite.hasNext()){
            ImageView imi1 = (ImageView) ite.next();
            imi1.setLayoutX(imi1.getLayoutX() + mapMove);
        }
        if(level > 1){
            Iterator itn = listMob2.iterator();
            while(itn.hasNext()){
                ImageView imi3 = (ImageView) itn.next();
                imi3.setLayoutX(imi3.getLayoutX() + mapMove);
            }
        }
        BombBuff.setLayoutX(BombBuff.getLayoutX() + mapMove); Door.setLayoutX(Door.getLayoutX() + mapMove);Boot.setLayoutX(Boot.getLayoutX() + mapMove); Ice.setLayoutX(Ice.getLayoutX() + mapMove);
        LaserLeftLeft.setLayoutX(LaserLeftLeft.getLayoutX() +mapMove);LaserRightRight.setLayoutX(LaserRightRight.getLayoutX() +mapMove);LaserUpUp.setLayoutX(LaserUpUp.getLayoutX() +mapMove);LaserDownDown.setLayoutX(LaserDownDown.getLayoutX() +mapMove);
        LaserLeftLeft1.setLayoutX(LaserLeftLeft1.getLayoutX() +mapMove);LaserRightRight1.setLayoutX(LaserRightRight1.getLayoutX() +mapMove);LaserUpUp1.setLayoutX(LaserUpUp1.getLayoutX() +mapMove);LaserDownDown1.setLayoutX(LaserDownDown1.getLayoutX() +mapMove);
        LaserLeftLeft2.setLayoutX(LaserLeftLeft2.getLayoutX() +mapMove);LaserRightRight2.setLayoutX(LaserRightRight2.getLayoutX() +mapMove);LaserUpUp2.setLayoutX(LaserUpUp2.getLayoutX() +mapMove);LaserDownDown2.setLayoutX(LaserDownDown2.getLayoutX() +mapMove);
        mapMove = 0;
        Player.setLayoutX(50);//dat lai toa do ban dau cua player
        Player.setLayoutY(50);
        realX = 50; realY = 50;
    }//ham set vi tri map tro ve ban dau
    public synchronized void breakWall(){
        //ham pha cac wall ben canh player
        MusicUtils.explode();
        LaserCenter.setLayoutX(Bomb.getLayoutX());
        LaserCenter.setLayoutY(Bomb.getLayoutY());
        //cac ham duoi ve ra laser khi bomb no
        //cac ham kiem tra xem co wall co dinh nao canh bomb hay khong
        //neu co thi khong the cho laser pha wall do duoc
        if(Utils.canExplodeLeft(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
            LaserLeft.setLayoutX(Bomb.getLayoutX() - 50);
            LaserLeft.setLayoutY(Bomb.getLayoutY());
        }
        if(Utils.canExplodeRight(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
            LaserRight.setLayoutX(Bomb.getLayoutX() + 50);
            LaserRight.setLayoutY(Bomb.getLayoutY());
        }
        if(Utils.canExplodeDown(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
            LaserDown.setLayoutY(Bomb.getLayoutY() + 50);
            LaserDown.setLayoutX(Bomb.getLayoutX());
        }
        if(Utils.canExplodeUp(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
            LaserUp.setLayoutY(Bomb.getLayoutY() - 50);
            LaserUp.setLayoutX(Bomb.getLayoutX());
        }
        if(bombLevel >= 2){
            if(Utils.canExplodeLeft(Bomb.getLayoutX() - 50,Bomb.getLayoutY(), WallList) && Utils.canExplodeLeft(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
                LaserLeftLeft.setLayoutX(Bomb.getLayoutX() - 100);
                LaserLeftLeft.setLayoutY(Bomb.getLayoutY());
            }
            if(Utils.canExplodeRight(Bomb.getLayoutX() + 50,Bomb.getLayoutY(), WallList) && Utils.canExplodeRight(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
                LaserRightRight.setLayoutX(Bomb.getLayoutX() + 100);
                LaserRightRight.setLayoutY(Bomb.getLayoutY());
            }
            if(Utils.canExplodeUp(Bomb.getLayoutX(),Bomb.getLayoutY() - 50, WallList) && Utils.canExplodeUp(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
                LaserUpUp.setLayoutX(Bomb.getLayoutX());
                LaserUpUp.setLayoutY(Bomb.getLayoutY() - 100);
            }
            if(Utils.canExplodeDown(Bomb.getLayoutX(),Bomb.getLayoutY() + 50, WallList) && Utils.canExplodeDown(Bomb.getLayoutX(), Bomb.getLayoutY(), WallList)){
                LaserDownDown.setLayoutX(Bomb.getLayoutX());
                LaserDownDown.setLayoutY(Bomb.getLayoutY() + 100);
            }
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi = (ImageView) itt.next();
            if(Utils.canBreakLv1(Bomb,imi)){
                //kiem tra xem wall nao co the pha
                imi.setLayoutX(-50);//lam cho wall bi pha  bien mat
                imi.setLayoutY(-50);//set toa do am
            }
            if(Utils.canBreakLv2(Bomb,imi) && bombLevel >= 2){
                //kiem tra xem wall nao co the pha
                imi.setLayoutX(-50);//lam cho wall bi pha  bien mat
                imi.setLayoutY(-50);//set toa do am
            } 
        }
        if(bombLevel == 1) KillMobLv1(Bomb);
        else if(bombLevel >= 2) KillMobLv2(Bomb);
        Thread t1  = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    Thread.sleep(500);
                    
                } catch (InterruptedException ex) {
                    System.out.println("An error has occured");
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    isPlaced = false;
                    if(bombLevel == 1) removeMobLv1(Bomb);
                    else if(bombLevel >= 2) removeMobLv2(Bomb);
                    LaserLeft.setLayoutX(-50); LaserLeft.setLayoutY(-50);
                    LaserRight.setLayoutX(-50); LaserRight.setLayoutY(-50);
                    LaserUp.setLayoutX(-50); LaserUp.setLayoutY(-50);
                    LaserDown.setLayoutX(-50); LaserDown.setLayoutY(-50);
                    LaserCenter.setLayoutX(-50); LaserCenter.setLayoutY(-50);
                    Bomb.setLayoutX(-50);Bomb.setLayoutY(-50);
                    LaserLeftLeft.setLayoutX(-50); LaserLeftLeft.setLayoutY(-50);
                    LaserRightRight.setLayoutX(-50); LaserRightRight.setLayoutY(-50);
                    LaserUpUp.setLayoutX(-50); LaserUpUp.setLayoutY(-50);
                    LaserDownDown.setLayoutX(-50); LaserDownDown.setLayoutY(-50);   
                });
            }
        });
        t1.start();
    }//cac ham pha tuong (do so bomb toi da duoc dat la 3 nen phai co 3 ham)
    public synchronized void breakWall1(){
        //ham pha cac wall ben canh player
        MusicUtils.explode();
        LaserCenter1.setLayoutX(Bomb1.getLayoutX());
        LaserCenter1.setLayoutY(Bomb1.getLayoutY());
        //cac ham duoi ve ra laser khi bomb no
        //cac ham kiem tra xem co wall co dinh nao canh bomb hay khong
        //neu co thi khong the cho laser pha wall do duoc
        if(Utils.canExplodeLeft(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
            LaserLeft1.setLayoutX(Bomb1.getLayoutX() - 50);
            LaserLeft1.setLayoutY(Bomb1.getLayoutY());
        }
        if(Utils.canExplodeRight(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
            LaserRight1.setLayoutX(Bomb1.getLayoutX() + 50);
            LaserRight1.setLayoutY(Bomb1.getLayoutY());
        }
        if(Utils.canExplodeDown(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
            LaserDown1.setLayoutY(Bomb1.getLayoutY() + 50);
            LaserDown1.setLayoutX(Bomb1.getLayoutX());
        }
       if(Utils.canExplodeUp(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
            LaserUp1.setLayoutY(Bomb1.getLayoutY() - 50);
            LaserUp1.setLayoutX(Bomb1.getLayoutX());
        }
       if(bombLevel >= 2){
            if(Utils.canExplodeLeft(Bomb1.getLayoutX() - 50,Bomb1.getLayoutY(), WallList) && Utils.canExplodeLeft(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
                LaserLeftLeft1.setLayoutX(Bomb1.getLayoutX() - 100);
                LaserLeftLeft1.setLayoutY(Bomb1.getLayoutY());
            }
            if(Utils.canExplodeRight(Bomb1.getLayoutX() + 50,Bomb1.getLayoutY(), WallList) &&Utils. canExplodeRight(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
                LaserRightRight1.setLayoutX(Bomb1.getLayoutX() + 100);
                LaserRightRight1.setLayoutY(Bomb1.getLayoutY());
            }
            if(Utils.canExplodeUp(Bomb1.getLayoutX(),Bomb.getLayoutY() - 50, WallList) && Utils.canExplodeUp(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
                LaserUpUp1.setLayoutX(Bomb1.getLayoutX());
                LaserUpUp1.setLayoutY(Bomb1.getLayoutY() - 100);
            }
            if(Utils.canExplodeDown(Bomb1.getLayoutX(),Bomb1.getLayoutY() + 50, WallList) && Utils.canExplodeDown(Bomb1.getLayoutX(), Bomb1.getLayoutY(), WallList)){
                LaserDownDown1.setLayoutX(Bomb1.getLayoutX());
                LaserDownDown1.setLayoutY(Bomb1.getLayoutY() + 100);
            }
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi = (ImageView) itt.next();
            if(Utils.canBreakLv1(Bomb1,imi)){
                imi.setLayoutX(-50);
                imi.setLayoutY(-50);
            }
            if(Utils.canBreakLv2(Bomb1,imi) && bombLevel >= 2){
                //kiem tra xem wall nao co the pha
                imi.setLayoutX(-50);//lam cho wall bi pha  bien mat
                imi.setLayoutY(-50);//set toa do am
            } 
        }
        if(bombLevel == 1) KillMobLv1(Bomb1);
        else if(bombLevel >= 2) KillMobLv2(Bomb1);
        Thread t1  = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    Thread.sleep(500);
                    
                } catch (InterruptedException ex) {
                    System.out.println("An error has occured");
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    isPlaced1 = false;
                    if(bombLevel == 1) removeMobLv1(Bomb1);
                    else if(bombLevel >= 2) removeMobLv2(Bomb1);
                    LaserLeft1.setLayoutX(-50); LaserLeft1.setLayoutY(-50);
                    LaserRight1.setLayoutX(-50); LaserRight1.setLayoutY(-50);
                    LaserUp1.setLayoutX(-50); LaserUp1.setLayoutY(-50);
                    LaserDown1.setLayoutX(-50); LaserDown1.setLayoutY(-50);
                    LaserCenter1.setLayoutX(-50); LaserCenter1.setLayoutY(-50);
                    Bomb1.setLayoutX(-50);Bomb1.setLayoutY(-50);
                    LaserLeftLeft1.setLayoutX(-50); LaserLeftLeft1.setLayoutY(-50);
                    LaserRightRight1.setLayoutX(-50); LaserRightRight1.setLayoutY(-50);
                    LaserUpUp1.setLayoutX(-50); LaserUpUp1.setLayoutY(-50);
                    LaserDownDown1.setLayoutX(-50); LaserDownDown1.setLayoutY(-50);
                });
            }
        });
        t1.start();
    }
    public synchronized void breakWall2(){
        //ham pha cac wall ben canh player
        MusicUtils.explode();
        LaserCenter2.setLayoutX(Bomb2.getLayoutX());
        LaserCenter2.setLayoutY(Bomb2.getLayoutY());
        //cac ham duoi ve ra laser khi bomb no
        //cac ham kiem tra xem co wall co dinh nao canh bomb hay khong
        //neu co thi khong the cho laser pha wall do duoc
        if(Utils.canExplodeLeft(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
            LaserLeft2.setLayoutX(Bomb2.getLayoutX() - 50);
            LaserLeft2.setLayoutY(Bomb2.getLayoutY());
        }
        if(Utils.canExplodeRight(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
            LaserRight2.setLayoutX(Bomb2.getLayoutX() + 50);
            LaserRight2.setLayoutY(Bomb2.getLayoutY());
        }
        if(Utils.canExplodeDown(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
            LaserDown2.setLayoutY(Bomb2.getLayoutY() + 50);
            LaserDown2.setLayoutX(Bomb2.getLayoutX());
        }
       if(Utils.canExplodeUp(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
            LaserUp2.setLayoutY(Bomb2.getLayoutY() - 50);
            LaserUp2.setLayoutX(Bomb2.getLayoutX());
        }
       if(bombLevel >= 2){
            if(Utils.canExplodeLeft(Bomb2.getLayoutX() - 50,Bomb2.getLayoutY(), WallList) && Utils.canExplodeLeft(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
                LaserLeftLeft2.setLayoutX(Bomb2.getLayoutX() - 100);
                LaserLeftLeft2.setLayoutY(Bomb2.getLayoutY());
            }
            if(Utils.canExplodeRight(Bomb2.getLayoutX() + 50,Bomb2.getLayoutY(), WallList) && Utils.canExplodeRight(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
                LaserRightRight2.setLayoutX(Bomb2.getLayoutX() + 100);
                LaserRightRight2.setLayoutY(Bomb2.getLayoutY());
            }
            if(Utils.canExplodeUp(Bomb2.getLayoutX(),Bomb2.getLayoutY() - 50, WallList) && Utils.canExplodeUp(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
                LaserUpUp2.setLayoutX(Bomb2.getLayoutX());
                LaserUpUp2.setLayoutY(Bomb2.getLayoutY() - 100);
            }
            if(Utils.canExplodeDown(Bomb1.getLayoutX(),Bomb2.getLayoutY() + 50, WallList) && Utils.canExplodeDown(Bomb2.getLayoutX(), Bomb2.getLayoutY(), WallList)){
                LaserDownDown2.setLayoutX(Bomb2.getLayoutX());
                LaserDownDown2.setLayoutY(Bomb2.getLayoutY() + 100);
            }
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi = (ImageView) itt.next();
            if(Utils.canBreakLv1(Bomb2,imi)){
                imi.setLayoutX(-50);
                imi.setLayoutY(-50);
            }   
            if(Utils.canBreakLv2(Bomb2,imi) && bombLevel >= 2){
                //kiem tra xem wall nao co the pha
                imi.setLayoutX(-50);//lam cho wall bi pha  bien mat
                imi.setLayoutY(-50);//set toa do am
            }
        }
        if(bombLevel == 1) KillMobLv1(Bomb2);
        else if(bombLevel >= 2) KillMobLv2(Bomb2);
        Thread t1  = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    Thread.sleep(500);
                   
                } catch (InterruptedException ex) {
                    System.out.println("An error has occured");
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    isPlaced2 = false;
                    if(bombLevel == 1) removeMobLv1(Bomb2);
                    else if(bombLevel >= 2) removeMobLv2(Bomb2);
                    LaserLeft2.setLayoutX(-50); LaserLeft2.setLayoutY(-50);
                    LaserRight2.setLayoutX(-50); LaserRight2.setLayoutY(-50);
                    LaserUp2.setLayoutX(-50); LaserUp2.setLayoutY(-50);
                    LaserDown2.setLayoutX(-50); LaserDown2.setLayoutY(-50);
                    LaserCenter2.setLayoutX(-50); LaserCenter2.setLayoutY(-50);
                    Bomb2.setLayoutX(-50);Bomb2.setLayoutY(-50);
                    LaserLeftLeft2.setLayoutX(-50); LaserLeftLeft2.setLayoutY(-50);
                    LaserRightRight2.setLayoutX(-50); LaserRightRight2.setLayoutY(-50);
                    LaserUpUp2.setLayoutX(-50); LaserUpUp2.setLayoutY(-50);
                    LaserDownDown2.setLayoutX(-50); LaserDownDown2.setLayoutY(-50);
                });
            }
        });
        t1.start();
    }
    public synchronized void KillMobLv1(ImageView Bomb){
        //ham nay tieu diet mob khi mob den gan bomb(ung voi bombLevel = 1)
        Iterator it = mobList.iterator();
        int i = 0;
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();//duyet tung ptu trong list mobs de biet duoc mob nao dung gan bomb
            double x = imi.getLayoutX() + imi.getTranslateX();
            double y = imi.getLayoutY() + imi.getTranslateY();
            //toa do x,y la toa do cua mob khi dang di chuyen
            if(((x >= Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 100) && (y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y > Bomb.getLayoutY() -100 && y <= Bomb.getLayoutY() - 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y >= Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 100)) || (abs(y - Bomb.getLayoutY()) < 5 && (x > Bomb.getLayoutX() - 100 && x <= Bomb.getLayoutX() - 50))){
                path[i].pause();//cho tam dung hieu ung voi path[pos] tuong ung
                mobLv1[i] = 0;
            }
            ++i;//duyet den phan tu path[pos] ke tiep
        }
        if(level > 1){//tieu diet mobLv2
            Iterator itt = listMob2.iterator();
            int j = 0;
            while(itt.hasNext()){
                ImageView imi1 = (ImageView) itt.next();//duyet tung ptu trong list mobs de biet duoc mob nao dung gan bomb
                double x = imi1.getLayoutX() + imi1.getTranslateX();
                double y = imi1.getLayoutY() + imi1.getTranslateY();
                //toa do x,y la toa do cua mob khi dang di chuyen
                if(((x >= Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 100) && (y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y > Bomb.getLayoutY() -100 && y <= Bomb.getLayoutY() - 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y >= Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 100)) || (abs(y - Bomb.getLayoutY()) < 5 && (x > Bomb.getLayoutX() - 100 && x <= Bomb.getLayoutX() - 50))){
                    --mobLv2[j];
                    if(mobLv2[j] == 0 && mobFreeze[j] == false) path1[j].pause();
                    if(mobFreeze[j] == true) {
                        mobFreeze[j] = false; path1[j].play();
                    }
                }
                ++j;//duyet den phan tu path[pos] ke tiep
            }
        }
        if(((Player.getLayoutX() >= Bomb.getLayoutX() - 50 && Player.getLayoutX() < Bomb.getLayoutX() + 100) && (Player.getLayoutY() >= Bomb.getLayoutY() - 50 && Player.getLayoutY() < Bomb.getLayoutY() + 50)) || ((Player.getLayoutX() >= Bomb.getLayoutX() && Player.getLayoutX() < Bomb.getLayoutX() + 15) && (Player.getLayoutY() > Bomb.getLayoutY() -100 && Player.getLayoutY() <= Bomb.getLayoutY() - 50)) || ((Player.getLayoutX() >= Bomb.getLayoutX() && Player.getLayoutX() < Bomb.getLayoutX() + 15) && (Player.getLayoutY() >= Bomb.getLayoutY() + 50 && Player.getLayoutY() < Bomb.getLayoutY() + 100)) || ((Player.getLayoutY() >= Bomb.getLayoutY() && Player.getLayoutY() < Bomb.getLayoutY() + 15) && (Player.getLayoutX() > Bomb.getLayoutX() - 100 && Player.getLayoutX() <= Bomb.getLayoutX() - 50))){
            if(processing == false){
                //chi xu ly ham duoi khi processing = false, nham tranh truong hop khi Player mat 2 mang khi vua bi bomb no vua bi mob giet (lap hieu ung)
                processing = true;  
                PlayerDisappear();   
            }
                //xu ly truong hop player bi bom no chet
        }
        for(int k=0;k<10;k++){
            if(((freeList[k].getLayoutX() >= Bomb.getLayoutX() - 50 && freeList[k].getLayoutX() < Bomb.getLayoutX() + 100) && (freeList[k].getLayoutY() >= Bomb.getLayoutY() - 50 && freeList[k].getLayoutY() < Bomb.getLayoutY() + 50)) || ((freeList[k].getLayoutX() >= Bomb.getLayoutX() && freeList[k].getLayoutX() < Bomb.getLayoutX() + 15) && (freeList[k].getLayoutY() > Bomb.getLayoutY() -100 && freeList[k].getLayoutY() <= Bomb.getLayoutY() - 50)) || ((freeList[k].getLayoutX() >= Bomb.getLayoutX() && freeList[k].getLayoutX() < Bomb.getLayoutX() + 15) && (freeList[k].getLayoutY() >= Bomb.getLayoutY() + 50 && freeList[k].getLayoutY() < Bomb.getLayoutY() + 100)) || ((freeList[k].getLayoutY() >= Bomb.getLayoutY() && freeList[k].getLayoutY() < Bomb.getLayoutY() + 15) && (freeList[k].getLayoutX() > Bomb.getLayoutX() - 100 && freeList[k].getLayoutX() <= Bomb.getLayoutX() - 50))){
                freeList[k].setVisible(false);
            }
        }
    }//ham giet mob va dung hieu ung 
    public synchronized void KillMobLv2(ImageView Bomb){
        Iterator it = mobList.iterator();
        int i = 0;
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();//duyet tung ptu trong list mobs de biet duoc mob nao dung gan bomb
            double x = imi.getLayoutX() + imi.getTranslateX();
            double y = imi.getLayoutY() + imi.getTranslateY();
            //toa do x,y la toa do cua mob khi dang di chuyen
            if( (((x > Bomb.getLayoutX() - 100 && x < Bomb.getLayoutX() && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50) || (x > Bomb.getLayoutX() -150 && x <= Bomb.getLayoutX() - 100 && y >= Bomb.getLayoutY() && y < Bomb.getLayoutY() + 5)) && Utils.canExplodeLeft(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList) ) ||  (x > Bomb.getLayoutX() + 50 && x < Bomb.getLayoutX() + 150 && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50 && Utils.canExplodeRight(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList ) ) || (( (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() - 100 && y < Bomb.getLayoutY()) || (x >= Bomb.getLayoutX() && x < Bomb.getLayoutX() + 5 && y > Bomb.getLayoutY() - 150 && y <= Bomb.getLayoutY() - 100 )) && Utils.canExplodeUp(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 150 && Utils.canExplodeDown(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x >= Bomb.getLayoutX() && x <= Bomb.getLayoutX() + 50 && y >= Bomb.getLayoutY() && y <= Bomb.getLayoutY() + 50)){
                path[i].pause();//cho tam dung hieu ung voi path[pos] tuong ung
                mobLv1[i] =0;
            }
            ++i;//duyet den phan tu path[pos] ke tiep
        }
        if(level > 1){
            Iterator itt = listMob2.iterator();
            int j = 0;
            while(it.hasNext()){
                ImageView imi1 = (ImageView) itt.next();//duyet tung ptu trong list mobs de biet duoc mob nao dung gan bomb
                double x = imi1.getLayoutX() + imi1.getTranslateX();
                double y = imi1.getLayoutY() + imi1.getTranslateY();
                //toa do x,y la toa do cua mob khi dang di chuyen
                if( (((x > Bomb.getLayoutX() - 100 && x < Bomb.getLayoutX() && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50) || (x > Bomb.getLayoutX() -150 && x <= Bomb.getLayoutX() - 100 && y >= Bomb.getLayoutY() && y < Bomb.getLayoutY() + 5)) && Utils.canExplodeLeft(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList) ) ||  (x > Bomb.getLayoutX() + 50 && x < Bomb.getLayoutX() + 150 && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50 && Utils.canExplodeRight(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList ) ) || (( (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() - 100 && y < Bomb.getLayoutY()) || (x >= Bomb.getLayoutX() && x < Bomb.getLayoutX() + 5 && y > Bomb.getLayoutY() - 150 && y <= Bomb.getLayoutY() - 100 )) && Utils.canExplodeUp(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 150 && Utils.canExplodeDown(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x >= Bomb.getLayoutX() && x <= Bomb.getLayoutX() + 50 && y >= Bomb.getLayoutY() && y <= Bomb.getLayoutY() + 50)){
                    --mobLv2[j];
                    if(mobLv2[j] <= 0 && mobFreeze[j] == false){
                        path1[j].pause();
                    }
                    if(mobFreeze[j] == true) {
                        mobFreeze[j] = false; path1[j].play();
                    }
                }
                ++j;//duyet den phan tu path[pos] ke tiep
            }
        }
        if( (((Player.getLayoutX() > Bomb.getLayoutX() - 100 && Player.getLayoutX() < Bomb.getLayoutX() && Player.getLayoutY() > Bomb.getLayoutY() - 50 && Player.getLayoutY() < Bomb.getLayoutY() + 50) || (Player.getLayoutX() > Bomb.getLayoutX() -150 && Player.getLayoutX() <= Bomb.getLayoutX() - 100 && Player.getLayoutY() >= Bomb.getLayoutY() && Player.getLayoutY() < Bomb.getLayoutY() + 15)) && Utils.canExplodeLeft(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList) ) ||  (Player.getLayoutX() > Bomb.getLayoutX() + 50 && Player.getLayoutX() < Bomb.getLayoutX() + 150 && Player.getLayoutY() > Bomb.getLayoutY() - 50 && Player.getLayoutY() < Bomb.getLayoutY() + 50 && Utils.canExplodeRight(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList ) ) || (( (Player.getLayoutX() > Bomb.getLayoutX() - 50 && Player.getLayoutX() < Bomb.getLayoutX() + 50 && Player.getLayoutY() > Bomb.getLayoutY() - 100 && Player.getLayoutY() < Bomb.getLayoutY()) || (Player.getLayoutX() >= Bomb.getLayoutX() && Player.getLayoutX() < Bomb.getLayoutX() + 15 && Player.getLayoutY() > Bomb.getLayoutY() - 150 && Player.getLayoutY() <= Bomb.getLayoutY() - 100 )) && Utils.canExplodeUp(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (Player.getLayoutX() > Bomb.getLayoutX() - 50 && Player.getLayoutX() < Bomb.getLayoutX() + 50 && Player.getLayoutY() > Bomb.getLayoutY() + 50 && Player.getLayoutY() < Bomb.getLayoutY() + 150 && Utils.canExplodeDown(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (Player.getLayoutX() >= Bomb.getLayoutX() && Player.getLayoutX() <= Bomb.getLayoutX() + 50 && Player.getLayoutY() >= Bomb.getLayoutY() && Player.getLayoutY() <= Bomb.getLayoutY() + 50)){
            if(processing == false){
                //chi xu ly ham duoi khi processing = false, nham tranh truong hop khi Player mat 2 mang khi vua bi bomb no vua bi mob giet (lap hieu ung)
                processing = true;  
                PlayerDisappear();   
            }
                //xu ly truong hop player bi bom no chet
        }
        for(int k=0;k<10;k++){
            if((((freeList[k].getLayoutX() > Bomb.getLayoutX() - 100 && freeList[k].getLayoutX() < Bomb.getLayoutX() && freeList[k].getLayoutY() > Bomb.getLayoutY() - 50 && freeList[k].getLayoutY() < Bomb.getLayoutY() + 50) || (freeList[k].getLayoutX() > Bomb.getLayoutX() -150 && freeList[k].getLayoutX() <= Bomb.getLayoutX() - 100 && freeList[k].getLayoutY() >= Bomb.getLayoutY() && freeList[k].getLayoutY() < Bomb.getLayoutY() + 15)) && Utils.canExplodeLeft(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList) ) ||  (freeList[k].getLayoutX() > Bomb.getLayoutX() + 50 && freeList[k].getLayoutX() < Bomb.getLayoutX() + 150 && freeList[k].getLayoutY() > Bomb.getLayoutY() - 50 && freeList[k].getLayoutY() < Bomb.getLayoutY() + 50 && Utils.canExplodeRight(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList ) ) || (( (freeList[k].getLayoutX() > Bomb.getLayoutX() - 50 && freeList[k].getLayoutX() < Bomb.getLayoutX() + 50 && freeList[k].getLayoutY() > Bomb.getLayoutY() - 100 && freeList[k].getLayoutY() < Bomb.getLayoutY()) || (freeList[k].getLayoutX() >= Bomb.getLayoutX() && freeList[k].getLayoutX() < Bomb.getLayoutX() + 15 && freeList[k].getLayoutY() > Bomb.getLayoutY() - 150 && freeList[k].getLayoutY() <= Bomb.getLayoutY() - 100 )) && Utils.canExplodeUp(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (freeList[k].getLayoutX() > Bomb.getLayoutX() - 50 && freeList[k].getLayoutX() < Bomb.getLayoutX() + 50 && freeList[k].getLayoutY() > Bomb.getLayoutY() + 50 && freeList[k].getLayoutY() < Bomb.getLayoutY() + 150 && Utils.canExplodeDown(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (freeList[k].getLayoutX() >= Bomb.getLayoutX() && freeList[k].getLayoutX() <= Bomb.getLayoutX() + 50 && freeList[k].getLayoutY() >= Bomb.getLayoutY() && freeList[k].getLayoutY() <= Bomb.getLayoutY() + 50)){
                freeList[k].setVisible(false);
            }
        }
    }
    public synchronized void removeMobLv1(ImageView Bomb){
        //ham xoa mob ra khoi map(truong hop bombLevel = 1)
        Iterator it = mobList.iterator();
        int i = 0;
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();//duyet tung ptu trong list mobs de biet duoc mob nao dung gan bomb
            double x = imi.getLayoutX() + imi.getTranslateX();
            double y = imi.getLayoutY() + imi.getTranslateY();
            //toa do x,y la toa do cua mob khi dang di chuyen
            if(((x >= Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 100) && (y >= Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y > Bomb.getLayoutY() -100 && y <= Bomb.getLayoutY() - 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y >= Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 100)) || (abs(y - Bomb.getLayoutY()) < 5 && (x > Bomb.getLayoutX() - 100 && x <= Bomb.getLayoutX() - 50))){
                path[i].stop();
                
                score += 100;//giet duoc mob se duoc cong 10 diem
                mobLeft -= 1;//giam so mob con lai di 1
                String s = String.valueOf(score);
                Point.setText(s);
                imi.setLayoutX(-200);imi.setTranslateX(0.0);
                imi.setLayoutY(-200);imi.setTranslateY(0.0);
                //o day LayoutX va LayoutY la toa do ban dau cua mob (truoc khi di chuyen)
                //TranslateX va TranslateY la toa do cua mob khi dang di chuyen voi goc toa do la vi tri ban dau cua mob(khong so voi layout)
               
            }
            ++i;//duyet den phan tu path[pos] ke tiep
        }
        if(level > 1){
            Iterator itt = listMob2.iterator();
            int j = 0;
            while(itt.hasNext()){
                ImageView imi1 = (ImageView) itt.next();
                double x = imi1.getLayoutX() + imi1.getTranslateX();
                double y = imi1.getLayoutY() + imi1.getTranslateY();
                if(((x >= Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 100) && (y >= Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y > Bomb.getLayoutY() -100 && y <= Bomb.getLayoutY() - 50)) || (abs(x - Bomb.getLayoutX()) < 5 && (y >= Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 100)) || (abs(y - Bomb.getLayoutY()) < 5 && (x > Bomb.getLayoutX() - 100 && x <= Bomb.getLayoutX() - 50))){
                    if(mobLv2[j] == 0){
                        //voi mob lv2 phai cho bomb no 2 lan moi giet dc
                        path1[j].stop();
                        score += 200;//giet duoc mob se duoc cong 200 diem
                        mobLeft -= 1;
                        String s = String.valueOf(score);
                        Point.setText(s);
                        imi1.setLayoutX(-200);imi1.setTranslateX(0.0);
                        imi1.setLayoutY(-200);imi1.setTranslateY(0.0);
                    }
                }
                ++j;
            }
            
        }
    }//ham xoa mob ra khoi ban do ung voi moi 
    public synchronized void removeMobLv2(ImageView Bomb){
        //ham xoa mob ra khoi map
        Iterator it = mobList.iterator();
        int i = 0;
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();//duyet tung ptu trong list mobs de biet duoc mob nao dung gan bomb
            double x = imi.getLayoutX() + imi.getTranslateX();
            double y = imi.getLayoutY() + imi.getTranslateY();
            //toa do x,y la toa do cua mob khi dang di chuyen
            if( (((x > Bomb.getLayoutX() - 100 && x < Bomb.getLayoutX() && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50) || (x > Bomb.getLayoutX() -150 && x <= Bomb.getLayoutX() - 100 && y > Bomb.getLayoutY() && y < Bomb.getLayoutY() + 5)) && Utils.canExplodeLeft(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList) ) ||  (x > Bomb.getLayoutX() + 50 && x < Bomb.getLayoutX() + 150 && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50 && Utils.canExplodeRight(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList ) ) || (( (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() - 100 && y < Bomb.getLayoutY()) || (x > Bomb.getLayoutX() && x < Bomb.getLayoutX() - 5 && y > Bomb.getLayoutY() - 150 && y <= Bomb.getLayoutY() - 100 )) && Utils.canExplodeUp(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 150 && Utils.canExplodeDown(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x >= Bomb.getLayoutX() && x <= Bomb.getLayoutX() + 50 && y >= Bomb.getLayoutY() && y <= Bomb.getLayoutY() + 50)){
                path[i].stop();
                score += 10;//giet duoc mob se duoc cong 10 diem
                mobLeft -= 1;//giam so mob con lai di 1
                String s = String.valueOf(score);
                Point.setText(s);
                imi.setLayoutX(-200);imi.setTranslateX(0.0);
                imi.setLayoutY(-200);imi.setTranslateY(0.0);
                //o day LayoutX va LayoutY la toa do ban dau cua mob (truoc khi di chuyen)
                //TranslateX va TranslateY la toa do cua mob khi dang di chuyen voi goc toa do la vi tri ban dau cua mob(khong so voi layout)
            }
            ++i;//duyet den phan tu path[pos] ke tiep
        }
        if(level > 1){
            Iterator itt = listMob2.iterator();
            int j = 0;
            while(itt.hasNext()){
                ImageView imi1 = (ImageView) itt.next();
                double x = imi1.getLayoutX() + imi1.getTranslateX();
                double y = imi1.getLayoutY() + imi1.getTranslateY();
                if( (((x > Bomb.getLayoutX() - 100 && x < Bomb.getLayoutX() && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50) || (x > Bomb.getLayoutX() -150 && x <= Bomb.getLayoutX() - 100 && y > Bomb.getLayoutY() && y < Bomb.getLayoutY() + 5)) && Utils.canExplodeLeft(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList) ) ||  (x > Bomb.getLayoutX() + 50 && x < Bomb.getLayoutX() + 150 && y > Bomb.getLayoutY() - 50 && y < Bomb.getLayoutY() + 50 && Utils.canExplodeRight(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList ) ) || (( (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() - 100 && y < Bomb.getLayoutY()) || (x > Bomb.getLayoutX() && x < Bomb.getLayoutX() - 5 && y > Bomb.getLayoutY() - 150 && y <= Bomb.getLayoutY() - 100 )) && Utils.canExplodeUp(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x > Bomb.getLayoutX() - 50 && x < Bomb.getLayoutX() + 50 && y > Bomb.getLayoutY() + 50 && y < Bomb.getLayoutY() + 150 && Utils.canExplodeDown(Bomb.getLayoutX(),Bomb.getLayoutY(), WallList)) || (x >= Bomb.getLayoutX() && x <= Bomb.getLayoutX() + 50 && y >= Bomb.getLayoutY() && y <= Bomb.getLayoutY() + 50)){
                    if(mobLv2[j] == 0){
                        path1[j].stop();
                        score += 20;//giet duoc mob se duoc cong 10 diem
                        mobLeft -= 1;//giam so mob con lai di 1
                        String s = String.valueOf(score);
                        Point.setText(s);
                        imi1.setLayoutX(-200);imi1.setTranslateX(0.0);
                        imi1.setLayoutY(-200);imi1.setTranslateY(0.0);
                    }
                }
                ++j;
            }
        }
    }
    public synchronized void placeBombIfStand(ImageView Bomb){
        //ham dat bomb trong truong hop map dung yen
        double bombX,bombY,playerX,playerY;
        playerX = Player.getLayoutX(); playerY = Player.getLayoutY();
        int n = (int) playerX % 100;
        switch(n){
            case 75 :case 80: case 85:case 90: case 95: 
                bombX = playerX + (100 - n);
                break;
            case 0: case 5: case 10: case 15: case 20: case 25:
                bombX = playerX - n;
                break;
            case 30: case 35: case 40: case 45: case 50: case 55: case 60: case 65: case 70:
                bombX = playerX + (50 - n);
                break;
            default:
                bombX = -50;
                break;        
        }
        int m = (int) playerY % 100;
        switch(m){
            case 75 :case 80: case 85:case 90: case 95: 
                bombY = playerY + (100 - m);
                break;
            case 0: case 5: case 10: case 15: case 20: case 25:
                bombY = playerY - m;
                break;
            case 30: case 35: case 40: case 45: case 50: case 55: case 60: case 65: case 70:
                bombY = playerY + (50 - m);
                break;
            default:
                bombY = -50;
                break;
        }
        Bomb.setLayoutX(bombX); Bomb.setLayoutY(bombY);
    }//ham dat bomb trong truong hop map dung yen
    public synchronized void placeBombIfMove(ImageView Bomb){
        //ham dat bomb trong truong hop toan map chuyen dong
        double bombX,bombY,playerX,playerY;
        playerX = Player.getLayoutX(); playerY = Player.getLayoutY();
        //lay toa do cua Player
        int n = (int) realX % 50;
        switch(n){
            case 10: 
                bombX = 350;
                break;
            case 15:
                bombX = 345;
                break;
            case 20: 
                bombX = 340;
                break;
            case 25:
                bombX = 335;
                break;
            case 30: 
                bombX = 380;
                break;
            case 35:
                bombX = 375;
                break;
            case 40: 
                bombX = 370;
                break;
            case 45:
                bombX = 365;
                break;
            case 0: 
                bombX = 360;
                break;
            case 5:
                bombX = 355;
                break;
            default:
                bombX = -50;
                break;
        }
        int m = (int) playerY % 100;
        switch(m){
            case 75: case 80: case 85: case 90: case 95: 
                bombY = playerY + (100 - m);
                break;
            case 0: case 5: case 10: case 15: case 20: case 25:
                bombY = playerY - m;
                break;
            case 30: case 35: case 40: case 45: case 50: case 55: case 60: case 65: case 70:
                bombY = playerY + (50 - m);
                break;
            default:
                bombY = -50;
                break;
        }
        Bomb.setLayoutX(bombX); Bomb.setLayoutY(bombY);
        //dat toa do cho bomb tuong ung
    }//ham dat bomb trong truong hop map di chuyen
    public synchronized void setMobPath(PathTransition path, ImageView mob){
        //ham set toa do trong mob va cho mob di chuyen theo 1 con duong
        ArrayList<Double> list = new ArrayList<>();
        list.add(25.0); list.add(25.0);//them toa do dau tien cua con duong
        getPath(list,mob.getLayoutX(),mob.getLayoutY(),"",25,25,1);//them toa do cua duong di vao ArrayList list
        int length = list.size();
        int nodeLength = length /2 -1;//node length la do dai cua path vua moi ve tinh theo so o vuong
        Double d[] = new Double[length];
        Iterator it = list.iterator();
        int i = 0;
        while(it.hasNext()){
            Double d1 = (Double) it.next();
            d[i] = d1;
            ++i;//cho cac phan tu trong arrayList vao mang Double 1 chieu   
        }
        path.setNode(mob);//cai dat hieu ung nay len mob
        path.setCycleCount(2);//cho lap lai 2 lan
        path.setAutoReverse(true);//cho hieu ung tu dong dao chieu
        path.setDuration(Duration.seconds(1.0 * nodeLength));//cai dat thoi gian cua hieu ung di chuyen
        //o day thoi gian duoc tinh = so Node cua con duong * 0.3s ->0.3s se di qua 1 node
        Polyline line = new Polyline();//tao ra 1 doi tuong line
        line.getPoints().addAll(d);//add toa do cua mang Double vao line nay
        path.setPath(line);//cai dat duong di cho hieu ung
        path.play();//bat dau hieu ung
        path.setOnFinished((e) -> {
            //doan code duoi day duoc thuc thi khi hieu ung di chuyen ket thuc
            //lap lai code ben tren, chi khac ra mang ArrayList se khac di do ham getPath() dung random de add toa do
            ArrayList<Double> list1 = new ArrayList<>();
            list1.add(25.0); list1.add(25.0);
            getPath(list1,mob.getLayoutX(),mob.getLayoutY(),"",25,25,1);
            int length1 = list1.size();
            int nodeLength1 = length1 /2 -1;
            Double d1[] = new Double[length1];
            Iterator itt = list1.iterator();
            int j = 0;
            while(itt.hasNext()){
                Double d2 = (Double) itt.next();
                d1[j] = d2;
                ++j;
            }
            path.setNode(mob);
            path.setCycleCount(2);
            path.setAutoReverse(true);
            path.setDuration(Duration.seconds(1.0 * nodeLength1));
            Polyline line1 = new Polyline();//tao ra 1 doi tuong line
            line1.getPoints().addAll(d1);
            path.setPath(line1);
            path.play();
        });
    }//ham set toa do trong mob va cho mob di chuyen theo 1 con duong 
    public synchronized void setAllMobAnimation(){
        //ham set hieu ung di chuyen cho mob
        Iterator it = mobList.iterator();
        int i = 0;
        while(it.hasNext()){
            //duyet arrayList mob
            ImageView imi = (ImageView) it.next();
            setMobPath(path[i],imi);//set duong di cho mob
            ++i;
        }
        if(level > 1){
            Iterator itt = listMob2.iterator();
            int j = 0;//ham set di chuyen cho moblv2
            while(itt.hasNext()){
                ImageView imi1 = (ImageView) itt.next();
                setMobPath(path1[j],imi1);
                ++j;
            }
        }
    }//ham cai dat hieu ung cho mob
    public void getPath(ArrayList<Double> list, double x, double y, String direct, double lengthX, double lengthY,int i){
        /* Ham tao ra duong cho mob di chuyen, toa do cac duong se duoc luu vao mang list (dung de quy)
         * Lan goi dau tien, tham so x ,y se la toa do cua mob. 2 gia tri lengthX va lengthY la toa do cua duong (so voi mob)
         * Toa do mac dinh ban dau la (25,25)
         * Tham so i truyen vao la so o cua con duong (con duong co do dai max la 15)
        */
        boolean bool[] = new boolean[4];
        int n;
        bool[0] = bool[1] = bool[2] = bool[3] = false;//tuong ung voi left,right,up,down
        //false la bi chan, true la ko bi chan
        if(!overlapLayout(x - 50,y) && !direct.equals("Left")) bool[0] = true;//neu o ben trai khong bi trung thi tra ve true
        if(!overlapLayout(x + 50,y) && !direct.equals("Right")) bool[1] = true;//neu o ben phai khong bi trung thi tra ve true
        if(!overlapLayout(x, y - 50) && !direct.equals("Up")) bool[2] = true;//tuong tu
        if(!overlapLayout(x ,y + 50) && !direct.equals("Down")) bool[3] = true;
        //luu y: o day tham so direct la huong truyen vao. Voi moi 1 truong hop thi phai kiem tra direct cua lan de quy truoc
        //xem co trung khong. Neu bi trung thi toa do se bi lap lai
        if((bool[0] == false && bool[1] == false && bool[2] == false && bool[3] == false) || i == 19) return;
        //truong hop ca 4 huong bi chan thi se nhay ra khoi ham -> ket thuc duong di cua mob
        //mang ArrayList<Double> list hien gio chua toa do duong di cua mob
        else{
            //neu van con duong di thi tiep tuc lenh duoi
            ++i;
            Random rd = new Random();
            n = rd.nextInt(4);
            while(bool[n] == false){
                n = rd.nextInt(4);
            }
            //gia tri n duoc chon sao cho khong bi chan (bool[n] tra ve true)
            //truong hop co nhieu duong di thi chon random huong di chuyen
            switch (n) {
                case 0:
                    //truong hop di chuyen trai
                    lengthX -= 50;
                    list.add(lengthX);
                    list.add(lengthY);//them toa do duong di moi vao list
                    getPath(list,x - 50,y,"Right",lengthX,lengthY,i);//goi de quy
                    /*lan truyen tham so nay (x - 50,y) la toa do ben trai cua mob. Lan de quy ke tiep tiep tuc kiem tra xem  toa do moi nay co bi trung voi wall khong
                    *neu khong thi them toa do cua huong co the di chuyen
                    *o day truyen tham so direct la "Right" de tranh truong hop trung toa do o lan goi de quy ke tiep
                    */ break;
                case 1:
                    //truong hop di chuyen phai, tuong tu nhu tren
                    lengthX += 50;
                    list.add(lengthX);
                    list.add(lengthY);
                    getPath(list,x + 50,y,"Left",lengthX,lengthY,i);
                    break;
                case 2:
                    //truong hop di chuyen len tren
                    lengthY -= 50;
                    list.add(lengthX);
                    list.add(lengthY);
                    getPath(list,x ,y - 50,"Down",lengthX,lengthY,i);//goi de quy
                    break;
                case 3:
                    //truong hop di chuyen xuong
                    lengthY += 50;
                    list.add(lengthX);
                    list.add(lengthY);
                    getPath(list,x ,y + 50,"Up",lengthX,lengthY,i);//goi de quy
                    break;
                default:
                    break;
            }
        }
    }//ham tao ra cho mob di chuyen
    public void setRandomMob(){
        //ham set vi tri ngau nhien cho mob
        Iterator it = mobList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            Random rd = new Random();
            int x,y;
            x = 50 +rd.nextInt(1350);y = 50 + rd.nextInt(650);
                while(Utils.overlapWall(x,y, WallList, DustList)){
                    Random rd1 = new Random();
                    x = 50 + rd1.nextInt(1350); y = 50 + rd1.nextInt(650);
                }
            imi.setLayoutX(x); imi.setLayoutY(y);
        }
        if(level > 1){
            //set vi tri ngau nhien cho mobLv2
            Iterator itt = listMob2.iterator();
            while(itt.hasNext()){
                ImageView imi1 = (ImageView) itt.next();
                Random rd1 = new Random();
                int x1,y1;
                x1 = 50 +rd1.nextInt(1350);y1 = 50 + rd1.nextInt(650);
                while(Utils.overlapWall(x1,y1, WallList, DustList)){
                    Random rd2 = new Random();
                    x1 = 50 + rd2.nextInt(1350); y1 = 50 + rd2.nextInt(650);
                }
                imi1.setLayoutX(x1); imi1.setLayoutY(y1);
            }
        }
        
    }//ham set vi tri ngau nhien cho mob
    public void randomWall(){
        //ham tao ra cac wall co the pha theo toa do ngau nhien
        Iterator itt = DustList.iterator();
        int x,y;
        while(itt.hasNext()){
            ImageView imi = (ImageView) itt.next();
            Random rd = new Random();
            x = 50 +rd.nextInt(1350);y = 50 + rd.nextInt(650);
            while(Utils.randomXY(x,y)){
                Random rd1 = new Random();
                x = 50 + rd.nextInt(1350); y = 50 + rd.nextInt(650);
            }
            imi.setLayoutX(x); imi.setLayoutY(y);
        }
    }//ham tao ra cac wall ngau nhien
    public void randomItem(){
        //ham set vi tri cac itemBuff giau o trong cac wall co the pha
        ListIterator it ;
        Random rd = new Random();
        int i = rd.nextInt(60) ;
        it = DustList.listIterator(i);//chon 1 phan tu wall ngau nhien trong WallList
        ImageView imi = (ImageView) it.next();
        Door.setLayoutX(imi.getLayoutX()); Door.setLayoutY(imi.getLayoutY());//set vi tri cua loi thoat tai vi tri wall do
        int j = rd.nextInt(60) ;
        while(i == j) {//tranh truong hop 2 itemBuff cung o 1 vi tri, boi vay i phai khac j
            j = rd.nextInt(60) ;
        }
        it = DustList.listIterator(j); imi = (ImageView) it.next();
        BombBuff.setLayoutX(imi.getLayoutX()); BombBuff.setLayoutY(imi.getLayoutY());//set vi tri buff Lv cua Bomb
        int k = rd.nextInt(60);
        while(i ==k || j ==k){
            k = rd.nextInt(60);
        }
        it = DustList.listIterator(k); imi = (ImageView) it.next();
        Boot.setLayoutX(imi.getLayoutX());Boot.setLayoutY(imi.getLayoutY());//set vi tri boot speed
        int l = rd.nextInt(60);
        while(i ==l || j == l || k == l){
            l = rd.nextInt(60);
        }
        it = DustList.listIterator(l); imi = (ImageView) it.next();
        Ice.setLayoutX(imi.getLayoutX()); Ice.setLayoutY(imi.getLayoutY());//set vi tri freeze item
    }//ham set vi tri ngau nhien cho cac item
    public boolean overlapLayout(double x,double y){
            //kiem tra xem toa do x,y co trung voi cac object khac khong
            Iterator it = WallList.iterator();
            while(it.hasNext()){
                ImageView imi = (ImageView) it.next();
                if(x == imi.getLayoutX() && y==  imi.getLayoutY()) return true;
            }
            Iterator itt = DustList.iterator();
            while(itt.hasNext()){
                ImageView imi2 = (ImageView) itt.next();
                if(x == imi2.getLayoutX() && y == imi2.getLayoutY()) return true;
            }
            if(x == Bomb.getLayoutX() && y == Bomb.getLayoutY() || (x == Bomb1.getLayoutX() && y == Bomb1.getLayoutY()) || (x == Bomb2.getLayoutX() && y == Bomb2.getLayoutY())) return true;
            
            return false;
       
       //tra ve true neu toa do trung voi wall co dinh hay wall co the pha
       //tra ve false neu khong bi trung
    }
    public synchronized void playerDie(ImageView Player){
        //xu ly tinh huong player chet boi mob
        Iterator it = mobList.iterator();//duyet cac phan tu mob
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            double x = imi.getLayoutX() + imi.getTranslateX();
            double y = imi.getLayoutY() + imi.getTranslateY();
            if(((Player.getLayoutX() >= x && Player.getLayoutX() < x + 50 && abs(y - Player.getLayoutY()) < 15) || (Player.getLayoutY() >= y && Player.getLayoutY() < y +50 && abs(x - Player.getLayoutX()) < 15))&& freezing == false ){
                processing = true; //dieu kien de player chet la gan mob
                bombLevel = 1;//khi chet thi se mat buff bomb
                PlayerDisappear();//lam cho Player dan bien mat va hoi sinh
                break;
            }
        } 
        if(level >1){
            Iterator itt = listMob2.iterator();
            while(itt.hasNext()){
                ImageView imi1 = (ImageView) itt.next();
                double x = imi1.getLayoutX() + imi1.getTranslateX();
                double y = imi1.getLayoutY() + imi1.getTranslateY();
                if(((Player.getLayoutX() >= x && Player.getLayoutX() < x + 50 && abs(y - Player.getLayoutY()) < 15) || (Player.getLayoutY() >= y && Player.getLayoutY() < y +50 && abs(x - Player.getLayoutX()) < 15)) && freezing == false){
                    processing = true; //dieu kien de player chet la gan mob
                    bombLevel = 1;//khi chet thi se mat buff bomb
                    PlayerDisappear();//lam cho Player dan bien mat va hoi sinh
                    break;
                }
            }
        }
    }//xu ly tinh huong player chet boi mob
    public synchronized void PlayerDisappear(){
        //lam cho nhan vat bien mat va tro ve vi tri ban dau
        //ham nay cung xu ly so mang cua Player va cac hieu ung khac
        isPlayerDead = true;//khi tra ve true, Player se khong the di chuyen
        lifeLeft -= 1;System.out.println(lifeLeft);
        loseLife();//ham lam mat mang
        Thread t1 = new Thread(()->{
            for( ii =0;ii< 5;ii++){
                Platform.runLater(()->{
                    if(ii % 2 == 0) Player.setVisible(false);
                    else if(ii % 2 ==1) Player.setVisible(true);
                });
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    
                }
            }
            if(lifeLeft > 0){//truong hop van con mang thi hoi sinh player
                Platform.runLater(()->{
                    allReturn(); //cho player ve vi tri cu
                    Player.setVisible(true);
                });
                isPlayerDead = false;
            }
            processing = false;
        });
        t1.start();
    }//lam cho nhan vat bien mat va tro ve vi tri ban dau
    public synchronized void loseLife(){//ham lam mat mang
        bombLevel = 1; speedLevel = 1;
        if(lifeLeft == 2) Heart2.setVisible(false);
        else if(lifeLeft == 1) Heart1.setVisible(false);
        else if(lifeLeft == 0) {//truong hop player khong con mang
            Heart.setVisible(false);
            isPlayerDead = true;
            Thread t = new Thread(()->{
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException ex) {
                    
                }
                Platform.runLater(()->{
                    time.stop();
                    endGame();
                });
            });
            t.start();
        }
    }//ham xu ly truong hop player mat mang
    public synchronized void endGame(){//xu ly truong hop gameOver
        ScoreBoard.setVisible(true);//thong bao GameOver
        Info.setText("You lose !!");
        NextLv.setText("Restart Level");
        NextLv.setVisible(true);
        ReturnMenu.setVisible(true);
        stageNotClear = false;
        isPlayerDead = true;
        int i =0;
        Iterator it = mobList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            path[i].stop();
            imi.setTranslateX(0.0);imi.setTranslateY(0.0);
            ++i;
        }
        if(level > 1){
            int j = 0;
            Iterator itt = listMob2.iterator();
            while(itt.hasNext()){
                ImageView imi1 = (ImageView) itt.next();
                path1[j].stop();
                imi1.setTranslateX(0.0);imi1.setTranslateY(0.0);
                ++j;
            }
        }
    }//ham ket thuc game (truong hop gameOver)
    public synchronized void checkStageClear(){
        //dieu kien de qua man: vi tri cua Player phai trung voi vi tri cua Door va tat ca cac Mob trong map phai chet het
        if((Player.getLayoutX() >= Door.getLayoutX() && Player.getLayoutX() < Door.getLayoutX() + 50) &&(Player.getLayoutY() >= Door.getLayoutY() && Player.getLayoutY() < Door.getLayoutY() + 50) && mobLeft <= 0){
            for(int i = 0;i < 8;i ++){
                path[i].stop();
            }
            if(level > 1){
                for(int j=0;j < level - 1;j++){
                    path1[j].stop();
                }
            }
            MusicUtils.playClearStage();
            ScoreBoard.setVisible(true);
            NextLv.setText("Next Level");
            isPlayerDead = true;
            stageNotClear = false;
            time.stop();
            gameResult();
        } 
    }//ham kiem tra xem stage da clear hay chua
    public synchronized void checkBuff(){
        //ham kiem tra xem Player da an buff hay chua
        if((Player.getLayoutX() >= BombBuff.getLayoutX() && Player.getLayoutX()< BombBuff.getLayoutX() + 50) &&(Player.getLayoutY() >= BombBuff.getLayoutY() && Player.getLayoutY() < BombBuff.getLayoutY() + 50)){
            bombLevel += 1;
            BombBuff.setLayoutX(-50);BombBuff.setLayoutY(-50);
        }
        if((Player.getLayoutX() >= Boot.getLayoutX() && Player.getLayoutX()< Boot.getLayoutX() + 50) &&(Player.getLayoutY() >= Boot.getLayoutY() && Player.getLayoutY() < Boot.getLayoutY() + 50)){
            speedLevel = 2;
            Boot.setLayoutX(-50);Boot.setLayoutY(-50);
        }
        if((Player.getLayoutX() >= Ice.getLayoutX() && Player.getLayoutX()< Ice.getLayoutX() + 50) &&(Player.getLayoutY() >= Ice.getLayoutY() && Player.getLayoutY() < Ice.getLayoutY() + 50)){
            canFreeze = true;
            Ice.setLayoutX(-50);Ice.setLayoutY(-50);
        }
    }//ham kiem tra cac buff da duoc an chua
    public synchronized void countdown(){
        //ham nay dung de dem nguoc thoi gian
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(
           new KeyFrame(Duration.seconds(1), (ActionEvent e) -> {
               //sau moi khoanng thoi gian la 1s thi cap nhat lai LabelTime
               sec --;
               TimeCount.setText(Utils.timeTrans(sec));
               if(sec <= 0) {
                   time.stop();//dung neu nhu da het gio
                   endGame();
               }
        })
       );
       time.play();
    }//ham dem nguoc thoi gian
    public synchronized void gameResult(){
        //hien ra bang ket qua man choi
        Result.setVisible(true);
        completed = true;
        ++star;
        if(sec >= LvTime / 2) {
            ++star;
            halfTime = true;
        }
        switch(level){
            case 1: case 2:
                if(lifeLeft == 3) {
                    ++star;
                    noLifeLost = true;
                }
                break;
            case 3:
                if(lifeLeft == 2){
                    ++star;
                    noLifeLost = true;
                }
                break;
            default:
                break;
        }
        LvScore.setText(String.valueOf(score));
        LvTimeLeft.setText(Utils.timeTrans(sec));
        Thread t = new Thread(()->{
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {}
        Thread t1 = new Thread(()->{
            Platform.runLater(()->{
                Timeline time1 = new Timeline();
                time1.setCycleCount(Timeline.INDEFINITE);
                time1.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(0.02), (ActionEvent e) -> {
                    sec --;
                    LvTimeLeft.setText(Utils.timeTrans(sec));
                    score = score + (1 * level);
                    LvScore.setText(String.valueOf(score));
                    if(sec <= 0) {
                        time1.stop();  
                    }
                })
                );
                time1.play();
            });
        });
        t1.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {}
        Thread t2 = new Thread(()->{
            Platform.runLater(()->{
                if(completed == true) Star1.setStyle("-fx-fill: #f1fa02");     
            });
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {}
            Platform.runLater(()->{
                if(halfTime == true) Star2.setStyle("-fx-fill: #f1fa02");     
            });
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {}
            Platform.runLater(()->{
                if(noLifeLost == true) Star3.setStyle("-fx-fill: #f1fa02");     
            });
        });
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
        NextLv.setVisible(true);
        ReturnMenu.setVisible(true);
        switch(level){
            case 1:
                HighScoreUtils.addData(0, score);
                HighScoreUtils.addData(1, star);
                break;
            case 2:
                HighScoreUtils.addData(2, score);
                HighScoreUtils.addData(3, star);
                break;
             case 3:
                HighScoreUtils.addData(4, score);
                HighScoreUtils.addData(5, star);
                break;
        }
        try {
            HighScoreUtils.writeData();
        } catch (IOException ex) {}
        });
        t.start();
    }//in ra ket qua game
    public synchronized void pauseGame(){
        //tam dung tro choi
        isPlayerDead = true;
        isPaused = true;
        time.pause();
        for(int i=0;i < 8;i++){
             path[i].pause();
        }
        if(level >1){
            for(int j=0;j < level -1;j++){
                path1[j].pause();
            }
        }
        ScoreBoard.setVisible(true);
        Result.setVisible(true);
        Info.setText("Game Paused");
        LvScore.setText(String.valueOf(score));
        LvTimeLeft.setText(Utils.timeTrans(sec));
        NextLv.setVisible(true);
        NextLv.setText("Continue");
        ReturnMenu.setVisible(true);
    }//ham dung game
    public synchronized void freeze(){
        freezing = true;
        double x,y;
        for(int i=0; i<8;i++){
            path[i].pause();
        }
        if(level > 1){
            for(int j=0;j<level - 1;j++){
                path1[j].pause();
            }
        }
        time.pause();
        Iterator ip = mobList.iterator();int p = 0;
        while(ip.hasNext()){
            ImageView im = (ImageView) ip.next();
             x = im.getLayoutX() + im.getTranslateX();
             y = im.getLayoutY() + im.getTranslateY();
            freeList[p].setVisible(true);
            freeList[p].setLayoutX(x); freeList[p].setLayoutY(y);
            ++p;
        }
        if(level > 1){
            int p1 = 0;
            Iterator in = listMob2.iterator();
            while(in.hasNext()){
                ImageView im1 = (ImageView) in.next();
                x = im1.getLayoutX() + im1.getTranslateX();
                y = im1.getLayoutY() + im1.getTranslateY();
               freeList[p].setVisible(true);
               freeList[p].setLayoutX(x); freeList[p].setLayoutY(y);
               mobFreeze[p1] = true;
               ++p;++p1;
            }
        }
        Lighting light = new Lighting();
        light.setSpecularConstant(2.0);
        background.setEffect(light);
        rot = new RotateTransition[10]; pah = new PathTransition[10]; par = new ParallelTransition[10];
        Line l1 = new Line(25,25,25,225);
        Iterator it = iceGroundList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            imi.setVisible(true);
        }
        Iterator itt = flowerList.iterator(); int k = 0;
        while(itt.hasNext()){
            ImageView imi1 = (ImageView) itt.next();
            imi1.setVisible(true);
            rot[k] = new RotateTransition();rot[k].setNode(imi1);
            rot[k].setByAngle(360);rot[k].setDuration(Duration.seconds(6));rot[k].setCycleCount(1);
            pah[k] = new PathTransition();pah[k].setNode(imi1);
            pah[k].setPath(l1);pah[k].setDuration(Duration.seconds(6));pah[k].setCycleCount(1);
            par[k] = new ParallelTransition(rot[k], pah[k]);
            par[k].play();
            ++k;
        }
      
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(6000);
            } catch (InterruptedException ex) {}
            freezing = false;
            Platform.runLater(()->{
            for(int i=0;i<10;i++){
                freeList[i].setVisible(false);
            }
            Iterator it1 = iceGroundList.iterator();
            Lighting light1 = new Lighting();
            light1.setSpecularConstant(1.0);
            background.setEffect(light1);
            while(it1.hasNext()){
                ImageView ima = (ImageView) it1.next();
                ima.setVisible(false);
            }
            Iterator it2 = flowerList.iterator();
            while(it2.hasNext()){
                ImageView ima1 = (ImageView) it2.next();
                ima1.setVisible(false);
            }
            if(isPaused == false){
                time.play();
                for(int i1=0;i1 <8;i1++){
                    if(mobLv1[i1] > 0) path[i1].play();
                }
                if(level >1){
                    for(int j1=0;j1< level -1;j1++){
                        if(mobLv2[j1] > 0 && mobFreeze[j1] == true) path1[j1].play();
                    }
                }
            }
            });
        });
        t1.start();
    }//ham dong bang map
    @FXML
    private synchronized void nextLv(){
        //doan code duoi day duoc thuc hien khi nhan button tren ScoreBoard
        ScoreBoard.setVisible(false);
        if(isPaused == false){
             if(lifeLeft > 0){//xu ly truong hop player chua chet (da hoan thanh stage)
                createNewStage();
                myPane.getScene().getRoot().requestFocus();
            }else {//xu ly truong hop player chet
                level -= 1;   
                createNewStage();
                myPane.getScene().getRoot().requestFocus();
            }
        }else if(isPaused == true){//xu ly truong hop khi game dang Pause
            isPaused = false;
            isPlayerDead = false;
            for(int i =0;i<8;i++){
                if(mobLv1[i] > 0) path[i].play();//chi cho di chuyen nhung mob con song
            }
            if(level > 1){
                for(int j=0;j< level -1;j++){
                    if(mobLv2[j] > 0) path1[j].play();
                }
            }
            time.play();
            myPane.getScene().getRoot().requestFocus();
            
        }
    }//ham xu ly button nextLv
    @FXML
    private synchronized void exitNextLv(){
        NextLv.setStyle("-fx-background-color: #321d04");
    }
    @FXML
    private synchronized void enterNextLv(){
        NextLv.setStyle("-fx-background-color: #e7f50a");
    }
    @FXML
    private synchronized void mainMenu(MouseEvent e) throws IOException{
        for(int i=0;i < 8;i ++){
            path[i].stop();
        } 
        if(level > 1){
            for(int j=0;j < level -1; j++){
                path1[j].stop();
            }
        }
        time.stop();
        stageNotClear = false;
        Result.setVisible(false);
        NextLv.setVisible(false);
        MusicUtils.endGameMusic();
        MusicUtils.playMenuMusic();
        Parent rooty = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene s = new Scene(rooty);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(s);
        window.show();
    }//xu ly button mainMenu
    @FXML
    private synchronized void enterMainMenu(){
        ReturnMenu.setStyle("-fx-background-color: #e7f50a");
    }
    @FXML
    private synchronized void exitMainMenu(){
        ReturnMenu.setStyle("-fx-background-color: #321d04");
    }
    @FXML
    private synchronized void createNewStage(){
        //ham tao ra man choi moi
        mobFreeze[0] = mobFreeze[1] = mobFreeze[2] = false;
        freeList[0] = IceCube;freeList[1] = IceCube1;freeList[2] = IceCube2;freeList[3] = IceCube3;freeList[4] = IceCube4;
        freeList[5] = IceCube5;freeList[6] = IceCube6;freeList[7] = IceCube7;freeList[8] = IceCube8;freeList[9] = IceCube9;
        ++level; star = 0;score = 0;
        bombLevel = 1; speedLevel = 1;
        if(level <= 2){
            lifeLeft = 3;
            if(level == 1) LvTime = 300;
            else LvTime = 240;
            Heart.setVisible(true);
            Heart1.setVisible(true);
            Heart2.setVisible(true);
        }else if (level == 3){
            lifeLeft = 2;LvTime = 210;
            Heart.setVisible(true);
            Heart1.setVisible(true);
            Heart2.setVisible(false);
        }
        sec = LvTime;
        completed = halfTime = noLifeLost = false;
        listMob2 = new ArrayList<>();
        allReturn();
        mobLeft = 7 + level;//cai dat so luong mob(cu len 1 lv thi tang 1
        Level.setText(String.valueOf(level));
        TimeCount.setText(Utils.timeTrans(sec));
        LvScore.setText("0");
        countdown();//bat dau dem nguoc thoi gian
        ScoreBoard.setVisible(false);
        Player.setVisible(true);
        realX = 50; realY = 50;
        mapMove = 0;mobLeft = 8;
        processing = false;isPlayerDead = false;
        stageNotClear = true; isPaused = false;
        Slime.setVisible(false);
        Slime1.setVisible(false);
        Slime2.setVisible(false);
        mobLv1 = new int[8];
        for(int i = 0;i < 8;i ++){
            path[i] = new PathTransition();
            mobLv1Move[i] = false;
            mobLv1[i] = 1;
        }
        if(level > 1){
            path1 = new PathTransition[level - 1];
            mobLv2 = new int[level -1];
            for(int j = 0;j < level -1;j++){
                path1[j] = new PathTransition();
                mobLv2[j] = 2;//mang cua mob lv2 - 2 mang
            }
        }
        switch(level){
            case 2:
                listMob2.add(Slime);
                Slime.setVisible(true);
                break;
            case 3:
                listMob2.add(Slime);
                listMob2.add(Slime1);
                Slime.setVisible(true);
                Slime1.setVisible(true);
                break;
            case 4:
                listMob2.add(Slime);
                listMob2.add(Slime1);
                listMob2.add(Slime2);
                Slime.setVisible(true);
                Slime1.setVisible(true);
                Slime2.setVisible(true);
                break;
            default:
                break;
        }
        moveAnimation();
        randomWall();//cai dat Wall ngau nhien
        randomItem();//cai dat item ngau nhien
        setRandomMob();//cai dat vi tri mob ngau nhien
        isPlaced = false;isPlaced1 = false;isPlaced2 = false;
        setAllMobAnimation();//cai hieu ung cho mob
        Thread t1 = new Thread(()->{
           while(stageNotClear){
               if(processing == false){
                   Platform.runLater(()->{
                       playerDie(Player);//chi thuc hien hieu ung playerDie khi processing = false, nham tranh truong hop thuc hien hieu ung nhieu lan
                   });
               }
               try {
                   if(processing == true) {
                       
                       Thread.sleep(1300);
                   }
                   else if(processing == false) Thread.sleep(100);
               } catch (InterruptedException ex) {
                  
               }
           } 
        });
        t1.start();
        /*thread nay lien tuc kiem tra xem Player da chet hay chua cu moi khoang thoi gian 0,2s
         *bien processing tra ve true neu nhu Player chet va dang trong qua trinh hoi sinh tro lai
         * khi processing dang true thi Thread kiem tra se sleep 1.3s de cho viec hoi sinh hoan tat
        */
        Thread t2 = new Thread(()->{
            while(stageNotClear){
                Platform.runLater(()->{
                    checkStageClear();
                    checkBuff();
                });
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                   
                }
            }
        });
        // thread nay lien tuc kiem tra xem Stage da clear hay chua cu moi khoang 0.3s
       t2.start();
       
    }//tao ra man choi moi
    @Override
    public synchronized void initialize(URL url, ResourceBundle rb) {
        // TODO
        Point.setText("0");
        MusicUtils.endMenuMusic();
        Thread t1 = new Thread(()->{
            MusicUtils.playReady();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            MusicUtils.playGameMusic();
        });
        t1.start();
        background.requestFocus();
        createNewStage();
    }    
    
}
