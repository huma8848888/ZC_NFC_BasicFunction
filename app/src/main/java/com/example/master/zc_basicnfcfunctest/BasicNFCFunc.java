package com.example.master.zc_basicnfcfunctest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


//mport android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;


import com.pda.hf.HFReader;
import com.pda.hf.ISO15693CardInfo;
import java.util.List;

import test.hfsimple.Tools;
import test.hfsimple.Util;
import test.hfsimple.MyApplication;


public class BasicNFCFunc extends AppCompatActivity {


    private MyApplication mapp ;
    private HFReader hfReader ;

    ////
    private TextView textViewVersion;
    private boolean djrun=false;
    private boolean djBroaded=false;

    private final int MSG_CARD = 1101 ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CARD:
                    String uid = msg.getData().getString("uid");
                    String cardType = msg.getData().getString("cardType");
                    Util.play(1, 0 );
                    textViewVersion.setText(uid);
                    Broaddata();
                    if (djrun) {
                        isStart = false ;
                        running = false ;
                        finish();
                    }
                    break ;
            }
        }
    };

    //���Ź㲥��Ϊ���ⲿapp��ȡ����
    private void Broaddata() {
        if (djBroaded) return;//ֻ�㲥һ��
        Intent intent=new Intent();
        intent.setAction("com.bjzc.frd");
        intent.putExtra("data", textViewVersion.getText());
        sendBroadcast(intent);
        djBroaded=true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_nfcfunc);
        //Log.d("Main",getApplication().toString());
        mapp = (MyApplication) getApplication() ;
        hfReader = mapp.getHfReader() ;
        Util.initSoundPool(this);
        initView() ;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            isStart = false ;
            running = false ;
            mapp.exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        textViewVersion = (TextView) findViewById(R.id.textView_version);
        //Ϊ���app�ṩ����
        int djtag=getIntent().getIntExtra("DJruntag",0);
        if(djtag==1)djrun=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!running) {
            running = true;
            new Thread(readTask).start();
        }
        isStart = true ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isStart = false ;
        running = false ;
    }

    boolean running = false ;
    boolean isStart = false ;
    //read thread
    private Runnable readTask = new Runnable() {
        byte[] uid14443 =  null;
        List<ISO15693CardInfo> listCard15693 = null ;
        byte[] uid15693 = null ;
        @Override
        public void run() {
            while(running){
                if(isStart && hfReader != null){
                    //14443A
                    uid14443 = hfReader.search14443Acard() ;
                    if (uid14443 != null) {
                        sendMSG(Tools.Bytes2HexString(uid14443, uid14443.length), "14443A", MSG_CARD) ;
                    }else{
                        //15693
                        listCard15693 = hfReader.search15693Card() ;
                        if (listCard15693 != null && !listCard15693.isEmpty()) {
                            for (ISO15693CardInfo card : listCard15693) {
                                uid15693 = card.getUid() ;
                                sendMSG(Tools.Bytes2HexString(uid15693, uid15693.length), "15693", MSG_CARD) ;
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {

                }
            }
        }
    } ;

    //send the result to handler
    private void sendMSG(String cardUid, String cardType, int msgCode) {
        Bundle bundle = new Bundle();
        bundle.putString("uid", cardUid);
        bundle.putString("cardType", cardType);
        Message msg = new Message() ;
        msg.setData(bundle);
        msg.what = msgCode ;
        handler.sendMessage(msg);
    }
}
