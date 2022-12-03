package vn.misa.nadat.loginlistusersmvp.ui.change;

public interface IChangePasswordView {
    void onChangeSuccess(String username, String password);

    void onConfigPassword();

    void onErrorChange();
}
