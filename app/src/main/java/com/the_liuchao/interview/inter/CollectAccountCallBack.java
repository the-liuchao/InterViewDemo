package com.the_liuchao.interview.inter;

import com.the_liuchao.interview.bean.CollectionAccount;

import java.util.List;

/**
 * Created by hp on 2016/5/19.
 */
public interface CollectAccountCallBack {
    void obtainData(List<CollectionAccount> collects);
}
