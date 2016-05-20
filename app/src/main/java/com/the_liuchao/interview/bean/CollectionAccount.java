package com.the_liuchao.interview.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by hp on 2016/5/19.
 * 收款账户
 */
@Table(name = "c_account")
public class CollectionAccount {

    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "account_numb")
    private String account_numb;//收款账户账号
    @Column(name = "account_date")
    private String account_date;//收款账户保存日期
    @Column(name = "account_name")
    private String account_name;//收款账户户名
    @Column(name = "collect_bank")
    private String collect_bank;//收款银行
    @Column(name = "collect_zone")
    private String collect_zone;//收款地区

    public CollectionAccount( String account_numb, String account_date, String account_name, String collect_bank, String collect_zone) {
        this.account_numb = account_numb;
        this.account_date = account_date;
        this.account_name = account_name;
        this.collect_bank = collect_bank;
        this.collect_zone = collect_zone;
    }

    public CollectionAccount() {
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

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCollect_bank() {
        return collect_bank;
    }

    public void setCollect_bank(String collect_bank) {
        this.collect_bank = collect_bank;
    }

    public String getCollect_zone() {
        return collect_zone;
    }

    public void setCollect_zone(String collect_zone) {
        this.collect_zone = collect_zone;
    }
}
