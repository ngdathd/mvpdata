package vn.misa.nadat.loginlistusersmvp.ui.change;

public interface IChangePasswordPresenter {
    void onSuccessResetPassword(String username, String password);

    void onConfigResetPassword();

    void onErrorResetPassword();
}
