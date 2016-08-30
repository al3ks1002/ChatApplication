package net.alexc.chat.client;

import net.alexc.chat.messages.Message;
import net.alexc.chat.messages.MessageHandler;
import net.alexc.chat.server.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
  private final Socket clientSocket;

  public ChatClient(int port) throws IOException {
    clientSocket = new Socket("localhost", port);
  }

  private void registerName(BufferedReader reader, PrintWriter writer, BufferedReader stdIn)
      throws IOException {
    boolean registeredName = false;
    while (!registeredName) {
      System.out.printf("Enter your username: ");
      String name = stdIn.readLine();
      writer.println("/name " + name);
      Message messageFromServer = new Message(reader.readLine());
      System.out.println(messageFromServer.getMessageString());
      if (messageFromServer.isGreetingMessage(name)) {
        registeredName = true;
      }
    }
  }

  public void start() {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
         PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
         BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
    ) {
      registerName(reader, writer, stdIn);

      MessageHandler messageHandler = new MessageHandler(reader);
      Thread messageHandlerThread = new Thread(messageHandler);
      messageHandlerThread.start();

      while (true) {
        Message messageFromStdIn = new Message(stdIn.readLine());
        writer.println(messageFromStdIn.getMessageString());
        if (messageFromStdIn.isExitMessage()) {
          messageHandler.terminate();
          messageHandlerThread.join();
          break;
        }
      }
    } catch (IOException e) {
      System.out.println("Could not initialize client reader/writer.");
      e.printStackTrace();
    } catch (InterruptedException e) {
      System.out.println("Interrupted thread in client.");
      e.printStackTrace();
    }
  }
}
