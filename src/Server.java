/*    */ import java.io.IOException;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class Server {
/*    */   private ServerSocket serverSocket;
/*    */   
/*    */   public Server(ServerSocket serverSocket) {
/*  9 */     this.serverSocket = serverSocket;
/*    */   }
/*    */   
/*    */   public void startServer() {
/*    */     try {
/* 14 */       while (!this.serverSocket.isClosed()) {
/* 15 */         Socket socket = this.serverSocket.accept();
/* 16 */         System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
/* 17 */         ClientHandler clientHandler = new ClientHandler(socket);
/*    */         
/* 19 */         Thread thread = new Thread(clientHandler);
/* 20 */         thread.start();
/*    */       } 
/* 22 */     } catch (IOException e) {
/* 23 */       System.out.println("Error: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws IOException {
/* 28 */     ServerSocket serverSocket = new ServerSocket(8080);
/* 29 */     Server server = new Server(serverSocket);
/* 30 */     server.startServer();
/*    */   }
/*    */ }


/* Location:              /home/stepantishhen/IdeaProjects/sockets/out/production/sockets/!/Server.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */