package bc;

import java.io.IOException;
import java.net.UnknownHostException;

public class Polaczenie {
	static WczytanieDanych w;

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		w=new WczytanieDanych();//dodaæ nazwy obslugiwanych polecen
		if(w.isTCP()==true) {
			TcpClient c =new TcpClient(w.getIp(),w.getPort());
			TCP(c);
		}
		else {
			UdpClient c=new UdpClient(w.getIp(), w.getPort());
			UDP(c);
		}
		
	}
	private static void TCP(TcpClient c) throws IOException{
		//operacje
		if(w.getPolecenie()=="ping") {
			boolean r=c.ping();
			//wiadomosc do konsoli
			if(r==true) {
				w.setPing("host is reachable");
			}
			else {
				w.setPing("host is not reachable");
			}
		}
		//c.send("a");
		try {
			c.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void UDP(UdpClient c) throws IOException{
		//operacje
		if(w.getPolecenie()=="ping") {
			boolean r=c.ping();
			//wiadomosc do konsoli
			if(r==true) {
				w.setPing("host is reachable");
			}
			else {
				w.setPing("host is not reachable");
			}
		}
		//c.send("a");
		c.close();
	}
}
