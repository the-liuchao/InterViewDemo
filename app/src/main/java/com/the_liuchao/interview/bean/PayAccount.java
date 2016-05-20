package com.the_liuchao.interview.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by hp on 2016/5/19.
 * 付款账户
 */
@Table(name = "p_account")
public class PayAccount {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "account_numb")
    private String account_numb;//付款账户账号
    @Column(name = "account_date")
    private String account_date;//付款账户保存日期

    public PayAccount() {
    }

    public PayAccount(String account_numb, String account_date) {
        this.account_numb = account_numb;
        this.account_date = account_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount_numb() {
        return account_numb;
    }

    public void setAccount_numb(String account_numb) {
        this.account_numb = account_numb;
    }

    public String getAccount_date() {
        return account_date;
    }

    public void setAccount_date(String account_date) {
        this.account_date = account_date;
    }
}
