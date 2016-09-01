package com.zy.open.demo.recycler.bean;

/**
 * Created by zhangll on 16/8/17.
 */
public class MultiTypeItem {
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;

    public int type;
    public String content = "";

    public MultiTypeItem(int type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return "MultiTypeItem{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
