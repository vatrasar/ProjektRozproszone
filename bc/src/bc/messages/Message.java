package bc.messages;

import bc.serializeUtils.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Message {
    String command;
    int payloadSize;

    public Message(String command, int payloadSize) {
        this.command = command;
        this.payloadSize = payloadSize;
    }


    /**
     * Uwaga! na początku każdej wiadomości send musi być wykonana super.send() wtedy doda sie nagłowek
     * @param outStream
     */
    void send(OutputStream outStream)
    {
        sendHeader(outStream);
    }
    private void sendHeader(OutputStream outStream)
    {
        int startMessage=0xf9beb4d9;
        try {

            Utils.uint32ToByteStreamLE(startMessage,outStream);
            byte[]commandBytes=new byte[12];
            int i=0;
            for(byte byt:command.getBytes())
            {
                commandBytes[i]=byt;
                i++;
            }
            outStream.write(commandBytes);
            Utils.uint32ToByteStreamLE(payloadSize,outStream);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

