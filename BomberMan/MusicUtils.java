/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BomberMan;

import javafx.scene.media.*;

/**
 *
 * @author Win10
 */
public class MusicUtils {
    static Media md , md1, md2, md3, md4 ,md5;
    static MediaPlayer mp, mp1, mp2, mp3 , mp4, mp5;
    public synchronized static void playMenuMusic(){
        md = new Media("file:///C:/Users/Win10/Documents/NetBeansProjects/BomberMan/src/Music/MenuMusic.mp3");
        mp = new MediaPlayer(md);
        mp.setVolume(1.0);
        mp.play();
    }
    public synchronized static void endMenuMusic(){
        mp.stop();
    }
    public synchronized static void playGameMusic(){
        md1 = new Media("file:///C:/Users/Win10/Documents/NetBeansProjects/BomberMan/src/Music/GameMusic.mp3");
        mp1 = new MediaPlayer(md1);
        mp1.setVolume(1.0);
        mp1.play();
    }
    public synchronized static void endGameMusic(){
        mp1.stop();
    }
    public synchronized static void playReady(){
        md2 = new Media("file:///C:/Users/Win10/Documents/NetBeansProjects/BomberMan/src/Music/GameReady.mp3");
        mp2 = new MediaPlayer(md2);
        mp2.setVolume(1.0);
        mp2.play();
    }
    public synchronized static void playClick(){
        md3 = new Media("file:///C:/Users/Win10/Documents/NetBeansProjects/BomberMan/src/Music/Click.mp3");
        mp3 = new MediaPlayer(md3);
        mp3.setVolume(1.0);
        mp3.play();
    }
    public synchronized static void explode(){
        md4 = new Media("file:///C:/Users/Win10/Documents/NetBeansProjects/BomberMan/src/Music/Explode.mp3");
        mp4 = new MediaPlayer(md4);
        mp4.setVolume(1.0);
        mp4.play();
    }
    public synchronized static void playClearStage(){
        md5 = new Media("file:///C:/Users/Win10/Documents/NetBeansProjects/BomberMan/src/Music/StageCleared.mp3");
        mp5 = new MediaPlayer(md5);
        mp5.setVolume(1.0);
        mp5.play();
    }
}
