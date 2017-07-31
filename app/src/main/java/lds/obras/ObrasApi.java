package lds.obras;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ObrasApi {

    @POST("Obras")
    Observable<JsonObject> obras();

    @POST("Capataces")
    Observable<JsonObject> capataces();

    @POST("Trabajadores")
    Observable<JsonObject> trabajadores();

}
