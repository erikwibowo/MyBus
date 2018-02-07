/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DetailTransaksi;
import models.Transaksi;
import models.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import tools.KoneksiDB;

/**
 *
 * @author ERIK
 */
public class IfrTiket extends javax.swing.JInternalFrame {
    
    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    DetailTransaksi dtrans = new DetailTransaksi();
    
    Transaksi trans = new Transaksi();
    User user = new User();
    Home home = new Home();
    

    /**
     * Creates new form Bus
     */
    public IfrTiket(String id) {
        initComponents();
        
        formTengah();
        
        setTabelTiket();
        showDataTiket();
        
        clearInputTiket();
        disableInputTiket();
        
        listBus();
        listSupir();
        listJadwal();
        txtIdUser.setText(id);
        txtIdUser.setEditable(false);
        
        txtIdTrans.setEditable(false);
        
        
    }
    
    private void clearInputTiket(){
        txtIdTrans.setText("");
        cmbBus.setSelectedIndex(0);
        cmbSupir.setSelectedIndex(0);
        cmbJadwal.setSelectedIndex(0);
        lblTambah.setText("Tambah");
        btnTambah.setBackground(new java.awt.Color(21,184,90));
        lblSimpan.setText("Simpan");
        lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_add_white_18dp.png")));
    }
    
    private void disableInputTiket(){
        cmbBus.setEnabled(false);
        cmbSupir.setEnabled(false);
        cmbJadwal.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInputTiket(){
        cmbBus.setEnabled(true);
        cmbSupir.setEnabled(true);
        cmbJadwal.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    
    private void setTabelTiket(){ //mengatur tampilan tabel
        String[] kolom1 = {"ID. Transaksi", "No.Polisi", "Kelas", "Harga Tiket", "Nama Supir","Tujuan","Tanggal","Jam", "Petugas"};
        trans.tbTransaksi = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class
            };
            
            @Override
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            @Override
            //agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola = trans.tbTransaksi.getColumnCount();
                return (col < cola) ? false:true;
            }
        };
        tbDataTrans.setModel(trans.tbTransaksi);
        tbDataTrans.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataTrans.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbDataTrans.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbDataTrans.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbDataTrans.getColumnModel().getColumn(4).setPreferredWidth(100);
        tbDataTrans.getColumnModel().getColumn(5).setPreferredWidth(25);
        tbDataTrans.getColumnModel().getColumn(6).setPreferredWidth(100);
        tbDataTrans.getColumnModel().getColumn(7).setPreferredWidth(100);
        tbDataTrans.getColumnModel().getColumn(8).setPreferredWidth(100);
    }
    
    private void clearTabelTiket(){
        int row = trans.tbTransaksi.getRowCount();
        for (int i = 0; i < row; i++) {
            trans.tbTransaksi.removeRow(0);
        }
    }
    
    private void showDataTiket(){
        tbDataTrans.setModel(trans.tbTransaksi);
        int row = trans.tbTransaksi.getRowCount();
        for (int i = 0; i < row; i++) {
            trans.tbTransaksi.removeRow(0);
        }
        trans.select();
        for (Object obj : trans.list) {
            trans.tbTransaksi.addRow((Object[]) obj);
        }
        
        lblRecord.setText("Record : "+tbDataTrans.getRowCount());
    }
    
    private void aksiSimpanTiket(){
        if (cmbBus.getSelectedIndex()<=0) {
            JOptionPane.showMessageDialog(this, "Mohon pilih bus!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbBus.requestFocus(true);
        }else if (cmbSupir.getSelectedIndex()<=0) {
            JOptionPane.showMessageDialog(this, "Mohon pilih supir!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbSupir.requestFocus(true);
        }else if (cmbJadwal.getSelectedIndex()<=0) {
            JOptionPane.showMessageDialog(this, "Mohon pilih jadwal!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbJadwal.requestFocus(true);
        }else if (txtIdUser.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mohon isi ID user!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtIdUser.requestFocus(true);
        }else {
            trans.isUpdate = !lblSimpan.getText().equals("Simpan");
            trans.vid_transaksi = txtIdTrans.getText();
            trans.vid_bus = KeyBus[cmbBus.getSelectedIndex()];
            trans.vid_supir = KeySupir[cmbSupir.getSelectedIndex()];
            trans.vid_jadwal = KeyJadwal[cmbJadwal.getSelectedIndex()];
            trans.vid_user = txtIdUser.getText();
            trans.insert_update();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            
            clearInputTiket();
            disableInputTiket();
            showDataTiket();
        }
    }
    
    
    private void aksiHapusTiket(){
        if (txtIdTrans.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihpus!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda akan menghapus data ini? Kode : "+trans.vid_transaksi,
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (jawab == JOptionPane.YES_OPTION) {
                trans.delete(trans.vid_transaksi);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                clearInputTiket(); disableInputTiket(); showDataTiket();
            }
        }
    }
    
    private void formTengah(){
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = this.getSize();
        if(framesize.height < screensize.height){
            framesize.height = screensize.height;
        }
        if(framesize.width > screensize.width){
            framesize.width = screensize.width;
        }
        this.setLocation((screensize.width - framesize.width)/2, 
                (screensize.height - framesize.height)/2);
    }
    
    String[] KeyBus;
    private void listBus(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            String sql = "select * from tb_bus order by id_bus asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res=stat.executeQuery(sql);
            cmbBus.removeAllItems();
            cmbBus.repaint();
            cmbBus.addItem("-- Pilih Bus --");
            int i = 1;
            while(res.next()){
                String kls = res.getString("class_bus");
                String nopol = res.getString("no_polisi");
                cmbBus.addItem(nopol+" - "+kls);
                i++;
            }
            res.first();
            KeyBus = new String[i+1];
            for(Integer x = 1;x < i;x++){
                KeyBus[x] = res.getString("id_bus");
                res.next();
            }
        } catch (SQLException ex) {
            System.out.println("Error method listBus() : "+ex);
        }
    }
    
    String[] KeySupir;
    private void listSupir(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            String sql = "select * from tb_supir order by nama_supir asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res=stat.executeQuery(sql);
            cmbSupir.removeAllItems();
            cmbSupir.repaint();
            cmbSupir.addItem("-- Pilih Supir --");
            int i = 1;
            while(res.next()){
                cmbSupir.addItem(res.getString("nama_supir"));
                i++;
            }
            res.first();
            KeySupir = new String[i+1];
            for(Integer x = 1;x < i;x++){
                KeySupir[x] = res.getString("id_supir");
                res.next();
            }
        } catch (SQLException ex) {
            System.out.println("Error method listSupir() : "+ex);
        }
    }
    
    String[] KeyJadwal;
    private void listJadwal(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            String sql = "select * from tb_jadwal order by tujuan asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res=stat.executeQuery(sql);
            cmbJadwal.removeAllItems();
            cmbJadwal.repaint();
            cmbJadwal.addItem("-- Pilih tujuan & keberangkatan --");
            int i = 1;
            while(res.next()){
                cmbJadwal.addItem(res.getString("tujuan")+" - "+res.getString("tgl_berangkat")+" - "+res.getString("jam_berangkat"));
                i++;
            }
            res.first();
            KeyJadwal = new String[i+1];
            for(Integer x = 1;x < i;x++){
                KeyJadwal[x] = res.getString("id_jadwal");
                res.next();
            }
        } catch (SQLException ex) {
            System.out.println("Error method listJadwal() : "+ex);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtIdTrans = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        cmbBus = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JPanel();
        lblTambah = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JPanel();
        lblSimpan = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        cmbSupir = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cmbJadwal = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataTrans = new javax.swing.JTable();
        btnHapus = new javax.swing.JPanel();
        lblHapus = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Transaksi");
        setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/source/Ticket_18px_1.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(106, 27, 154));

        jPanel4.setBackground(new java.awt.Color(156, 42, 225));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(156, 42, 225), 1, true));

        txtIdTrans.setBackground(new java.awt.Color(156, 42, 225));
        txtIdTrans.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIdTrans.setForeground(new java.awt.Color(255, 255, 255));
        txtIdTrans.setBorder(null);
        txtIdTrans.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdTrans.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIdTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdTransActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID Transaksi");

        cmbBus.setBackground(new java.awt.Color(156, 42, 225));
        cmbBus.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        cmbBus.setForeground(new java.awt.Color(255, 255, 255));
        cmbBus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbBus.setBorder(null);
        cmbBus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBusItemStateChanged(evt);
            }
        });
        cmbBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBusActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pilih Bus");

        btnTambah.setBackground(new java.awt.Color(21, 184, 90));
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahMouseClicked(evt);
            }
        });

        lblTambah.setBackground(new java.awt.Color(21, 184, 90));
        lblTambah.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblTambah.setForeground(new java.awt.Color(255, 255, 255));
        lblTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/source/ic_add_white_18dp.png"))); // NOI18N
        lblTambah.setText("Tambah");

        javax.swing.GroupLayout btnTambahLayout = new javax.swing.GroupLayout(btnTambah);
        btnTambah.setLayout(btnTambahLayout);
        btnTambahLayout.setHorizontalGroup(
            btnTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTambahLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTambah)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnTambahLayout.setVerticalGroup(
            btnTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTambahLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblTambah)
                .addGap(5, 5, 5))
        );

        btnSimpan.setBackground(new java.awt.Color(0, 113, 255));
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
        });

        lblSimpan.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblSimpan.setForeground(new java.awt.Color(255, 255, 255));
        lblSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/source/ic_save_white_18dp.png"))); // NOI18N
        lblSimpan.setText("Simpan");

        javax.swing.GroupLayout btnSimpanLayout = new javax.swing.GroupLayout(btnSimpan);
        btnSimpan.setLayout(btnSimpanLayout);
        btnSimpanLayout.setHorizontalGroup(
            btnSimpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSimpanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSimpan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSimpanLayout.setVerticalGroup(
            btnSimpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSimpanLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblSimpan)
                .addGap(5, 5, 5))
        );

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Petugas");

        txtIdUser.setBackground(new java.awt.Color(156, 42, 225));
        txtIdUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIdUser.setForeground(new java.awt.Color(255, 255, 255));
        txtIdUser.setBorder(null);
        txtIdUser.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdUser.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIdUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdUserKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdUserKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Pilih Supir");

        cmbSupir.setBackground(new java.awt.Color(156, 42, 225));
        cmbSupir.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        cmbSupir.setForeground(new java.awt.Color(255, 255, 255));
        cmbSupir.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbSupir.setBorder(null);

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Pilih Jadwal");

        cmbJadwal.setBackground(new java.awt.Color(156, 42, 225));
        cmbJadwal.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        cmbJadwal.setForeground(new java.awt.Color(255, 255, 255));
        cmbJadwal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbJadwal.setBorder(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdTrans))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbJadwal, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbSupir, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbBus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(txtIdTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbBus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSupir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(0, 0, 0)
                .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(156, 42, 225));

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        tbDataTrans.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        tbDataTrans.setForeground(new java.awt.Color(51, 51, 51));
        tbDataTrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "No. Polisi Bus", "Kelas Bus", "Harga Tiket", "Nama Supir", "Tujuan", "Tanggal", "Jam", "Petugas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDataTrans.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataTransMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataTrans);

        btnHapus.setBackground(new java.awt.Color(240, 50, 95));
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });

        lblHapus.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblHapus.setForeground(new java.awt.Color(255, 255, 255));
        lblHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/source/ic_delete_white_18dp.png"))); // NOI18N
        lblHapus.setText("Hapus");

        javax.swing.GroupLayout btnHapusLayout = new javax.swing.GroupLayout(btnHapus);
        btnHapus.setLayout(btnHapusLayout);
        btnHapusLayout.setHorizontalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHapusLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lblHapus)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        btnHapusLayout.setVerticalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHapusLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblHapus)
                .addGap(5, 5, 5))
        );

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tabel Transaksi : klik 1x untuk ubah/hapus, klik 2x untuk detail transaksi");

        lblRecord.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblRecord.setText("Record : ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblRecord)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDataTransMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataTransMouseClicked
        if(evt.getClickCount()==1){
            int row = tbDataTrans.getSelectedRow();
            trans.vid_transaksi = tbDataTrans.getValueAt(row, 0).toString();
            trans.vkelas = tbDataTrans.getValueAt(row, 2).toString();
            trans.vnama_supir = tbDataTrans.getValueAt(row, 4).toString();
            trans.vtujuan = tbDataTrans.getValueAt(row, 5).toString();
            
            txtIdTrans.setText(trans.vid_transaksi);
            cmbBus.setSelectedItem(trans.vkelas);
            cmbSupir.setSelectedItem(trans.vnama_supir);
            cmbJadwal.setSelectedItem(trans.vtujuan);
            
            enableInputTiket();
            txtIdTrans.setEditable(false);
            btnHapus.setEnabled(true);
            lblSimpan.setText("Ubah");
            txtIdTrans.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240,50,95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_cancel_white_18dp.png")));
        }else if(evt.getClickCount()==2){
            IfrDetailTiket fr = new IfrDetailTiket(trans.vid_transaksi);
            this.getParent().add(fr);
            fr.setVisible(true);
            try {
                fr.setMaximum(true);
            } catch (PropertyVetoException e) {
                // Vetoed by internalFrame
                // ... possibly add some handling for this case
            }
        }
    }//GEN-LAST:event_tbDataTransMouseClicked

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        aksiSimpanTiket();
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        if (lblTambah.getText().equals("Tambah")) {
            clearInputTiket();
            enableInputTiket();
            trans.createAutoID();
            txtIdTrans.setText(trans.vid_transaksi);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240,50,95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_cancel_white_18dp.png")));
        }else{
            clearInputTiket();
            disableInputTiket();
            lblTambah.setText("Tambah"); 
            btnTambah.setBackground(new java.awt.Color(21,184,90));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_add_white_18dp.png")));
        }
    }//GEN-LAST:event_btnTambahMouseClicked

    private void txtIdTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdTransActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdTransActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        Home.frTiket = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void cmbBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBusActionPerformed
        /*if (cmbBus.getSelectedIndex() == 0) {
            lblKelasBus.setText("-");
            lblHargaTiket.setText("-");
            lblNoPolisi.setText("-");
        }else{
            trans.selectId(KeyBus[cmbBus.getSelectedIndex()]);
            lblKelasBus.setText(trans.vkelas1);
            lblHargaTiket.setText(trans.vharga1);
            lblNoPolisi.setText(trans.vno_polisi1);
        }*/
    }//GEN-LAST:event_cmbBusActionPerformed

    private void cmbBusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBusItemStateChanged
        
    }//GEN-LAST:event_cmbBusItemStateChanged

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        aksiHapusTiket();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void txtIdUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdUserKeyPressed

    private void txtIdUserKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdUserKeyTyped
        if (txtIdUser.getText().length()==5) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdUserKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnHapus;
    private javax.swing.JPanel btnSimpan;
    private javax.swing.JPanel btnTambah;
    private javax.swing.JComboBox<String> cmbBus;
    private javax.swing.JComboBox<String> cmbJadwal;
    private javax.swing.JComboBox<String> cmbSupir;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblHapus;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JLabel lblSimpan;
    private javax.swing.JLabel lblTambah;
    private javax.swing.JTable tbDataTrans;
    private javax.swing.JTextField txtIdTrans;
    private javax.swing.JTextField txtIdUser;
    // End of variables declaration//GEN-END:variables
}
