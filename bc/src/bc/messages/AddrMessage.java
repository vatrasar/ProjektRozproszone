package bc.messages;

import bc.serializeUtils.VarInt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

public class AddrMessage implements Message {

    List<PeerAddress> adrList;

    public AddrMessage(List<PeerAddress>adresses) {
        adrList=adresses;

    }

    @Override
    public void send(OutputStream outStream) {
        try {

            outStream.write(new VarInt(Math.max(adrList.size(),1000)).encode());
        } catch (IOException e) {

            Logger.getGlobal().warning("Blad przy widaomości addr");
        }
        //maxymalnie mozna przekazać 1000 adresów
        for(int i=0;i<Math.min(adrList.size(),1000);i++)
        {
            var adr=adrList.get(i);
            adr.serialize(outStream);
        }

    }
}
