package com.waskj.hongloumeng.common;

import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.platformtools.Util;
import com.waskj.hongloumeng.main.Constants;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class WeiXinShare {

    Context context;
    Bitmap bitmap;

    public WeiXinShare(Context context, Bitmap bitmap){
        this.context = context;
        this.bitmap = bitmap;
    }
    /**
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    public void wechatShare(int flag){
        IWXAPI wxApi;
        //实例化
        wxApi = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        wxApi.registerApp(Constants.APP_ID);


//        WXWebpageObject webpage = new WXWebpageObject();
//        String webUrl = "http://www.waskj.com";
//        webpage.webpageUrl = webUrl;
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        //msg.mediaObject = webpage;
//        msg.title = "三国志";
//        msg.description = "古典文学名著";
//        //这里替换一张自己工程里的图片资源
//        msg.setThumbImage(null);

        //-----------------
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);
        //-----------------

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis()) + "00";
        req.message = msg;
        req.scene = flag==0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }
}
