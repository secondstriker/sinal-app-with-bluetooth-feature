package org.thoughtcrime.securesms.bluetooth;

public interface BluetoothSender {

    void send(String message);
    boolean isUserPaired(String id);
}
