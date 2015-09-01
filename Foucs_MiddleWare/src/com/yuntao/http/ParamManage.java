package com.yuntao.http;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.yuntao.utils.BaseParamUtils;

/**
 * @author jomeslu
 */
public class ParamManage {

    /**
     * 楼盘详情页
     *
     * @return
     */
    public static String getBound(String clientId,
                                  String token) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "BindCliendToUser");
        map.put("token", token);
        map.put("clientId", clientId);
        return BaseParamUtils.parase(UrlManage.URL_bound, map);
    }


}
