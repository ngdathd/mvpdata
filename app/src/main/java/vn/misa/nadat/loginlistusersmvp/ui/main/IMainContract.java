package vn.misa.nadat.loginlistusersmvp.ui.main;

import java.util.List;

import vn.misa.nadat.loginlistusersmvp.objects.User;

public interface IMainContract {
    interface IMainPresenter {
        void onDeleteSuccess(User user);

        void onDeleteError();

        void onUpdatePasswordError();

        void onSaveToFileError();

        void onLoadUsersDatabaseSuccess(List<User> users);

        void onLoadUserDatabaseError();

        void loadAllUsers();
    }

    interface IMainView {
        void onDeleteUserSuccess(User user);

        void onDeleteUserError();

        void showListUsers(List<User> users);

        void showListUsersError();
    }
}
