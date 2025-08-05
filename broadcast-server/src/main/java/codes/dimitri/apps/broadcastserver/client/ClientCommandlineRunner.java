package codes.dimitri.apps.broadcastserver.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("client")
public class ClientCommandlineRunner implements CommandLineRunner {
    private final WebSocketClient client;

    public ClientCommandlineRunner(WebSocketClient client) {
        this.client = client;
    }


    @Override
    public void run(String... args) throws JsonProcessingException {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String message = in.nextLine();
            client.send(message);
        }
    }
}
