package BomberMan;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Win10
 */
public class HighScoreUtils {
    private static String[] arr = new String[6];
    public static void autoAdd(){
        for(int i=0;i < 6;i++){
            arr[i] = String.valueOf(0);
        }
    }
    public static void addData(int pos, int data){
        String s = String.valueOf(data);
        if(s.compareTo(arr[pos]) > 0){
            arr[pos] = s;
        }
    }
    
    public static void readData() throws IOException{
        InputStream in = new FileInputStream("C:\\Users\\Win10\\Documents\\NetBeansProjects\\BomberMan\\HighScore.txt");
        Reader rd = new InputStreamReader(in,"UTF-8");
        BufferedReader br = new BufferedReader(rd);
        String s;int i = 0;
        while((s = br.readLine()) != null){
            arr[i] = s;
            ++i;
        }
        br.close();
    }
    public static void writeData() throws IOException{
        try{
            Writer w = new FileWriter("C:\\Users\\Win10\\Documents\\NetBeansProjects\\BomberMan\\HighScore.txt");
            BufferedWriter bw = new BufferedWriter(w);
            for(int i =0; i< 6;i++){
                bw.write(arr[i]);
                bw.newLine();
            }
            bw.close();
        }catch(IOException e){
             System.out.println("Co loi xay ra:");
             System.out.println(e.getMessage());
        }
    } 
    public static String[] getList(){
        return arr;
    }
}
