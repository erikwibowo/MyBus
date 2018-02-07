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
 * @author QOMARIYAH
 */
public class Bus {
    
    public String vid_bus, vclass_bus, vno_polisi;
    public String vharga_tiket;
    
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tblbus = new DefaultTableModel();
    public List<Object> list;
    
    public void select(){
        tblbus.setColumnIdentifiers(new Object[]{"ID. Bus", "Class Bus", "Harga Tiket", "No. Polisi"});
        try {
            _Cnn    = getCnn.getConnection();
            query   = "select * from tb_bus order by id_bus desc";
            stat    = _Cnn.prepareStatement(query);
            res     = stat.executeQuery(query);
            list    = new ArrayList<>();
            while (res.next()) {
                vid_bus         = res.getString("id_bus");
                vclass_bus      = res.getString("class_bus");
                String no    = Integer.toString(res.getInt("harga_tiket"));
                String n3 = no.substring(2);
                String n2 = no.substring(0, 2);
                vharga_tiket = n2+"."+n3;
                vno_polisi      = res.getString("no_polisi");
                
                list.add(new Object[]{vid_bus, vclass_bus, vharga_tiket, vno_polisi});
            }
            stat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error method select() : " + ex);
        }
    }
    
    public void insert_update(){
        try {
            _Cnn = getCnn.getConnection();
            if (isUpdate == false) {
                query = "insert into tb_bus values (?,?,?,?)";
            } else {
                query = "update tb_bus set id_bus=?, class_bus=?, harga_tiket=?, no_polisi=?"
                        + " where id_bus = '" + vid_bus + "' ";
            }
            
            stat = _Cnn.prepareStatement(query);
            stat.setString(1, vid_bus);
            stat.setString(2, vclass_bus);
            stat.setString(3, vharga_tiket);
            stat.setString(4, vno_polisi);
            stat.executeUpdate();
            stat.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error method insert_update() : " + ex);
        }
    }
    
    //menggunakan prepareStatement lebih cepat daripada statement.
    public void delete(String vid_bus){
        try {
            _Cnn    = getCnn.getConnection();
            query   = "delete from tb_bus where id_bus ='" + vid_bus + "' ";
            stat    = _Cnn.prepareStatement(query);
            stat.executeUpdate();
            stat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void createAutoID(){
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_bus order by id_bus desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            int row = res.getRow();
            if (res.next()) {
                String id_trans = res.getString("id_bus").substring(2);
                String AN = "" + (Integer.parseInt(id_trans) + 1);
                String Nol = "";

                if(AN.length()==1){
                    Nol = "00";
                }else if(AN.length()==2){
                    Nol = "0";
                }else if(AN.length()==3){
                    Nol = "";
                }

               vid_bus = "BS"+ Nol + AN;
            } else {
               vid_bus = "BS001";
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() "+e,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}
