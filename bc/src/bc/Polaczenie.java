package bc;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;
import java.net.InetAddress;
public class Polaczenie {
	static WczytanieDanych w;
	static PeerDiscovery peerDiscovery=new PeerDiscovery();
	public static void main(String[] args) throws UnknownHostException, IOException {
		boolean test=true;
		// TODO Auto-generated method stub


		try {

			List<String>addresses=peerDiscovery.getAddressesListByDNS();

			String polecenie="version";
			w=new WczytanieDanych(8333,addresses.get(0),polecenie);//doda� nazwy obslugiwanych polecen

			if(w.isTCP()) {
				TcpClient c =new TcpClient(w.getIp(),w.getPort());
				TCP(c);
			}
			else {
				UdpClient c=new UdpClient(w.getIp(), w.getPort());
				UDP(c);
			}
		}
		catch (Exception e) {
			if (test) {

				Logger.getGlobal().info("Test zakonczony");
			} else {
				Logger.getGlobal().warning("Nieznany blad!");
			}
		}

	}
	private static void TCP(TcpClient c) throws IOException{
		//operacje

		switch (w.getPolecenie())
		{
			case "ping":
				boolean r=c.ping();
				//wiadomosc do konsoli
				if(r) {
					w.setPing("host is reachable");
				}
				else {
					w.setPing("host is not reachable");
				}
				break;
			case "version":
				VersionMessage versionMessage=new VersionMessage(new PeerAddress(c.s.getInetAddress(),c.port),new PeerAddress(InetAddress.getLocalHost(),c.port));
				versionMessage.send(c.oS);
				while (true)
				{
					int a=c.bR.read();
					System.out.println(a);
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
			if(r) {
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
