package Utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IpChecker {
    public static String getIp() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            return in.readLine();
        } catch (Exception e){
            System.out.println("Could not extract public IP address with exeption: \n" + e);
            return "Not Found";
        }

    }
}