package arsw.eci.ARSWParcial1;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockGatewayService gatewayService;

    public StockController(StockGatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping(value = "/{symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStock(
            @PathVariable String symbol,
            @RequestParam(name = "interval", defaultValue = "daily") String interval,
            @RequestParam(name = "extra", required = false) String extra,
            @RequestParam(name = "provider", required = false) String provider
    ) throws Exception {
        String result = gatewayService.getStock(provider, symbol, interval, extra);
        return ResponseEntity.ok(result);
    }
}