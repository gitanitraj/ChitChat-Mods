===============================
GROUP CHAT APPLICATION (LAN)
===============================

This is a simple multi-client chat application using Java sockets.
Clients connect to a central server and exchange messages in real time.

--------------------------------
1. REQUIREMENTS
--------------------------------
- Java 8 or higher
- SQLite JDBC driver (sqlite-jdbc.jar)
- LAN network (or localhost)

--------------------------------
2. PROJECT STRUCTURE
--------------------------------
/ChatApp
   /data
       chat.db
   /src
       SocketServer.java
       SocketClient.java
       SQLiteLogger.java
       README.txt

--------------------------------
3. HOW TO RUN THE SERVER
--------------------------------
1. Open a terminal in /ChatApp/src
2. Compile:
   javac *.java
3. Run:
   java SocketServer

The server will:
- Listen on port 1234
- Create /data/chat.db
- Log all connections and messages

--------------------------------
4. HOW TO RUN A CLIENT
--------------------------------
1. Open another terminal
2. Run:
   java SocketClient
3. Enter the server's IP address
4. Enter your nickname

--------------------------------
5. HOW TO CONNECT OVER LAN
--------------------------------
1. Find server machine IP:
   Windows: ipconfig
   Mac/Linux: ifconfig

2. Clients enter that IP when prompted.

--------------------------------
6. VIEWING LOGS
--------------------------------
Open the database:
   sqlite3 data/chat.db

View logs:
   SELECT * FROM logs;

--------------------------------
7. TROUBLESHOOTING
--------------------------------
- If clients cannot connect:
  - Ensure server firewall allows port 1234
  - Ensure all machines are on same LAN
  - Ensure correct IP is used

- If chat.db is not created:
  - Ensure /data folder exists
  - Ensure write permissions
