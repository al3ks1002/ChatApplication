package net.alexc.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
  public static final int PORT = 4444;

  private final ServerSocket serverSocket;
  private final Map<String, PrintWriter> users;

  public ChatServer() throws IOException {
    serverSocket = new ServerSocket(PORT);
    users = new ConcurrentHashMap<>();
  }

  public void start() {
    while (true) {
      try {
        Connection connection = new Connection(serverSocket.accept(), this);
        Thread connectionThread = new Thread(connection);
        connectionThread.start();
      } catch (IOException e) {
        System.out.println("Could not open a connection.");
        e.printStackTrace();
      }
    }
  }

  boolean addUser(String username, PrintWriter writer) {
    if (users.containsKey(username)) {
      return false;
    }
    users.put(username, writer);
    return true;
  }

  void removeUser(String username) {
    users.remove(username);
  }

  void distributeMessage(String username, String message) {
    for (PrintWriter writer : users.values()) {
      writer.println(currentDate() + " - " + username + ": " + message);
    }
  }

  private String currentDate() {
    return new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(
        Calendar.getInstance().getTime());
  }
}
