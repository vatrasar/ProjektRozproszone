package bc;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.*;
import java.lang.String;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.util.stream.Collectors;

public class PeerDiscovery {

    public PeerDiscovery() {
    }





    public List<String> getAddressesListByDNS()throws UnknownHostException {
        List<String>dnsList=new ArrayList<String>(Arrays.asList(
                "seed.bitcoin.sipa.be",
                "dnsseed.bluematt.me",
                "dnsseed.bitcoin.dashjr.org",
                "seed.bitcoinstats.com",
                "seed.bitcoin.jonasschnelli.ch",
                "seed.btc.petertodd.org")
        );
        int time=LocalTime.now().getSecond();
        InetAddress[]adressArray=null;


        for(String adress:dnsList)
        {

            try {
                adressArray=InetAddress.getAllByName(adress);
                break;

            } catch (UnknownHostException e) {

                if (LocalTime.now().getSecond()-time>60) {

                    throw new UnknownHostException();
                }
            }
        };
        return Arrays.asList(adressArray).stream().map(InetAddress::getHostAddress).collect(Collectors.toList());

    }


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
    void printIpFromDns()throws UnknownHostException
    {
        System.out.print(getAddressesListByDNS());
    }

}
