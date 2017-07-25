package net.suntrans.suntransyanshi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import net.suntrans.suntransyanshi.R;
import net.suntrans.suntransyanshi.utils.DbHelper;
import net.suntrans.suntransyanshi.utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Looney on 2017/4/13.
 */

public class AddDevicesActivity2 extends AppCompatActivity {

    @InjectView(R.id.RSAddr)
    EditText RSAddr;
    @InjectView(R.id.zhilianip)
    EditText zhilianip;
    @InjectView(R.id.zhilianport)
    EditText zhilianport;
    @InjectView(R.id.waiwangip)
    EditText waiwangip;
    @InjectView(R.id.waiwangport)
    EditText waiwangport;
    @InjectView(R.id.bendiip)
    EditText bendiip;
    @InjectView(R.id.bendiport)
    EditText bendiport;
    @InjectView(R.id.qvxiao)
    Button qvxiao;
    @InjectView(R.id.add)
    Button add;
    @InjectView(R.id.name)
    EditText name;
    @InjectView(R.id.type)
    Spinner spinner;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddevices2);
        ButterKnife.inject(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.qvxiao, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qvxiao:
                Intent intent = new Intent();
                intent.putExtra("result", "failed");
                setResult(100, intent);
                finish();
                overridePendingTransition(0, android.support.v7.appcompat.R.anim.abc_slide_out_bottom);
                break;
            case R.id.add:
                upDateDataBased();
                break;
        }
    }

    private void upDateDataBased() {
        String name1 = name.getText().toString();
        String rsAddr1 = RSAddr.getText().toString();
        String zhilianip1 = zhilianip.getText().toString();
        String zhilianport1 = zhilianport.getText().toString();
        String waiwangip1 = waiwangip.getText().toString();
        String waiwangport1 = waiwangport.getText().toString();
        String bendiip1 = bendiip.getText().toString();
        String bendiport1 = bendiport.getText().toString();

        DbHelper helper = new DbHelper(AddDevicesActivity2.this, "IBMS", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();

        if (type==1) {
            for (int i = 1; i <= 6; i++) {
                ContentValues cv = new ContentValues();

                cv.put("Name", name1);
                cv.put("RSAddr", rsAddr1);
                cv.put("zhilianip", zhilianip1);
                cv.put("zhilianport", zhilianport1);
                cv.put("waiwangip", waiwangip1);
                cv.put("waiwangport", waiwangport1);
                cv.put("bendiip", bendiip1);
                cv.put("bendiport", bendiport1);
                cv.put("Type", "6");
                cv.put("Channel", i + "");
                db.insert("switchs_tb", null, cv);
            }
        } else if (type==0) {
            for (int i = 1; i <= 10; i++) {
                ContentValues cv = new ContentValues();

                cv.put("Name", name1);
                cv.put("RSAddr", rsAddr1);
                cv.put("zhilianip", zhilianip1);
                cv.put("zhilianport", zhilianport1);
                cv.put("waiwangip", waiwangip1);
                cv.put("waiwangport", waiwangport1);
                cv.put("bendiip", bendiip1);
                cv.put("bendiport", bendiport1);
                cv.put("Type", "10");
                cv.put("Channel", i + "");
                db.insert("switchs_tb", null, cv);
            }
        } else if (type==2) {
            ContentValues cv = new ContentValues();
            cv.put("Name", name1);
            cv.put("RSAddr", rsAddr1);
            cv.put("zhilianip", zhilianip1);
            cv.put("zhilianport", zhilianport1);
            cv.put("waiwangip", waiwangip1);
            cv.put("waiwangport", waiwangport1);
            cv.put("bendiip", bendiip1);
            cv.put("bendiport", bendiport1);
//            cv.put("Type", "10");
//            cv.put("Channel",i+"");
            db.insert("sixsensor_tb", null, cv);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        UiUtils.showToast(getString(R.string.add_success));
        Intent intent = new Intent();
        intent.putExtra("result", "success");
        setResult(100, intent);
        finish();
        overridePendingTransition(0, android.support.v7.appcompat.R.anim.abc_slide_out_bottom);
    }
}
