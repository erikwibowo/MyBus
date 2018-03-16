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
public class Transaksi {
    public String vid_transaksi, vid_jadwal, vtujuan, vid_supir, vnama_supir, vid_bus, vkelas, vharga, vno_polisi, vid_user, vnama_user, vtanggal, vjam;
    public String vkelas1, vharga1, vno_polisi1;
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tbTransaksi = new DefaultTableModel();
    public List<Object> list;
    
    public void select(){
        tbTransaksi.setColumnIdentifiers(new Object[]{"ID. Transaksi", "No.Polisi", "Kelas", "Harga Tiket", "Nama Supir","Tujuan","Tanggal","Jam", "Petugas"});
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_transaksi a, tb_jadwal b, tb_user c, tb_supir d, tb_bus e "
                    + "where a.id_jadwal=b.id_jadwal and a.id_user=c.id_user and a.id_supir=d.id_supir "
                    + "and a.id_bus=e.id_bus order by id_transaksi desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            list = new ArrayList<>();
            
            while (res.next()) {                
                vid_transaksi = res.getString("id_transaksi");
                vno_polisi = res.getString("no_polisi");
                vkelas = res.getString("class_bus");
                vharga = res.getString("harga_tiket");
                vnama_supir = res.getString("nama_supir");
                vtujuan = res.getString("tujuan");
                vtanggal = res.getString("tgl_berangkat");
                vjam = res.getString("jam_berangkat");
                vnama_user = res.getString("nama_user");
                
                list.add(new Object[]{vid_transaksi, vno_polisi, vkelas, vharga, vnama_supir, vtujuan, vtanggal, vjam, vnama_user});
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method select() "+e);
        }
    }
    
    public void selectId(String id){
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_bus where id_bus='"+id+"'";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            
            while (res.first()) {                
                vkelas1 = res.getString("class_bus");
                vharga1 = res.getString("harga_tiket");
                vno_polisi1 = res.getString("no_polisi");
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method selectid() "+e);
        }
    }
    
    public void insert_update(){
        try {
            _Cnn = getCnn.getConnection();
            if(isUpdate == false){
                query = "insert into tb_transaksi values(?,?,?,?,?)";
            }else{
                query = "update tb_transaksi set id_transaksi=?, id_jadwal=?, id_user=?,"
                        + " id_supir=?, id_bus=? "
                        + "where id_transaksi='"+vid_transaksi+"' ";
            }
            
            stat = _Cnn.prepareStatement(query);
            stat.setString(1, vid_transaksi);
            stat.setString(2, vid_jadwal);
            stat.setString(3, vid_user);
            stat.setString(4, vid_supir);
            stat.setString(5, vid_bus);
            stat.executeUpdate();
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method insert_update() : "+e);
        }
    }
    
    public void delete( String vid_user){
        try {
            _Cnn = getCnn.getConnection();
            query = "delete from tb_transaksi where id_transaksi='"+vid_user+"'";
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
            query = "select * from tb_transaksi order by id_transaksi desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            int row = res.getRow();
            if (res.next()) {
                String id_trans = res.getString("id_transaksi").substring(5);
                String AN = "" + (Integer.parseInt(id_trans) + 1);
                String Nol = "";

                if(AN.length()==1){
                    Nol = "00";
                }else if(AN.length()==2){
                    Nol = "0";
                }else if(AN.length()==3){
                    Nol = "";
                }

               vid_transaksi = "TRANS"+ Nol + AN;
            } else {
               vid_transaksi = "TRANS0001";
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() "+e,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
