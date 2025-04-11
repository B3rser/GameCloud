package com.mycompany.gamecloud;

import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONException;

public class ConnectionManager implements Runnable {

    private final String serverIP;
    private final int serverPort;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String username;

    private Thread senderThread;
    private Thread receiverThread;
    
    private static ConnectionManager connectionManagerInstance;

    private final ConcurrentLinkedQueue<JSONObject> incomingQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<JSONObject> outgoingQueue = new ConcurrentLinkedQueue<>();

    public static ConnectionManager getConnectionManagerInstance() {
        if (connectionManagerInstance == null) {
            throw new IllegalStateException("ConnectionManager has not yet been initialized.");
        }
        return connectionManagerInstance;
    }
    
    public static boolean isInitialized() {
        return connectionManagerInstance != null;
    }
    
    public static void init(String serverIP, int serverPort) {
        if (connectionManagerInstance != null) {
            throw new IllegalStateException("ConnectionManager ya ha sido inicializado.");
        }

        connectionManagerInstance = new ConnectionManager(serverIP, serverPort);
    }
    
    public ConnectionManager(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    private void startCommunication() {
        senderThread = new Thread(this::sender);
        receiverThread = new Thread(this::receiver);
        senderThread.start();
        receiverThread.start();
    }

    private void receiver() {
        while (true) {
            try {
                JSONObject msg = new JSONObject(this.dis.readUTF());
                System.out.println("Received: " + msg);
                incomingQueue.add(msg);
            } catch (JSONException e) {
                System.err.println("Ignoring message because it has invalid JSON: " + e);
            } catch (IOException e) {
                System.err.println("Connection closed.");
                closeConnection();
                break;
            }
        }
    }

    private void sender() {
        while (true) {
            try {
                JSONObject message = outgoingQueue.poll();
                if (message != null) {
                    System.out.println("Sending message\n" + message);
                    dos.writeUTF(message.toString());
                }
            } catch (IOException e) {
                System.err.println("Connection closed.");
                closeConnection();
                break;
            }
        }
    }

    public void queueMessage(JSONObject message) {
        message.put("username", this.username);
        outgoingQueue.add(message);
    }

    public JSONObject pollIncomingMessage() {
        return incomingQueue.poll();
    }

    public void closeConnection() {
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

    public boolean login(String username, String password) {
        try {
            JSONObject loginMsg = new JSONObject();
            loginMsg.put("command", "login");
            loginMsg.put("username", username);
            loginMsg.put("password", password);

            dos.writeUTF(loginMsg.toString());
            JSONObject response = new JSONObject(dis.readUTF());

            if (response.getString("command").equals("ok")) {
                this.username = username;
                System.out.println("Login successful!");
                startCommunication();
                return true;
            } else {
                System.out.println("Login failed: " + response);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void run() {
        try {
            InetAddress ip = InetAddress.getByName(serverIP);
            socket = new Socket(ip, serverPort);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            closeConnection();
        }
    }

    public String getUsername() {
        return username;
    }
}
