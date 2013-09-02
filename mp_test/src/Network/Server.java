package Network;

public class Server implements Runnable{


	public void networking(){
		System.out.println("serv serv serv");
	}

	public void run() {
		while(true){
			try {
				networking();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
