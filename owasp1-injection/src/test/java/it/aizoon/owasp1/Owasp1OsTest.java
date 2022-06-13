package it.aizoon.owasp1;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import it.aizoon.owasp1.os.PingService;

@SpringBootTest
@ActiveProfiles("test")
class Owasp1OsTest {

    @Autowired
    private PingService pingService;


    @Test
    void testPingUnsafe() throws IOException, InterruptedException {
        System.out.println(pingService.monitorExternalSystemUnsafe("www.aizoongroup.com"));

        //todo create an empty file
        //&& separatore
        //copy NUL myfile.txt
    }

    @Test
    void testPingSafe() throws IOException {
        System.out.println(pingService.monitorExternalSystemSafe("www.aizoongroup.com"));
    }
    
}
