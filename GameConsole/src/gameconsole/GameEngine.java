/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameconsole;

/**
 *
 * @author Aqib
 */
public class GameEngine {
    public static String gameName = "Tic Tac Toe";
    public static String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator";
    public static String boardArray[] = new String[10];
    public static int timeOut = 30;
    public static String gamePassword="";
    public static boolean firstPlayer = true;
    public static boolean gameStarted = false;
    public static int whoseFirst = 0;
       
    
    
    public static void printBoard()
    {
        System.out.println("_"+boardArray[1]+"_|_"+boardArray[2]+"_|_"+boardArray[3]+"_\n"
                         + "_"+boardArray[4]+"_|_"+boardArray[5]+"_|_"+boardArray[6]+"_\n"
                         + " "+boardArray[7]+" | "+boardArray[8]+" | "+boardArray[9]+"\n" );
    }
    
    public static void reset()
    {
        for(int i=0;i<=9;i++) boardArray[i] = "";
    }
    
    
}
