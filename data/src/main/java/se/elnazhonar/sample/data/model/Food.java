package se.elnazhonar.sample.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Food implements Parcelable {

    public final static String TAG  = Food.class.getSimpleName();

    @SerializedName("categoryid")
    private long mCategoryId;

    @SerializedName("fiber")
    private double mFiber;

    @SerializedName("headimage")
    private String mHeadImage;

    @SerializedName("pcsingram")
    private double mPcsingram;

    @SerializedName("brand")
    private String mBrand;

    @SerializedName("unsaturatedfat")
    private double mUnsaturatedFat;

    @SerializedName("fat")
    private double mFat;

    @SerializedName("servingcategory")
    private int mServingcategory;

    @SerializedName("typeofmeasurement")
    private int mTypeofmeasurement;

    @SerializedName("protein")
    private double mProtein;

    @SerializedName("defaultserving")
    private int mDefaultServing;

    @SerializedName("mlingram")
    private double mMlingram;

    @SerializedName("id")
    private long mFoodId;

    @SerializedName("saturatedfat")
    private double mSaturatedFat;

    @SerializedName("category")
    private String mCategory;

    @SerializedName("verified")
    private boolean mVerified;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("pcstext")
    private String mPcstext;

    @SerializedName("sodium")
    private double mSodium;

    @SerializedName("carbohydrates")
    private double mCarbohydrates;

    @SerializedName("showonlysametype")
    private int mShowOnlySameType;

    @SerializedName("calories")
    private long mCalories;

    @SerializedName("serving_version")
    private int mServingVersion;

    @SerializedName("sugar")
    private double mSugar;

    @SerializedName("source")
    private int mSource;

    @SerializedName("measurementid")
    private long mMeasuremenTid;

    @SerializedName("cholesterol")
    private double mCholesterol;

    @SerializedName("gramsperserving")
    private double mGramsPerServing;

    @SerializedName("showmeasurement")
    private int mShowMeasurement;

    @SerializedName("potassium")
    private double potassium;


    public Food(se.elnazhonar.sample.data.orm.generated.Food food) {

        this.mCategoryId = food.getCategoryid();
        this.mFiber = food.getFiber();
        this.mHeadImage = food.getHeadimage();
        this.mPcsingram = food.getPcsingram();
        this.mBrand = food.getBrand();
        this.mUnsaturatedFat = food.getUnsaturatedfat();
        this.mFat = food.getFat();
        this.mServingcategory = food.getServingcategory();
        this.mTypeofmeasurement = food.getTypeofmeasurement();
        this.mProtein = food.getProtein();
        this.mDefaultServing = food.getDefaultserving();
        this.mMlingram = food.getMlingram();
        this.mFoodId = food.getFoodid();
        this.mSaturatedFat = food.getSaturatedfat();
        this.mCategory = food.getCategory();
        this.mVerified = food.getVerified();
        this.mTitle = food.getTitle();
        this.mPcstext = food.getPcstext();
        this.mSodium = food.getSodium();
        this.mCarbohydrates = food.getCarbohydrates();
        this.mShowOnlySameType = food.getShowOnlySameType();
        this.mCalories = food.getCalories();
        this.mServingVersion = food.getServing_version();
        this.mSugar = food.getSugar();
        this.mSource = food.getSource();
        this.mMeasuremenTid = food.getMeasurementid();
        this.mCholesterol = food.getCholesterol();
        this.mGramsPerServing = food.getGramsperserving();
        this.mShowMeasurement = food.getShowOnlySameType();
        this.potassium = food.getPotassium();
    }

    public long getCategoryId() {
        return mCategoryId;
    }

    public double getFiber() {
        return mFiber;
    }

    public String getHeadImage() {
        return mHeadImage;
    }

    public double getPcsingram() {
        return mPcsingram;
    }

    public String getBrand() {
        return mBrand;
    }

    public double getUnsaturatedFat() {
        return mUnsaturatedFat;
    }

    public double getFat() {
        return mFat;
    }

    public int getServingcategory() {
        return mServingcategory;
    }

    public int getTypeofmeasurement() {
        return mTypeofmeasurement;
    }

    public double getProtein() {
        return mProtein;
    }

    public int getDefaultServing() {
        return mDefaultServing;
    }

    public double getMlingram() {
        return mMlingram;
    }

    public long getFoodId() {
        return mFoodId;
    }

    public double getSaturatedFat() {
        return mSaturatedFat;
    }

    public String getCategory() {
        return mCategory;
    }

    public boolean isVerified() {
        return mVerified;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPcstext() {
        return mPcstext;
    }

    public double getSodium() {
        return mSodium;
    }

    public double getCarbohydrates() {
        return mCarbohydrates;
    }

    public int getShowOnlySameType() {
        return mShowOnlySameType;
    }

    public long getCalories() {
        return mCalories;
    }

    public int getServingVersion() {
        return mServingVersion;
    }

    public double getSugar() {
        return mSugar;
    }

    public int getSource() {
        return mSource;
    }

    public long getMeasuremenTid() {
        return mMeasuremenTid;
    }

    public double getCholesterol() {
        return mCholesterol;
    }

    public double getGramsPerServing() {
        return mGramsPerServing;
    }

    public int getShowMeasurement() {
        return mShowMeasurement;
    }

    public double getPotassium() {
        return potassium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mCategoryId);
        dest.writeDouble(this.mFiber);
        dest.writeString(this.mHeadImage);
        dest.writeDouble(this.mPcsingram);
        dest.writeString(this.mBrand);
        dest.writeDouble(this.mUnsaturatedFat);
        dest.writeDouble(this.mFat);
        dest.writeInt(this.mServingcategory);
        dest.writeInt(this.mTypeofmeasurement);
        dest.writeDouble(this.mProtein);
        dest.writeInt(this.mDefaultServing);
        dest.writeDouble(this.mMlingram);
        dest.writeLong(this.mFoodId);
        dest.writeDouble(this.mSaturatedFat);
        dest.writeString(this.mCategory);
        dest.writeByte(mVerified ? (byte) 1 : (byte) 0);
        dest.writeString(this.mTitle);
        dest.writeString(this.mPcstext);
        dest.writeDouble(this.mSodium);
        dest.writeDouble(this.mCarbohydrates);
        dest.writeInt(this.mShowOnlySameType);
        dest.writeLong(this.mCalories);
        dest.writeInt(this.mServingVersion);
        dest.writeDouble(this.mSugar);
        dest.writeInt(this.mSource);
        dest.writeLong(this.mMeasuremenTid);
        dest.writeDouble(this.mCholesterol);
        dest.writeDouble(this.mGramsPerServing);
        dest.writeInt(this.mShowMeasurement);
        dest.writeDouble(this.potassium);
    }

    public Food() {
    }

    private Food(Parcel in) {
        this.mCategoryId = in.readLong();
        this.mFiber = in.readDouble();
        this.mHeadImage = in.readString();
        this.mPcsingram = in.readDouble();
        this.mBrand = in.readString();
        this.mUnsaturatedFat = in.readDouble();
        this.mFat = in.readDouble();
        this.mServingcategory = in.readInt();
        this.mTypeofmeasurement = in.readInt();
        this.mProtein = in.readDouble();
        this.mDefaultServing = in.readInt();
        this.mMlingram = in.readDouble();
        this.mFoodId = in.readLong();
        this.mSaturatedFat = in.readDouble();
        this.mCategory = in.readString();
        this.mVerified = in.readByte() != 0;
        this.mTitle = in.readString();
        this.mPcstext = in.readString();
        this.mSodium = in.readDouble();
        this.mCarbohydrates = in.readDouble();
        this.mShowOnlySameType = in.readInt();
        this.mCalories = in.readLong();
        this.mServingVersion = in.readInt();
        this.mSugar = in.readDouble();
        this.mSource = in.readInt();
        this.mMeasuremenTid = in.readLong();
        this.mCholesterol = in.readDouble();
        this.mGramsPerServing = in.readDouble();
        this.mShowMeasurement = in.readInt();
        this.potassium = in.readDouble();
    }

    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }

        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}

