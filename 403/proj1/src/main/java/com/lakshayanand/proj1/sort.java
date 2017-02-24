/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lakshayanand.proj1;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/sort")
public class sort {
    
    @GET
    @Produces("text/html")
    public Response getStartingPage()
    {
        String output = "{\"message\": \"Must use POST method with correct request body\"}";
        return Response.status(200).entity(output).build();
    }
   
    @POST
    @Consumes("text/plain")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayPlainTextHello(String input) {
        
        JsonObject jObj = new JsonParser().parse(input).getAsJsonObject(); // convert String into JSON Object
        
        JsonArray inList = jObj.getAsJsonArray("inList"); // extract inList from the object
        
        if(inList == null){
            String error = "{\"message\": \"Malformed JSON\"}";
            return Response.status(200).entity(error).build();
        }
        
        int[] inListArray = new int[inList.size()];
        int[] outListArray;
        
        for(int i = 0; i < inList.size(); i++){
            inListArray[i] = Integer.parseInt(inList.get(i).toString());
        }
        long startTime = System.currentTimeMillis();
        
        outListArray = bubbleSort(inListArray); // get the sorted array
        
        long endTime = System.currentTimeMillis();

        long duration = (endTime - startTime);

        
        String output = "{\"outList\": [";
        for(int i = 0; i < outListArray.length; i++){
            System.out.println(outListArray[i]);
            output += outListArray[i];
            if(i >= 0 && i < outListArray.length-1) output += ",";
        }
        
        output += "], \"algorithm\": \"bubblesort\", \"timeMS\": ";
        output += duration;
        output += "}";
        
        System.out.println("Final Output : " + output);
        
      return Response.status(200).entity(output).build();
    }
    
    public static int[] bubbleSort( int[] data )
{
    int i;
    boolean flag = true;
    int temp;
     
    while (flag)
    {
       flag = false;
       for(i=0;  i<data.length -1;  i++)
       {
           if (data[i] > data[i+1])
           {
                temp = data[i];
                data[i] = data[i+1];
                data[i+1] = temp;
                flag = true;
           } 
       } 
    } 
    return data;
} 
    
}
