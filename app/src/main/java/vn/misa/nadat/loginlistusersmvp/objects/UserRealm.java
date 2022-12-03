package vn.misa.nadat.loginlistusersmvp.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserRealm extends RealmObject {
    @PrimaryKey
    private String username;
    private String password;

    public UserRealm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserRealm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
