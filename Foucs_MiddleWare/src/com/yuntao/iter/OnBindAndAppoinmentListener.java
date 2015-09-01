package com.yuntao.iter;


import com.yuntao.mode.BindReslut;

/**
 * 绑定门店以及预约
 * @author jomeslu
 *
 */
public interface OnBindAndAppoinmentListener {
  void onBindResult(BindReslut reslut, int mode);

}
