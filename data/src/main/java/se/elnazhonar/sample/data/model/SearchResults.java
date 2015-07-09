package se.elnazhonar.sample.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SearchResults extends ResponseData {

    @SerializedName("list")
    private List<Food> mSearchResultList;

    public List<Food> getSearchResultList() {
        return mSearchResultList;
    }
}
