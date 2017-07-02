package xiaoan.com.application.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xiaoan.com.application.Bean.showProcessBean;
import xiaoan.com.application.R;
import xiaoan.com.application.engine.showProcessEngine;


public class showProcessActivity extends AppCompatActivity {

    private List<showProcessBean> sysapp;
    private List<showProcessBean> dataapp;
    private List<showProcessBean> allApp;

    @SuppressLint("HandlerLeak")
    private Handler h = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                findViewById(R.id.showprocess_linearlayout_count).bringToFront();
                final TextView vie = (TextView) findViewById(R.id.showprocess_count);
                vie.bringToFront();
                lvapk.setAdapter(new myappinfo());
                lvapk.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        if (sysapp != null && dataapp != null) {

                            if (firstVisibleItem == 0)//判断是否最顶端,否则刷新控件会与listview冲突
                                refesh.setEnabled(true);
                            else
                                refesh.setEnabled(false);


                            if (firstVisibleItem > dataapp.size()) {
                                vie.setText("系统应用" + sysapp.size() + "个");
                                vie.setBackgroundColor(Color.parseColor("#F235DF"));

                            } else {
                                vie.setText("用户应用" + dataapp.size() + "个");
                                vie.setBackgroundColor(Color.parseColor("#9c1fdf"));

                            }
                        }
                    }
                });
                findViewById(R.id.showprocess_linearlayout).setVisibility(View.INVISIBLE);

            /*
            bringToFront的作用是将控件置顶!!!
             */

            refesh.setRefreshing(false);
            }


        }
    };
    private ListView lvapk;
    private SwipeRefreshLayout refesh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showprocess);
        setTitle(R.string.jinchengguanli);
        init();
        inintData();


    }


    private void init() {
        if (!showProcessEngine.haspermission(getApplicationContext())) {
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 1101);
        }

        StringBuilder[] mem = showProcessEngine.mem(getApplicationContext());

        TextView tv = (TextView) findViewById(R.id.showprocess_meminfo);
        lvapk = (ListView) findViewById(R.id.showprocess_lv);

        tv.setText("可用/总数:" + Integer.parseInt(mem[0].toString()) / 1024 + "MB / " + Integer.parseInt(mem[1].toString()) / 1024 + "MB");
        refesh = (SwipeRefreshLayout) findViewById(R.id.showprocess_refresh_layout);
        refesh.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);

        refesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //刷新监听s
            @Override
            public void onRefresh() {
                inintData();

            }
        });
    }

    private void inintData() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                sysapp = new ArrayList<showProcessBean>();
                dataapp = new ArrayList<>();
                allApp = showProcessEngine.getAllApp(getApplicationContext());
                for (showProcessBean s : allApp) {
                    if (s.isSystem())
                        sysapp.add(s);
                    else
                        dataapp.add(s);
                }

                h.sendEmptyMessage(0);


            }
        }.start();
    }

    static class viewHolder {
        ImageView img;
        TextView txv1;
        TextView txv2;

    }

    class myappinfo extends BaseAdapter {

        private viewHolder holder;
        private int newpos;

        @Override
        public int getCount() {
            return allApp.size() + 2;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            showProcessBean ssBean;

            if (i == 0) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText("用户应用:" + dataapp.size() + "个");
                textView.setBackgroundColor(Color.parseColor("#AD4CCE"));
                return textView;
            } else if (i == dataapp.size() + 1) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText("系统应用:" + sysapp.size() + "个");
                textView.setBackgroundColor(Color.RED);
                return textView;
            } else if (i <= dataapp.size()) {
                //显示userapp
                int newpos = i - 1;
                ssBean = dataapp.get(newpos);
            } else {
                int pos = i - 1 - dataapp.size() - 1;
                ssBean = sysapp.get(pos);
            }

            if (view != null && view instanceof RelativeLayout) {

                holder = (viewHolder) view.getTag();
            } else {
                view = View.inflate(getApplicationContext(), R.layout.listview_showprocess, null);
                holder = new viewHolder();

                holder.img = (ImageView) view.findViewById(R.id.showprocess_imagev);
                holder.txv1 = (TextView) view.findViewById(R.id.showprocess_textvname);
                holder.txv2 = (TextView) view.findViewById(R.id.showprocess_Installsys);
                view.setTag(holder);

            }
            holder.img.setImageDrawable(ssBean.getIcon());
            holder.txv1.setText(ssBean.getNames());
            holder.txv2.setText(ssBean.isSystem() ? "系统应用" : "用户应用");


            return view;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1101) {
            if (!showProcessEngine.haspermission(getApplicationContext())) {
                Toast.makeText(this, "您拒绝了本次授权请求", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "授权成功,请稍候", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
