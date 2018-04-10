package Model;

/**
 * Created by him on 3/18/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthProblem {

    public HealthProblem(String problemName) {
        this.problemName = problemName;
    }

    @SerializedName("problem_name")
    @Expose
    private String problemName;

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

}