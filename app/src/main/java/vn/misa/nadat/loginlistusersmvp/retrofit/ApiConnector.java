package vn.misa.nadat.loginlistusersmvp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Là class khởi tạo đối tượng {@link Retrofit} để kết nối api.
 *
 * @created_by nadat on 19/03/2019
 */
public class ApiConnector {
    private static final String BASE_LINK = "https://nadatfresherandroid.000webhostapp.com/";
    private static Retrofit retrofit = null;

    /**
     * Khởi tạo đối tượng {@link Retrofit} được gắn baseUrl là BASE_LINK.
     *
     * @return retrofit
     */
    public static Retrofit getClient() {
        try {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_LINK)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        } catch (Exception e) {
            e.printStackTrace();
            return retrofit;
        }
    }
}
