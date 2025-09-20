package arsw.eci.ARSWParcial1;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class StockGatewayServiceImpl implements StockGatewayService {


    private final Map<String, StockProvider> providers = new ConcurrentHashMap<>();

    public StockGatewayServiceImpl(Collection<StockProvider> providerBeans) {
        // Spring inyecta todas las implementaciones de StockProvider
        for (StockProvider p : providerBeans) {
            providers.put(p.providerId(), p);
        }
    }

    @Override
    public String getStock(String providerId, String symbol, String interval, String extraParams) throws Exception {
        String id = providerId == null || providerId.isBlank() ? "alpha" : providerId;
        StockProvider provider = providers.get(id);
        if (provider == null) {
            throw new IllegalArgumentException("Provider not found: " + id);
        }
        return provider.fetch(symbol, interval, extraParams);
    }
}