package vn.misa.nadat.loginlistusersmvp.ui.change;

import vn.misa.nadat.loginlistusersmvp.db.DBSQLiteManager;

public class ChangePasswordModel {
    private IChangePasswordPresenter mIChangePasswordPresenter;

    public ChangePasswordModel(IChangePasswordPresenter iChangePasswordPresenter) {
        this.mIChangePasswordPresenter = iChangePasswordPresenter;
    }

    public void updatePasswordUser(String username, String password, String confirm) {
        if (!password.equals(confirm)) {
            mIChangePasswordPresenter.onConfigResetPassword();
        } else if (DBSQLiteManager.getInstance().updatePassword(username, password) != 0) {
            mIChangePasswordPresenter.onSuccessResetPassword(username, password);
        } else {
            mIChangePasswordPresenter.onErrorResetPassword();
        }
    }
}
