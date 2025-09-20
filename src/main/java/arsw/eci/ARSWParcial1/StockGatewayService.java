package arsw.eci.ARSWParcial1;

public interface StockGatewayService {
    /**
     * Obtiene datos para un símbolo y un intervalo.
     * @param providerId id del proveedor ("alpha" por defecto)
     * @param symbol símbolo (ej MSFT)
     * @param interval intraday/daily/weekly/monthly
     * @param extraParams parámetros opcionales
     * @return raw JSON String
     * @throws Exception
     */
    String getStock(String providerId, String symbol, String interval, String extraParams) throws Exception;
}