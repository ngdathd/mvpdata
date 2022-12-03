package vn.misa.nadat.loginlistusersmvp.ui.main;

import java.util.List;

import vn.misa.nadat.loginlistusersmvp.objects.User;

class MainPresenter implements IMainContract.IMainPresenter {
    private MainModel mMainModel;
    private IMainContract.IMainView mIMainView;

    MainPresenter(IMainContract.IMainView mIMainView) {
        this.mIMainView = mIMainView;
        mMainModel = new MainModel(this);
    }

    void deleteUser(User user) {
        mMainModel.deleteUserDatabase(user);
    }

    @Override
    public void onDeleteSuccess(User user) {
        mIMainView.onDeleteUserSuccess(user);
    }

    @Override
    public void onDeleteError() {
        mIMainView.onDeleteUserError();
    }

    @Override
    public void onUpdatePasswordError() {

    }

    @Override
    public void onSaveToFileError() {

    }

    @Override
    public void onLoadUsersDatabaseSuccess(List<User> users) {
        mIMainView.showListUsers(users);
    }

    @Override
    public void onLoadUserDatabaseError() {
        mIMainView.showListUsersError();
    }

    @Override
    public void loadAllUsers() {
//        List<User> u = mMainModel.loadAllUsersInDatabase();
//       mIMainView.showListUsers(u);
        mMainModel.loadAllUsersInDatabase();
    }
}
