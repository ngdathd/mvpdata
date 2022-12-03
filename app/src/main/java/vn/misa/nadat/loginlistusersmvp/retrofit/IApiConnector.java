package vn.misa.nadat.loginlistusersmvp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.misa.nadat.loginlistusersmvp.objects.User;
import vn.misa.nadat.loginlistusersmvp.objects.Version;

/**
 * @created_by nadat at 19/03/2019
 */
public interface IApiConnector {
    @GET("nadat/users.php")
    Call<List<User>> getUsers();

    @GET("nadat/version.php")
    Call<List<Version>> getVersion();
}
