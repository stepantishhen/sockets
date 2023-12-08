/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class ClientHandler implements Runnable {
/*  6 */   public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
/*    */   private Socket socket;
/*    */   private BufferedReader bufferedReader;
/*    */   private BufferedWriter bufferedWriter;
/*    */   private String clientUsername;
/*    */   
/*    */   public ClientHandler(Socket socket) {
/*    */     try {
/* 14 */       this.socket = socket;
/* 15 */       this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
/* 16 */       this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
/* 17 */       this.clientUsername = this.bufferedReader.readLine();
/* 18 */       clientHandlers.add(this);
/* 19 */       broadcastMessage("Server: " + this.clientUsername + " has joined the chat.");
/* 20 */     } catch (IOException e) {
/* 21 */       closeEverything(socket, this.bufferedReader, this.bufferedWriter);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 28 */     while (this.socket.isConnected()) {
/*    */       try {
/* 30 */         String messageFromClient = this.bufferedReader.readLine();
/* 31 */         broadcastMessage(messageFromClient);
/* 32 */       } catch (IOException e) {
/* 33 */         closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void broadcastMessage(String messageToSend) {
/* 40 */     for (ClientHandler clientHandler : clientHandlers) {
/*    */       try {
/* 42 */         if (!clientHandler.clientUsername.equals(this.clientUsername)) {
/* 43 */           clientHandler.bufferedWriter.write(messageToSend);
/* 44 */           clientHandler.bufferedWriter.newLine();
/* 45 */           clientHandler.bufferedWriter.flush();
/*    */         } 
/* 47 */       } catch (IOException e) {
/* 48 */         closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void removeClientHandler() {
/* 54 */     clientHandlers.remove(this);
/* 55 */     broadcastMessage("Server: " + this.clientUsername + " has left the chat.");
/*    */   }
/*    */   
/*    */   public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
/* 59 */     removeClientHandler();
/*    */     try {
/* 61 */       if (bufferedReader != null) {
/* 62 */         bufferedReader.close();
/*    */       }
/* 64 */       if (bufferedWriter != null) {
/* 65 */         bufferedWriter.close();
/*    */       }
/* 67 */       if (socket != null) {
/* 68 */         socket.close();
/*    */       }
/* 70 */     } catch (IOException e) {
/* 71 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/stepantishhen/IdeaProjects/sockets/out/production/sockets/!/ClientHandler.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */