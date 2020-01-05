package edu.tomerbu.lec4part3permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabCall = findViewById(R.id.fab_call);
        fabCall.setOnClickListener(v -> {
            call();
        });
    }

    //practice: another method that requires permission:
    private void sendSMS(){
        //1) manifest:
        //2) check if we have permission:
        //  if not { request permission; return}
        //3) send the sms:

        //requires permission:
        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage("+972542045516", null, "Please help :)", null, null);
    }

    //method that requires permission:
    //step 1: )manifest (<uses-permission.../>)
    private void call() {
        //step 2: check for permission:
        String callPhonePermission = Manifest.permission.CALL_PHONE;//"android.permission.CALL_PHONE"

        String[] array = {callPhonePermission};

        if (ActivityCompat.checkSelfPermission(this, callPhonePermission) != PackageManager.PERMISSION_GRANTED) {
            //request permission:
            ActivityCompat.requestPermissions(this, array, 0);

            //get out of the method
            return; //don't continue to the dangerous code if we d'ont have a permission yet.
        }

        //call is dangerous: hence -> 1) manifest, 2) we need to request a run time permission:
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0542045516"));
        startActivity(intent);
    }

    //add this method: to get notified when the permission is granted / not

    //callback for the dialog
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //if we got a permission -> call()

        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //we have permission:
            call();
        }else {
            //No Permission:
            Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
//homework: read.
//https://developer.android.com/training/permissions/requesting