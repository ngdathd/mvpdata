package vn.misa.nadat.loginlistusersmvp.ui.splash;

public interface ISplashContract {

    interface ISplashPresenter {
        void onUpdateSuccess();

        void onUpdateError();

        void onConnectedError();
    }

    interface ISplashView {
        void onConnectedSuccess();

        void onConnectedError();

        void onNewestDatabase();

        void onDeprecatedDatabase();

        void showUpdateSuccess();

        void showUpdateError();
    }
}
