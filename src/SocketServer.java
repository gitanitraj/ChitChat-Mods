import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {

    private ServerSocket server;
    private ArrayList<ServerThread> clients = new ArrayList<>();
    private SQLiteLogger logger = new SQLiteLogger();

    public SocketServer() {
        try {
            server = new ServerSocket(1234);
            logger.log("SERVER STARTED on port 1234");
            System.out.println("Server running on port 1234...");

            while (true) {
                Socket clientSocket = server.accept();
                logger.log("CONNECTION from " + clientSocket.getInetAddress());

                ServerThread st = new ServerThread(this, clientSocket);
                addClient(st);
                st.start();
            }

        } catch (Exception e) {
            logger.log("SERVER ERROR: " + e.getMessage());
            System.out.println("Server error: " + e);
        }
    }

    public synchronized void addClient(ServerThread st) {
        clients.add(st);
    }

    public synchronized void removeClient(ServerThread st) {
        clients.remove(st);
    }

    public synchronized void broadcast(String msg) {
        logger.log("BROADCAST: " + msg);
        for (ServerThread st : clients) {
            st.send(msg);
        }
    }

    public static void main(String[] args) {
        new SocketServer();
    }
}

class ServerThread extends Thread {

    private SocketServer server;
    private Socket socket;
    private PrintWriter pw;
    private String name;

    public ServerThread(SocketServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void send(String msg) {
        pw.println(msg);
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            pw = new PrintWriter(socket.getOutputStream(), true);

            name = br.readLine();
            server.broadcast("**[" + name + "] joined the chat**");

            String msg;
            while ((msg = br.readLine()) != null) {
                server.broadcast("[" + name + "]: " + msg);
            }

        } catch (Exception e) {
            server.broadcast("**[" + name + "] left the chat**");
        } finally {
            server.removeClient(this);
            try { socket.close(); } catch (Exception ignored) {}
        }
    }
}
