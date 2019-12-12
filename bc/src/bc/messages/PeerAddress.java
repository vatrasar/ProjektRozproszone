package bc.messages;

import bc.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.logging.Logger;


public class PeerAddress
{
    public InetAddress addr;
    private String hostname; // Used for .onion addresses
    public int port;
    private BigInteger services=new BigInteger("0");


    public PeerAddress(InetAddress addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    void serialize(OutputStream outStream)
    {
        try {
            Utils.uint64ToByteStreamLE(services,outStream);
            byte[] ipBytes = addr.getAddress();
           //zmiana ipv4 na ipv6
            if (ipBytes.length == 4) {
                byte[] v6addr = new byte[16];
                System.arraycopy(ipBytes, 0, v6addr, 12, 4);
                v6addr[10] = (byte) 0xFF;
                v6addr[11] = (byte) 0xFF;
                ipBytes = v6addr;
            }
            outStream.write(ipBytes);
            Utils.uint16ToByteStreamBE(port, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}