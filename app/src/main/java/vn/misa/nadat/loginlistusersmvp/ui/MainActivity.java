package vn.misa.nadat.loginlistusersmvp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.misa.nadat.loginlistusersmvp.R;
import vn.misa.nadat.loginlistusersmvp.receiver.NetworkChangeReceiver;
import vn.misa.nadat.loginlistusersmvp.service.StartService;
import vn.misa.nadat.loginlistusersmvp.ui.change.ChangePasswordFragment;
import vn.misa.nadat.loginlistusersmvp.ui.login.LoginFragment;
import vn.misa.nadat.loginlistusersmvp.ui.main.MainFragment;
import vn.misa.nadat.loginlistusersmvp.ui.register.RegisterFragment;
import vn.misa.nadat.loginlistusersmvp.ui.splash.SplashFragment;

public class MainActivity extends AppCompatActivity
        implements SplashFragment.OnSplashFragmentListener,
        LoginFragment.OnLoginFragmentListener,
        RegisterFragment.OnRegisterFragmentListener,
        MainFragment.OnMainFragmentListener,
        ChangePasswordFragment.OnChangePasswordFragmentListener {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private NetworkChangeReceiver mReceiver;

    private SplashFragment mSplashFragment = SplashFragment.newInstance();
    private LoginFragment mLoginFragment = LoginFragment.newInstance();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mReceiver, filter);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.layout_main, mSplashFragment);
        mFragmentTransaction.commit();

        Intent intent = new Intent(this, StartService.class);
        intent.putExtra("test","test");
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void moveLoginToMain() {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        mFragmentTransaction.replace(R.id.layout_main, MainFragment.newInstance(), MainFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(MainFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void moveRegisterToMain(String username, String password) {
        moveToMainFrom(username, password);
    }

    @Override
    public void moveLoginToRegister() {
        moveToRegisterFragmentFrom(LoginFragment.class.getSimpleName());
    }

    @Override
    public void moveMainToRegister() {
        moveToRegisterFragmentFrom(MainFragment.class.getSimpleName());
    }

    @Override
    public void moveMainToChangePassword(String username) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        mFragmentTransaction.replace(R.id.layout_main,
                ChangePasswordFragment.newInstance(username, MainFragment.class.getSimpleName()),
                ChangePasswordFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(ChangePasswordFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void moveLoginToChangePassword(String username) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        mFragmentTransaction.replace(R.id.layout_main,
                ChangePasswordFragment.newInstance(username, LoginFragment.class.getSimpleName()),
                ChangePasswordFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(ChangePasswordFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void moveRegisterToLogin(String username, String password) {
        moveToLoginFrom(username, password);
    }

    @Override
    public void moveChangePasswordToLogin(String username, String password) {
        moveToLoginFrom(username, password);
    }

    @Override
    public void moveChangePasswordToMain(String username, String password) {
        moveToMainFrom(username, password);
    }

    private void moveToMainFrom(String username, String password) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        mFragmentTransaction.replace(R.id.layout_main, MainFragment.newInstance(username, password), MainFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(MainFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    private void moveToLoginFrom(String username, String password) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        mFragmentTransaction.replace(R.id.layout_main, LoginFragment.newInstance(username, password), LoginFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(LoginFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    private void moveToRegisterFragmentFrom(String nameFragment) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        mFragmentTransaction.replace(R.id.layout_main, RegisterFragment.newInstance(nameFragment), RegisterFragment.class.getSimpleName());
        mFragmentTransaction.addToBackStack(RegisterFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void moveSplashToLogin() {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        mFragmentTransaction.replace(R.id.layout_main, mLoginFragment, RegisterFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }
}
