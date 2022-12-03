package vn.misa.nadat.loginlistusersmvp.ui.register;

import android.widget.EditText;

public interface IRegisterContract {
    interface IRegisterPresenter {
        void onSuccess(String username, String password);

        void onError();

        void onDuplicate();

        void onConfig();
//        ham ma view goi presenter se viet o day
        void validateCredentials();
    }

    interface IRegisterView {
//        ham ma presenter goi sang view
        void onRegisterSuccess(String username, String password);

        void onRegisterError();

        void onDuplicateUser();

        void onConfigPassword();

        void showEditTextError();

//        khong truyen view vao presenter
        void showEditTextEmptyData(EditText editText);
    }
}
