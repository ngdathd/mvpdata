package vn.misa.nadat.loginlistusersmvp.ui.register;

import android.widget.EditText;

import vn.misa.nadat.loginlistusersmvp.db.DBSQLiteManager;
import vn.misa.nadat.loginlistusersmvp.utils.Utils;

/**
 * Class truyền tải dữ liệu {@link RegisterFragment}
 *
 * @created_by nadat on 19/03/2019
 */
public class RegisterPresenter implements IRegisterContract.IRegisterPresenter {
    private IRegisterContract.IRegisterView mIRegisterView;

    /**
     * @param iRegisterView biến {@link IRegisterContract.IRegisterView}
     */
    RegisterPresenter(IRegisterContract.IRegisterView iRegisterView) {
        this.mIRegisterView = iRegisterView;
    }

    /**
     * Kiểm tra các EditText có rỗng hay không.
     *
     */
    @Override
    public void validateCredentials() {
//        if (edtUsername == null || edtPassword == null || edtConfirm == null) {
//            mIRegisterView.showEditTextError();
//            return;
//        }
//        try {
//            if (Utils.isEmpty(edtUsername)) {
//                mIRegisterView.showEditTextEmptyData(edtUsername);
//            } else if (Utils.isEmpty(edtPassword)) {
//                mIRegisterView.showEditTextEmptyData(edtPassword);
//            } else {
////                mRegisterModel.createNewUser(
////                        Utils.getTextInEditText(edtUsername),
////                        Utils.getTextInEditText(edtPassword),
////                        Utils.getTextInEditText(edtConfirm));
//                String username = Utils.getTextInEditText(edtUsername);
//                String password = Utils.getTextInEditText(edtPassword);
//                String confirm = Utils.getTextInEditText(edtConfirm);

//                try {
//                    if (DBSQLiteManager.getInstance().getUserByUsername(username) != null) {
//                        mIRegisterView.onDuplicateUser();
//                    } else {
//                        if (password.equals(confirm)) {
//                            if (DBSQLiteManager.getInstance().insertUserToDatabase(username
//                                    , password) != -1) {
//                                mIRegisterView.onRegisterSuccess(username, password);
//                            } else {
//                                mIRegisterView.onRegisterError();
//                            }
//                        } else {
//                            mIRegisterView.onConfigPassword();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    mIRegisterView.onRegisterError();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            mIRegisterView.showEditTextError();
//        }
    }

    /**
     * Nhận khi đăng ký thành công.
     *
     * @param username tên người dùng
     * @param password mật khẩu
     */
    @Override
    public void onSuccess(String username, String password) {
        if (username == null || password == null) {
            return;
        }
        try {
            mIRegisterView.onRegisterSuccess(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nhận khi đăng ký thất bại.
     */
    @Override
    public void onError() {
        try {
            mIRegisterView.onRegisterError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nhận khi đăng ký trùng tên với người dùng khác.
     */
    @Override
    public void onDuplicate() {
        try {
            mIRegisterView.onDuplicateUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nhận khi mật khẩu và xác nhận mật khẩu không trùng nhau.
     */
    @Override
    public void onConfig() {
        try {
            mIRegisterView.onConfigPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
