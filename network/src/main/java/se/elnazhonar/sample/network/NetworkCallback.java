package se.elnazhonar.sample.network;

public interface NetworkCallback<ResponseData> {

    void onSuccess(ResponseData data);

    void onFailure(NetworkError error);

}
