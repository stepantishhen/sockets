/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import java.util.Scanner;
/*    */ 
/*    */ public class Client {
/*    */   private Socket socket;
/*    */   private BufferedReader bufferedReader;
/*    */   
/*    */   public Client(Socket socket, String username) {
/*    */     try {
/* 13 */       this.socket = new Socket("localhost", 8080);
/* 14 */       this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
/* 15 */       this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
/* 16 */       this.username = username;
/* 17 */     } catch (IOException e) {
/* 18 */       closeEverything(socket, this.bufferedReader, this.bufferedWriter);
/*    */     } 
/*    */   }
/*    */   private BufferedWriter bufferedWriter; private String username;
/*    */   public void sendMessage() {
/*    */     try {
/* 24 */       this.bufferedWriter.write(this.username);
/* 25 */       this.bufferedWriter.newLine();
/* 26 */       this.bufferedWriter.flush();
/*    */       
/* 28 */       Scanner scanner = new Scanner(System.in);
/* 29 */       while (this.socket.isConnected()) {
/* 30 */         String messageToSend = scanner.nextLine();
/* 31 */         this.bufferedWriter.write(this.username + ": " + this.username);
/* 32 */         this.bufferedWriter.newLine();
/* 33 */         this.bufferedWriter.flush();
/*    */       } 
/* 35 */     } catch (IOException e) {
/* 36 */       closeEverything(this.socket, this.bufferedReader, this.bufferedWriter);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void listenForMessages() {
/* 41 */     (new Thread(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 46 */             while (Client.this.socket.isConnected()) {
/*    */               try {
/* 48 */                 String msgFromGroupChat = Client.this.bufferedReader.readLine();
/* 49 */                 System.out.println(msgFromGroupChat);
/* 50 */               } catch (IOException e) {
/* 51 */                 Client.this.closeEverything(Client.this.socket, Client.this.bufferedReader, Client.this.bufferedWriter);
/*    */                 break;
/*    */               } 
/*    */             } 
/*    */           }
/* 56 */         })).start();
/*    */   }
/*    */   
/*    */   public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
/* 71 */       System.out.println("Error: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws IOException {
/* 76 */     Scanner scanner = new Scanner(System.in);
/* 77 */     System.out.print("Enter your username: ");
/* 78 */     String username = scanner.nextLine();
/* 79 */     Socket socket = new Socket("localhost", 8080);
/* 80 */     Client client = new Client(socket, username);
/* 81 */     client.listenForMessages();
/* 82 */     client.sendMessage();
/*    */   }
/*    */ }


/* Location:              /home/stepantishhen/IdeaProjects/sockets/out/production/sockets/!/Client.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */