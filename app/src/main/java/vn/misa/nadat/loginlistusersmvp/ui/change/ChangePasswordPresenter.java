package vn.misa.nadat.loginlistusersmvp.ui.change;

public class ChangePasswordPresenter implements IChangePasswordPresenter {
    private ChangePasswordModel mChangePasswordModel;
    private IChangePasswordView mIChangePasswordView;

    public ChangePasswordPresenter(IChangePasswordView iChangePasswordView) {
        this.mIChangePasswordView = iChangePasswordView;
        mChangePasswordModel = new ChangePasswordModel(this);
    }

    public void changePasswordUser(String username, String password, String confirm) {
        mChangePasswordModel.updatePasswordUser(username, password, confirm);
    }

    @Override
    public void onSuccessResetPassword(String username, String password) {
        mIChangePasswordView.onChangeSuccess(username, password);
    }

    @Override
    public void onConfigResetPassword() {
        mIChangePasswordView.onConfigPassword();
    }

    @Override
    public void onErrorResetPassword() {
        mIChangePasswordView.onErrorChange();
    }
}
