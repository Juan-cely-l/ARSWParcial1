# Uso / Ejecución

Arquitectura 

Una API REST es una interfaz de programación de aplicaciones (API) que se ajusta a los principios de diseño del estilo arquitectónico de transferencia de estado representacional (REST), un estilo utilizado para conectar sistemas de hipermedia distribuidos. Las API REST a veces se denominan API RESTful o API web RESTful.
1. Clona/crea el proyecto con la estructura indicada y pega los archivos.
2. En `application.properties` reemplaza `REEMPLAZAR_POR_TU_API_KEY` por la llave real de Alpha Vantage.
3. `mvn clean package`
4. `mvn spring-boot:run` o ejecutar el jar.
5. Llamadas de ejemplo:

    * Intraday (5min):

      ```
      GET http://localhost:8080/api/stocks/MSFT?interval=intraday&extra=interval=5min&provider=alpha
      ```
      ![img1.png](media%2Fimg1.png)
    * Diario:

      ```
      GET http://localhost:8080/api/stocks/AAPL?interval=daily
      ```
      !![img2.png](media%2Fimg2.png)
      
      

El backend devolverá el JSON crudo de Alpha Vantage 

API Parameters
❚ Required: function

The time series of your choice. In this case, function=TIME_SERIES_INTRADAY

❚ Required: symbol

The name of the equity of your choice. For example: symbol=IBM

❚ Required: interval

Time interval between two consecutive data points in the time series. The following values are supported: 1min, 5min, 15min, 30min, 60min

❚ Optional: adjusted

By default, adjusted=true and the output time series is adjusted by historical split and dividend events. Set adjusted=false to query raw (as-traded) intraday values.

❚ Optional: extended_hours

By default, extended_hours=true and the output time series will include both the regular trading hours and the extended (pre-market and post-market) trading hours (4:00am to 8:00pm Eastern Time for the US market). Set extended_hours=false to query regular trading hours (9:30am to 4:00pm US Eastern Time) only.

❚ Optional: month

By default, this parameter is not set and the API will return intraday data for the most recent days of trading. You can use the month parameter (in YYYY-MM format) to query a specific month in history. For example, month=2009-01. Any month in the last 20+ years since 2000-01 (January 2000) is supported.

❚ Optional: outputsize

By default, outputsize=compact. Strings compact and full are accepted with the following specifications: compact returns only the latest 100 data points in the intraday time series; full returns trailing 30 days of the most recent intraday data if the month parameter (see above) is not specified, or the full intraday data for a specific month in history if the month parameter is specified. The "compact" option is recommended if you would like to reduce the data size of each API call.

❚ Optional: datatype

By default, datatype=json. Strings json and csv are accepted with the following specifications: json returns the intraday time series in JSON format; csv returns the time series as a CSV (comma separated value) file.








