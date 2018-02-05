package com.example.lcc.mybluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextView tv_state;
    private TextView tv_control;
    private BluetoothAdapter mBlue;
    private final int REQUEST_CODE = 2;
    // 与本地蓝牙设备配对的远程蓝牙设备
    private Set<BluetoothDevice> remoteBlueToothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_state = findViewById(R.id.blue_state);
        tv_control = findViewById(R.id.button_control);
        mBlue = BluetoothAdapter.getDefaultAdapter();
        remoteBlueToothes=mBlue.getBondedDevices();
        if (mBlue == null) {
            return;
        }
        if(remoteBlueToothes.size()!=0){
            for (Iterator iterator=remoteBlueToothes.iterator();iterator.hasNext();){
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator
                        .next();
                Log.i("CXC", bluetoothDevice.getAddress());
            }
        }
        initView();

        tv_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBlue.isEnabled()) {
                    mBlue.disable();
                    initView();
                } else {
                    mBlue.enable();
                    initView();

//                    //打开蓝牙的另一种方式
//                    Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(intent,REQUEST_CODE);
                }

            }
        });

    }

    private void initView() {

        if (mBlue.isEnabled()||mBlue.getState()==BluetoothAdapter.STATE_TURNING_OFF) {
            tv_control.setText("打开蓝牙");
            tv_state.setText(mBlue.getName() + ": 关闭");
        } else {
            tv_control.setText("关闭蓝牙");
            tv_state.setText(mBlue.getName() + ": 打开");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "打开失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "打开成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
