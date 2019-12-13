package bc.messages;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class VerackMessage extends Message {

    public VerackMessage() {
        super("verack", 0);
    }

    @Override
    public void send(OutputStream outStream) {
        super.send(outStream);

//        byte[]verackByte="verack".getBytes();
//        try {
//            outStream.write(verackByte);
//        } catch (IOException e) {
//            Logger.getGlobal().warning("Bład z wyslaniem wiadomosci verack");
//
//
//        }
        Logger.getGlobal().info("Wiadomść verack wysłana");
    }
}
