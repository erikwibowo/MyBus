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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import tools.KoneksiDB;

/**
 *
 * @author ERIK
 */
public class DetailTransaksi {
    public String vid_transaksi, vid_bus, vkelas_bus, vno_polisi, vid_kursi, vno_kursi, vnama_penumpang, vid_user, vnama_user, vtanggal, vjam;
    public int vid_detailtrans;
    DateFormat ftanggal = new SimpleDateFormat("yyyy-m-d");
    DateFormat fjam = new SimpleDateFormat("HH:mm:ss");
    Date dtanggal = new Date();
    Date djam = new Date();
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tbDetailTransaksi = new DefaultTableModel();
    public List<Object> list;
    
    public void select(String id){
        tbDetailTransaksi.setColumnIdentifiers(new Object[]{"ID. Detail Transaksi", "ID. Transaksi", "No. Polisi", "Kelas Bus",""
                + "No. Kursi","Nama Penumpang", "Tanggal"});
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_detailtransaksi a, tb_transaksi b, tb_bus c, tb_supir d, tb_kursi e "
                    + "where a.id_transaksi=b.id_transaksi and b.id_bus=c.id_bus and b.id_supir=d.id_supir and a.id_kursi=e.id_kursi "
                    + "and a.id_transaksi='"+id+"' order by id_detailtransaksi desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            list = new ArrayList<>();
            
            while (res.next()) {          
                vid_detailtrans = res.getInt("id_detailtransaksi");
                vid_transaksi = res.getString("id_transaksi");
                vno_polisi = res.getString("no_polisi");
                vkelas_bus = res.getString("class_bus");
                vno_kursi = res.getString("nomor_kursi");
                vnama_penumpang = res.getString("nama_penumpang");
                vtanggal = res.getString("tanggal");
                
                list.add(new Object[]{vid_detailtrans, vid_transaksi, vno_polisi, vkelas_bus, vno_kursi, vnama_penumpang, vtanggal});
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
                query = "insert into tb_detailtransaksi values(?,?,?,?,null)";
            }else{
                query = "update tb_detailtransaksi set id_detailtransaksi=?, id_transaksi=?, id_kursi=?,"
                        + " nama_penumpang=? "
                        + "where id_detailtransaksi='"+vid_detailtrans+"' ";
            }
            
            stat = _Cnn.prepareStatement(query);
            stat.setInt(1, vid_detailtrans);
            stat.setString(2, vid_transaksi);
            stat.setString(3, vid_kursi);
            stat.setString(4, vnama_penumpang);
            stat.executeUpdate();
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method insert_update() : "+e);
        }
    }
    
    public void delete( int vid){
        try {
            _Cnn = getCnn.getConnection();
            query = "delete from tb_detailtransaksi where id_detailtransaksi='"+vid+"'";
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
            query = "select max(id_detailtransaksi) from tb_detailtransaksi";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            if (res.next()) {
                vid_detailtrans = res.getInt(1)+1;
            }else{
                vid_detailtrans = 1;
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() "+e,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
