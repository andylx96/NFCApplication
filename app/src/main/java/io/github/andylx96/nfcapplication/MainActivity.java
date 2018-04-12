
package io.github.andylx96.nfcapplication;


import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MainActivity extends Activity {

    TextView textInfo;
    EditText textOut;

    NfcAdapter nfcAdapter;
    Button button;
FirebaseAuth user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInfo = (TextView)findViewById(R.id.info);
        textOut = (EditText)findViewById(R.id.textout);
        button = (Button)findViewById(R.id.nextButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

    }

}




//package io.github.andylx96.nfcapplication;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.nfc.NdefMessage;
//import android.nfc.NdefRecord;
//import android.nfc.NfcAdapter;
//import android.nfc.NfcAdapter.CreateNdefMessageCallback;
//import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
//import android.nfc.NfcEvent;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.gson.Gson;
//
//import org.json.JSONObject;
//
//public class MainActivity extends Activity implements
//        CreateNdefMessageCallback, OnNdefPushCompleteCallback{
//
//    TextView textInfo;
//    EditText textOut;
//
//    NfcAdapter nfcAdapter;
//    Button button;
//FirebaseAuth user;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textInfo = (TextView)findViewById(R.id.info);
//        textOut = (EditText)findViewById(R.id.textout);
//        button = (Button)findViewById(R.id.nextButton);
//
//        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        if(nfcAdapter==null){
//            Toast.makeText(MainActivity.this,
//                    "nfcAdapter==null, no NFC adapter exists",
//                    Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(MainActivity.this,
//                    "Set Callback(s)",
//                    Toast.LENGTH_LONG).show();
//            nfcAdapter.setNdefPushMessageCallback(this, this);
//            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
//        }
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,Main2Activity.class));
//            }
//        });
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = getIntent();
//        String action = intent.getAction();
//        if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
//            Parcelable[] parcelables =
//                    intent.getParcelableArrayExtra(
//                            NfcAdapter.EXTRA_NDEF_MESSAGES);
//            NdefMessage inNdefMessage = (NdefMessage)parcelables[0];
//            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
//            NdefRecord NdefRecord_0 = inNdefRecords[0];
//            String inMsg = new String(NdefRecord_0.getPayload());
//            textInfo.setText(inMsg);
//
////            user = FirebaseAuth.getInstance();
////            if(user.getInstance().getCurrentUser()!= null)
//
//
//
//            Gson gson = new Gson();
//
//            ProfileAccount profileAccount =  gson.fromJson(inMsg, ProfileAccount.class);
//            textInfo.setText(profileAccount.getMyProfile().getName()+"\n"+ profileAccount.getMyProfile().getInfo());
//
////            textInfo.setText(inMsg);
//
//
//
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//    }
//
//    @Override
//    public void onNdefPushComplete(NfcEvent event) {
//
//        final String eventString = "onNdefPushComplete\n" + event.toString();
//        runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(),
//                        eventString,
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//    @Override
//    public NdefMessage createNdefMessage(NfcEvent event) {
//
//
//
////        Gson gson;
////        JSONObject
//        Gson gson = new Gson();
//        ProfileAccount profileAccount = new ProfileAccount();
//gson.toJson(profileAccount);
//String jsonInString = gson.toJson(profileAccount);
//
//        byte[] bytesOut = jsonInString.getBytes();
//
//
//
////        String stringOut = textOut.getText().toString();
////        byte[] bytesOut = stringOut.getBytes();
//
//        NdefRecord ndefRecordOut = new NdefRecord(
//                NdefRecord.TNF_MIME_MEDIA,
//                "text/plain".getBytes(),
//                new byte[] {},
//                bytesOut);
//
//        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
//        return ndefMessageout;
//
//    }
//
//}