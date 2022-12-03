package vn.misa.nadat.loginlistusersmvp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.misa.nadat.loginlistusersmvp.R;
import vn.misa.nadat.loginlistusersmvp.objects.User;

public class UsersAdapter extends RecyclerView.Adapter {
    private List<User> mUsers;
    private OnButtonClickListener mOnButtonClickListener;

    public UsersAdapter(OnButtonClickListener onButtonClickListener) {
        if (onButtonClickListener != null) {
            this.mOnButtonClickListener = onButtonClickListener;
        }
    }

    public void setUsers(List<User> users) {
        try {
            if (users == null) {
                return;
            }
            this.mUsers = users;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_user, viewGroup, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        User itemApp = mUsers.get(i);
        UserViewHolder userViewHolder = (UserViewHolder) viewHolder;
        userViewHolder.bind(itemApp.getUsername(), itemApp.getPassword());
    }

    @Override
    public int getItemCount() {
        if (mUsers == null) {
            return 0;
        }
        return mUsers.size();
    }

    public interface OnButtonClickListener {
        void onButtonUpdateClickListener(int position);

        void onButtonSaveClickListener(int position);

        void onButtonDeleteClickListener(int position);
    }

    private class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatTextView mTvUsername;
        private AppCompatTextView mTvPassword;
        private AppCompatImageButton mBtnUpdate;
        private AppCompatImageButton mBtnSave;
        private AppCompatImageButton mBtnDelete;

        private UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvUsername = itemView.findViewById(R.id.tv_username);
            mTvPassword = itemView.findViewById(R.id.tv_password);
            mBtnUpdate = itemView.findViewById(R.id.btn_update_password);
            mBtnSave = itemView.findViewById(R.id.btn_save_file);
            mBtnDelete = itemView.findViewById(R.id.btn_delete);

            mBtnUpdate.setOnClickListener(this);
            mBtnSave.setOnClickListener(this);
            mBtnDelete.setOnClickListener(this);
        }

        private void bind(String username, String password) {
            try {
                if (username != null) {
                    mTvUsername.setText(username);
                }
                if (password != null) {
                    mTvPassword.setText(password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.btn_update_password: {
                        mOnButtonClickListener.onButtonUpdateClickListener(getAdapterPosition());
                        break;
                    }
                    case R.id.btn_save_file: {
                        mOnButtonClickListener.onButtonSaveClickListener(getAdapterPosition());
                        break;
                    }
                    case R.id.btn_delete: {
                        mOnButtonClickListener.onButtonDeleteClickListener(getAdapterPosition());
                        break;
                    }
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
