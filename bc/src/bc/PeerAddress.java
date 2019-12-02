package bc;

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
            if (ipBytes.length == 4) {
                Logger.getGlobal().warning("Uwaga! adresy ipv6 nie są obsługiwane");
                throw new IOException();
            }
            outStream.write(ipBytes);
            Utils.uint16ToByteStreamBE(port, outStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}