package Model;

/**
 * Created by him on 3/19/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("f_id")
    @Expose
    private String fId;
    @SerializedName("f_memberName")
    @Expose
    private String fMemberName;

    public String getFId() {
        return fId;
    }

    public void setFId(String fId) {
        this.fId = fId;
    }

    public String getFMemberName() {
        return fMemberName;
    }

    public void setFMemberName(String fMemberName) {
        this.fMemberName = fMemberName;
    }

}