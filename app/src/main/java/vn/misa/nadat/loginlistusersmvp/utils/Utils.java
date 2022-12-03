package vn.misa.nadat.loginlistusersmvp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.EditText;

import vn.misa.nadat.loginlistusersmvp.MvpApp;

/**
 * Class chứa các phương thức static dùng trong toàn bộ project.
 *
 * @created_by nadat on 19/03/2019
 */
public class Utils {

    /**
     * Kiểm tra EditText có rỗng hay không ?
     *
     * @param editText EditText cần kiểm tra.
     * @return true nếu EditText rỗng.
     */
    public static boolean isEmpty(EditText editText) {
        if (editText == null) {
            return false;
        }
        try {
            return editText.getText().toString().trim().length() <= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy dữ liêu trong EditText.
     *
     * @param editText EditText cần lấy dữ liệu.
     * @return String có trong EditText.
     */
    public static String getTextInEditText(EditText editText) {
        if (editText == null) {
            return null;
        }
        try {
            return editText.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Kiểm tra kết nối mạng Internet.
     *
     * @return tình trạng kết nối mạng, true nếu có kết nối.
     */
    public static boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) MvpApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
