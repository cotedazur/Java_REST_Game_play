/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import java.util.*;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
/**
 * REST Web Service
 *
 * @author Adeel
 */
@Path("gamevalidator")
public class GameService {
    
    //Hashtable<String,String> map;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GameService
     */
    public GameService() {
        //map=new Hashtable<String,String>();
        //ServerGlobals.map.put("counter", "0");
        if(!ServerGlobals.map.containsKey("counter"))       ServerGlobals.map.put("counter", "0");
    }

    /**
     * Retrieves representation of an instance of gameserver.GameService
     * @return an instance of java.lang.String
     */
    
    @GET
    public Response responseMsg( @QueryParam("command") String command, @QueryParam("parameter2") String parameter2)
    { 
        String output = "Prameter1: " + command + "\nParameter2: " + parameter2.toString();
        switch(command)
        {
            case "NEWGAMEPASS":
                int counter = 0;
                for(Map.Entry m:ServerGlobals.map.entrySet())
                {  
                    if(m.getKey().toString().startsWith(parameter2+"_"))
                    {
                        counter++;
                       //output = "NEWGAMEPASS_ALREADY_TAKEN"; 
                    }
                    else
                    {
                        //output ="NEWGAME_PASS_AVAILABLE";
                        
                    }
                }  
                if(counter==0)
                {
                    output ="NEWGAME_PASS_AVAILABLE";
                    ServerGlobals.map.put(parameter2+"_TimeStamp", Calendar.getInstance().getTimeInMillis()+"");
                    ServerGlobals.map.put(parameter2+"_PlayerCount", "1");
                    
                    //output = "NEWGAMEPASS_ALREADY_TAKEN";
                }
                else
                {
                    //try
                      //  {
                            output = "NEWGAMEPASS_ALREADY_TAKEN";
                            //output ="NEWGAME_PASS_AVAILABLE";
                            
                       // }
                        //catch(Exception e)
                        //{
                         //   output = e.toString();
                        //}
                }
                
                
                //System.out.println("");
                break;
            case "PlayerCount":
                output = ServerGlobals.map.get(parameter2+"_PlayerCount");
                break;
            case "Player2Join":
                output = ServerGlobals.map.replace(parameter2+"_PlayerCount","2");
                ServerGlobals.map.put(parameter2+"_Position1"," ");
                ServerGlobals.map.put(parameter2+"_Position2"," ");
                ServerGlobals.map.put(parameter2+"_Position3"," ");
                ServerGlobals.map.put(parameter2+"_Position4"," ");
                ServerGlobals.map.put(parameter2+"_Position5"," ");
                ServerGlobals.map.put(parameter2+"_Position6"," ");
                ServerGlobals.map.put(parameter2+"_Position7"," ");
                ServerGlobals.map.put(parameter2+"_Position8"," ");
                ServerGlobals.map.put(parameter2+"_Position9"," ");
                break;
            case "WhoseFirst":
                Random rand = new Random();
                int n = 2;//rand.nextInt(1)+1;
                if(!ServerGlobals.map.containsKey(parameter2+"_WhoseFirst"))
                    ServerGlobals.map.put(parameter2+"_WhoseFirst", n+"");
                output = ServerGlobals.map.get(parameter2+"_WhoseFirst");
                if(!ServerGlobals.map.containsKey(parameter2+"_MoveCounter"))
                    ServerGlobals.map.put(parameter2+"_MoveCounter", "1");
                break;
            case "MoveCounter":
                output = ServerGlobals.map.get(parameter2+"_MoveCounter");
                break;
            case "Position1":
                output = ServerGlobals.map.get(parameter2+"_Position1");
                break;  
            case "Position2":
                output = ServerGlobals.map.get(parameter2+"_Position2");
                break;    
            case "Position3":
                output = ServerGlobals.map.get(parameter2+"_Position3");
                break;
            case "Position4":
                output = ServerGlobals.map.get(parameter2+"_Position4");
                break; 
            case "Position5":
                output = ServerGlobals.map.get(parameter2+"_Position5");
                break;
            case "Position6":
                output = ServerGlobals.map.get(parameter2+"_Position6");
                break;  
            case "Position7":
                output = ServerGlobals.map.get(parameter2+"_Position7");
                break;  
            case "Position8":
                output = ServerGlobals.map.get(parameter2+"_Position8");
                break;
            case "Position9":
                output = ServerGlobals.map.get(parameter2+"_Position9");
                break;      
            case "SetPosition1":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position1",parameter2.split("-")[1]+"");
                break;  
             case "SetPosition2":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position2",parameter2.split("-")[1]+"");
                break;      
             case "SetPosition3":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position3",parameter2.split("-")[1]+"");
                break;  
             case "SetPosition4":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position4",parameter2.split("-")[1]+"");
                break;  
             case "SetPosition5":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position5",parameter2.split("-")[1]+"");
                break;  
             case "SetPosition6":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position6",parameter2.split("-")[1]+"");
                break;  
             case "SetPosition7":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position7",parameter2.split("-")[1]+"");
                break;  
             case "SetPosition8":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position8",parameter2.split("-")[1]+"");
                break;  
             case "SetPosition9":
                output = ServerGlobals.map.put(parameter2.split("-")[0]+"_Position9",parameter2.split("-")[1]+"");
                break;       
                
                
                
            case "MoveCounterNext":
                output = ServerGlobals.map.replace(parameter2+"_MoveCounter",((Integer.parseInt(ServerGlobals.map.get(parameter2+"_MoveCounter")))+1)+"");
                break;
        }
 
        return Response.status(200).entity(output).build(); 
 
    }
    
    
    
    //@GET
    //@Produces("text/html")
    //public String getHtml() {
        
    //    ServerGlobals.map.replace("counter", ((Integer.parseInt(ServerGlobals.map.get("counter"))+1)+""));
    //    return ServerGlobals.map.get("counter");
        //return "<html><body><h1>Hello, World!!</body></h1></html>";
    //}

    /**
     * PUT method for updating or creating an instance of GameService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
