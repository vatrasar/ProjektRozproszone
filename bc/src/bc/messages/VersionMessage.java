package bc.messages;

import bc.serializeUtils.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;



public class VersionMessage extends Message {
    public int clientVersion=106;


    /**
     * adres adresata wiadomości
     */
    public PeerAddress receivingAddr;
    /**
     *  nasz adres
     */

    public PeerAddress sourceAddr;

    public VersionMessage(PeerAddress receivingAddr, PeerAddress sourceAddr) {
        super("version",84);
        this.receivingAddr = receivingAddr;
        this.sourceAddr = sourceAddr;
    }

    public void send(OutputStream outStream)
    {

        long time= Utils.currentTimeSeconds();
        long localServices=0;
        super.send(outStream);//send header
        try {
            Utils.uint32ToByteStreamLE(clientVersion,outStream);
            sendServices(outStream,localServices);
            Utils.uint32ToByteStreamLE(time >> 32, outStream);
            receivingAddr.serialize(outStream);
            sourceAddr.serialize(outStream);
            //nonce(dwa razy bo 64)
            Utils.uint32ToByteStreamLE(0, outStream);
            Utils.uint32ToByteStreamLE(0, outStream);
            //user agnet (dlugosc, jesli 0 to nie ma tego useragenta)
            outStream.write(new byte[]{(byte) 0});

            //wysokosc bloku
            Utils.uint32ToByteStreamLE((long) 1, outStream);
            Logger.getGlobal().info("Wiadomosc Version wysłana");
        } catch (IOException e) {
            Logger.getGlobal().warning("Blad przy widaomości Version");
        }

    }

    private void sendServices(OutputStream outStream, long localServices) throws IOException {
        Utils.uint32ToByteStreamLE(localServices, outStream);
        Utils.uint32ToByteStreamLE(localServices >> 32, outStream);
    }


}


