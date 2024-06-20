import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MacAddressFetcher {

    public static void main(String[] args) {
        System.out.println("NOTE THAT THE FIRST ARGUMENT REQUIRES IP ADDRESS");
        try {
            // Replace this IP address with the IP address of the client device
            String macAddress = getMacAddress(args[0]); // args[0] is the client IP

            if (macAddress != null) {
                System.out.println("MAC Address: " + macAddress);
            } else {
                System.out.println("MAC Address not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getMacAddress(String ip) {
        String macAddress = null;
        try {
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "./get_mac_address.sh" + ip);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the output is the MAC address
                macAddress = line.trim();
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error running shell script, exit code: " + exitCode);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddress;
    }
}
