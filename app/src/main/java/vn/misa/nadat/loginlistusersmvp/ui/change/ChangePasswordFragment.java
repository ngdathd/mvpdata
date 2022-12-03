package vn.misa.nadat.loginlistusersmvp.ui.change;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import vn.misa.nadat.loginlistusersmvp.R;
import vn.misa.nadat.loginlistusersmvp.utils.Utils;
/**
 * Là class con của {@link Fragment} hiển thị màn hình đăng nhập người dùng.
 * Activity chứa fragment này phải implement {@link ChangePasswordFragment.OnChangePasswordFragmentListener}
 * để xử lý các sự kiện tương tác.
 * Sử dụng phương thức {@link ChangePasswordFragment#newInstance} để khởi tạo một thực thể của {@link ChangePasswordFragment}.
 *
 * @created_by nadat on 19/03/2019
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener, IChangePasswordView {
    private static final String TAG = ChangePasswordFragment.class.getSimpleName();
    private static final String USERNAME = "USERNAME";
    private static final String PREVIOUS_FRAGMENT = "PREVIOUS_FRAGMENT";

    private OnChangePasswordFragmentListener mListener;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtConfirm;

    private ChangePasswordPresenter mResetPasswordPresenter;
    private String mUsername;
    private String mPreviousFragment;

    public ChangePasswordFragment() {
        mResetPasswordPresenter = new ChangePasswordPresenter(this);
    }

    public static ChangePasswordFragment newInstance(String username, String previousFragment) {
        ChangePasswordFragment resetPasswordFragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(PREVIOUS_FRAGMENT, previousFragment);
        resetPasswordFragment.setArguments(args);
        return resetPasswordFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(USERNAME);
            mPreviousFragment = getArguments().getString(PREVIOUS_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mEdtUsername = view.findViewById(R.id.edt_username);
        mEdtPassword = view.findViewById(R.id.edt_password);
        mEdtConfirm = view.findViewById(R.id.edt_confirm);

        mEdtUsername.setText(mUsername);

        AppCompatButton btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnChangePasswordFragmentListener) {
                mListener = (OnChangePasswordFragmentListener) context;
            } else {
                Log.i(TAG, "Must implement OnLoginFragmentListener");
            }
        } catch (RuntimeException e) {
            Log.i(TAG, e.getMessage() + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_password: {
                if (Utils.isEmpty(mEdtUsername) && Utils.isEmpty(mEdtPassword) && Utils.isEmpty(mEdtConfirm)) {
                    mResetPasswordPresenter.changePasswordUser(
                            Objects.requireNonNull(mEdtUsername.getText()).toString(),
                            Objects.requireNonNull(mEdtPassword.getText()).toString(),
                            Objects.requireNonNull(mEdtConfirm.getText()).toString());
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onChangeSuccess(String username, String password) {
        if (username == null || password == null || mListener == null) {
            return;
        }
        try {
            switch (mPreviousFragment) {
                case "LoginFragment": {
                    mListener.moveChangePasswordToLogin(username, password);
                    break;
                }
                case "MainFragment": {
                    mListener.moveChangePasswordToMain(username, password);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigPassword() {
        Toast.makeText(getContext(), "Confirm invalidate!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorChange() {
        Toast.makeText(getContext(), "Error reset password!", Toast.LENGTH_SHORT).show();
    }

    public interface OnChangePasswordFragmentListener {
        void moveChangePasswordToLogin(String username, String password);

        void moveChangePasswordToMain(String username, String password);
    }
}
