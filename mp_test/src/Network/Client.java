package Network;

public class Client implements Runnable{


	public void networking(){
		System.out.println("net net net");
	}

	public void run() {
		while(true){
			try {
				networking();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
