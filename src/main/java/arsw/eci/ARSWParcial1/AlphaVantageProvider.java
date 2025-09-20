package arsw.eci.ARSWParcial1;

import arsw.eci.ARSWParcial1.CacheService;
import arsw.eci.ARSWParcial1.StockProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AlphaVantageProvider implements StockProvider {

  private final RestTemplate restTemplate;
  private final String apiKey;
  private final CacheService cacheService;

  public AlphaVantageProvider(RestTemplate restTemplate,
                              @Value("${alpha.api.key}") String apiKey,
                              CacheService cacheService) {
    this.restTemplate = restTemplate;
    this.apiKey = apiKey;
    this.cacheService = cacheService;
  }

  @Override
  public String providerId() {
    return "alpha";
  }

  /**
   * extraParams: for intraday can pass "interval=5min&outputsize=full"
   */
  @Override
  public String fetch(String symbol, String interval, String extraParams) throws Exception {
    // build cache key
    String key = String.format("%s:%s:%s:%s", providerId(), symbol, interval, extraParams == null ? "" : extraParams);
    String cached = cacheService.get(key);
    if (cached != null) {
      return cached;
    }

    UriComponentsBuilder b;
    switch (interval.toLowerCase()) {
      case "intraday":
        // expects extraParams contains interval=5min or 15min etc
        b = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                .queryParam("function", "TIME_SERIES_INTRADAY")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey);
        if (extraParams != null && !extraParams.isBlank()) {
          // extraParams like "interval=5min&outputsize=compact"
          String[] parts = extraParams.split("&");
          for (String p : parts) {
            String[] kv = p.split("=", 2);
            if (kv.length == 2) b.queryParam(kv[0], kv[1]);
          }
        }
        break;
      case "daily":
        b = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                .queryParam("function", "TIME_SERIES_DAILY")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey);
        break;
      case "weekly":
        b = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                .queryParam("function", "TIME_SERIES_WEEKLY")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey);
        break;
      case "monthly":
        b = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co/query")
                .queryParam("function", "TIME_SERIES_MONTHLY")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey);
        break;
      default:
        throw new IllegalArgumentException("Interval not supported: " + interval);
    }

    String url = b.build().toUriString();
    ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
    if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
      throw new RuntimeException("Failed to fetch from AlphaVantage: " + resp.getStatusCode());
    }

    String body = resp.getBody();
    cacheService.put(key, body);
    return body;
  }
}