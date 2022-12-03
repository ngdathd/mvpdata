package vn.misa.nadat.loginlistusersmvp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Class này sẽ được chạy cùng với ứng dụng.
 *
 * @created_by nadat on 19/03/2019
 */
public class MvpApp extends Application {
    private static MvpApp mInstance;

    /**
     * Lấy ra MvpApp
     *
     * @return đối tượng MvpApp.
     */
    public static MvpApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("DB_REALM_USERS").build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
