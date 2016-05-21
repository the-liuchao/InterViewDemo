package com.the_liuchao.interview.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.the_liuchao.interview.R;
import com.the_liuchao.interview.bean.CollectBank;
import com.the_liuchao.interview.bean.CollectionAccount;
import com.the_liuchao.interview.bean.PayAccount;
import com.the_liuchao.interview.dao.DBUtils;
import com.the_liuchao.interview.date.CustomDatetimeDialog;
import com.the_liuchao.interview.inter.CollectAccountCallBack;
import com.the_liuchao.interview.inter.CollectBankCallBack;
import com.the_liuchao.interview.inter.CustomTextWatcher;
import com.the_liuchao.interview.inter.ItemSelectedListener;
import com.the_liuchao.interview.inter.ZoneSelectedListener;
import com.the_liuchao.interview.utils.LocationSearchtResults;
import com.the_liuchao.interview.utils.ZoneSelectWindow;

import org.xutils.ex.DbException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author the_liucihao
 * @Description 面试笔试题
 * @Date 2016/05/19
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int AUTO_SEARCH_PAY_ACCOUNT = 0;
    private static final int AUTO_SEARCH_COLLECT_ACCOUNT = 1;
    private static final int AUTO_SEARCH_COLLECT_BANK = 2;
    private EditText _payAccount, _collectAccount;
    private LinearLayout _backBtn;
    private String pay_account = "", collect_account = "", collect_bank = "";//付款账户,收款账户,收款银行
    private TextView _payDate, _cashReduce, _cashAdd, _billReduce, _billAdd;
    private EditText _cashExcept;
    private EditText _billExcept;
    private EditText _digest;
    private TextView _nextSize;
    private EditText _collectName;
    private EditText _collectBank;
    private TextView _collectZone;
    private TextView _cashActual, _billActual, _cashMatch, _billMatch;
    List<CollectionAccount> collectAccounts;
    List<CollectBank> collectBanks;
    private ZoneSelectWindow zoneWindow;
    /**
     * Handler处理UI更新
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_SEARCH_PAY_ACCOUNT:
                    List<PayAccount> list = null;
                    try {
                        list = DBUtils.findAllPayAccount();
                        final ArrayList<String> results = new ArrayList<>();
                        if (list == null || list.size() <= 0)
                            break;
                        for (PayAccount payAccount : list) {
                            String account_num = payAccount.getAccount_numb();
                            if (account_num.indexOf(msg.obj.toString()) != -1)
                                results.add(account_num);
                            if (results.size() >= 5)
                                break;
                        }
                        showPopupWindow(results, "pay");
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    break;
                case AUTO_SEARCH_COLLECT_ACCOUNT:
                    final ArrayList<String> results = new ArrayList<>();
                    collectAccounts = (List<CollectionAccount>) msg.obj;
                    if (collectAccounts == null || collectAccounts.size() <= 0)
                        break;
//                    collectAccounts.clear();//为防止模糊查询产生过多对象回收掉使用过的对象
                    for (CollectionAccount collectAccount : collectAccounts) {
                        String account_num = collectAccount.getAccount_numb();
                        results.add(account_num);
                    }
                    showPopupWindow(results, "collect");
                    break;
                case AUTO_SEARCH_COLLECT_BANK:
                    final ArrayList<String> banks = new ArrayList<>();
                    collectBanks = (List<CollectBank>) msg.obj;
                    if (collectBanks == null || collectBanks.size() <= 0)
                        break;
                    for (CollectBank collectBank : collectBanks) {
                        String account_num = collectBank.getBank_name();
                        banks.add(account_num);
                    }
                    showPopupWindow(banks, "bank");
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flash);
        final Runnable loadLayoutRunnable = new Runnable() {
            ;

            public void run() {
                setContentView(R.layout.activity_main);
                initView();
                initData();
            }
        };
        handler.postDelayed(loadLayoutRunnable, 3000);
    }

    /**
     * 初始化视图方法
     */
    private void initView() {
        _payAccount = (EditText) findViewById(R.id.tv_payment_account);
        _collectAccount = (EditText) findViewById(R.id.tv_collect_account);
        _backBtn = (LinearLayout) findViewById(R.id.ll_back);
        _payDate = (TextView) findViewById(R.id.tv_paydate);
        _cashReduce = (TextView) findViewById(R.id.tv_cash_reduce);
        _cashAdd = (TextView) findViewById(R.id.tv_cash_add);
        _billReduce = (TextView) findViewById(R.id.tv_bill_reduce);
        _billAdd = (TextView) findViewById(R.id.tv_bill_add);
        _cashExcept = (EditText) findViewById(R.id.et_cash_except);
        _billExcept = (EditText) findViewById(R.id.et_bill_except);
        _digest = (EditText) findViewById(R.id.tv_digest);
        _nextSize = (TextView) findViewById(R.id.tv_next_input_size);
        _collectName = (EditText) findViewById(R.id.tv_collect_account_name);
        _collectBank = (EditText) findViewById(R.id.tv_collect_account_bank);
        _collectZone = (TextView) findViewById(R.id.tv_collect_account_zone);
        _cashActual = (TextView) findViewById(R.id.tv_cash_actual);
        _billActual = (TextView) findViewById(R.id.tv_bill_actual);
        _cashMatch = (TextView) findViewById(R.id.et_cash_matching);
        _billMatch = (TextView) findViewById(R.id.et_bill_matching);
        zoneWindow = new ZoneSelectWindow(this, getWindow().getDecorView().getRootView()//
                , new ZoneSelectedListener() {
            public void selected(String province, String city, String district) {
                _collectZone.setText(province + "-" + city + "-" + district);
            }
        });
    }


    private void initData() {
        try {
            DBUtils.saveCollectionAccount(new CollectionAccount("642310251210250451", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("216212515626615151", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("845451231321565454", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("121215465464848488", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("121275465464848488", System.currentTimeMillis() + "", "李四", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("121295465464848488", System.currentTimeMillis() + "", "王五", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("342310251210250451", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("416212515626615151", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("545451231321565454", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("621215465464848488", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("621715465464848488", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("621715465464843288", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("621715465464843280", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("621715465464843223", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("621715465464846723", System.currentTimeMillis() + "", "张三", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("721275465464848488", System.currentTimeMillis() + "", "李四", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("821295465464848488", System.currentTimeMillis() + "", "王五", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.saveCollectionAccount(new CollectionAccount("921295465464848488", System.currentTimeMillis() + "", "王五", "宝山友谊路招商分行", "上海,浦东新区"));
            DBUtils.savePayAccount(new PayAccount("642310251210250451", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("216212515626615151", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("845451231321565454", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("715455151515151521", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("121215465464848488", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("321215465464848488", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("421215465464848488", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("521215465464848488", System.currentTimeMillis() + ""));
            DBUtils.savePayAccount(new PayAccount("921215465464848488", System.currentTimeMillis() + ""));
            DBUtils.saveBank(new CollectBank("上海宝山友谊分行招商分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("长沙雨湖区浦东大道建设分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("湘潭市九龙农业银行分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("常德市文理学院建设分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("北京朝阳区同城招商分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("深圳湖田区工商银行分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("上海宝山友谊分行招商分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("上海宝山友谊分行招商分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("上海宝山友谊分行招商分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("上海宝山友谊分行招商分行", System.currentTimeMillis() + "", "1"));
            DBUtils.saveBank(new CollectBank("上海宝山友谊分行招商分行", System.currentTimeMillis() + "", "1"));
        } catch (DbException e) {
            e.printStackTrace();
        }
        _backBtn.setOnClickListener(this);
        _payDate.setOnClickListener(this);
        _cashReduce.setOnClickListener(this);
        _cashAdd.setOnClickListener(this);
        _billReduce.setOnClickListener(this);
        _billAdd.setOnClickListener(this);
        _collectZone.setOnClickListener(this);
        _payAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (searchtResults != null)
                        searchtResults.setFocusable(true);
                }
            }
        });
        /**付款账户模糊查询监听*/
        _payAccount.addTextChangedListener(new CustomTextWatcher() {
            public void afterTextChanged(Editable s) {
                String account = _payAccount.getText().toString();
                if (account.length() > 0) {
                    //如果没有选择自动匹配记录则开始搜索
                    if (pay_account.length() <= 0) {
                        Message msg = handler.obtainMessage(AUTO_SEARCH_PAY_ACCOUNT);
                        msg.obj = account;
                        msg.sendToTarget();
                    } else {
                        pay_account = "";
                    }
                } else {
                    if (searchtResults != null && searchtResults.isShowing()) {
                        searchtResults.dismiss();
                    }
                }
            }
        });
        /**收款账户模糊查询监听*/
        _collectAccount.addTextChangedListener(new CustomTextWatcher() {
            public void afterTextChanged(Editable s) {
                final String account = _collectAccount.getText().toString();
                if (account.length() > 0) {
                    //如果没有选择自动匹配记录则开始搜索
                    if (collect_account.length() <= 0) {
                        try {
                            DBUtils.filterCollectionAccount(account, new CollectAccountCallBack() {
                                @Override
                                public void obtainData(List<CollectionAccount> collects) {
                                    Message msg = handler.obtainMessage(AUTO_SEARCH_COLLECT_ACCOUNT);
                                    msg.obj = collects;
                                    msg.sendToTarget();
                                }
                            });
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        collect_account = "";
                    }
                } else {
                    if (searchtResults != null && searchtResults.isShowing()) {
                        searchtResults.dismiss();
                    }
                }
            }
        });
        _collectBank.addTextChangedListener(new CustomTextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String bank = _collectBank.getText().toString();
                if (bank.length() > 0) {
                    if (collect_bank.length() <= 0) {
                        try {
                            DBUtils.filterCollectionBank(bank, new CollectBankCallBack() {
                                public void obtainData(List<CollectBank> banks) {
                                    Message msg = handler.obtainMessage(AUTO_SEARCH_COLLECT_BANK);
                                    msg.obj = banks;
                                    msg.sendToTarget();
                                }
                            });
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        collect_bank = "";
                    }
                } else {
                    if (searchtResults != null && searchtResults.isShowing()) {
                        searchtResults.dismiss();
                    }
                }
            }

        });

        _digest.addTextChangedListener(new CustomTextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _nextSize.setText("你还可以输入" + (200 - s.length()) + "字!");
            }

        });
        _cashMatch.addTextChangedListener(new CustomTextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeActualPercent();
            }
        });
        _billMatch.addTextChangedListener(new CustomTextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeActualPercent();
            }
        });
        _cashExcept.addTextChangedListener(new CustomTextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        _billExcept.addTextChangedListener(new CustomTextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    /**
     * 监听付款金额改变 设置对应实际比例
     */
    private void changeActualPercent() {
        String cashStr = _cashMatch.getText().toString();
        String billStr = _billMatch.getText().toString();
        int cash = Integer.parseInt(TextUtils.isEmpty(cashStr) ? "0" : cashStr);
        int bill = Integer.parseInt(TextUtils.isEmpty(billStr) ? "0" : billStr);
        int total = cash + bill;
        if (total == 0) {
            _cashActual.setText("50%");
            _billActual.setText("50%");
        } else {
            _cashActual.setText(String.format("%2.1f %%", ((cash * 100) / total) * 1.0f));
            _billActual.setText(String.format("%2.1f %%", ((bill * 100) / total) * 1.0f));

        }
    }

    LocationSearchtResults searchtResults;//收款信息模糊查询下拉视图

    /**
     * 显示模糊查询数据
     */
    private void showPopupWindow(final ArrayList<String> results, final String type) {
        if (searchtResults != null && searchtResults.isShowing()) {
            if (results == null || results.size() <= 0) {
                searchtResults.dismiss();
            } else {
                searchtResults.refresh(results);
            }
            return;
        }
        //实例化自定义的PopupWindow
        searchtResults = new LocationSearchtResults(this, getLayoutInflater(),
                results, _payAccount.getWidth() - 13, new ItemSelectedListener() {
            public void onItemSelected(String item, int position) {
                if ("pay".equals(type)) {  //付款账号
                    pay_account = item;
                    _payAccount.setText(pay_account);
                } else if ("collect".equals(type)) {//收款账号
                    collect_account = item;
                    _collectAccount.setText(collect_account);
                    CollectionAccount collectionAccount = collectAccounts.get(position);
                    _collectBank.setText(collectionAccount.getCollect_bank());
                    _collectName.setText(collectionAccount.getAccount_name());
                    _collectZone.setText(collectionAccount.getCollect_zone());

                } else if ("bank".equals(type)) {
                    collect_bank = collectBanks.get(position).getBank_name();
                    _collectBank.setText(collect_bank);
                }
            }
        });
        //制定自定义PopupWindow显示的位置
        if ("pay".equals(type)) {
            searchtResults.showAsDropDown(_payAccount, 0, 0);
        } else if ("collect".equals(type)) {
            searchtResults.showTop(_collectAccount);
        } else if ("bank".equals(type)) {
            searchtResults.showTop(_collectBank);
//            searchtResults.showAsDropDown(_collectBank, 0, 0, Gravity.TOP);
        }
    }

    Editable billExcept;
    Editable cashExcept;

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back://返回按钮
                finish();
                break;
            case R.id.tv_paydate://选择支付日
                CustomDatetimeDialog dialog = new CustomDatetimeDialog(this, System.nanoTime());
                dialog.setOnDateSetChnange(new CustomDatetimeDialog.OnDateChangeSetListener() {
                    public void OnDateSet(AlertDialog alertDailog, long date) {
                        //设置日期时间选择的值long
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStr = formatter.format(new Date(date));
                        _payDate.setText(dateStr);
                    }
                });
                dialog.show();
                break;
            case R.id.tv_bill_add:
                billExcept = _billExcept.getText();
                if (!TextUtils.isEmpty(billExcept) && Integer.parseInt(billExcept.toString()) < 100) {
                    int bill = Integer.parseInt(billExcept.toString()) + 1;
                    _billExcept.setText(bill + "");
                    _cashExcept.setText((100 - bill) + "");
                } else {
                    _billExcept.setText("0");
                    _cashExcept.setText("100");
                }
                break;
            case R.id.tv_bill_reduce:
                billExcept = _billExcept.getText();
                if (!TextUtils.isEmpty(billExcept) && Integer.parseInt(billExcept.toString()) > 0) {
                    int bill = Integer.parseInt(billExcept.toString()) - 1;
                    _billExcept.setText(bill + "");
                    _cashExcept.setText((100 - bill) + "");
                } else {
                    _billExcept.setText("0");
                    _cashExcept.setText("100");
                }
                break;
            case R.id.tv_cash_add:
                cashExcept = _cashExcept.getText();
                if (!TextUtils.isEmpty(cashExcept) && Integer.parseInt(cashExcept.toString()) < 100) {
                    int cash = Integer.parseInt(cashExcept.toString()) + 1;
                    _cashExcept.setText(cash + "");
                    _billExcept.setText((100 - cash) + "");
                } else {
                    _cashExcept.setText("100");
                    _billExcept.setText("0");
                }
                break;
            case R.id.tv_cash_reduce:
                cashExcept = _cashExcept.getText();
                if (!TextUtils.isEmpty(cashExcept) && Integer.parseInt(cashExcept.toString()) > 0) {
                    int cash = Integer.parseInt(cashExcept.toString()) - 1;
                    _cashExcept.setText(cash + "");
                    _billExcept.setText((100 - cash) + "");
                } else {
                    _cashExcept.setText("100");
                    _billExcept.setText("0");
                }
                break;
            case R.id.tv_collect_account_zone:
                zoneWindow.showZoneWindow();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (searchtResults != null && searchtResults.isShowing()) {
            searchtResults.dismiss();
            return;
        }
        super.onBackPressed();
    }
}
