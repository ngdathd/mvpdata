package vn.misa.nadat.loginlistusersmvp.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import vn.misa.nadat.loginlistusersmvp.R;
import vn.misa.nadat.loginlistusersmvp.adapters.UsersAdapter;
import vn.misa.nadat.loginlistusersmvp.objects.User;

/**
 * Là class con của {@link Fragment} hiển thị màn hình đăng nhập người dùng.
 * Activity chứa fragment này phải implement {@link MainFragment.OnMainFragmentListener}
 * để xử lý các sự kiện tương tác.
 * Sử dụng phương thức {@link MainFragment#newInstance} để khởi tạo một thực thể của {@link MainFragment}.
 *
 * @created_by nadat on 19/03/2019
 */
public class MainFragment extends Fragment implements UsersAdapter.OnButtonClickListener, IMainContract.IMainView, View.OnClickListener {
    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private List<User> mUsers;
    private IMainContract.IMainPresenter mMainPresenter;
    private UsersAdapter mUsersAdapter;
    private OnMainFragmentListener mListener;

    private String mUsername;
    private String mPassword;

    /**
     * Là phương thức khởi tạo của {@link MainFragment}.
     */
    public MainFragment() {
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.loadAllUsers();
    }

    /**
     * Là phương thức khởi tạo của {@link MainFragment}.
     *
     * @return một thực thể của {@link MainFragment}.
     */
    public static MainFragment newInstance() {
        try {
            return new MainFragment();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Là phương thức khởi tạo của {@link MainFragment} có chứa đối tượng Bundle args
     * trong phương thức {@link MainFragment#setArguments(Bundle)} để truyền dữ liệu.
     *
     * @param username là tên của người dùng.
     * @param password là mật khẩu của người dùng.
     * @return một thực thể của {@link MainFragment}.
     */
    public static MainFragment newInstance(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        try {
            MainFragment mainFragment = new MainFragment();
            Bundle args = new Bundle();
            args.putString(USERNAME, username);
            args.putString(PASSWORD, password);
            mainFragment.setArguments(args);
            return new MainFragment();
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
            return inflater.inflate(R.layout.fragment_main, container, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mUsersAdapter = new UsersAdapter(this);
        mUsersAdapter.setUsers(mUsers);

        RecyclerView rcvUsers = view.findViewById(R.id.rcv_users);
        rcvUsers.setLayoutManager(linearLayoutManager);
        rcvUsers.setAdapter(mUsersAdapter);

        FloatingActionButton fabAddUser = view.findViewById(R.id.fab_add_user);
        fabAddUser.setOnClickListener(this);

        if (getArguments() != null) {
            mUsers.add(new User(mUsername, mPassword));
            mUsersAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnMainFragmentListener) {
                mListener = (OnMainFragmentListener) context;
            } else {
                Log.i(TAG, "Must implement OnMainFragmentListener");
            }
        } catch (RuntimeException e) {
            Log.i(TAG, e.getMessage() + " must implement OnMainFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Thay đổi mật khẩu cho người dùng.
     *
     * @param position vị trí item được click
     */
    @Override
    public void onButtonUpdateClickListener(int position) {
        if (mListener == null || mUsers.get(position) == null) {
            return;
        }
        try {
            mListener.moveMainToChangePassword(mUsers.get(position).getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lưu item vào file text trong máy
     *
     * @param position vị trí item được click
     */
    @Override
    public void onButtonSaveClickListener(int position) {
//        mMainPresenter.saveUserToFile(mUsers.get(position));
    }

    /**
     * Xóa một người dùng
     *
     * @param position vị trí item được click
     */
    @Override
    public void onButtonDeleteClickListener(int position) {
//        mMainPresenter.deleteUser(mUsers.get(position));
    }

    /**
     * Nhận khi xóa người dùng trong database thành công.
     * Cập nhật giao diện cho recycle view.
     * @param user người dùng được xóa.
     */
    @Override
    public void onDeleteUserSuccess(User user) {
        mUsers.remove(user);
        mUsersAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Delete user success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteUserError() {
        Toast.makeText(getContext(), "Delete user error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListUsers(List<User> users) {
        this.mUsers = users;
    }

    @Override
    public void showListUsersError() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_user: {
                if (mListener != null) {
                    mListener.moveMainToRegister();
                }
                break;
            }
            default:
                break;
        }
    }

    public interface OnMainFragmentListener {
        void moveMainToRegister();

        void moveMainToChangePassword(String username);
    }
}
