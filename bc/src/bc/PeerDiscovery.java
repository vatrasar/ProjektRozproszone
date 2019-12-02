package bc;

import java.net.InetAddress;
import java.util.List;
import java.lang.String;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeerDiscovery {
    public PeerDiscovery() {
    }

//    public List<String> getAddressesList() {
//
//    }
//
//
//    public List<String> getAddressesListByDNS() {
//
//    }


    public String getAddressFromUser() {


        String adress=null;
        while (true) {
            System.out.print("Prosze wprowadzić adress ip w formacie xxx.xxx.xxx\n" +
                    "przykład: 93.103.2\n" +
                    ">");
            Scanner scanner=new Scanner(System.in);
            adress=scanner.nextLine();
            if(validateIp(adress))
            {
                break;
            }
            else
            {

                System.out.println("Nieprawidłowy adres ip, prosze spróbować ponownie\n");
            }

        }
        return adress;
    }

    private boolean validateIp(String adress) {
        String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return adress.matches(ipPattern);
    }

}
