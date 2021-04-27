package org.thoughtcrime.securesms.bluetooth;

import android.content.Intent;

import com.zjh.btim.Activity.BluetoothConnectionActivity;

import org.thoughtcrime.securesms.conversation.ConversationActivity;

public class BluetoothConversationActivity extends ConversationActivity {

    @Override
    public void sendTextViaBluetooth() {
        BluetoothSender sender = new BluetoothSenderImpl();
        if(sender.isUserPaired("1")){
            sender.send("hello developer");
        }
        else{
            Intent intent = new Intent(this, BluetoothConnectionActivity.class);
            startActivity(intent);

        }
    }
}
