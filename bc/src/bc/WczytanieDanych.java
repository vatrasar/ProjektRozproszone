package bc;

//dane wczytywane od uzytkownika, zostanie dodane pó¿niej

public class WczytanieDanych {
	int port;
	boolean TCP;
	String ip;
	String polecenie;
	String ping;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isTCP() {
		return TCP;
	}
	public void setTCP(boolean tCP) {
		TCP = tCP;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
	public String getPing() {
		return ping;
	}
	public void setPing(String ping) {
		this.ping = ping;
	}
	public String getPolecenie() {
		return polecenie;
	}
	public void setPolecenie(String polecenie) {
		//wrzucajcie tu w  komentarzu jakie poelcenia ma przyjomwaæ
		/*
		 * ping, ?, ...
		 */
		this.polecenie = polecenie;
		
	}
	
	
}
