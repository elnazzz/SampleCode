package se.elnazhonar.sample.network;


public class NetworkError {

    private String mErrorMessage;
    private int mStatusCode;
    private String mUrl;

    public NetworkError(String errorMessage, int statusCode, String mUrl) {
        this.mErrorMessage = errorMessage;
        this.mStatusCode = statusCode;
        this.mUrl = mUrl;
    }


    public String getErrorMessage() {
        return mErrorMessage;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public String getUrl() {
        return mUrl;
    }
}
