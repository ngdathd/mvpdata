package vn.misa.nadat.loginlistusersmvp.ui.login;

import vn.misa.nadat.loginlistusersmvp.db.DBSQLiteManager;

/**
 * Là class xử lý việc kiểm tra đăng nhập.
 *
 * @created_by nadat on 19/03/2019
 */
class LoginModel {
    private ILoginContract.ILoginPresenter mILoginPresenter;

    /**
     * Phương thức khởi tạo {@link LoginModel} có chứa tham số là {@link ILoginContract.ILoginPresenter}.
     *
     * @param iLoginPresenter biến {@link ILoginContract.ILoginPresenter}
     */
    LoginModel(ILoginContract.ILoginPresenter iLoginPresenter) {
        this.mILoginPresenter = iLoginPresenter;
    }

    /**
     * Kiểm tra đăng nhập với tên người dùng và mật khẩu.
     *
     * @param username tên người dùng.
     * @param password mật khẩu người dùng.
     */
    void validateLogin(String username, String password) {
        if (username == null || password == null) {
            mILoginPresenter.onLoginError();
            return;
        }
        try {
            if (DBSQLiteManager.getInstance().getUserByUsername(username) == null) {
                mILoginPresenter.onDatabaseEmpty();
            } else if (DBSQLiteManager.getInstance().checkUsernamePassword(username, password)) {
                mILoginPresenter.onLoginSuccess();
            } else {
                mILoginPresenter.onLoginError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mILoginPresenter.onLoginError();
        }
    }
}
