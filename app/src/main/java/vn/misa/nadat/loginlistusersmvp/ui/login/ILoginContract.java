package vn.misa.nadat.loginlistusersmvp.ui.login;

import android.widget.EditText;

public interface ILoginContract {

    interface ILoginPresenter {
        void onLoginSuccess();

        void onLoginError();

        void onDatabaseEmpty();
    }

    interface ILoginView {
        void showLoginSuccess();

        void showLoginError();

        void showEmptyUser();

        void showEditTextEmptyData(EditText editText);

        void showEditTextError();

        void moveLoginToChangePassword(EditText editText);
    }
}
