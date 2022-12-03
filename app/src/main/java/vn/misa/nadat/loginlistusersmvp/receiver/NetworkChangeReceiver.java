package vn.misa.nadat.loginlistusersmvp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Network is turned ON/OFF", Toast.LENGTH_SHORT).show();
    }
}
