package com.example.filip.pantkampen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
    private ImageButton scanBtn,stats, info;
    private PopupWindow pop;
    private EditText team;
    private LayoutInflater layoutInflater;
    private int sum,added;
    private TextView formatTxt, contentTxt, statsView;
    private RelativeLayout relativelayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        sum=0;
       // formatTxt = (TextView)findViewById(R.id.scan_format);
       // contentTxt = (TextView)findViewById(R.id.scan_content);
        team = (EditText)findViewById(R.id.editText);
        // statsView =(TextView)findViewById(R.id.textView2);
        //statsView.setText("Poäng: ");




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
    public void onClick(View v){
    //respond to clicks

    if(v.getId()==R.id.imageButton) {
        String temp;
        temp= team.getText().toString();
        if(temp!=null){
            if("blåtiger".equalsIgnoreCase(temp.replaceAll("\\s+",""))){
                setContentView(R.layout.activity_main);

                relativelayout = (RelativeLayout) findViewById(R.id.relative);
                scanBtn = (ImageButton)findViewById(R.id.scan_button);
                stats = (ImageButton)findViewById(R.id.button);
                info = (ImageButton)findViewById(R.id.button2);

                statsView= (TextView)findViewById(R.id.textView3);
                scanBtn.setOnClickListener(this);
                stats.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        ViewGroup container = (ViewGroup)layoutInflater.inflate(R.layout.stats,null);

                        pop = new PopupWindow(container,600,1000,true);
                        pop.showAtLocation(relativelayout, Gravity.NO_GRAVITY,60,100);
                        // statsView= (TextView)findViewById(R.id.textView3);
                        statsView.setText("Dina poäng: "+sum+"\nKlassens poäng: "+(sum+531));
                        container.setOnTouchListener(new View.OnTouchListener(){
                            public boolean onTouch(View view, MotionEvent motionEvent){
                                pop.dismiss();
                                return true;
                            }
                        });
                    }
                });
                info.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        ViewGroup container2 = (ViewGroup) layoutInflater.inflate(R.layout.info, null);

                        pop = new PopupWindow(container2, 600, 1000, true);
                        pop.showAtLocation(relativelayout, Gravity.NO_GRAVITY, 60, 100);
                        container2.setOnTouchListener(new View.OnTouchListener() {
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                pop.dismiss();

                                return true;
                            }
                        });
                    }
                });
                statsView.setText("Dina poäng: "+sum+"\nKlassens poäng: "+(sum+531));

            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Skriv in ett giltligt lag!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Skriv in ett lag!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }else if(v.getId()==R.id.scan_button){
    //scan
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    //retrieve scan result
    IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
        //we have a result
            String temp;
        String scanContent = scanningResult.getContents();
        temp = scanContent.substring(scanContent.length()-4);

            added = Integer.parseInt(temp);
            sum =sum + added;
            statsView.setText("Dina poäng: "+sum+"\nKlassens poäng: "+(sum+531));

        /*String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);*/
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Antal poäng skannade: "+added+"!", Toast.LENGTH_SHORT);
            toast.show();

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
