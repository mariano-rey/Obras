package lds.obras;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ObrasServicio {

    private static final int timeout = 60;

    private static final String URL = "http://192.168.1.47:51412/api/Service/";
    //    private static final String URL = "http://192.168.116.28:55262/api/Servicio/";
    private static ObrasServicio sObrasServicio;
    private final ObrasApi obrasApi;


    private ObrasServicio() {

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .client(client)
                .addCallAdapterFactory(rxAdapter)
                .build();

        obrasApi = retrofit.create(ObrasApi.class);
    }

    public static ObrasServicio getInstance() {
        if (sObrasServicio == null) {
            sObrasServicio = new ObrasServicio();
        }

        return sObrasServicio;
    }

    public ObrasApi getObrasServicio() {
        return obrasApi;
    }

}
