package vn.misa.nadat.loginlistusersmvp.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.misa.nadat.loginlistusersmvp.MvpApp;
import vn.misa.nadat.loginlistusersmvp.objects.Version;
import vn.misa.nadat.loginlistusersmvp.retrofit.ApiConnector;
import vn.misa.nadat.loginlistusersmvp.retrofit.IApiConnector;
import vn.misa.nadat.loginlistusersmvp.utils.Utils;

/**
 * Class truyền tải dữ liệu giữa {@link SplashFragment} và {@link SplashModel}
 *
 * @created_by nadat on 19/03/2019
 */
class SplashPresenter implements ISplashContract.ISplashPresenter {
    private static final String VERSION_DATABASE = "VERSION_DATABASE";
    private static final String VERSION = "VERSION";
    private SharedPreferences mSharedPreferences;
    private SplashModel mSplashModel;
    private ISplashContract.ISplashView mISplashView;
    private IApiConnector mIApiConnector;

    /**
     * @param iSplashView biến {@link ISplashContract.ISplashView}
     */
    SplashPresenter(ISplashContract.ISplashView iSplashView) {
        mISplashView = iSplashView;
        mSplashModel = new SplashModel(this);
//        DE O MOT CLASS RIENG DE TAI SU DUNG
        mSharedPreferences = MvpApp.getInstance().getSharedPreferences(VERSION_DATABASE, Context.MODE_PRIVATE);
        mIApiConnector = ApiConnector.getClient().create(IApiConnector.class);
    }

    /**
     * Kiểm tra kết nối mạng.
     */
    void checkConnectedInternet() {
        try {
            if (Utils.isOnline()) {
                mISplashView.onConnectedSuccess();
            } else {
                mISplashView.onConnectedError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra version của database.
     * So sánh version của database trên server với trên thiết bị.
     */
    void checkVersionDatabase() {
        try {
            mIApiConnector.getVersion().enqueue(new Callback<List<Version>>() {
                @Override
                public void onResponse(@NonNull Call<List<Version>> call, @NonNull Response<List<Version>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().get(0).getVersion().equals(mSharedPreferences.getString(VERSION, "0.0.0"))) {
                                mISplashView.onNewestDatabase();
                            } else {
                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                editor.putString(VERSION, response.body().get(0).getVersion());
                                editor.apply();
                                mISplashView.onDeprecatedDatabase();
                            }
                        } else {
                            mISplashView.onConnectedError();
                        }
                    } else {
                        mISplashView.onConnectedError();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Version>> call, @NonNull Throwable t) {
                    mISplashView.onConnectedError();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật database.
     */
    void updateDatabase() {
        mSplashModel.updateDatabase();
    }

    /**
     * Khi nhận được sự kiện cập nhật database thành công.
     */
    @Override
    public void onUpdateSuccess() {
        mISplashView.showUpdateSuccess();
    }

    /**
     * Khi nhận được sự kiện cập nhật database thất bại.
     */
    @Override
    public void onUpdateError() {
        mISplashView.showUpdateError();
    }

    /**
     * Khi nhận được sự kiện không có kết nối.
     */
    @Override
    public void onConnectedError() {
        mISplashView.onConnectedError();
    }
}
