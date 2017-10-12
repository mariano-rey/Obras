package lds.obras;

import com.loopj.android.http.AsyncHttpClient;

public class CommunicationClient extends AsyncHttpClient {

    private static final int TIMEOUT = 20 * 10000;
    private static String URL = "http://192.168.1.47:51412/api/Service/";

    public static final String OBRAS = URL + "Obras";
    public static final String CAPATACES = URL + "Capataces";
    public static final String TRABAJADORES = URL + "Trabajadores";


    public CommunicationClient() {
        super();

        setTimeout(TIMEOUT);
        setConnectTimeout(TIMEOUT);
        setResponseTimeout(TIMEOUT);
        setMaxRetriesAndTimeout(10, TIMEOUT);

        setLoggingEnabled(true);
    }
}
