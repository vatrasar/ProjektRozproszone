package bc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//zród³o: https://systembash.com/a-simple-java-tcp-server-and-tcp-client/

public class TcpClient {
	Socket s;
	String ip;
	int port;
	 DataOutputStream oS; // wyslanie na server
	 BufferedReader  bR; //odczyt z servera
	public TcpClient(String ip, int port) throws UnknownHostException, IOException {
		super();
		if(ip.length()==16)
			this.ip = ip;//zostanie zabezpiecozne przy wczytywaniu
		this.port = port;
		s=new Socket(ip,port);
		oS=new DataOutputStream(s.getOutputStream());
		bR=new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	  public String send(String msg) throws IOException {
		  oS.writeBytes(msg + 'n');
		  return bR.readLine();
	  }
	  public boolean ping () throws IOException {
		  InetAddress i = s.getInetAddress();
		  if (i.isReachable(2000)) 
		      return true; 
		    else
		    	  return false; 
	  }
	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	 
	public void close() throws IOException {
        s.close();
    }
	 
}
