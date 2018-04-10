package Model;

/**
 * Created by him on 3/18/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OriginLoc {

    public OriginLoc(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}