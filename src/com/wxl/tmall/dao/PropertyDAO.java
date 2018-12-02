package com.wxl.tmall.dao;

import com.wxl.tmall.bean.Category;
import com.wxl.tmall.bean.Property;
import com.wxl.tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    public static void main(String[] args) {
        int test = new PropertyDAO().getTotal(1);
    }
    //获取某种分类下的属性总数
    public int getTotal(int cid){
        int total = 0;
        String sql = "select count(*) from property where cid = "+cid;
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    //查询某个分类下的属性对象
    public List<Property> list(int cid, int start, int count){
        List<Property> beans = new ArrayList<>();
        String sql = "select * from property where cid = "+ cid;
        try(Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ){

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return beans;
    }
    //查询某个分类下的属性对象
    public List<Property> list(int cid){
        return list(cid,0,Short.MAX_VALUE);
    }


    public void add(Property bean) {

        String sql = "insert into Property values(null,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(Property bean) {

        String sql = "update Property set cid= ?, name=? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
            ps.setInt(3, bean.getId());
            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "delete from Property where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public Property get(int id) {
        Property bean = new Property();

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select * from Property where id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
                String name = rs.getString("name");
                int cid = rs.getInt("cid");
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setId(id);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }

}
