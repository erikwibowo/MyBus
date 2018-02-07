/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import tools.KoneksiDB;

/**
 *
 * @author ERIK
 */
public class User {
    public String vid_user, vnama_user, vpass, valamat, vjk, vno_telp;
    
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tbluser = new DefaultTableModel();
    public List<Object> list;
    
    public void select(){
        tbluser.setColumnIdentifiers(new Object[]{"ID. User", "Username", "Password","Alamat","L/P","No. Telp"});
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_user";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            list = new ArrayList<>();
            
            while (res.next()) {                
                vid_user = res.getString("id_user");
                vnama_user = res.getString("nama_user");
                vpass = res.getString("password");
                valamat = res.getString("alamat");
                vjk = res.getString("jk");
                vno_telp = res.getString("telp");
                
                list.add(new Object[]{vid_user, vnama_user, vpass, valamat, vjk, vno_telp});
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method select() "+e);
        }
    }
    
    public void insert_update(){
        try {
            _Cnn = getCnn.getConnection();
            if(isUpdate == false){
                query = "insert into tb_user values(?,?,?,?,?,?)";
            }else{
                query = "update tb_user set id_user=?, nama_user=?, password=?,"
                        + " alamat=?, jk=?, telp=? "
                        + "where id_user='"+vid_user+"' ";
            }
            
            stat = _Cnn.prepareStatement(query);
            stat.setString(1, vid_user);
            stat.setString(2, vnama_user);
            stat.setString(3, vpass);
            stat.setString(4, valamat);
            stat.setString(5, vjk);
            stat.setString(6, vno_telp);
            stat.executeUpdate();
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method insert_update() : "+e);
        }
    }
    
    public void delete( String vid_user){
        try {
            _Cnn = getCnn.getConnection();
            query = "delete from tb_user where id_user='"+vid_user+"'";
            stat = _Cnn.prepareStatement(query);
            stat.executeUpdate();
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void createAutoID(){
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_user order by id_user desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            int row = res.getRow();
            if (res.next()) {
                String id_trans = res.getString("id_user").substring(2);
                String AN = "" + (Integer.parseInt(id_trans) + 1);
                String Nol = "";

                if(AN.length()==1){
                    Nol = "00";
                }else if(AN.length()==2){
                    Nol = "0";
                }else if(AN.length()==3){
                    Nol = "";
                }

               vid_user = "ID"+ Nol + AN;
            } else {
               vid_user = "ID001";
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() "+e,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
