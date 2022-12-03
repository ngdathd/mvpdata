package vn.misa.nadat.loginlistusersmvp.ui.main;

import java.util.List;

import vn.misa.nadat.loginlistusersmvp.db.DBSQLiteManager;
import vn.misa.nadat.loginlistusersmvp.objects.User;

class MainModel {
    private IMainContract.IMainPresenter mIMainPresenter;

    MainModel(IMainContract.IMainPresenter mIMainPresenter) {
        this.mIMainPresenter = mIMainPresenter;
    }


//    public void updatePasswordDatabase(User user) {
////        if (user == null) {
////            mIMainPresenter.onUpdatePasswordError();
////            return;
////        }
////        try {
////            if (DBSQLiteManager.getInstance().updatePassword(user.getUsername(), ))
////        }catch (Exception e){
////
////        }
////    }
////
////    public void saveUserFileTxt(User user) {
////        if (user == null) {
////            mIMainPresenter.onSaveToFileError();
////            return;
////        }
////    }

    void deleteUserDatabase(User user) {
        if (user == null) {
            mIMainPresenter.onDeleteError();
            return;
        }
        try {
            if (DBSQLiteManager.getInstance().deleteUser(user) != -1) {
                mIMainPresenter.onDeleteSuccess(user);
            } else {
                mIMainPresenter.onDeleteError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadAllUsersInDatabase() {
        try {
            List<User> users = DBSQLiteManager.getInstance().getAllUsers();
            if (users != null) {
                mIMainPresenter.onLoadUsersDatabaseSuccess(users);
            } else {
                mIMainPresenter.onLoadUserDatabaseError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
