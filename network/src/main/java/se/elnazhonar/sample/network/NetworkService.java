package se.elnazhonar.sample.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import se.elnazhonar.sample.data.model.ResponseData;


public interface NetworkService {

    @GET("/search/query?type=food")
    void search(@Query("search") String searchTerm, Callback<ResponseData> callback);

}
