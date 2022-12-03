package vn.misa.nadat.loginlistusersmvp.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import vn.misa.nadat.loginlistusersmvp.R;
import vn.misa.nadat.loginlistusersmvp.utils.Utils;

/**
 * Là class con của {@link Fragment} hiển thị màn hình đăng nhập người dùng.
 * Activity chứa fragment này phải implement {@link LoginFragment.OnLoginFragmentListener}
 * để xử lý các sự kiện tương tác.
 * Sử dụng phương thức {@link LoginFragment#newInstance} để khởi tạo một thực thể của {@link LoginFragment}.
 *
 * @created_by nadat on 19/03/2019
 */
public class LoginFragment extends Fragment implements View.OnClickListener, ILoginContract.ILoginView {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    private OnLoginFragmentListener mOnLoginFragmentListener;

    private EditText edtUsername;
    private EditText edtPassword;
    private AppCompatButton btnLogin;
    private AppCompatButton btnRegister;
    private AppCompatButton btnForgotPassword;

    private String mUsername;
    private String mPassword;

    private LoginPresenter mLoginPresenter;

    /**
     * Là phương thức khởi tạo của {@link LoginFragment}.
     */
    public LoginFragment() {
        mLoginPresenter = new LoginPresenter(this);
    }

    /**
     * Là phương thức khởi tạo của {@link LoginFragment}.
     *
     * @return một thực thể của {@link LoginFragment}.
     */
    public static LoginFragment newInstance() {
        try {
            return new LoginFragment();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Là phương thức khởi tạo của {@link LoginFragment} có chứa đối tượng Bundle args
     * trong phương thức {@link LoginFragment#setArguments(Bundle)} để truyền dữ liệu.
     *
     * @param username là tên của người dùng.
     * @param password là mật khẩu của người dùng.
     * @return một thực thể của {@link LoginFragment}.
     */
    public static LoginFragment newInstance(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        try {
            LoginFragment loginFragment = new LoginFragment();
            Bundle args = new Bundle();
            args.putString(USERNAME, username);
            args.putString(PASSWORD, password);
            loginFragment.setArguments(args);
            return loginFragment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                mUsername = getArguments().getString(USERNAME);
                mPassword = getArguments().getString(PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            return inflater.inflate(R.layout.fragment_login, container, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setActionViews();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnLoginFragmentListener) {
                mOnLoginFragmentListener = (OnLoginFragmentListener) context;
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
        try {
            mOnLoginFragmentListener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_login: {
                    mLoginPresenter.validateCredentials(edtUsername, edtPassword);
                    break;
                }
                case R.id.btn_register: {
                    try {
                        if (mOnLoginFragmentListener != null) {
                            mOnLoginFragmentListener.moveLoginToRegister();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case R.id.btn_change_password: {
                    try {
                        if (mOnLoginFragmentListener != null) {
                            mLoginPresenter.checkEdtUsername(edtUsername);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Chuyển sang màn hình chính sau khi đăng nhập thành công.
     */
    @Override
    public void showLoginSuccess() {
        try {
            if (mOnLoginFragmentListener != null) {
                mOnLoginFragmentListener.moveLoginToMain();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện toast tên người dùng hoặc mật khẩu không đúng.
     */
    @Override
    public void showLoginError() {
        try {
            Toast.makeText(getContext(), getString(R.string.notification_invalidate), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện toast tên người dùng chưa tồn tại.
     */
    @Override
    public void showEmptyUser() {
        try {
            Toast.makeText(getContext(), getString(R.string.notification_user_not_exist), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện thông báo phải điền đủ thông tin vào các EditText.
     *
     * @param editText EditText không được điền.
     */
    @Override
    public void showEditTextEmptyData(EditText editText) {
        if (editText == null) {
            return;
        }
        try {
            editText.requestFocus();
            editText.setError(getString(R.string.notification_fill_infor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiện toast lỗi của EditText.
     */
    @Override
    public void showEditTextError() {
        try {
            Toast.makeText(getContext(), getString(R.string.notification_edittext_not_exist), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Chuyển sang màn hình đổi mật khẩu.
     *
     * @param editText tên người dùng.
     */
    @Override
    public void moveLoginToChangePassword(EditText editText) {
        try {
            if (mOnLoginFragmentListener != null) {
                mOnLoginFragmentListener.moveLoginToChangePassword(Utils.getTextInEditText(editText));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ánh xạ các view từ xml sang code.
     *
     * @param view là view root của {@link LoginFragment}
     */
    private void initViews(View view) {
        if (view == null) {
            return;
        }
        try {
            edtUsername = view.findViewById(R.id.edt_username);
            edtPassword = view.findViewById(R.id.edt_password);
            btnLogin = view.findViewById(R.id.btn_login);
            btnRegister = view.findViewById(R.id.btn_register);
            btnForgotPassword = view.findViewById(R.id.btn_change_password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cài đặt các sự kiện cho các views.
     */
    private void setActionViews() {
        try {
            btnLogin.setOnClickListener(this);
            btnRegister.setOnClickListener(this);
            btnForgotPassword.setOnClickListener(this);
            if (getArguments() != null) {
                edtUsername.setText(mUsername);
                edtPassword.setText(mPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Interface các sự kiện tương tác với các  view khác của {@link LoginFragment}.
     */
    public interface OnLoginFragmentListener {
        void moveLoginToMain();

        void moveLoginToRegister();

        void moveLoginToChangePassword(String username);
    }
}
