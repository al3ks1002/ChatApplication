package net.alexc.chat.messages;

public class Message {
  private final String messageString;

  public Message(String messageString) {
    this.messageString = messageString;
  }

  public boolean isNameMessage() {
    return messageString.startsWith("/name ");
  }

  public boolean isExitMessage() {
    return messageString.equals("/exit");
  }

  public boolean isGreetingMessage(String username) {
    return messageString.equals("Welcome, " + username + ".");
  }

  public String getUserName() {
    return messageString.replaceFirst("/name ", "");
  }

  public String getMessageString() {
    return messageString;
  }
}
