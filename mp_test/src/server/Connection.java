package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Connection {

    Socket socket;
    String username, ip;
    DataOutputStream dos;
    DataInputStream dis;
    float x = 0, y = 0, z = 0;

    public Connection(Socket socket, String name, DataOutputStream dos, DataInputStream dis) {
	this.socket = socket;
	this.username = name;
	this.ip = socket.getInetAddress().getHostAddress();
	this.dis = dis;
	this.dos = dos;
	System.out.println("Created connection with " + ip + " as " + username);
    }
}
