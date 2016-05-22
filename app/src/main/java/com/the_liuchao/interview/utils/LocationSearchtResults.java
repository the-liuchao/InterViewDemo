package com.the_liuchao.interview.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.the_liuchao.interview.R;
import com.the_liuchao.interview.adapter.StringListAdapter;
import com.the_liuchao.interview.bean.CollectBank;
import com.the_liuchao.interview.bean.CollectionAccount;
import com.the_liuchao.interview.bean.PayAccount;
import com.the_liuchao.interview.dao.DBUtils;
import com.the_liuchao.interview.inter.CollectAccountCallBack;
import com.the_liuchao.interview.inter.CollectBankCallBack;
import com.the_liuchao.interview.inter.CustomTextWatcher;
import com.the_liuchao.interview.inter.ItemSelectedListener;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 2016/5/19.
 */
public class LocationSearchtResults extends PopupWindow {

    private static final int AUTO_SEARCH_PAY_ACCOUNT = 0;
    private static final int AUTO_SEARCH_COLLECT_ACCOUNT = 1;
    private static final int AUTO_SEARCH_COLLECT_BANK = 2;
    private EditText etSearch;
    private TextView tvConfirm;
    private View contentView;
    private ListView lvResults;
    private List<String> list;
    private BaseAdapter adapter;
    private String result = "";
    private String type;
    List<CollectionAccount> collectAccounts = null;
    /**
     * Handler处理UI更新
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_SEARCH_PAY_ACCOUNT:
                    List<PayAccount> payAccounts = null;
                    try {
                        payAccounts = DBUtils.findAllPayAccount();
                        final ArrayList<String> results = new ArrayList<>();
                        if (payAccounts == null || payAccounts.size() <= 0)
                            break;
                        for (PayAccount payAccount : payAccounts) {
                            String account_num = payAccount.getAccount_numb();
                            if (account_num.indexOf(msg.obj.toString()) != -1)
                                results.add(account_num);
                            if (results.size() >= 5)
                                break;
                        }
                        onRefresh(results);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    break;
                case AUTO_SEARCH_COLLECT_ACCOUNT:
                    final ArrayList<String> results = new ArrayList<>();
                    collectAccounts = (List<CollectionAccount>) msg.obj;
                    if (collectAccounts == null || collectAccounts.size() <= 0)
                        break;
                    for (CollectionAccount collectAccount : collectAccounts) {
                        String account_num = collectAccount.getAccount_numb();
                        results.add(account_num);
                    }
                    onRefresh(results);
                    break;
                case AUTO_SEARCH_COLLECT_BANK:
                    List<CollectBank> collectBanks = null;
                    final ArrayList<String> banks = new ArrayList<>();
                    collectBanks = (List<CollectBank>) msg.obj;
                    if (collectBanks == null || collectBanks.size() <= 0)
                        break;
                    for (CollectBank collectBank : collectBanks) {
                        String account_num = collectBank.getBank_name();
                        banks.add(account_num);
                    }
                    onRefresh(banks);
                    break;
            }
        }
    };

    public void onRefresh(List<String> results) {
        list.clear();
        list.addAll(results);
        adapter.notifyDataSetChanged();
    }

    public LocationSearchtResults(Context context, LayoutInflater layoutInflater) {
        super(context);
        this.list = new ArrayList<>();
        contentView = layoutInflater.inflate(R.layout.pull_list_layout, null);
        lvResults = (ListView) contentView.findViewById(R.id.lv_data);
        etSearch = (EditText) contentView.findViewById(R.id.et_search);
        tvConfirm = (TextView) contentView.findViewById(R.id.tv_search_confirm);
        this.adapter = new StringListAdapter(context, list);
        lvResults.setAdapter(this.adapter);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setContentView(contentView);  //设置悬浮窗体内显示的内容View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);   //设置悬浮窗体的宽度
        //this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);   //设置悬浮窗体的高度
        this.setBackgroundDrawable(new ColorDrawable(0xffffff)); // 设置悬浮窗体背景
        this.setAnimationStyle(R.style.popup_enter);     //设置悬浮窗体出现和退出时的动画
        this.setOutsideTouchable(true);   //可以再外部点击隐藏掉PopupWindow
        this.setFocusable(true);
    }

    public void show(View rootView, final String type, final ItemSelectedListener listener) {
        this.type = type;
        //清楚原先数据
        this.list.clear();
        adapter.notifyDataSetChanged();
        etSearch.setText("");
        etSearch.setFocusable(true);
        //则通知外部的下拉列表项单击监听器并传递当前单击项的数据
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                if (listener != null) {
                    if ("collect".equals(type)) {
                        if (collectAccounts != null && collectAccounts.size() >= 0)
                            listener.onItemSelected(list.get(index), collectAccounts.get(index));
                    } else
                        listener.onItemSelected(list.get(index), index);
                    dismiss();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (listener != null) {
                    String result = etSearch.getText().toString();
                    if(TextUtils.isEmpty(result)){
                        Toast.makeText(v.getContext(),"请输入！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if("collect".equals(type)||"pay".equals(type)){
                        Pattern pattern = Pattern.compile("[0-9]{18}");
                        Matcher matcher = pattern.matcher(result);
                        if(!matcher.matches()){
                            Toast.makeText(v.getContext(),"请输入正确的账户！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    listener.onItemSelected(etSearch.getText().toString(), null);
                    dismiss();
                }
            }
        });
//        if (watcher != null)
//            etSearch.removeTextChangedListener(watcher);
//        watcher = new SearchTextWatcher(type);
        etSearch.addTextChangedListener(new SearchTextWatcher(type));
        showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
    }

    /***/
    private final class SearchTextWatcher extends CustomTextWatcher {

        private String type;

        public SearchTextWatcher(String type) {
            this.type = type;
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            {
                String pattern = etSearch.getText().toString();
                if (pattern.length() > 0) {
                    if (result.length() <= 0) {
                        try {
                            if ("pay".equals(type)) {
                                Message msg = handler.obtainMessage(AUTO_SEARCH_PAY_ACCOUNT);
                                msg.obj = pattern;
                                msg.sendToTarget();
                            } else if ("collect".equals(type)) {
                                DBUtils.filterCollectionAccount(pattern, new CollectAccountCallBack() {
                                    public void obtainData(List<CollectionAccount> collectAccounts) {
                                        Message msg = handler.obtainMessage(AUTO_SEARCH_COLLECT_ACCOUNT);
                                        msg.obj = collectAccounts;
                                        msg.sendToTarget();
                                    }
                                });
                            } else if ("bank".equals(type)) {
                                DBUtils.filterCollectionBank(pattern, new CollectBankCallBack() {
                                    public void obtainData(List<CollectBank> banks) {
                                        Message msg = handler.obtainMessage(AUTO_SEARCH_COLLECT_BANK);
                                        msg.obj = banks;
                                        msg.sendToTarget();
                                    }
                                });
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        result = "";
                    }
                }
            }

        }
    }
}
