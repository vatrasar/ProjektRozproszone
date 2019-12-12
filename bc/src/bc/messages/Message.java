package bc.messages;

import java.io.OutputStream;

public interface Message {
    void send(OutputStream outStream);
}

