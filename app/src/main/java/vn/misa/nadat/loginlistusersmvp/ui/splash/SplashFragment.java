package vn.misa.nadat.loginlistusersmvp.ui.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vn.misa.nadat.loginlistusersmvp.R;

/**
 * Là class con của {@link Fragment} hiển thị màn hình khởi động.
 * Activity chứa fragment này phải implement {@link SplashFragment.OnSplashFragmentListener}
 * để xử lý các sự kiện tương tác.
 * Sử dụng phương thức {@link SplashFragment#newInstance} để khởi tạo một thực thể của {@link SplashFragment}.
 *
 * @created_by nadat on 19/03/2019
 */
public class SplashFragment extends Fragment implements ISplashContract.ISplashView {
    private static final String TAG = SplashFragment.class.getSimpleName();
    private SplashPresenter mSplashPresenter;
    private OnSplashFragmentListener mOnSplashFragmentListener;

    /**
     * Là phương thức khởi tạo của {@link SplashFragment}
     */
    public SplashFragment() {
        mSplashPresenter = new SplashPresenter(this);
        mSplashPresenter.checkConnectedInternet();
    }

    /**
     * Là phương thức khởi tạo của {@link SplashFragment}.
     *
     * @return một thực thể của {@link SplashFragment}.
     */
    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnSplashFragmentListener) {
                mOnSplashFragmentListener = (OnSplashFragmentListener) context;
            } else {
                Log.i(TAG, "Must implement OnSplashFragmentListener");
            }
        } catch (RuntimeException e) {
            Log.i(TAG, e.getMessage() + " must implement OnSplashFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            mOnSplashFragmentListener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Có kết nối Internet.
     */
    @Override
    public void onConnectedSuccess() {
        mSplashPresenter.checkVersionDatabase();
    }

    /**
     * Không có kết nối Internet.
     */
    @Override
    public void onConnectedError() {
        Toast.makeText(this.getContext(), "Internet not connecting!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Database ở phiên bản mới nhất.
     * Chuyển sang màn hình login.
     */
    @Override
    public void onNewestDatabase() {
        mOnSplashFragmentListener.moveSplashToLogin();
    }

    /**
     * Database ở phiên bản cũ.
     * Cập nhật database.
     */
    @Override
    public void onDeprecatedDatabase() {
        mSplashPresenter.updateDatabase();
    }

    /**
     * Database cập nhật thành công.
     */
    @Override
    public void showUpdateSuccess() {
        mOnSplashFragmentListener.moveSplashToLogin();
    }

    /**
     * Database cập nhật thất bại.
     */
    @Override
    public void showUpdateError() {
        Toast.makeText(this.getContext(), "Update database error!", Toast.LENGTH_SHORT).show();
    }

    public interface OnSplashFragmentListener {
        void moveSplashToLogin();
    }
}
