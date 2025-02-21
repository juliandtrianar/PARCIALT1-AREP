package org.edu.eci.arep;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServiceFacade {

    public static void main(String[] args) throws IOException {
    
        ServerSocket serverSocket = new ServerSocket(8080);  
        System.out.println("Fachada de servicios en ejecución en el puerto 8080...");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
   
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String inputLine;
        
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("/computar")) {
                String command = inputLine.split("comando=")[1].split(" ")[0];
                
                try {
                    ReflexCalculator calculator = new ReflexCalculator();
                    double result = calculator.compute(command);
                    
                    
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: application/json");
                    out.println();
                    out.println("{\"result\": " + result + "}");
                } catch (Exception e) {
                    out.println("HTTP/1.1 500 Internal Server Error");
                    out.println("Content-Type: application/json");
                    out.println();
                    out.println("{\"error\": \"Error en el cálculo\"}");
                }
            }
        }
        
        
        in.close();
        out.close();
        clientSocket.close();
    }
}
