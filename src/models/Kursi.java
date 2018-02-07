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

/**
 *
 * @author Qiena
 */
public class Kursi {
    public String id_kursi;
    public String vno_kursi;
    
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tblkursi = new DefaultTableModel();
    public List<Object> list;
    
    
    public void select(){
        tblkursi.setColumnIdentifiers(new Object[]{"ID. Kursi", "Nomor Kursi"});//set nama column
        try{
            _Cnn = getCnn.getConnection();
            query = "select * from tb_kursi";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            list = new ArrayList<>();
            while(res.next()){
                id_kursi = res.getString("id_kursi");
                vno_kursi = res.getString("nomor_kursi");
                
                list.add(new Object []{id_kursi, vno_kursi});//set isi masing2 column
                
            }
        stat.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error method select() : "+ex);
            
        }
    }
    
    public void insert_update(){
        try{
            _Cnn = getCnn.getConnection();
            if(isUpdate == false){
                query = " insert into tb_kursi values(?,?) ";
            }else {
                query = " update tb_kursi set id_kursi=?, nomor_kursi=?"
                        + "where id_kursi='"+id_kursi+"' ";
            }
            
            stat = _Cnn.prepareStatement(query);
            stat.setString(1, id_kursi); 
            stat.setString(2, vno_kursi);
            stat.executeUpdate();
            stat.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error method insert_update() : "+ex);
        }
    }
    
    public void delete(String id_kursi){
        try{
            _Cnn = getCnn.getConnection();
            query = "delete from tb_kursi where id_kursi='"+id_kursi+"' ";
            stat = _Cnn.prepareStatement(query);
            stat.executeUpdate();
            stat.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void createAutoID(){
        try {
            _Cnn = getCnn.getConnection();
            query = "select * from tb_kursi order by id_kursi desc";
            stat = _Cnn.prepareStatement(query);
            res = stat.executeQuery(query);
            int row = res.getRow();
            if (res.next()) {
                String id_trans = res.getString("id_kursi").substring(2);
                String AN = "" + (Integer.parseInt(id_trans) + 1);
                String Nol = "";

                if(AN.length()==1){
                    Nol = "00";
                }else if(AN.length()==2){
                    Nol = "0";
                }else if(AN.length()==3){
                    Nol = "";
                }

               id_kursi = "KS"+ Nol + AN;
            } else {
               id_kursi = "KS001";
            }
            stat.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() "+e,
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    } 
   
}
