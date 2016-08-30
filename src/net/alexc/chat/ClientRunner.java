package net.alexc.chat;

import net.alexc.chat.client.ChatClient;
import net.alexc.chat.utils.Utils;

import java.io.IOException;

public class ClientRunner {
  public static void main(String[] args) {
    try {
      int port = Utils.readInteger("Enter the port for the client: ") ;
      ChatClient client = new ChatClient(port);
      client.start();
    } catch (IOException e) {
      System.out.println("Couldn't initialize the client socket.");
      e.printStackTrace();
    }
  }
}
