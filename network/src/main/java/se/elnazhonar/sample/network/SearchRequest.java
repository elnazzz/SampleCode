package se.elnazhonar.sample.network;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.elnazhonar.sample.data.model.ResponseData;

public class SearchRequest {

    private NetworkService mNetworkService;


    public SearchRequest(NetworkService networkService) {
        this.mNetworkService = networkService;
    }

    public void search( String searchTerm, final NetworkCallback<ResponseData> callback){
        Callback<ResponseData> retrofitCallback = new Callback<ResponseData>() {
            @Override
            public void success(ResponseData result, Response response) {
                callback.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {

                String errMsg;
                if (error.getMessage() != null) {
                    errMsg = error.getMessage();
                } else {
                    errMsg = error.toString();
                }

                int status = 0;
                if (error.getResponse() != null) {
                    status = error.getResponse().getStatus();
                }
                callback.onFailure(new NetworkError(errMsg, status, error.getUrl()));
            }
        };

        mNetworkService.search(searchTerm, retrofitCallback);


    }
}
