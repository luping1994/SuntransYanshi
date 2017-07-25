package net.suntrans.suntransyanshi.slslc10;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.suntrans.suntransyanshi.R;
import net.suntrans.suntransyanshi.activity.AddDevicesActivity;
import net.suntrans.suntransyanshi.activity.base.BasedActiity;
import net.suntrans.suntransyanshi.bean.SwitchItem;
import net.suntrans.suntransyanshi.stslc6.Stslc6_control_activity2;
import net.suntrans.suntransyanshi.utils.DbHelper;
import net.suntrans.suntransyanshi.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Looney on 2017/2/22.
 */

public class St_slc_10list_activity extends BasedActiity {


    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MyAdapter adapter;

    @Override
    public int getLayoutSourceId() {
        return R.layout.activity_stslc10;
    }

    @Override
    public void init() {
        super.init();
        setTitle(getString(R.string.st_slc_10));
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new MyAdapter();
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private List<SwitchItem> datas = new ArrayList<>();

    @Override
    protected void initData() {
        datas.clear();

        DbHelper helper = new DbHelper(St_slc_10list_activity.this, "IBMS", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query(true, "switchs_tb", new String[]{"RSAddr",
                "Name",
                "zhilianip", "zhilianport",
                "bendiip", "bendiport",
                "waiwangip", "waiwangport"}, "Type=?", new String[]{"10"}, null, null, null, null);
        while (cursor.moveToNext()) {
            SwitchItem item = new SwitchItem();
            item.setRSaddr(cursor.getString(0));
            item.setName(cursor.getString(1));
            item.setZhilianip(cursor.getString(2));
            item.setZhilianport(cursor.getString(3));
            item.setBendiip(cursor.getString(4));
            item.setBendiport(cursor.getString(5));
            item.setWaiwangip(cursor.getString(6));
            item.setWaiwangport(cursor.getString(7));
            datas.add(item);
        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        adapter.notifyDataSetChanged();

        LogUtil.i("个数=" + datas.size());
    }

    class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0)
                return new ViewHolder1(LayoutInflater.from(St_slc_10list_activity.this).inflate(R.layout.item_device, parent, false));
            else
                return new ViewHolder2(LayoutInflater.from(St_slc_10list_activity.this).inflate(R.layout.item_tipsno, parent, false));

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder1)
                ((ViewHolder1) holder).setData(position);
        }

        @Override
        public int getItemCount() {
            return datas.size() == 0 ? 1 : datas.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (datas.size() == 0)
                return 1;
            return 0;
        }

        class ViewHolder1 extends RecyclerView.ViewHolder {
            TextView name;


            public ViewHolder1(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                name.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(St_slc_10list_activity.this).setMessage("是否删除?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delete(datas.get(getAdapterPosition()).getRSaddr());
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                        return true;
                    }
                });
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("item", datas.get(getAdapterPosition()));
                        intent.putExtra("source","bendi");
                        intent.setClass(St_slc_10list_activity.this, St_slc_10_activity.class);
                        startActivity(intent);
                    }
                });
            }

            public void setData(int position) {
                name.setText(datas.get(position).getName() + "(" + datas.get(position).getRSaddr() + ")");
            }
        }

        class ViewHolder2 extends RecyclerView.ViewHolder {
            TextView name;


            public ViewHolder2(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                name.setTextColor(getResources().getColor(R.color.secondary_text));
                name.setText(getResources().getString(R.string.tips_hasnodevice));
            }

            public void setData(int position) {
            }
        }
    }

    private void delete(String rSaddr) {
        DbHelper helper = new DbHelper(St_slc_10list_activity.this, "IBMS", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        db.delete("switchs_tb","RSAddr=?",new String[]{rSaddr});


        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        initData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain, menu);
//
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.toggle:
                Intent intent = new Intent(this, AddDevicesActivity.class);
                intent.putExtra("type","stslc10");
                startActivityForResult(intent,101);
                overridePendingTransition(android.support.v7.appcompat.R.anim.abc_slide_in_bottom,0);
                return true;
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==100){
            String s = data.getStringExtra("result");
                if (s.equals("success")){
                    initData();
                    adapter.notifyDataSetChanged();
                }
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
