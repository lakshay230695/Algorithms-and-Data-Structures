/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.topologicalsort;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author NAME
 */
@Path("TopSort")
public class TopoSort {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
 @GET
    @Produces("text/html")
    public Response TopoSort()
    {
        String output = "{\"message\": \"Must use POST method with correct request body\"}";
        return Response.status(200).entity(output).build();
    }
    /**
     * Retrieves representation of an instance of com.mycompany.topologicalsort.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @POST
    @Path("PerformSort")
    @Consumes(MediaType.APPLICATION_JSON)
    public String topSort(String incomingData){
        ObjectMapper objectMapper = new ObjectMapper();
        RelationList relList;
        try{
            relList = objectMapper.readValue(incomingData, RelationList.class);
        }catch(IOException e){
            return "Incorrent JSON passed";
        }
        
        HashSet<String> distinctNames = new HashSet<>();
        for(Relation rel : relList.getInList()){
            distinctNames.add(rel.getSmarter().get(0));
            distinctNames.add(rel.getSmarter().get(1));
        }
        
        Graph g = new Graph(distinctNames.size());
        
        HashMap<String, Integer> hmStringInt = new HashMap<>();
        HashMap<Integer, String> hmIntString = new HashMap<>();
        int count =0;
        
        Iterator it = distinctNames.iterator();
        while(it.hasNext()){
            String name = (String) it.next();
            System.out.println(name + " " + count);
            hmStringInt.put(name, count);
            hmIntString.put(count, name);
            count++;
        }
        
        for(Relation rel : relList.getInList()){
//            System.out.println(rel.getSmarter().get(0) + " " + rel.getSmarter().get(1));
            System.out.println(hmStringInt.get(rel.getSmarter().get(0)) + " " + hmStringInt.get(rel.getSmarter().get(1)));
            g.addEdge(hmStringInt.get(rel.getSmarter().get(0)), hmStringInt.get(rel.getSmarter().get(1)));
        }
        
        Stack resStk = g.topologicalSort();
        
        String output = "{\"outList\" : [";
        String nameOutput = "";
        while (resStk.empty()==false){
//            System.out.print(hmIntString.get(resStk.pop()) + " ");
            nameOutput+= "\"" + hmIntString.get(resStk.pop()) + "\",";
        }
        nameOutput = nameOutput.substring(0, nameOutput.length()-1);
        output += nameOutput + "]}";
        
        return output;
    }
}

class Graph
{
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency List
 
    //Constructor
    Graph(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }
 
    // Function to add an edge into the graph
    void addEdge(int v,int w) { adj[v].add(w); }
 
    // A recursive function used by topologicalSort
    void topologicalSortUtil(int v, boolean visited[],
                             Stack stack)
    {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;
 
        // Recur for all the vertices adjacent to this
        // vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext())
        {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }
 
        // Push current vertex to stack which stores result
        stack.push(new Integer(v));
    }
 
    // The function to do Topological Sort. It uses
    // recursive topologicalSortUtil()
    Stack topologicalSort()
    {
        Stack stack = new Stack();
 
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;
 
        // Call the recursive helper function to store
        // Topological Sort starting from all vertices
        // one by one
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                topologicalSortUtil(i, visited, stack);
 
        // Print contents of stack
//        while (stack.empty()==false)
//            System.out.print(stack.pop() + " ");
        return stack;
    }
 
  
}
