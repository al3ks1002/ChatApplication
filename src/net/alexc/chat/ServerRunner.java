package net.alexc.chat;

import net.alexc.chat.server.ChatServer;
import net.alexc.chat.utils.Utils;

import java.io.IOException;

public class ServerRunner {
  public static void main(String[] args) throws IOException {
    try {
      int port = Utils.readInteger("Enter the port for the server: ");
      ChatServer server = new ChatServer(port);
      System.out.printf("Server started on port %d!", port);
      server.start();
    } catch (IOException e) {
      System.out.println("Could not initialize server.");
      e.printStackTrace();
    }
  }
}
