package Model;

/**
 * Created by him on 3/19/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignIn {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("gen_id")
    @Expose
    private String genId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("origin_loc")
    @Expose
    private OriginLoc originLoc;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("health_problems")
    @Expose
    private List<HealthProblem> healthProblems = null;
    @SerializedName("addedby")
    @Expose
    private List<Object> addedby = null;
    @SerializedName("friends")
    @Expose
    private List<Friend> friends = null;
    @SerializedName("token")
    @Expose
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenId() {
        return genId;
    }

    public void setGenId(String genId) {
        this.genId = genId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public OriginLoc getOriginLoc() {
        return originLoc;
    }

    public void setOriginLoc(OriginLoc originLoc) {
        this.originLoc = originLoc;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<HealthProblem> getHealthProblems() {
        return healthProblems;
    }

    public void setHealthProblems(List<HealthProblem> healthProblems) {
        this.healthProblems = healthProblems;
    }

    public List<Object> getAddedby() {
        return addedby;
    }

    public void setAddedby(List<Object> addedby) {
        this.addedby = addedby;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}


