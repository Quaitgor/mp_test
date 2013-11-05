package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ThreadListen extends Thread {

	ServerSocket server;
	
	public ThreadListen(ServerSocket server){
		this.server = server;
	}
	
	public void run(){
		while(true){
			try{

				Socket socket = server.accept();
				System.out.println("received connection from "+socket.getInetAddress().getHostAddress());
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				String username = dis.readUTF();
				
				int slot = Server.getAvailableSlot();
				if(slot == -1 || username.contains(" ") || username.equalsIgnoreCase("NULL")){
					try{
						dos.writeBoolean(false);
						dos.flush();
						dos.close();
						dis.close();
						socket.close();
						continue;
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				dos.writeBoolean(true);
				dos.flush();
				
				String map = "";
				Scanner scanner = new Scanner(new File("cola.map"));
				while(scanner.hasNextLine()) map += scanner.nextLine()+"\n";
				scanner.close();
				
				//Send map
				dos.writeUTF("cola.map");
				dos.flush();
				dos.writeUTF(map);
				dos.flush();
				
				//Send floor texture id
				dos.writeInt(2);
				dos.flush();
				
				//Send Texture Manifest
				String textureManifest = "";
				scanner = new Scanner(new File("cola.tex"));
				while(scanner.hasNextLine()) textureManifest += scanner.nextLine()+"\n";
				scanner.close();
				dos.writeUTF("cola.tex");
				dos.flush();
				dos.writeUTF(textureManifest);
				dos.flush();
				
				while(dis.readBoolean() == true){
					String fname = "./res/"+dis.readUTF().replace("..", "");
					ImageIcon icon = new ImageIcon(ImageIO.read(new File(fname)));
					ObjectOutputStream oos = new ObjectOutputStream(dos);
					oos.writeObject(icon);
					oos.flush();
				}
				
				char[] chars = Server.last[0].toCharArray();
				chars[slot] = '1';
				Server.first[0] = new String(chars);
				
				Server.connections[slot] = new Connection(socket,username,dos,dis);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
