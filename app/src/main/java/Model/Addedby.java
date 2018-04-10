package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by him on 3/31/2018.
 */
public class Addedby {

    @SerializedName("f_id")
    @Expose
    private String fId;
    @SerializedName("addedby_memberName")
    @Expose
    private String addedbyMemberName;

    public String getFId() {
        return fId;
    }

    public void setFId(String fId) {
        this.fId = fId;
    }

    public String getAddedbyMemberName() {
        return addedbyMemberName;
    }

    public void setAddedbyMemberName(String addedbyMemberName) {
        this.addedbyMemberName = addedbyMemberName;
    }
}
