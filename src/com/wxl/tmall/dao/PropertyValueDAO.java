package com.wxl.tmall.dao;

import com.wxl.tmall.bean.Product;
import com.wxl.tmall.bean.Property;
import com.wxl.tmall.bean.PropertyValue;
import com.wxl.tmall.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyValueDAO {
    public int getTotal(){
        int total = 0;
        String sql = "select * from propertyvalue";
        try(Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void add(PropertyValue bean){
        String sql = "insert into propertyvalue values(null,?,?,?)";
        try(Connection c = DBUtil.getConnection();
               PreparedStatement ps = c.prepareStatement(sql);
                ){
            ps.setInt(1,bean.getProduct().getId());
            ps.setInt(2,bean.getProperty().getId());
            ps.setString(3,bean.getValue());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                bean.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id){
        String sql = "delete from propertyvalue where id = "+ id;
        try(Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(PropertyValue bean){
        String sql = "update propertyvalue set pid = ?, ptid = ?, value = ? where id = ?";
        try(Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ){
            ps.setInt(1,bean.getProduct().getId());
            ps.setInt(2,bean.getProperty().getId());
            ps.setString(3,bean.getValue());
            ps.setInt(4,bean.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PropertyValue get(int id){
        PropertyValue bean = new PropertyValue();
        String sql ="select * from propertyimage where id = "+ id;
        try(Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                bean.setId(id);
                bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
                bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
                bean.setValue(rs.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  bean;
    }
    public PropertyValue get(int ptid, int pid){
        PropertyValue bean = null;
        String sql = "select * from propertyimage where ptid = ? and pid = ?";

        try(Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ){
            ps.setInt(1,ptid);
            ps.setInt(2,pid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                bean = new PropertyValue();
                bean.setValue(rs.getString("value"));
                bean.setProperty(new PropertyDAO().get(pid));
                bean.setProduct(new ProductDAO().get(pid));
                bean.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }
    public List<PropertyValue> list(){
        return list();
    }
    public List<PropertyValue> list(int start,int count){
        List<PropertyValue> beans = new ArrayList<>();

        String sql = "select * from propertyvalue order by id desc limit "+ start+","+count;
        try(Connection c = DBUtil.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PropertyValue bean = new PropertyValue();
                bean.setId(rs.getInt("id"));
                bean.setProduct(new ProductDAO().get(rs.getInt("pid")));
                bean.setProperty(new PropertyDAO().get(rs.getInt("ptid")));
                bean.setValue(rs.getString("value"));
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }
    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue where pid = ? order by ptid desc";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, pid);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }
}
