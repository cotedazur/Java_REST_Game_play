package gameconsole;


import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class GameConsole{
	
   public static void main(String[] args)
   {
        //new GameConsole().testIt();
       System.out.println("Welcome to the game "+GameEngine.gameName+"\n");
       System.out.println("[S]tart a new game or [J]oin an existing one [s/j]");
       Scanner scanner = new Scanner(System.in);
       String ans = scanner.next();
       if(!(ans.equalsIgnoreCase("s")|| ans.equalsIgnoreCase("j")))
       {
           System.out.println("Wrong choice, enter again");
           ans = scanner.next();
           if(!(ans.equalsIgnoreCase("s")|| ans.equalsIgnoreCase("j")))
           {
               System.out.println("Wrong Choice: Good Bye");
               System.exit(1);
           }
       }
        if(ans.equalsIgnoreCase("s"))
        {
            boolean awaiting_player = false;
            System.out.println("Starting a new game.");
            System.out.print("Please enter new game password :");
            String password = scanner.next();
            if(new GameConsole().verifyPassword(password).equals("NEWGAME_PASS_AVAILABLE"))
            {
                System.out.println("New game started, awaiting player 2");
                awaiting_player = true;
                GameEngine.firstPlayer = true;
                GameEngine.gamePassword = password;
            }
            else
            {
                System.out.println("Game already in progress with password: "+password);
                System.out.println("Enter another password: ");
                password = scanner.next();
                if(new GameConsole().verifyPassword(password).equals("NEWGAME_PASS_AVAILABLE"))
                {
                     System.out.println("New game started, awaiting player 2");
                     awaiting_player = true;
                     GameEngine.firstPlayer = true;
                     GameEngine.gamePassword = password;
                }
                else
                {
                    System.out.println("Sorry, wrong password again, exitting.");
                    System.exit(1);
                }                   
            }
            if(awaiting_player)
            {
                for(int i=1;i<=GameEngine.timeOut;i++)
                {
                    try
                    {
                        Thread.sleep(1000);

                    }catch (InterruptedException ex) {
                        Logger.getLogger(GameConsole.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print(".");
                    System.out.print(new GameConsole().verifyPlayerCount(GameEngine.gamePassword));
                    if(new GameConsole().verifyPlayerCount(GameEngine.gamePassword).equals("2"))
                    {   System.out.println("");
                        System.out.println("Player 2 has joined");
                        GameEngine.gameStarted = true;
                        break;
                    }
                    

                }
                System.out.println("");
            }
        }
        if(ans.equalsIgnoreCase("j"))
        {
            boolean awaiting_player = false;
            System.out.println("Join an existing game.");
            System.out.print("Please enter existing game password :");
            String password = scanner.next();
            if(new GameConsole().verifyPassword(password).equals("NEWGAMEPASS_ALREADY_TAKEN"))
            {
                GameEngine.gamePassword = password;
                System.out.println("Game in progress having "+new GameConsole().verifyPlayerCount(GameEngine.gamePassword)+" players");
                //awaiting_player = true;
                if(new GameConsole().verifyPlayerCount(GameEngine.gamePassword).trim().equals("2"))
                {
                    System.out.println("Aleady maximum number of players are playing this game");
                    System.out.println("Quitting.");
                }
                else if(new GameConsole().verifyPlayerCount(GameEngine.gamePassword).trim().equals("1"))
                {
                    System.out.println("Joining game with password:"+GameEngine.gamePassword);
                    new GameConsole().Player2Join(GameEngine.gamePassword);
                    GameEngine.firstPlayer = false;
                    GameEngine.gameStarted = true;
                }
                
            }
            else
            {
                System.out.println("No available game exists with password: "+password);
                System.out.println("Enter another password: ");
                password = scanner.next();
                if(new GameConsole().verifyPassword(password).equals("NEWGAMEPASS_ALREADY_TAKEN"))
                {
                     GameEngine.gamePassword = password;
                    System.out.println("Game in progress having "+new GameConsole().verifyPlayerCount(GameEngine.gamePassword)+" players");
                    //awaiting_player = true;
                    if(new GameConsole().verifyPlayerCount(GameEngine.gamePassword).trim().equals("2"))
                    {
                     System.out.println("Aleady maximum number of players are playing this game");
                     System.out.println("Quitting.");
                    }
                    else if(new GameConsole().verifyPlayerCount(GameEngine.gamePassword).trim().equals("1"))
                    {
                      System.out.println("Joining game with password:"+GameEngine.gamePassword);
                      new GameConsole().Player2Join(GameEngine.gamePassword);
                      GameEngine.firstPlayer = false;
                      GameEngine.gameStarted = true;
                      
                      
                    }
                }
                else
                {
                    System.out.println("Sorry, wrong password again, exitting.");
                    System.exit(1);
                }                   
            }
        }
        if(GameEngine.gameStarted)
        {
            System.out.println("Awaiting server to select player...");
            for(int i=1;i<=GameEngine.timeOut;i++)
            {
                try 
                {
                    Thread.sleep(1000);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(GameConsole.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.print(".");
                //System.out.print(new GameConsole().whoseFirst(GameEngine.gamePassword));
                if(!new GameConsole().whoseFirst(GameEngine.gamePassword).equals("0"))
                {   
                    if(new GameConsole().whoseFirst(GameEngine.gamePassword).equals("1"))
                    {
                        System.out.println("First Player has first move:");
                        GameEngine.whoseFirst = 1;
                    }
                    if(new GameConsole().whoseFirst(GameEngine.gamePassword).equals("2"))
                    {
                        System.out.println("Second Player has first move:");
                        GameEngine.whoseFirst = 2;
                    }
                    break;
                }
            }
            if( GameEngine.whoseFirst==1){
            if(GameEngine.firstPlayer /*&& GameEngine.whoseFirst==1*/)
            {
                if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==1)
                {
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1; 
                    if(new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                    {
                        //System.out.println("debug 1");
                        new GameConsole().setPosition(GameEngine.gamePassword,n,"X");
                        new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
                for(int i=1;i<=GameEngine.timeOut;i++)
                {
                    try
                    {
                        Thread.sleep(1000);

                    }catch (InterruptedException ex) {
                        Logger.getLogger(GameConsole.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print(".");
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1;
                    System.out.println("Move Counter is :"+new GameConsole().moveCounter(GameEngine.gamePassword));
                    if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==1)
                    {
                        while(!new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                        {
                            n = rand.nextInt(8)+1;
                        }
                           new GameConsole().setPosition(GameEngine.gamePassword,n,"X");
                            new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
                }
                
            }
            else
            {
                if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==0)
                {
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1; 
                    if(new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                    {
                        //System.out.println("debug 1");
                        new GameConsole().setPosition(GameEngine.gamePassword,n,"O");
                        new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
                for(int i=1;i<=GameEngine.timeOut;i++)
                {
                    try
                    {
                        Thread.sleep(1000);

                    }catch (InterruptedException ex) {
                        Logger.getLogger(GameConsole.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print(".");
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1;
                    if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==0)
                    {
                        while(!new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                        {
                            n = rand.nextInt(8)+1;
                        }
                           new GameConsole().setPosition(GameEngine.gamePassword,n,"O");
                            new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
            }
   
        }}
            else if( GameEngine.whoseFirst==2)
                    {
                           if(GameEngine.firstPlayer /*&& GameEngine.whoseFirst==1*/)
            {
                if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==1)
                {
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1; 
                    if(new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                    {
                        //System.out.println("debug 1");
                        new GameConsole().setPosition(GameEngine.gamePassword,n,"O");
                        new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
                for(int i=1;i<=GameEngine.timeOut;i++)
                {
                    try
                    {
                        Thread.sleep(1000);

                    }catch (InterruptedException ex) {
                        Logger.getLogger(GameConsole.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print(".");
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1;
                    System.out.println("Move Counter is :"+new GameConsole().moveCounter(GameEngine.gamePassword));
                    if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==1)
                    {
                        while(!new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                        {
                            n = rand.nextInt(8)+1;
                        }
                           new GameConsole().setPosition(GameEngine.gamePassword,n,"O");
                            new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
                }
                
            }
            else
            {
                if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==0)
                {
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1; 
                    if(new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                    {
                        //System.out.println("debug 1");
                        new GameConsole().setPosition(GameEngine.gamePassword,n,"X");
                        new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
                for(int i=1;i<=GameEngine.timeOut;i++)
                {
                    try
                    {
                        Thread.sleep(1000);

                    }catch (InterruptedException ex) {
                        Logger.getLogger(GameConsole.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print(".");
                    Random rand = new Random();
                    int n = rand.nextInt(8)+1;
                    if(Integer.parseInt(new GameConsole().moveCounter(GameEngine.gamePassword))%2==0)
                    {
                        while(!new GameConsole().getPosition(GameEngine.gamePassword,n).equals(" "))
                        {
                            n = rand.nextInt(8)+1;
                        }
                           new GameConsole().setPosition(GameEngine.gamePassword,n,"X");
                            new GameConsole().moveCounterNext(GameEngine.gamePassword);
                    }
                new GameConsole().loadBoard();
                GameEngine.printBoard();
                
            }
   
        }
                    
                    }
       
       
       
       //GameEngine.reset();
       GameEngine.printBoard();
        //new GameConsole().testIt();
   }}
	
   private void testIt()
   {
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=NEWGAMEPASS&parameter2=adeel");
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //dumpl all cert info
	     //print_https_cert(con);
			
	     //dump all the content
	         System.out.println(print_content(con));
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }

   }
	
   private void print_https_cert(HttpsURLConnection con){
     
    if(con!=null){
			
      try {
				
	System.out.println("Response Code : " + con.getResponseCode());
	System.out.println("Cipher Suite : " + con.getCipherSuite());
	System.out.println("\n");
				
	Certificate[] certs = con.getServerCertificates();
	for(Certificate cert : certs){
	   System.out.println("Cert Type : " + cert.getType());
	   System.out.println("Cert Hash Code : " + cert.hashCode());
	   System.out.println("Cert Public Key Algorithm : " 
                                    + cert.getPublicKey().getAlgorithm());
	   System.out.println("Cert Public Key Format : " 
                                    + cert.getPublicKey().getFormat());
	   System.out.println("\n");
	}
				
	} catch (SSLPeerUnverifiedException e) {
		e.printStackTrace();
	} catch (IOException e){
		e.printStackTrace();
	}

     }
	
   }
	
   private String print_content(HttpsURLConnection con){
       String result = "";
	if(con!=null){
			
	try {
		
	   //System.out.println("****** Content of the URL ********");			
	   BufferedReader br = 
		new BufferedReader(
			new InputStreamReader(con.getInputStream()));
				
	   String input;
				
	   while ((input = br.readLine()) != null){
	      //System.out.println(input);
               result = result + input;
	   }
	   br.close();
           
				
	} catch (IOException e) {
	   e.printStackTrace();
	}
			
       }
	return result;	
   }
   public HttpsURLConnection createConnection()
   {
      HttpsURLConnection con = null;
      URL url;
      try {
             url = new URL(GameEngine.https_url);
	     con = (HttpsURLConnection)url.openConnection();
	  		
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
      return con;
   }
   
   private String verifyPassword(String pass)
   {
      String result = "NEWGAMEPASS_ALREADY_TAKEN";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=NEWGAMEPASS&parameter2="+pass);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //dumpl all cert info
	     //print_https_cert(con);
			
	     //dump all the content
	         //System.out.println(print_content(con));
             result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        return result;
   }
   
   private String verifyPlayerCount(String pass)
   {
      String result = "NEWGAMEPASS_ALREADY_TAKEN";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=PlayerCount&parameter2="+GameEngine.gamePassword);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //dumpl all cert info
	     //print_https_cert(con);
			
	     //dump all the content
	         //System.out.println(print_content(con));
             result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        return result;
   }
   
   private String Player2Join(String pass)
   {
      String result = "awaiting player 2 join";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=Player2Join&parameter2="+GameEngine.gamePassword);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //dumpl all cert info
	     //print_https_cert(con);
			
	     //dump all the content
	         //System.out.println(print_content(con));
             result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        return result;
   }
   
   private String whoseFirst(String pass)
   {
      String result = "waiting for server to select first player";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=WhoseFirst&parameter2="+GameEngine.gamePassword);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //dumpl all cert info
	     //print_https_cert(con);
			
	     //dump all the content
	         //System.out.println(print_content(con));
             result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        return result;
   }
   
   private String moveCounter(String pass)
   {
      String result = "0";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=MoveCounter&parameter2="+GameEngine.gamePassword);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
	     result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        return result;
   }

    private void moveCounterNext(String pass)
   {
      String result = "0";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=MoveCounterNext&parameter2="+GameEngine.gamePassword);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
	     result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        //return result;
   }

   private String getPosition(String pass,int i)
   {
      String result = " ";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=Position"+i+"&parameter2="+GameEngine.gamePassword);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
	     result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        return result;
   }
   
    private void setPosition(String pass,int i,String s)
   {
      String result = " ";
      //String https_url = "https://localhost:8181/GameProjectREST/webresources/gamevalidator?command=NEWGAMEPASS&parameter2=adeel";
      URL url;
      try {

	     url = new URL(GameEngine.https_url+"?command=SetPosition"+i+"&parameter2="+GameEngine.gamePassword+"-"+s);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
	     result = print_content(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }
        //return result;
   }
    public void loadBoard()
    {
        for(int i=1;i<=9;i++)
        {
            GameEngine.boardArray[i] = new GameConsole().getPosition(GameEngine.gamePassword,i);
        }
    }

	
}
