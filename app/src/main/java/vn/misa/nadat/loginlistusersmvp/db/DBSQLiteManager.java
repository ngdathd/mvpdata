package vn.misa.nadat.loginlistusersmvp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.misa.nadat.loginlistusersmvp.MvpApp;
import vn.misa.nadat.loginlistusersmvp.objects.User;

/**
 * Class để thao tác với cơ sở dữ liệu.
 *
 * @created_by nadat on 19/03/2019
 */
public class DBSQLiteManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DB_SQLITE_USERS";
    private static final String TABLE_NAME = "user";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static DBSQLiteManager mDBSQLiteManager = new DBSQLiteManager();

    private DBSQLiteManager() {
        super(MvpApp.getInstance(), DATABASE_NAME, null, 1);
    }

    public static DBSQLiteManager getInstance() {
        return mDBSQLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                USERNAME + " TEXT primary key, " +
                PASSWORD + " TEXT)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Thêm một người dùng vào database.
     *
     * @param username tên người dùng
     * @param password mật khẩu
     * @return chỉ số dòng thêm vào. Nếu bằng -1 thì thêm thất bại.
     */
    public long insertUserToDatabase(String username, String password) {
        if (username == null || password == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USERNAME, username);
            values.put(PASSWORD, password);
            long rowId = db.insert(TABLE_NAME, null, values);
            db.close();
            return rowId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Lấy ra một người dùng có tên trùng với username
     *
     * @param username tên người dùng
     * @return một đối tượng người dùng
     */
    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, null, USERNAME + "=?",
                    new String[]{String.valueOf(username)}, null, null, null, null);
            User user = new User();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                user.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                cursor.close();
                db.close();
            } else {
                db.close();
                return null;
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy ra toàn bộ người dùng có trong database
     *
     * @return danh sách toàn bộ người dùng
     */
    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setUsername(cursor.getString(0));
                    user.setPassword(cursor.getString(1));
                    listUsers.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return listUsers;
        } catch (Exception e) {
            e.printStackTrace();
            return listUsers;
        }
    }

    /**
     * Kiểm tra tên người dùng và mật khẩu có khớp nhau
     *
     * @param username tên người dùng
     * @param password mật khẩu
     * @return true nếu tên người dùng và mật khẩu khớp nhau.
     */
    public boolean checkUsernamePassword(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        try {
            User user = getUserByUsername(username);
            return user.getPassword().equals(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Thay đổi mật khẩu của người dùng
     *
     * @param username tên người dùng
     * @param password mật khẩu mới
     * @return dòng bị thay đổi. Nếu bằng -1 thì thay đổi thất bại.
     */
    public int updatePassword(String username, String password) {
        if (username == null || password == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PASSWORD, password);
            return db.update(TABLE_NAME, values, USERNAME + "=?", new String[]{String.valueOf(username)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Xóa một người dùng.
     *
     * @param user đối tượng người dùng cần xóa
     * @return dòng bị xóa. Nếu bằng -1 thì xóa thất bại.
     */
    public int deleteUser(User user) {
        if (user == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int rowsAffected = db.delete(TABLE_NAME, USERNAME + " = ?", new String[]{String.valueOf(user.getUsername())});
            db.close();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Xóa toàn bộ user
     *
     * @return dòng bị xóa. Nếu bằng -1 thì xóa thất bại.
     */
    public int deleteAllUsers() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int rowsAffected = db.delete(TABLE_NAME, null, null);
            db.close();
            return rowsAffected;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
