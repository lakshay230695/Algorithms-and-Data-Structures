/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lakshay;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author NAME
 */
@Path("generic")
public class Polygon {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Polygon
     */
    public Polygon() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.lakshay.Polygon
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Polygon
     * @param content representation for the resource
     */
    @PUT
    //@Path("PointsInside")
    @Consumes(MediaType.APPLICATION_JSON)
    public String putJson(String content){
        int count = 0;
        try{
            ObjectMapper mapper = new ObjectMapper(); 
            List<Point> pointList =
            mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(List.class, Point.class));

            

            for(int i=0;i<19;i++){
                for(int j=0;j<19;j++){
                    if(contain(new Point(i,j), pointList))
                        count++;
                }
            }
        }catch(Exception e){
            return "Malformed JSON input";
        }
        
        return "{\"count\":" + count + "}";
    }

    private boolean contain(Point test, List<Point> points) {
        int i;
      int j;
      boolean result = false;
      for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
        if ((points.get(i).y > test.y) != (points.get(j).y > test.y) &&
            (test.x < (points.get(j).x - points.get(i).x) * (test.y - points.get(i).y) / (points.get(j).y-points.get(i).y) + points.get(i).x)) {
          result = !result;
         }
      }
      return result;
    }
}
