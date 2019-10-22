package com.oec.sdl.fade;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edit_text);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }
    private boolean buttonFlg=false;
    @Override
    public void onClick(View v) {
        //接続ボタン
        if (v.getId() == R.id.button){
            if(!buttonFlg) {
                // エディットテキストのテキストを取得
                String port = editText.getText().toString();
                Toast.makeText(this, "Manticore ポート:" +  port + "に接続されます", Toast.LENGTH_LONG).show();
                //If we are connected to a module we want to start our SdlService
                if (BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
                    //SdlReceiver.queryForConnectedService(this);

                    Intent sdlServiceIntent = new Intent(MainActivity.this, SdlService.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(SdlService.PORT_NAME, Integer.parseInt(port));
                    sdlServiceIntent.putExtras(bundle);
                    startService(sdlServiceIntent);
                } else if (BuildConfig.TRANSPORT.equals("TCP")) {
                    Intent sdlServiceIntent = new Intent(MainActivity.this, SdlService.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(SdlService.PORT_NAME, Integer.parseInt(port));
                    sdlServiceIntent.putExtras(bundle);
                    startService(sdlServiceIntent);
                }
                buttonFlg=true;
                button.setText("DISCONNECT");
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
