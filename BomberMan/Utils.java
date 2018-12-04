/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BomberMan;

import java.util.*;
import java.util.Iterator;
import javafx.scene.image.ImageView;

/**
 *
 * @author Win10
 */
public class Utils {
    public static boolean blockedRight(ImageView r1, ImageView r2){
        //ham kiem tra xem block r2 co chan phai r1 hay khong neu bi chan thi ham tra ve true
        //ham tra ve true thi block r1 ko the di chuyen sang ben phai
        //cac ham boolean duoi cung tuong tu
        if(r1.getLayoutX() >=1450 - r1.getFitWidth()) return true;
        else{
             if((r1.getLayoutX() >= (r2.getLayoutX()-r1.getFitWidth()) && r1.getLayoutX() < r2.getLayoutX()+r2.getFitWidth()  ) && (r1.getLayoutY() < r2.getLayoutY() + r2.getFitHeight()&& r1.getLayoutY() >r2.getLayoutY() - r1.getFitHeight() )) return true;
             else return false;
        }
    }
    public static boolean blockedLeft(ImageView r1, ImageView r2){
        if(r1.getLayoutX() <=50) return true;
        else {
            if((r1.getLayoutX() <= (r2.getLayoutX() + r2.getFitWidth()) && r1.getLayoutX() > r2.getLayoutX()  ) && (r1.getLayoutY() < r2.getLayoutY() + r2.getFitHeight()&& r1.getLayoutY() >r2.getLayoutY() - r1.getFitHeight()  ))return true;
            else return false;
        }   
    }
    public static boolean blockedUp(ImageView r1, ImageView r2){
        if(r1.getLayoutY() <= 50) return true;
        else{
            if((r1.getLayoutY() <= r2.getLayoutY() + r2.getFitHeight() && r1.getLayoutY() > r2.getLayoutY()) && (r1.getLayoutX() > r2.getLayoutX() - r1.getFitWidth()&& r1.getLayoutX() < r2.getLayoutX() +r2.getFitWidth()   ))return true;
            else return false;
        }
    }
    public static boolean blockedDown(ImageView r1, ImageView r2){
        if(r1.getLayoutY() >= 700 -r1.getFitHeight()) return true;
        else{
            if((r1.getLayoutY() >= r2.getLayoutY() - r1.getFitHeight()&&r1.getLayoutY() < r2.getLayoutY() + r2.getFitHeight() )&& (r1.getLayoutX() > r2.getLayoutX() - r1.getFitWidth()&& r1.getLayoutX() < r2.getLayoutX() +r2.getFitWidth()   )) return true;
            else return false;
        }
    }
    public static boolean totalBlockLeft(ImageView Player, ArrayList<ImageView> WallList, ArrayList<ImageView> DustList){
        //ham duyet toan bo cac Wall trong game
        //kiem tra toa do cac wall xem co wall nao chan ben trai player khong
        //neu khong co wall nao chan thi player co the di chuyen sang trai
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            //duyet cac wall co dinh
            ImageView imi = (ImageView) it.next();
            if(blockedLeft(Player,imi)) return true;//kiem tra tung wall mot
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi2 = (ImageView) itt.next();
            //duyet cac wall co the pha
            if(blockedLeft(Player,imi2)) return true;
        }   
        return false;
        //cac ham kiem tra duoi cx tuong tu
    }
    public static boolean totalBlockRight(ImageView Player, ArrayList<ImageView> WallList, ArrayList<ImageView> DustList){
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            if(blockedRight(Player,imi)) return true;
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi2 = (ImageView) itt.next();
            if(blockedRight(Player,imi2)) return true;
        }   
        return false;
    }
    public static boolean totalBlockUp(ImageView Player, ArrayList<ImageView> WallList, ArrayList<ImageView> DustList){
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            if(blockedUp(Player,imi)) return true;
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi2 = (ImageView) itt.next();
            if(blockedUp(Player,imi2)) return true;
        }   
        return false;
    }
    public static  boolean totalBlockDown(ImageView Player, ArrayList<ImageView> WallList, ArrayList<ImageView> DustList){
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            if(blockedDown(Player,imi)) return true;
        }
        Iterator itt = DustList.iterator();
        while(itt.hasNext()){
            ImageView imi2 = (ImageView) itt.next();
            if(blockedDown(Player,imi2)) return true;
        }   
        return false;
    }
    public static boolean randomXY(int x,int y){
        //ham kiem tra xem toa do cac wall co dung hay khong
        //neu wall da bi trung thi tra ve true -> lap lai vong lap while ben tren
        if(x % 100 == 0 && y % 100 ==0) return true;//cap toa do nay trung voi toa do cac wall co dinh
        else if(x % 50 != 0 || y % 50 != 0) return true;//cac wall ban dau phai co toa do la boi cua 50
        else if(x == 50 && y == 50) return true;//trung vi tri ban dau cua Player
        else return false;
    }
    public static boolean canExplodeLeft(double x, double y, ArrayList<ImageView> WallList){
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            if((x - 50 == imi.getLayoutX())&&(y == imi.getLayoutY())) return false;
        }
        return true;
    }//4 ham duoi kiem tra nhung wall ben canh co the pha hay khong
    public static boolean canExplodeRight(double x, double y,  ArrayList<ImageView> WallList){
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            if((x + 50 == imi.getLayoutX())&&(y == imi.getLayoutY())) return false;
        }
        return true;
    }
    public static boolean canExplodeUp(double x, double y,  ArrayList<ImageView> WallList){
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            if((y - 50 == imi.getLayoutY())&&(x == imi.getLayoutX())) return false;
        }
        return true;
    }
    public static boolean canExplodeDown(double x, double y,  ArrayList<ImageView> WallList){
        Iterator it = WallList.iterator();
        while(it.hasNext()){
            ImageView imi = (ImageView) it.next();
            if((y + 50 == imi.getLayoutY())&&(x == imi.getLayoutX())) return false;
        }
        return true;
    }
    public static boolean canBreakLv1(ImageView r1, ImageView r2){
        //kiem tra xem nhung wall nao co the break duoc
        //r1 la toa do cua bomb, r2 la toa do cua wall co the pha
        if(r1.getLayoutX() - 50 == r2.getLayoutX() && r1.getLayoutY() == r2.getLayoutY()) return true;
        else if(r1.getLayoutX() + 50 == r2.getLayoutX() && r1.getLayoutY() == r2.getLayoutY()) return true;
        else if(r1.getLayoutY() - 50 == r2.getLayoutY() && r1.getLayoutX() == r2.getLayoutX()) return true;
        else if(r1.getLayoutY() + 50 == r2.getLayoutY() && r1.getLayoutX() == r2.getLayoutX()) return true;
        return false;
    }//ham kiem tra xem nhung wall nao co the bi pha boi bomb Lv1
    public static boolean canBreakLv2(ImageView r1, ImageView r2){
        //kiem tra xem nhung wall nao co the break duoc
        //r1 la toa do cua bomb, r2 la toa do cua wall co the pha
        if(r1.getLayoutX() - 100 == r2.getLayoutX() && r1.getLayoutY() == r2.getLayoutY()) return true;
        else if(r1.getLayoutX() + 100 == r2.getLayoutX() && r1.getLayoutY() == r2.getLayoutY()) return true;
        else if(r1.getLayoutY() - 100 == r2.getLayoutY() && r1.getLayoutX() == r2.getLayoutX()) return true;
        else if(r1.getLayoutY() + 100 == r2.getLayoutY() && r1.getLayoutX() == r2.getLayoutX()) return true;
        return false;
    }//ham kiem tra xem nhung wall nao co the bi pha boi bomb Lv2
    public static boolean overlapWall(double x,double y, ArrayList<ImageView> WallList, ArrayList<ImageView> DustList){
       if(x % 50 != 0 || y % 50 != 0) return true;
       else{
            Iterator it = WallList.iterator();
            while(it.hasNext()){
                ImageView imi = (ImageView) it.next();
                if(x == imi.getLayoutX() && y== imi.getLayoutY()) return true;
            }
            Iterator itt = DustList.iterator();
            while(itt.hasNext()){
                ImageView imi2 = (ImageView) itt.next();
                if(x == imi2.getLayoutX() && y == imi2.getLayoutY()) return true;
            }
            return false;
       }
       //tra ve true neu toa do trung voi wall co dinh hay wall co the pha
       //tra ve false neu khong bi trung
    }
     public static String timeTrans(int sec){
        int min = sec/60;
        int second = sec %60;
        StringBuilder ss = new StringBuilder();
        ss.append(min);
        ss.append(" : ");
        ss.append(second);
        return ss.toString();
        //ham chuyen doi giay thanh phut
    }
    
}
