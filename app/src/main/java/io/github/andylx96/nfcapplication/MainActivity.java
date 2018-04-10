//package io.github.andylx96.nfcapplication;
//
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.nfc.NfcAdapter;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//private  Button testButton;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
//
//        if(nfcAdapter!=null && nfcAdapter.isEnabled() ){
//            Toast.makeText(this,"NFC Available", Toast.LENGTH_LONG).show();
//        }else{Toast.makeText(this,"NFC NOT Available", Toast.LENGTH_LONG).show();}
//
//
//       Intent intent = new Intent(this,MainActivity.class);
//        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
//
//        PendingIntent pendingIntent = PendingIntent.getActivities(this,0, new Intent[]{intent},0);
//////        onNewIntent();
////        onNewIntent();
//
//        IntentFilter[] intentFilters = new IntentFilter[]{};
//
//        testButton = (Button) findViewById(R.id.button2);
//        setButtons();
////        nfcAdapter.
//
//    }
//
//    protected void onNewIntent(Intent intent) {
//
//        Toast.makeText(this,"NFC intent recieved!", Toast.LENGTH_LONG).show();
//        super.onNewIntent(intent);
//
//    }
//
//
//    //    Setting OnClickListener For Test Button
//    public void setButtons() {
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Changes Menus from This Class MainActivity to TestActivity Page
//                startActivity(new Intent(MainActivity.this, Main2Activity.class));
//            }
//        });
//    }
//}
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

public class MainActivity extends Activity implements
        CreateNdefMessageCallback, OnNdefPushCompleteCallback{

    TextView textInfo;
    EditText textOut;

    NfcAdapter nfcAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInfo = (TextView)findViewById(R.id.info);
        textOut = (EditText)findViewById(R.id.textout);
        button = (Button)findViewById(R.id.nextButton);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter==null){
            Toast.makeText(MainActivity.this,
                    "nfcAdapter==null, no NFC adapter exists",
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,
                    "Set Callback(s)",
                    Toast.LENGTH_LONG).show();
            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
            Parcelable[] parcelables =
                    intent.getParcelableArrayExtra(
                            NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage inNdefMessage = (NdefMessage)parcelables[0];
            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
            NdefRecord NdefRecord_0 = inNdefRecords[0];
            String inMsg = new String(NdefRecord_0.getPayload());
            textInfo.setText(inMsg);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        eventString,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        String stringOut = textOut.getText().toString();
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord ndefRecordOut = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),
                new byte[] {},
                bytesOut);

        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
        return ndefMessageout;
    }

}