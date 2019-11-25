package bc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

//zród³o: https://www.baeldung.com/udp-in-java
public class UdpClient {
    private DatagramSocket socket;
    private InetAddress address;
    String ip;
    int port;
 
    private byte[] buf;
 
    public UdpClient(String ip, int port) throws SocketException, UnknownHostException {
    	if(ip.length()==16)
			this.ip = ip;//zostanie zabezpiecozne przy wczytywaniu
        socket = new DatagramSocket();
        address = InetAddress.getByName(ip);
        this.port=port;
    }
 
    public String send(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet  = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
          packet.getData(), 0, packet.getLength());
        return received;
    }
 
    public boolean ping () throws IOException {
		  InetAddress i =  	socket.getInetAddress();
		  if (i.isReachable(2000)) 
		      return true; 
		    else
		    	  return false; 
	  }
    public void close() {
        socket.close();
    }
}
