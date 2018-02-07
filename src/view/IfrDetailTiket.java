package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DetailTransaksi;
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
public class IfrDetailTiket extends javax.swing.JInternalFrame {

    Connection _Cnn;
    KoneksiDB getCnn = new KoneksiDB();
    DetailTransaksi dtrans = new DetailTransaksi();
    
    public IfrDetailTiket(String id) {
        initComponents();
        formTengah();
        txtIdTrans.setText(id);
        clearInputDetailTiket();
        disableInputDetailTiket();
        setTabelDetailTiket();
        showDataDetailTiket();
        if (tbDataDetailTrans.getRowCount() > 0) {
            listKursi(true);
        }else{
            listKursi(false);
        }
        txtIdDetailTrans.setEditable(false);
        txtIdTrans.setEditable(false);
    }
    
    private void clearInputDetailTiket(){
        txtIdDetailTrans.setText("");
        cmbKursi.setSelectedIndex(0);
        txtNamaPenumpang.setText("");
        lblTambah.setText("Tambah");
        btnTambah.setBackground(new java.awt.Color(21,184,90));
        lblSimpan.setText("Simpan");
        lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_add_white_18dp.png")));
    }
    
    private void disableInputDetailTiket(){
        cmbKursi.setEnabled(false);
        txtNamaPenumpang.setEditable(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInputDetailTiket(){
        cmbKursi.setEnabled(true);
        txtNamaPenumpang.setEditable(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    private void aksiCetakTiket(){
        if (txtIdDetailTrans.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Anda belum memilih tiket untuk dicetak!");
        }else {
            String path = System.getProperty("user.dir") + "/src/laporan/Tiket.jrxml";
            try {
                 Map<String, Object> parameters = new HashMap<>();
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                parameters.put("parId", txtIdDetailTrans.getText());
                JasperReport jrpt = JasperCompileManager.compileReport(path);
                JasperPrint jrprint = JasperFillManager.fillReport(jrpt, parameters, _Cnn);

                JasperViewer.viewReport(jrprint, false);
            } catch (SQLException | JRException e) {
                JOptionPane.showMessageDialog(this, "error method cetak() : "+e);
            }
        }
    }
    
    private void setTabelDetailTiket(){ //mengatur tampilan tabel
        String[] kolom1 = {"ID. Detail Transaksi", "ID. Transaksi", "No. Polisi", "Kelas Bus",""
                + "No. Kursi","Nama Penumpang", "Tanggal"};
        dtrans.tbDetailTransaksi = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
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
                int cola = dtrans.tbDetailTransaksi.getColumnCount();
                return (col < cola) ? false:true;
            }
        };
        tbDataDetailTrans.setModel(dtrans.tbDetailTransaksi);
        tbDataDetailTrans.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataDetailTrans.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbDataDetailTrans.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbDataDetailTrans.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbDataDetailTrans.getColumnModel().getColumn(4).setPreferredWidth(25);
        tbDataDetailTrans.getColumnModel().getColumn(5).setPreferredWidth(100);
        tbDataDetailTrans.getColumnModel().getColumn(6).setPreferredWidth(200);
    }
    
    private void clearTabelDetailTiket(){
        int row = dtrans.tbDetailTransaksi.getRowCount();
        for (int i = 0; i < row; i++) {
            dtrans.tbDetailTransaksi.removeRow(0);
        }
    }
    
    private void showDataDetailTiket(){
        tbDataDetailTrans.setModel(dtrans.tbDetailTransaksi);
        int row = dtrans.tbDetailTransaksi.getRowCount();
        for (int i = 0; i < row; i++) {
            dtrans.tbDetailTransaksi.removeRow(0);
        }
        dtrans.select(txtIdTrans.getText());
        for (Object obj : dtrans.list) {
            dtrans.tbDetailTransaksi.addRow((Object[]) obj);
        }
        
        lblRecord.setText("Record : "+tbDataDetailTrans.getRowCount());
    }
    
    private void aksiSimpanDetailTiket(){
        if (txtIdTrans.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mohon isi Id Trans!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtIdTrans.requestFocus(true);
        }else if (cmbKursi.getSelectedIndex()<=0) {
            JOptionPane.showMessageDialog(this, "Mohon pilih jenis kelamin!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbKursi.requestFocus(true);
        }else if (txtNamaPenumpang.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mohon isi nama penumpang!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtNamaPenumpang.requestFocus(true);
        }else{
            dtrans.isUpdate = !lblSimpan.getText().equals("Simpan");
            dtrans.vid_detailtrans = Integer.parseInt(txtIdDetailTrans.getText());
            dtrans.vid_transaksi = txtIdTrans.getText();
            dtrans.vid_kursi = KeyKursi[cmbKursi.getSelectedIndex()];
            dtrans.vnama_penumpang = txtNamaPenumpang.getText();
            dtrans.insert_update();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            
            clearInputDetailTiket();
            disableInputDetailTiket();
            showDataDetailTiket();
            cmbKursi.removeAllItems();
            if (tbDataDetailTrans.getRowCount() > 0) {
            listKursi(true);
            }else{
                listKursi(false);
            }
        }
    }
    
    private void aksiHapusDetailTiket(){
        if (txtIdDetailTrans.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihpus!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda akan menghapus data ini? Kode : "+dtrans.vid_detailtrans,
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (jawab == JOptionPane.YES_OPTION) {
                dtrans.delete(dtrans.vid_detailtrans);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                clearInputDetailTiket(); disableInputDetailTiket(); showDataDetailTiket();
                cmbKursi.removeAllItems();
                if (tbDataDetailTrans.getRowCount() > 0) {
                    listKursi(true);
                }else{
                    listKursi(false);
                }
            }
        }
    }
    
    String[] KeyKursi;
    private void listKursi(boolean kursi){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            String sql = "";
            if (kursi == true) {
                sql = "select b.id_kursi, b.nomor_kursi "
                    + "from tb_detailtransaksi a, tb_kursi b, tb_transaksi c "
                    + "where b.id_kursi not in(select id_kursi from tb_detailtransaksi where id_transaksi='"+txtIdTrans.getText()+"') "
                    + "and a.id_transaksi=c.id_transaksi and c.id_transaksi='"+txtIdTrans.getText()+"' group by b.id_kursi";
            }else{
                sql = "select * from tb_kursi";
            }
            Statement stat = _Cnn.createStatement();
            ResultSet res=stat.executeQuery(sql);
            cmbKursi.removeAllItems();
            cmbKursi.repaint();
            cmbKursi.addItem("-- Pilih Kursi --");
            int i = 1;
            while(res.next()){
                cmbKursi.addItem(res.getString("nomor_kursi"));
                i++;
            }
            res.first();
            KeyKursi = new String[i+1];
            for(Integer x = 1;x < i;x++){
                KeyKursi[x] = res.getString("id_kursi");
                res.next();
            }
        } catch (SQLException ex) {
            System.out.println("Error method listSupir() : "+ex);
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
    
    private void cetak(){
        String path = System.getProperty("user.dir") + "/laporan/Tiket.jrxml";
        String logo = System.getProperty("user.dir") + "/laporan/";
        try {
             Map<String, Object> parameters = new HashMap<>();
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            parameters.put("parLogo", logo);
            JasperReport jrpt = JasperCompileManager.compileReport(path);
            JasperPrint jrprint = JasperFillManager.fillReport(jrpt, parameters, _Cnn);
            
            JasperViewer.viewReport(jrprint, false);
        } catch (SQLException | JRException e) {
            JOptionPane.showMessageDialog(this, "error method cetak() : "+e);
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

        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtIdDetailTrans = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtIdTrans = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        cmbKursi = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtNamaPenumpang = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JPanel();
        lblTambah = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JPanel();
        lblSimpan = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDataDetailTrans = new javax.swing.JTable();
        btnHapus = new javax.swing.JPanel();
        lblHapus1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        btnPrint = new javax.swing.JPanel();
        lblRecord3 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Detail Transaksi");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/source/Ticket_18px_1.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(106, 27, 154));

        jPanel5.setBackground(new java.awt.Color(156, 42, 225));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(156, 42, 225), 1, true));

        txtIdDetailTrans.setBackground(new java.awt.Color(156, 42, 225));
        txtIdDetailTrans.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtIdDetailTrans.setForeground(new java.awt.Color(255, 255, 255));
        txtIdDetailTrans.setBorder(null);
        txtIdDetailTrans.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdDetailTrans.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIdDetailTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdDetailTransActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("ID Detail Transaksi");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("ID Transaksi");

        txtIdTrans.setBackground(new java.awt.Color(156, 42, 225));
        txtIdTrans.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtIdTrans.setForeground(new java.awt.Color(255, 255, 255));
        txtIdTrans.setBorder(null);
        txtIdTrans.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdTrans.setDisabledTextColor(new java.awt.Color(204, 204, 204));

        cmbKursi.setBackground(new java.awt.Color(156, 42, 225));
        cmbKursi.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        cmbKursi.setForeground(new java.awt.Color(255, 255, 255));
        cmbKursi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "L", "P" }));
        cmbKursi.setBorder(null);
        cmbKursi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKursiActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Nama Penumpang");

        txtNamaPenumpang.setBackground(new java.awt.Color(156, 42, 225));
        txtNamaPenumpang.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtNamaPenumpang.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaPenumpang.setBorder(null);
        txtNamaPenumpang.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNamaPenumpang.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtNamaPenumpang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPenumpangActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Pilih Kursi");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNamaPenumpang, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdTrans, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbKursi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtIdDetailTrans, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(0, 0, 0)
                .addComponent(txtIdDetailTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel14)
                        .addGap(0, 0, 0)
                        .addComponent(txtIdTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(0, 0, 0)
                .addComponent(cmbKursi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addGap(0, 0, 0)
                .addComponent(txtNamaPenumpang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(156, 42, 225));

        jScrollPane3.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane3.setForeground(new java.awt.Color(255, 255, 255));

        tbDataDetailTrans.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        tbDataDetailTrans.setForeground(new java.awt.Color(51, 51, 51));
        tbDataDetailTrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Detail Transaksi", "ID Transaksi", "No. Bus", "Kelas Bus", "No. Kursi", "Nama Penumpang", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDataDetailTrans.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataDetailTransMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbDataDetailTrans);

        btnHapus.setBackground(new java.awt.Color(240, 50, 95));
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });

        lblHapus1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHapus1.setForeground(new java.awt.Color(255, 255, 255));
        lblHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/source/ic_delete_white_18dp.png"))); // NOI18N

        javax.swing.GroupLayout btnHapusLayout = new javax.swing.GroupLayout(btnHapus);
        btnHapus.setLayout(btnHapusLayout);
        btnHapusLayout.setHorizontalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHapusLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lblHapus1)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        btnHapusLayout.setVerticalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHapusLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblHapus1)
                .addGap(5, 5, 5))
        );

        jLabel19.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Tabel Data Detail Transaksi : klik 2x untuk ubah/hapus/cetak");

        lblRecord.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblRecord.setText("Record : ");

        btnPrint.setBackground(new java.awt.Color(21, 184, 90));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });

        lblRecord3.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblRecord3.setForeground(new java.awt.Color(255, 255, 255));
        lblRecord3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/source/ic_print_white_18dp.png"))); // NOI18N
        lblRecord3.setText("Cetak");
        lblRecord3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblRecord3AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout btnPrintLayout = new javax.swing.GroupLayout(btnPrint);
        btnPrint.setLayout(btnPrintLayout);
        btnPrintLayout.setHorizontalGroup(
            btnPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRecord3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPrintLayout.setVerticalGroup(
            btnPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPrintLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblRecord3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(lblRecord)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRecord))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdDetailTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdDetailTransActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdDetailTransActionPerformed

    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        if (lblTambah.getText().equals("Tambah")) {
            clearInputDetailTiket();
            enableInputDetailTiket();
            dtrans.createAutoID();
            txtIdDetailTrans.setText(Integer.toString(dtrans.vid_detailtrans));
            cmbKursi.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240,50,95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_cancel_white_18dp.png")));
        }else{
            clearInputDetailTiket();
            disableInputDetailTiket();
            lblTambah.setText("Tambah"); 
            btnTambah.setBackground(new java.awt.Color(21,184,90));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_add_white_18dp.png")));
        }
    }//GEN-LAST:event_btnTambahMouseClicked

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        aksiSimpanDetailTiket();
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void tbDataDetailTransMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataDetailTransMouseClicked
        if(evt.getClickCount()==2){
            int row = tbDataDetailTrans.getSelectedRow();
            dtrans.vid_detailtrans = (int) tbDataDetailTrans.getValueAt(row, 0);
            dtrans.vno_kursi = tbDataDetailTrans.getValueAt(row, 4).toString();
            dtrans.vnama_penumpang = tbDataDetailTrans.getValueAt(row, 5).toString();
            
            txtIdDetailTrans.setText(Integer.toString(dtrans.vid_detailtrans));
            cmbKursi.setSelectedItem(dtrans.vno_kursi);
            txtNamaPenumpang.setText(dtrans.vnama_penumpang);
            
            enableInputDetailTiket();
            txtIdTrans.setEditable(false);
            btnHapus.setEnabled(true);
            lblSimpan.setText("Ubah");
            txtIdTrans.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240,50,95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_cancel_white_18dp.png")));
        }
    }//GEN-LAST:event_tbDataDetailTransMouseClicked

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        aksiHapusDetailTiket();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        cetak();
    }//GEN-LAST:event_btnPrintMouseClicked

    private void cmbKursiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKursiActionPerformed
        txtNamaPenumpang.requestFocus(true);
    }//GEN-LAST:event_cmbKursiActionPerformed

    private void txtNamaPenumpangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPenumpangActionPerformed
        aksiSimpanDetailTiket();
    }//GEN-LAST:event_txtNamaPenumpangActionPerformed

    private void lblRecord3AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblRecord3AncestorAdded
       
    }//GEN-LAST:event_lblRecord3AncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnHapus;
    private javax.swing.JPanel btnPrint;
    private javax.swing.JPanel btnSimpan;
    private javax.swing.JPanel btnTambah;
    private javax.swing.JComboBox<String> cmbKursi;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblHapus1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JLabel lblRecord3;
    private javax.swing.JLabel lblSimpan;
    private javax.swing.JLabel lblTambah;
    private javax.swing.JTable tbDataDetailTrans;
    private javax.swing.JTextField txtIdDetailTrans;
    private javax.swing.JTextField txtIdTrans;
    private javax.swing.JTextField txtNamaPenumpang;
    // End of variables declaration//GEN-END:variables
}
