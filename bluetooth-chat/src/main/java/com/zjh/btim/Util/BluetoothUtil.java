package com.zjh.btim.Util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import com.zjh.btim.Bean.BlueToothBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BluetoothUtil {

    private Context context;
    private BluetoothAdapter bluetoothAdapter;

    public BluetoothUtil(Context context) {
        this.context = context;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取本地蓝牙实例
    }

    /**
     * 判断蓝牙是否开启
     */
    public boolean isBluetoothEnable() {
        return bluetoothAdapter.isEnabled();
    }

    /**
     * 开启蓝牙,可被发现300秒
     */
    public void openBluetooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 关闭蓝牙
     */
    public void disableBluetooth() {
        bluetoothAdapter.disable();
    }

    /**
     * 查询已配对设备
     */
    public List<BlueToothBean> getDevicesList() {
        List<BlueToothBean> list = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices)
                list.add(new BlueToothBean(device.getName(), device.getAddress()));
        }
        return list;
    }

    /**
     * 扫描附件设备需要定位权限
     */
    public void startDiscovery() {
        bluetoothAdapter.startDiscovery();
    }

    public BluetoothDevice getBluetoothDevice(String mac) {
        return bluetoothAdapter.getRemoteDevice(mac);
    }

    public void close(){
        bluetoothAdapter.cancelDiscovery();
    }

    public String getBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        try {
            Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
            mServiceField.setAccessible(true);

            Object btManagerService = mServiceField.get(bluetoothAdapter);

            if (btManagerService != null) {
                bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
            }
        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) {

        }
        return bluetoothMacAddress;
    }

}
