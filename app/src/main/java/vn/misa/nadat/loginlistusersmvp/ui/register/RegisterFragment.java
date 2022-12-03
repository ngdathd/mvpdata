package vn.misa.nadat.loginlistusersmvp.ui.register;

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

/**
 * Là class con của {@link Fragment} hiển thị màn hình đăng nhập người dùng.
 * Activity chứa fragment này phải implement {@link RegisterFragment.OnRegisterFragmentListener}
 * để xử lý các sự kiện tương tác.
 * Sử dụng phương thức {@link RegisterFragment#newInstance} để khởi tạo một thực thể của {@link RegisterFragment}.
 *
 * @created_by nadat on 19/03/2019
 */
public class RegisterFragment extends Fragment implements View.OnClickListener, IRegisterContract.IRegisterView {
    private static final String TAG = RegisterFragment.class.getSimpleName();
    private static final String PREVIOUS_FRAGMENT = "PREVIOUS_FRAGMENT";

    private OnRegisterFragmentListener mListener;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtConfirm;

    private IRegisterContract.IRegisterPresenter mIRegisterPresenter;
//    private RegisterPresenter mRegisterPresenter;
    private String mPreviousFragment;

    /**
     * Là phương thức khởi tạo của {@link RegisterFragment}.
     */
    public RegisterFragment() {
        mIRegisterPresenter = new RegisterPresenter(this);
    }

    /**
     * Là phương thức khởi tạo của {@link RegisterFragment} có chứa đối tượng Bundle args
     * trong phương thức {@link RegisterFragment#setArguments(Bundle)} để truyền dữ liệu.
     *
     * @param previousFragment tên của fragment hiển thị trước {@link RegisterFragment}
     * @return một thực thể của {@link RegisterFragment}.
     */
    public static RegisterFragment newInstance(String previousFragment) {
        RegisterFragment registerFragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(PREVIOUS_FRAGMENT, previousFragment);
        registerFragment.setArguments(args);
        return registerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPreviousFragment = getArguments().getString(PREVIOUS_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mEdtUsername = view.findViewById(R.id.edt_username);
        mEdtPassword = view.findViewById(R.id.edt_password);
        mEdtConfirm = view.findViewById(R.id.edt_confirm);

        AppCompatButton btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnRegisterFragmentListener) {
                mListener = (OnRegisterFragmentListener) context;
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

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_register: {
                    mIRegisterPresenter.validateCredentials();
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
     * Nhận khi đăng ký người dùng thành công.
     *
     * @param username tên người dùng
     * @param password mật khẩu
     */
    @Override
    public void onRegisterSuccess(String username, String password) {
        if (username == null || password == null || mListener == null) {
            return;
        }
        try {
            switch (mPreviousFragment) {
                case "LoginFragment": {
                    mListener.moveRegisterToLogin(username, password);
                    break;
                }
                case "MainFragment": {
                    mListener.moveRegisterToMain(username, password);
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
     * Nhận khi đăng ký thất bại.
     */
    @Override
    public void onRegisterError() {
        try {
            Toast.makeText(getContext(), "Register Error!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nhận sự kiện đăng ký người dùng đã tồn tại.
     */
    @Override
    public void onDuplicateUser() {
        try {
            Toast.makeText(getContext(), "Username already exist!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Nhận sự kiện nhập mật khẩu và mật khẩu nhắc lại không khớp nhau.
     */
    @Override
    public void onConfigPassword() {
        try {
            Toast.makeText(getContext(), "Confirm invalidate!", Toast.LENGTH_SHORT).show();
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

    public interface OnRegisterFragmentListener {
        void moveRegisterToLogin(String username, String password);

        void moveRegisterToMain(String username, String password);
    }
}
