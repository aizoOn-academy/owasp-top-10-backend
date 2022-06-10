package it.aizoon.owasp1.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import org.springframework.stereotype.Service;

@Service
public class PingService {

  public int monitorExternalSystemUnsafe(String host) throws IOException, InterruptedException {
    Runtime rt = Runtime.getRuntime();
    Process pr = rt.exec("cmd.exe /c ping " + host);


    try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
      String s = null;
      while ((s = stdInput.readLine()) != null) {
        System.out.println(s);
      }
    }

    try (BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()))) {
      String s = null;
      while ((s = stdError.readLine()) != null) {
        System.out.println(s);
      }
    }


    return pr.waitFor();
  }

  public boolean monitorExternalSystemSafe(String host) throws IOException {
    InetAddress hostInet = InetAddress.getByName(host);
    return hostInet.isReachable(5000);
  }

}
