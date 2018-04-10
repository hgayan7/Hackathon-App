package Model;

/**
 * Created by him on 3/18/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUp {

    public SignUp(String genId, String username, String fullName, String password, String email,
                  Integer age, String bloodGroup,
                  List<HealthProblem> healthProblems,int pincode) {
        this.genId = genId;
        this.username = username;
        this.fullName = fullName;
        this.password=password;
        this.email = email;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.healthProblems = healthProblems;
        this.pincode=pincode;
    }

    public SignUp(String genId){
        this.genId=genId;
    }


    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    @SerializedName("pincode")
    @Expose
    private int pincode;

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
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("age")
    @Expose
    private Integer age;

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
    private List<Object> friends = null;
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

    public List<Object> getFriends() {
        return friends;
    }

    public void setFriends(List<Object> friends) {
        this.friends = friends;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}


