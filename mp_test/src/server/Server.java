package server;

import java.net.InetAddress;
import java.net.ServerSocket;

public class Server {

	static String ip;
	static int port;
	static ServerSocket server;

	public static void main(String[] args) {
		if (args.length == 1) {
			ip = args[0].split(":")[0];
			port = Integer.parseInt(args[0].split(":")[1]);
		} else {
			ip = "localhost";
			port = 11125;
		}
		try {
			new Server();
		} catch (Exception e) {
			System.err.println("Something went wrong in server execution!");
			e.printStackTrace();
		}
	}

	static Connection[] connections = new Connection[8];
	static String[] last = new String[256];
	static String[] first = new String[256];

	public Server() throws Exception {
		System.out.println("Starting server @ " + ip + ":" + port);
		server = new ServerSocket(port, 4, InetAddress.getByName(ip));

		first[0] = "00000000";
		last[0] = "00000000";

		new ThreadListen(server).start();
		run();
	}

	public void run() throws Exception {
		while (true) {
			// Receive
			for (int i = 0; i < connections.length; i++) {
				try {
					Connection connection = connections[i];
					if (connection == null)
						continue;

					byte packetID;
					while ((packetID = connection.dis.readByte()) != -1) {
						if (packetID == 1) {
							connection.x = connection.dis.readFloat();
							connection.y = connection.dis.readFloat();
							connection.z = connection.dis.readFloat();
						}
					}
				} catch (Exception e) {
					System.out.println("Disconnecting " + connections[i].ip + " (" + connections[i].username + ")");
					disconnect(i);
					continue;
				}
			}

			// Send
			for (int i = 0; i < connections.length; i++) {
				try {
					Connection connection = connections[i];
					if (connection == null)
						continue;

					for (int j = 0; j < connections.length; j++) {
						Connection target = connections[j];
						if (target == null || target == connection)
							continue;

						connection.dos.writeByte(1);
						connection.dos.flush();
						connection.dos.writeInt(j);
						connection.dos.writeFloat(target.x);
						connection.dos.writeFloat(target.y);
						connection.dos.writeFloat(target.z);
						connection.dos.flush();
					}

					if (last[0] != first[0]) {
						// Send manifest
						connection.dos.writeByte(10);
						connection.dos.flush();
						connection.dos.writeUTF(first[0]);
						connection.dos.flush();
						System.out.println("sending player manifest: " + first[0]);

						// Send player usernames
						for (int j = 0; j < connections.length; j++) {
							Connection target = connections[j];
							if (target == null)
								continue;
							connection.dos.writeByte(11);
							connection.dos.flush();
							connection.dos.writeInt(j);
							connection.dos.writeUTF(target.username);
							connection.dos.flush();
							System.out.println("sending player username " + target.username + " to " + connection.username);
						}
					}

					connection.dos.writeByte(-1);
					connection.dos.flush();
				} catch (Exception e) {
					System.out.println("Disconnecting " + connections[i].ip + " (" + connections[i].username + ")");
					disconnect(i);
					continue;
				}

				// Change last sent data
				if (last[0] != first[0])
					last[0] = first[0];
			}
			Thread.sleep(10);
		}
	}

	public static void disconnect(int i) {
		connections[i] = null;
		char[] chars = Server.last[0].toCharArray();
		chars[i] = '0';
		Server.first[0] = new String(chars);
	}

	public static int getAvailableSlot() {
		for (int i = 0; i < connections.length; i++) {
			if (connections[i] == null)
				return i;
		}
		return -1;
	}
}
