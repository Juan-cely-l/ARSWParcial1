package arsw.eci.ARSWParcial1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class StockGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockGatewayApplication.class, args);
	}
}