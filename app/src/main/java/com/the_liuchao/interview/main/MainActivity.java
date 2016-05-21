package com.the_liuchao.interview.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.the_liuchao.interview.R;
import com.the_liuchao.interview.bean.CollectBank;
import com.the_liuchao.interview.bean.CollectionAccount;
import com.the_liuchao.interview.bean.PayAccount;
import com.the_liuchao.interview.dao.DBUtils;
import com.the_liuchao.interview.date.CustomDatetimeDialog;
import com.the_liuchao.interview.inter.CustomTextWatcher;
import com.the_liuchao.interview.inter.ItemSelectedListener;
import com.the_liuchao.interview.inter.ZoneSelectedListener;
import com.the_liuchao.interview.utils.LocationSearchtResults;
import com.the_liuchao.interview.utils.ZoneSelectWindow;

import org.xutils.ex.DbException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author the_liucihao
 * @Description 面试笔试题
 * @Date 2016/05/19
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView _payAccount, _collectAccount;
    private LinearLayout _backBtn;
    private TextView _payDate, _cashReduce, _cashAdd, _billReduce, _billAdd;
    private EditText _cashExcept;
    private EditText _billExcept;
    private EditText _digest;
    private TextView _nextSize;
    private EditText _collectName;
    private TextView _collectBank;
    private TextView _collectZone;
    private TextView _cashActual, _billActual, _cashMatch, _billMatch;
    private ZoneSelectWindow zoneWindow;
    LocationSearchtResults searchtResults;
    /**
     * Handler处理UI更新
     */
    Handler handler = new Handler();

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
        _payAccount = (TextView) findViewById(R.id.tv_payment_account);
        _collectAccount = (TextView) findViewById(R.id.tv_collect_account);
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
        _collectBank = (TextView) findViewById(R.id.tv_collect_account_bank);
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
        searchtResults = new LocationSearchtResults(this, LayoutInflater.from(this));
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
        _payAccount.setOnClickListener(this);
        _collectAccount.setOnClickListener(this);
        _collectBank.setOnClickListener(this);
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
            case R.id.tv_payment_account:
                searchtResults.show(getWindow().getDecorView().getRootView(), "pay", new ItemSelectedListener() {
                    public void onItemSelected(String item, Object object) {
                        Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.tv_collect_account:
                searchtResults.show(getWindow().getDecorView().getRootView(), "collect", new ItemSelectedListener() {
                    public void onItemSelected(String item, Object object) {
                        Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.tv_collect_account_bank:
                searchtResults.show(getWindow().getDecorView().getRootView(), "bank", new ItemSelectedListener() {
                    public void onItemSelected(String item, Object object) {
                        Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

}
