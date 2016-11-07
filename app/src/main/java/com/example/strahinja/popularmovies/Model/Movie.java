package com.example.strahinja.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int mId;
    private String mTitle;
    private String mImageUrl;
    private String mReleaseDate;
    private double mVoteAverage;
    private String mPlotSynopsis;

    public Movie(int id, String title, String imageUrl, String releaseDate, double voteAverage, String plotSynopsis) {
        mId = id;
        mTitle = title;
        mImageUrl = imageUrl;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mPlotSynopsis = plotSynopsis;
    }

    //region GETTER
    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }
    //endregion

    public Movie(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        this.mTitle = data[0];
        this.mImageUrl = data[1];
        this.mReleaseDate = data[2];
        this.mPlotSynopsis = data[3];
        this.mId = in.readInt();
        this.mVoteAverage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mTitle,
                this.mImageUrl,
                this.mReleaseDate,
                this.mPlotSynopsis});
        dest.writeInt(this.mId);
        dest.writeDouble(this.mVoteAverage);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }
        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };
}
