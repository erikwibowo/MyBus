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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import tools.KoneksiDB;

/**
 *
 * @author ERIK
 */
public class Jadwal {
    public String vid_jadwal, vtanggal, vjam, vtujuan;
    public SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tbljadwal = new DefaultTableModel();
    public List<Object> list;
    
    public void select(){
        tbljadwal.setColumnIdentifiers(new Object[]{"ID. Jadwal", "Tanggal", "Jam", "Tujuan"});
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_jadwal order by id_jadwal desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            list = new ArrayList<>();
            
            while (res.next()) {                
                vid_jadwal = res.getString("id_jadwal");
                vtanggal = res.getString("tgl_berangkat");
                vjam = res.getString("jam_berangkat");
                vtujuan = res.getString("tujuan");
                
                list.add(new Object[]{vid_jadwal, vtanggal, vjam, vtujuan});
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
                query = "insert into tb_jadwal values(?,?,?,?)";
            }else{
                query = "update tb_jadwal set id_jadwal=?, tgl_berangkat=?, jam_berangkat=?, tujuan=? "
                        + " where id_jadwal='"+vid_jadwal+"' ";
            }
            
            stat = _Cnn.prepareStatement(query);
            stat.setString(1, vid_jadwal);
            stat.setString(2, vtanggal);
            stat.setString(3, vjam);
            stat.setString(4, vtujuan);
            stat.executeUpdate();
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method insert_update() : "+e);
        }
    }
    
    public void delete( String vid_jadwal){
        try {
            _Cnn = getCnn.getConnection();
            query = "delete from tb_jadwal where id_jadwal='"+vid_jadwal+"'";
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
            query = "select * from tb_jadwal order by id_jadwal desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            int row = res.getRow();
            if (res.next()) {
                String id_trans = res.getString("id_jadwal").substring(2);
                String AN = "" + (Integer.parseInt(id_trans) + 1);
                String Nol = "";

                if(AN.length()==1){
                    Nol = "00";
                }else if(AN.length()==2){
                    Nol = "0";
                }else if(AN.length()==3){
                    Nol = "";
                }

               vid_jadwal = "JD"+ Nol + AN;
            } else {
               vid_jadwal = "JD001";
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() "+e,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
