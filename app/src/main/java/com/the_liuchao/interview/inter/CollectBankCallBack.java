package com.the_liuchao.interview.inter;

import com.the_liuchao.interview.bean.CollectBank;

import java.util.List;

/**
 * Created by hp on 2016/5/19.
 */
public interface CollectBankCallBack {
    void obtainData(List<CollectBank> collects);
}
