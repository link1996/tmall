package com.wxl.tmall.bean;

import java.util.List;

public class Category {
    private String name;
    private int id;
    //一对多关系 products setter getter
    List<Product> products;
    //一个分类会对应多行产品，而一行产品里又有多个产品记录。
    //一个分类又对应多个 List<Product>，提供这个属性，是为了在首页竖状导航的分类名称右边显示产品列表
    List<List<Product>> productsByRow;

    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
