package io.github.andylx96.nfcapplication;




        import java.nio.charset.Charset;
        import android.app.Activity;
        import android.nfc.NdefMessage;
        import android.nfc.NdefRecord;
        import android.nfc.NfcAdapter;
        import android.nfc.NfcAdapter.CreateNdefMessageCallback;
        import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
        import android.nfc.NfcEvent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;


public class Main2Activity extends Activity implements
        CreateNdefMessageCallback,     OnNdefPushCompleteCallback {
    private Button sendButton;
    private TextView textView;
    NfcAdapter mNfcAdapter;
    private static final int MESSAGE_SENT = 1;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //initialisierung
        sendButton = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        //mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //if(mNfcAdapter==null) toast("NFC ist nicht vorhanden");
        //else toast("NFC ist  vorhandne");


        toast("start");

        sendButton.setOnClickListener(mTagWriter);
    }

    //lokaler OnClickListener für den Button
    private View.OnClickListener mTagWriter = new View.OnClickListener() {
        public void onClick(View arg0) {
            // Write to a tag for as long as the dialog is shown.
            //disableNdefExchangeMode();
            //enableTagWriteMode();

            toast(textView.getText().toString());
        }
    };

    //notification-methode
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onNdefPushComplete(NfcEvent arg0) {
        // A handler is needed to send messages to the activity when this
        // callback occurs, because it happens from a binder thread
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    public NdefMessage createNdefMessage(NfcEvent event) {
//        String text = (textView.toString());
//        NdefRecord uriRecord = new NdefRecord(
//                NdefRecord.TNF_ABSOLUTE_URI,
//                "http://www.google.de".getBytes(Charset.forName("US-ASCII")),
//                new byte[0], new byte[0]);
//        NdefMessage msg = new NdefMessage(
//                new NdefRecord[]{uriRecord
//                        //createMimeRecord("text/plain", text.getBytes())
//                        /**
//                         * The Android Application Record (AAR) is commented out. When a device
//                         * receives a push with an AAR in it, the application specified in the AAR
//                         * is guaranteed to run. The AAR overrides the tag dispatch system.
//                         * You can add it back in to guarantee that this
//                         * activity starts when receiving a beamed message. For now, this code
//                         * uses the tag dispatch system.
//                        */
//                        //,NdefRecord.createApplicationRecord("com.example.android.beam")
//                });
//        return msg;

        String text = (textView.toString());
        NdefMessage msg = new NdefMessage(NdefRecord.createMime( "application/com.example.android.beam", text.getBytes()));
        return msg;
    }

    /**
     * Creates a custom MIME type encapsulated in an NDEF record
     *
     * @param mimeType
     */
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }

    /**
     * This handler receives a message from onNdefPushComplete
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}

