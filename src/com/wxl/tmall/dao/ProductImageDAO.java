package com.wxl.tmall.dao;

import com.wxl.tmall.bean.Product;
import com.wxl.tmall.bean.ProductImage;
import com.wxl.tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAO {
    //两种静态属性分别表示单个和详情图片
    public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";
    public static void main(String[] args) {
        ProductImageDAO p = new ProductImageDAO();
        ProductImage productImage = new ProductImage();
        Product product = new Product();
        product.setId(5);
        productImage.setProduct(product);
        productImage.setType("5");
        p.add(productImage);
        System.out.println(productImage.getId());
    }

    public int getTotal() {
        int total = 0;
        String sql = "select count(*) from productimage";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(ProductImage bean) {
        String sql = "insert into productimage values(null,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
        ) {
            ps.setInt(1, bean.getProduct().getId());
            ps.setString(2, bean.getType());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bean.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //不需要更新
    public void update(ProductImage bean) {
    }

    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
            String sql = "delete from ProductImage where id = " + id;
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductImage get(int id) {
        ProductImage bean = null;
        String sql = "select * from productimage where id =" + id;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bean = new ProductImage();
                bean.setId(id);
                bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
                bean.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    //业务方法 查询指定产品下 某种类型的productimage
    public List<ProductImage> list(Product p, String type) {
        return list(p, type, 0, Short.MAX_VALUE);
    }

    public List<ProductImage> list(Product p, String type, int start, int count) {
        List<ProductImage> beans = new ArrayList<>();

        String sql = "select * from productimage where pid = ? and type = ? order by id desc limit ?,?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
        ) {
            ps.setInt(1,p.getId());
            ps.setString(2,type);
            ps.setInt(3,start);
            ps.setInt(4,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductImage bean = new ProductImage();
                bean.setId(rs.getInt("id"));
                bean.setType(type);
                bean.setProduct(p);

                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
}
