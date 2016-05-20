package com.the_liuchao.interview.dao;

import com.the_liuchao.interview.bean.CollectionAccount;
import com.the_liuchao.interview.bean.PayAccount;
import com.the_liuchao.interview.inter.CollectAccountCallBack;
import com.the_liuchao.interview.main.InterApplication;

import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hp on 2016/5/19.
 */
public class DBUtils {

    private static ExecutorService executorService;

    static {
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * 保存付款账户信息
     */
    public static void savePayAccount(PayAccount payAccount) throws DbException {
        InterApplication.getDb().delete(PayAccount.class, WhereBuilder.b("account_numb", "=", payAccount.getAccount_numb()));
        InterApplication.getDb().save(payAccount);
    }

    /**
     * 查询所有付款用户列表
     */
    public static List<PayAccount> findAllPayAccount() throws DbException {
        return InterApplication.getDb().findAll(PayAccount.class);
    }

    /**
     * 查询表中记录数
     */
    public static int count(String tableName) throws DbException {
        SqlInfo sqlInfo = new SqlInfo();
        sqlInfo.setSql("select count(*) as size from " + tableName);
        DbModel dbModel = InterApplication.getDb().findDbModelFirst(sqlInfo);
        return dbModel.getInt("size");
    }

    /**
     * 保存收款账户信息
     */
    public static void saveCollectionAccount(CollectionAccount collectionAccount) throws DbException {
        InterApplication.getDb().delete(CollectionAccount.class, WhereBuilder.b("account_numb", "=", collectionAccount.getAccount_numb()));
        InterApplication.getDb().save(collectionAccount);
    }

    /**
     * 查询所有收款用户列表
     */
    public static List<CollectionAccount> findAllCollectionAccount() throws DbException {
        return InterApplication.getDb().findAll(CollectionAccount.class);
    }

    /**
     * 查询所有收款用户列表
     */
    public static synchronized void filterCollectionAccount( final String pattern,final CollectAccountCallBack callback) throws DbException {
        executorService.submit(new Runnable() {
            public void run() {
                List<CollectionAccount> results = new ArrayList<CollectionAccount>();
                final SqlInfo sqlInfo = new SqlInfo();
                sqlInfo.setSql("select * from c_account where account_numb like '" + pattern + "%'");
                try {
                    SoftReference<List<DbModel>> models = new SoftReference<List<DbModel>>(InterApplication.getDb().findDbModelAll(sqlInfo));
                    if(models.get()==null||models.get().size()<=0)
                        return;
                    for (DbModel model : models.get()) {
                        String account_numb = model.getString("account_numb");
                        String account_date = model.getString("account_date");
                        String account_name = model.getString("account_name");
                        String collect_bank = model.getString("collect_bank");
                        String collect_zone = model.getString("collect_zone");
                        int id = model.getInt("id");
                        CollectionAccount account = new CollectionAccount();
                        account.setId(id);
                        account.setAccount_date(account_date);
                        account.setAccount_numb(account_numb);
                        account.setAccount_name(account_name);
                        account.setCollect_bank(collect_bank);
                        account.setCollect_zone(collect_zone);
                        results.add(account);
                        if (results.size() > 10)
                            break;
                    }
                    callback.obtainData(results);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
