package vn.misa.nadat.loginlistusersmvp.ui.login;

import android.widget.EditText;

import vn.misa.nadat.loginlistusersmvp.utils.Utils;

/**
 * Class truyền tải dữ liệu giữa {@link LoginFragment} và {@link LoginModel}
 *
 * @created_by nadat on 19/03/2019
 */
class LoginPresenter implements ILoginContract.ILoginPresenter {
    private LoginModel mLoginModel;
    private ILoginContract.ILoginView mILoginView;

    /**
     * @param iLoginView biến {@link ILoginContract.ILoginView}
     */
    LoginPresenter(ILoginContract.ILoginView iLoginView) {
        mLoginModel = new LoginModel(this);
        this.mILoginView = iLoginView;
    }

    /**
     * Khi nhận được sự kiện đăng nhập thành công.
     */
    @Override
    public void onLoginSuccess() {
        mILoginView.showLoginSuccess();
    }

    /**
     * Khi nhận được sự kiện đăng nhập thất bại.
     */
    @Override
    public void onLoginError() {
        mILoginView.showLoginError();
    }

    /**
     * Khi nhận được sự kiện người dùng không tồn tại.
     */
    @Override
    public void onDatabaseEmpty() {
        mILoginView.showEmptyUser();
    }

    /**
     * Kiểm tra tính đầy đủ thông tin người dùng điền vào EditText.
     *
     * @param edtUsername EditText tên người dùng.
     * @param edtPassword EditText mật khẩu người dùng.
     */
    void validateCredentials(EditText edtUsername, EditText edtPassword) {
        if (edtUsername == null || edtPassword == null) {
            mILoginView.showEditTextError();
            return;
        }
        try {
            if (Utils.isEmpty(edtUsername)) {
                mILoginView.showEditTextEmptyData(edtUsername);
            } else if (Utils.isEmpty(edtPassword)) {
                mILoginView.showEditTextEmptyData(edtPassword);
            } else {
                mLoginModel.validateLogin(
                        Utils.getTextInEditText(edtUsername),
                        Utils.getTextInEditText(edtPassword));
            }
        } catch (Exception e) {
            e.printStackTrace();
            mILoginView.showEditTextError();
        }
    }

    /**
     * Kiểm tra EditText có dự liệu.
     *
     * @param edtUsername EditText tên người dùng.
     */
    void checkEdtUsername(EditText edtUsername) {
        if (edtUsername == null) {
            mILoginView.showEditTextError();
            return;
        }
        try {
            if (Utils.isEmpty(edtUsername)) {
                mILoginView.showEditTextEmptyData(edtUsername);
            } else {
                mILoginView.moveLoginToChangePassword(edtUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mILoginView.showEditTextError();
        }
    }
}
