package Graphics;

import Network.Client;

public class InitGraphics{

	public InitGraphics(){
		//graphics stuff
		System.out.println("starting Graphics");
		System.out.println("starting Network");
		initNetworking();
	}

	private void initNetworking() {
		new Thread(){
			Client client = new Client();
			public void run(){
				try{
					while(true) client.networking();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}

}
