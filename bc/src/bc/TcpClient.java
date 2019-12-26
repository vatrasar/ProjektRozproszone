package bc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

//zr�d�o: https://systembash.com/a-simple-java-tcp-server-and-tcp-client/

public class TcpClient {
	Socket s;
	String ip;
	InetSocketAddress ip2;
	int port;
	 DataOutputStream oS; // wyslanie na server
	 BufferedReader  bR; //odczyt z servera
	 public TcpClient(String ip, int port) throws UnknownHostException, IOException {
			super();
			this.port = port;
			s = new Socket(ip, port);//6789 domyslnie
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
	 

private InetAddress getIP() throws SocketException {
    Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
    NetworkInterface ni;
    while (nis.hasMoreElements()) {
        ni = nis.nextElement();
        if (!ni.isLoopback() && ni.isUp()) {
            for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                if (ia.getAddress().getAddress().length == 4) {
                    return ia.getAddress();
                }
            }
        }
    }
    return null;
}
}