package Rest;

import Model.SignIn;
import Model.SignUp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by him on 3/30/2018.
 */

public interface ApiInterface {

    //sign up
    @Headers("Content-Type: application/json")
    @POST("auth/signup")
    Call<SignUp> postSignUp(@Body SignUp body);


    //sign in
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("auth/signin")
    Call<SignIn> postSignIn(@Field("username") String username, @Field("password") String password);

    //add friends

    @Headers("Content-Type: application/json")
    @PUT("add/{_id}")
    Call<SignUp> putFriend(@Header("Authorization") String auth,
                           @Body SignUp signUp,@Field("_id")String id);

}
