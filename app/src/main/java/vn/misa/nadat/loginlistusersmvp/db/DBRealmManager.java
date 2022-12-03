package vn.misa.nadat.loginlistusersmvp.db;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import vn.misa.nadat.loginlistusersmvp.objects.User;
import vn.misa.nadat.loginlistusersmvp.objects.UserRealm;

public class DBRealmManager {
//    nhuoc diem kho viet cac cau lenh dai
    private static final DBRealmManager mDBRealmManager = new DBRealmManager();

    private DBRealmManager() {
    }

    public static DBRealmManager getInstance() {
        return mDBRealmManager;
    }

    public boolean insertUserToDatabase(String username, String password) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UserRealm userRealm = realm.createObject(UserRealm.class, UUID.randomUUID().toString());
        userRealm.setUsername(username);
        userRealm.setPassword(password);
        realm.commitTransaction();
        return true;
    }

    public User getUserByUsername(String username) {
        return null;
    }

    public List<User> getAllUsers() {
        Realm realm = Realm.getDefaultInstance();
        List<User> userRealms = realm.where(User.class).findAll();
        realm.close();
        return userRealms;
    }

    public boolean checkUsernamePassword(String username, String password) {
        return false;
    }

    private void updatePassword(final String username, final String password) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                UserRealm userRealm = realm.createObject(UserRealm.class);
                userRealm.setUsername(username);
                userRealm.setPassword(password);
                realm.copyToRealmOrUpdate(userRealm);
            }
        });
    }

    private void deleteUser(User userRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        userRealm.deleteFromRealm();
        realm.commitTransaction();
    }
}
