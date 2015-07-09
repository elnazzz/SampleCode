package se.elnazhonar.sample.data.model;

import com.google.gson.annotations.SerializedName;


public class ResponseData {

    @SerializedName("meta")
    private MetaData mMetaData;

    @SerializedName("response")
    private SearchResults mResponse;


    public SearchResults getSearchResponse() {
        return mResponse;
    }
}
