package org.edu.eci.arep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ReflexCalculator {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(35001);  // Puerto para ReflexCalculator
            System.out.println("ReflexCalculator listo en el puerto 35001...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream();

                String requestLine = in.readLine();
                if (requestLine == null) continue;

                System.out.println("Solicitud recibida: " + requestLine);

                String[] parts = requestLine.split(" ");
                if (parts.length > 1 && parts[1].startsWith("/compreflex=")) {
                    String command = parts[1].substring(parts[1].indexOf("=") + 1);
                    String response = compute(command);

                    String httpResponse = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: application/json\r\n"
                            + "\r\n"
                            + "{\"result\": " + response + "}";

                    out.write(httpResponse.getBytes());
                    out.flush();
                }

                clientSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String compute(String command) {
        try {
            if (command.startsWith("bbl(")) {
                return bubbleSort(command);
            } else {
                return invokeMathFunction(command);
            }
        } catch (Exception e) {
            return "{\"error\": \"Error procesando la operación\"}";
        }
    }

    private static String bubbleSort(String command) {
        String numbersStr = command.substring(4, command.length() - 1);
        double[] numbers = Arrays.stream(numbersStr.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();

        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    double temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        return Arrays.toString(numbers);
    }

    private static String invokeMathFunction(String command) throws Exception {
        int startIndex = command.indexOf("(");
        int endIndex = command.indexOf(")");
        String methodName = command.substring(0, startIndex);
        String paramStr = command.substring(startIndex + 1, endIndex);

        double[] params = Arrays.stream(paramStr.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();

        Class<?> mathClass = Math.class;
        Method method;
        if (params.length == 1) {
            method = mathClass.getMethod(methodName, double.class);
            return String.valueOf(method.invoke(null, params[0]));
        } else if (params.length == 2) {
            method = mathClass.getMethod(methodName, double.class, double.class);
            return String.valueOf(method.invoke(null, params[0], params[1]));
        }

        return "{\"error\": \"Método no válido\"}";
    }
}
