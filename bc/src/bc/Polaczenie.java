package bc;

import bc.messages.PeerAddress;
import bc.messages.VerackMessage;
import bc.messages.VersionMessage;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.logging.Logger;

public class Polaczenie {
	static WczytanieDanych w;
	static PeerDiscovery peerDiscovery=new PeerDiscovery();
	String ip,comand;
	public Polaczenie(String comand,String ip) {
		this.ip=ip;
		this.comand=comand;
	}

	public Polaczenie() {
		this.ip="127.0.0.1";
		this.comand="version";
	}

	void pol() throws UnknownHostException, IOException {
		boolean test=true;
		// TODO Auto-generated method stub


		try {

//			List<String>addresses=peerDiscovery.getAddressesListByDNS();


			w=new WczytanieDanych(8333,ip,comand);//dodaæ nazwy obslugiwanych polecen

			if(w.isTCP()) {
				TcpClient c =new TcpClient(w.getIp(),w.getPort());
				System.out.println(c.s.getLocalAddress());
//				Logger.getGlobal().info("zaczynam pisac");

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
					Logger.getGlobal().info("ping dotarl");
				}
				else {
					w.setPing("host is not reachable");
					Logger.getGlobal().info("ping nie dotarl");
				}
				break;
			case "version":
				System.out.println(c.s.getLocalAddress());
				int delay = getDelay();
				VersionMessage versionMessage=new VersionMessage(new PeerAddress(c.s.getInetAddress(),c.port),new PeerAddress(c.s.getLocalAddress(),c.port));
				versionMessage.send(c.oS);
				LocalDateTime start=LocalDateTime.now();

				System.out.println("Odieranie wiadomosci...");


				while (LocalTime.now().getSecond() -start.getSecond()<delay)
				{
					if(LocalTime.now().getSecond() -start.getSecond()<0)
						break;
					int a=c.bR.read();
					if(a!=-1)
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

	private static int getDelay() {
		Scanner scanner=new Scanner(System.in);
		System.out.print("Podaj czas trfania rzadania(sekundy)>");
		return scanner.nextInt();
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
