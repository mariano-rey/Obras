package lds.obras;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by jlionti on 14/7/2017.
 */

public interface ObrasApi {

    @POST("Obras")
    Observable<JsonObject> obras();

    @POST("Capataces")
    Observable<JsonObject> capataces();

}