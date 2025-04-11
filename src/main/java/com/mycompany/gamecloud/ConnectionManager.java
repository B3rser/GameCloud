package com.mycompany.gamecloud;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionManager implements Runnable {

    private static Socket socket;
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static Thread senderThread;
    private static Thread receiverThread;
    private static Scanner scanner;
    private static String username;

    public static void receiver() {
        while (true) {
            try {
                JSONObject received = new JSONObject(dis.readUTF());
                System.out.println("Received:\n" + received);
            } catch (JSONException e) {
                System.err.println("Ignoring message because it has invalid JSON: " + e);
            } catch (IOException e) {
                System.err.println("Connection closed.");
                closeConnection();
                break;
            }
        }
    }

    public static void sender() {
        while (true) {
            try {
                System.out.print("Enter message: ");
                String input = scanner.nextLine();
                JSONObject message = new JSONObject();
                message.put("username", username);
                message.put("message", input);
                message.put("command", "message");

                System.out.println("Sending message\n" + message);
                dos.writeUTF(message.toString());
            } catch (IOException e) {
                System.err.println("Connection closed.");
                closeConnection();
                break;
            }
        }
        scanner.close();
    }

    public static void closeConnection() {
        try {
            if (dis != null) {
                dis.close();
            }
            if (dos != null) {
                dos.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e);
        }

        if (senderThread != null) {
            senderThread.interrupt();
        }
        if (receiverThread != null) {
            receiverThread.interrupt();
        }
    }

    public static void login() {
        try {
            System.out.print("Enter user: ");
            username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            JSONObject message = new JSONObject();
            message.put("username", username);
            message.put("password", password);
            message.put("command", "login");

            System.out.println("Sending message\n" + message);
            dos.writeUTF(message.toString());

            JSONObject received = new JSONObject(dis.readUTF());
            System.out.println("Received:\n" + received);

        } catch (IOException e) {
            System.err.println("Connection closed.");
            closeConnection();
        }
    }

    @Override
    public void run() {
        try {
            scanner = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("10.103.150.207");
            socket = new Socket(ip, 2555);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            login();

            senderThread = new Thread(ConnectionManager::sender);
            receiverThread = new Thread(ConnectionManager::receiver);

            senderThread.start();
            receiverThread.start();
        } catch (IOException e) {
            System.err.println("Client Error: " + e);
        }
    }
}
