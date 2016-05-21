package com.the_liuchao.interview.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by hp on 2016/5/21.
 * 收款银行实体类
 */
@Table(name = "c_bank")
public class CollectBank {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "bank_name")
    private String bank_name;
    @Column(name = "bank_date")
    private String bank_date;
    @Column(name = "bank_type")
    private String bank_type;

    public CollectBank() {
    }

    public CollectBank(String bank_name, String bank_date, String bank_type) {
        this.bank_name = bank_name;
        this.bank_date = bank_date;
        this.bank_type = bank_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_date() {
        return bank_date;
    }

    public void setBank_date(String bank_date) {
        this.bank_date = bank_date;
    }
}
