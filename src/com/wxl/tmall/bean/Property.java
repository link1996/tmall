package com.wxl.tmall.bean;

public class Property {
    private  String name;
    //为什么在Property类里不用一个数字类型的cid属性呢？ 因为在页面上
    // 显示的时候，需要通过属性获取分类的名称， 如果是一个数字类型的cid属性，获取起来就会比较繁琐了。
    private Category category;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
