package arsw.eci.ARSWParcial1;


public interface StockProvider {

    String fetch(String symbol, String interval, String extraParams) throws Exception;


    String providerId();
}