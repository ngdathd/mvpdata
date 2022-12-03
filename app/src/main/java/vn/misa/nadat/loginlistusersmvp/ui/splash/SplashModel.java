package vn.misa.nadat.loginlistusersmvp.ui.splash;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.misa.nadat.loginlistusersmvp.db.DBSQLiteManager;
import vn.misa.nadat.loginlistusersmvp.objects.User;
import vn.misa.nadat.loginlistusersmvp.retrofit.ApiConnector;
import vn.misa.nadat.loginlistusersmvp.retrofit.IApiConnector;

/**
 * Là class xử lý việc kiểm tra database.
 *
 * @created_by nadat on 19/03/2019
 */
class SplashModel {
    private ISplashContract.ISplashPresenter mISplashPresenter;
    private IApiConnector mIApiConnector;

    /**
     * Phương thức khởi tạo {@link SplashModel} có chứa tham số là {@link ISplashContract.ISplashPresenter}.
     *
     * @param iSplashPresenter biến {@link ISplashContract.ISplashPresenter}
     */
    SplashModel(ISplashContract.ISplashPresenter iSplashPresenter) {
        mISplashPresenter = iSplashPresenter;
        mIApiConnector = ApiConnector.getClient().create(IApiConnector.class);
    }

    /**
     * Cập nhật database.
     */
    void updateDatabase() {
        try {
            if (DBSQLiteManager.getInstance().deleteAllUsers() == -1) {
                mISplashPresenter.onUpdateError();
                return;
            }
            mIApiConnector.getUsers().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            for (int i = 0; i < response.body().size(); i++) {
                                DBSQLiteManager.getInstance().insertUserToDatabase(
                                        response.body().get(i).getUsername(),
                                        response.body().get(i).getPassword());
                            }
                            mISplashPresenter.onUpdateSuccess();
                        } else {
                            mISplashPresenter.onConnectedError();
                        }
                    } else {
                        mISplashPresenter.onConnectedError();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                    mISplashPresenter.onUpdateError();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
