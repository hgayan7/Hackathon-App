package Rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by him on 3/30/2018.
 */

public class ApiClient {

    public static final String BASE_URL="http://10.0.2.2:3000/api/";
    public static final String TEST_URL="http://192.168.43.104:3000/api/";

    public static Retrofit retrofit=null;
    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(TEST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}