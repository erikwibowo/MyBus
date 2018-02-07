
package models;

import tools.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Supir {
    public String vid_supir;
    public String vnama_supir, valamat, vnosim;
    public String vjk, vtelp;
    
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tblsupir = new DefaultTableModel();
    public List<Object> list;
    
    public void select(){
        tblsupir.setColumnIdentifiers(new Object[]{"ID. Supir", "Nama Supir", "No. Sim",
            "No. Hp", "Jenis Kelamin", "Alamat"});
        try{
            _Cnn = getCnn.getConnection();
            query = "select * from tb_supir order by id_supir desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery();
            list = new ArrayList<>();
            
            while (res.next()){
                vid_supir = res.getString("id_supir");
                vnama_supir = res.getString("nama_supir");
                vnosim = res.getString("no_sim");
                vtelp = res.getString("no_hp");
                vjk = res.getString("jk");
                valamat = res.getString("alamat");
                
                list.add(new Object[]{vid_supir, vnama_supir, vnosim, vtelp, vjk, valamat});
            }
            stat.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error method select() :"+ex);
        }
    }
    
    public void insert_update(){
        try{
            _Cnn = getCnn.getConnection();
            if(isUpdate == false){
                query = "insert into tb_supir values(?,?,?,?,?,?)";
            }else{
                query = "update tb_supir set id_supir=?, nama_supir=?, no_sim=?, no_hp=?, jk=?, alamat=? "
                        + "where id_supir='"+vid_supir+"' ";
            }
            stat = _Cnn.prepareStatement(query);
            stat.setString(1, vid_supir);
            stat.setString(2, vnama_supir);
            stat.setString(3, vnosim);
            stat.setString(4, vtelp);
            stat.setString(5, vjk);
            stat.setString(6, valamat);
            stat.executeUpdate();
            stat.close();
        }catch(SQLException ex){
            
        }
    }
    
    public void delete(String id_supir){
        try{
            _Cnn = getCnn.getConnection();
            query = "delete from tb_supir where id_supir='"+vid_supir+"' ";
            stat = _Cnn.prepareStatement(query);
            stat.executeUpdate();
            stat.close();
        }catch(SQLException ex){
            
        }
    }
    
    public void createAutoID(){
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_supir order by id_supir desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            int row = res.getRow();
            if (res.next()) {
                String id_trans = res.getString("id_supir").substring(2);
                String AN = "" + (Integer.parseInt(id_trans) + 1);
                String Nol = "";

                if(AN.length()==1){
                    Nol = "00";
                }else if(AN.length()==2){
                    Nol = "0";
                }else if(AN.length()==3){
                    Nol = "";
                }

               vid_supir = "SP"+ Nol + AN;
            } else {
               vid_supir = "SP001";
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() "+e,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
