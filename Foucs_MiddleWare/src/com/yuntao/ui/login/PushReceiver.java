package com.yuntao.ui.login;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.Constants;
import com.yuntao.MyApplication;
import com.yuntao.R;
import com.yuntao.base.core.BaseFragmentActivity;
import com.yuntao.mode.PushMessage;

import java.io.IOException;

public class PushReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
        LogUtils.i("GetuiSdkDemo____________" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);

                    Log.d("GetuiSdkDemo", "receiver payload : " + data);
                    ObjectMapper mapper = new ObjectMapper();
                    PushMessage pushMessage = null;
                    try {
                        pushMessage = mapper.readValue(data.trim(), PushMessage.class);
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    NotificationManager notificationManager =
                            (NotificationManager) context
                                    .getSystemService(Context.NOTIFICATION_SERVICE);
//                    Notification notification =
//                            new Notification(R.drawable.fxb_icon, pushMessage.getMessage(),
//                                    System.currentTimeMillis());
                    if(pushMessage==null)
                        return;

                    if(pushMessage.getType()==1){
                        //退出登录
                        MyApplication.getInstance().onBindAndAppoinmentSuccess(null,Constants.EVENT_PUSH_LOGOUT);
                        LogUtils.i("退出登录");

                    }else{
                        Notification notification = new Notification();
                        notification.icon = R.drawable.push;
                        notification.when = System.currentTimeMillis();
                        notification.defaults = Notification.DEFAULT_ALL;
                        notification.tickerText = pushMessage.getTitle();
                        Intent mIntent = new Intent();
                        Bundle bundles = new Bundle();
                        bundles.putString("url", pushMessage.getUrl());
                        mIntent.putExtra(BaseFragmentActivity.PARAM_INTENT, bundles);
                        mIntent.setClass(context, LoginActivity.class);
                        int requestID = (int) System.currentTimeMillis();
//                    mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        PendingIntent contentIntent =
                                PendingIntent.getActivity(context, requestID, mIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setLatestEventInfo(context, "一元云涛",
                                pushMessage.getContent(), contentIntent);
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notificationManager.notify(1, notification);

                    }


                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
//                if (GetuiSdkDemoActivity.tView != null) {
//                    GetuiSdkDemoActivity.tView.setText(cid);
//                }
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }
}
