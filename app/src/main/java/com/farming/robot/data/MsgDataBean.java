package com.farming.robot.data;


import android.util.Log;

import com.farming.robot.util.ByteUtil;
import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Tony on 2017/10/24.
 */

public class MsgDataBean implements ISendable {
    private String content = "";

    public MsgDataBean(String content) {
        this.content = content;
    }

    @Override
    public byte[] parse() {
//        byte[] body = content.getBytes(Charset.defaultCharset());
        byte[] body = ByteUtil.hexStr2bytes(content);
        ByteBuffer bb = ByteBuffer.allocate(4 + body.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(body.length);
        Log.i("ServerCallback发送",body.length+"");
        bb.put(body);
        return body;
//        return body;
    }
}
