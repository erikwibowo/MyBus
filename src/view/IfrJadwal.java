/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import models.Jadwal;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ERIK
 */
public class IfrJadwal extends javax.swing.JInternalFrame {

    Jadwal jadwal = new Jadwal();
    
    public IfrJadwal() {
        initComponents();
        
        formTengah();
        
        clearInput();
        disableInput();
        setTabelJadwal();
        showDataJadwal();
    }
    
    private void clearInput(){
        txtIdJadwal.setText("");
        cmbTujuan.setSelectedIndex(0);
        txtTanggal.setDate(new Date());
        lblTambah.setText("Tambah");
        btnTambah.setBackground(new java.awt.Color(21,184,90));
        lblSimpan.setText("Simpan");
        lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_add_white_18dp.png")));
    }
    
    private void disableInput(){
        txtIdJadwal.setEditable(false);
        cmbTujuan.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInput(){
        txtIdJadwal.setEditable(true);
        cmbTujuan.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    private void setTabelJadwal(){ //mengatur tampilan tabel
        String[] kolom1 = {"ID. Jadwal", "Tanggal", "Jam", "Tujuan"};
        jadwal.tbljadwal = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
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
                int cola = jadwal.tbljadwal.getColumnCount();
                return (col < cola) ? false:true;
            }
        };
        tbDataJadwal.setModel(jadwal.tbljadwal);
        tbDataJadwal.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataJadwal.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbDataJadwal.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbDataJadwal.getColumnModel().getColumn(3).setPreferredWidth(200);
    }
    
    private void clearTabelJadwal(){
        int row = jadwal.tbljadwal.getRowCount();
        for (int i = 0; i < row; i++) {
            jadwal.tbljadwal.removeRow(0);
        }
    }
    
    private void showDataJadwal(){
        tbDataJadwal.setModel(jadwal.tbljadwal);
        int row = jadwal.tbljadwal.getRowCount();
        for (int i = 0; i < row; i++) {
            jadwal.tbljadwal.removeRow(0);
        }
        jadwal.select();
        for (Object obj : jadwal.list) {
            jadwal.tbljadwal.addRow((Object[]) obj);
        }
        
        lblRecord.setText("Record : "+tbDataJadwal.getRowCount());
    }
    
    private void aksiSimpan(){
        if (txtIdJadwal.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mohon isi Id Jadwal!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtIdJadwal.requestFocus(true);
        }else if (cmbTujuan.getSelectedIndex()<=0) {
            JOptionPane.showMessageDialog(this, "Mohon pilih Tujuan!",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbTujuan.requestFocus(true);
        }else{
            jadwal.isUpdate = !lblSimpan.getText().equals("Simpan");
            jadwal.vid_jadwal = txtIdJadwal.getText();
            jadwal.vtanggal = jadwal.tglinput.format(txtTanggal.getDate());
            jadwal.vtujuan = cmbTujuan.getSelectedItem().toString();
            jadwal.vjam = txtJam.getFormatedTime();
            jadwal.insert_update();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            
            clearInput();
            disableInput();
            showDataJadwal();
        }
    }
    
    private void aksiHapus(){
        if(txtIdJadwal.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Informasi",
                    "Anda belum memilih data yang akan dihapus", JOptionPane.INFORMATION_MESSAGE);
        }else {
            int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda akan menghapus data ini? Kode : "+jadwal.vid_jadwal,
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(jawab == JOptionPane.YES_OPTION){
                jadwal.delete(jadwal.vid_jadwal);
                JOptionPane.showMessageDialog(this, "Data Berhasil dihapus",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                clearInput(); 
                disableInput(); 
                showDataJadwal();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIdJadwal = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        txtJam = new lu.tudor.santec.jtimechooser.JTimeChooser();
        cmbTujuan = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JPanel();
        lblTambah = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JPanel();
        lblSimpan = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataJadwal = new javax.swing.JTable();
        btnHapus = new javax.swing.JPanel();
        lblHapus = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Form Jadwal");
        setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/source/Calendar_18px_1.png"))); // NOI18N
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

        jPanel2.setBackground(new java.awt.Color(106, 27, 154));

        jPanel7.setBackground(new java.awt.Color(156, 42, 225));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(156, 42, 225), 1, true));

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID Jadwal");

        txtIdJadwal.setBackground(new java.awt.Color(156, 42, 225));
        txtIdJadwal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIdJadwal.setForeground(new java.awt.Color(255, 255, 255));
        txtIdJadwal.setBorder(null);
        txtIdJadwal.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdJadwal.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIdJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdJadwalKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tanggal");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Jam");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tujuan");

        txtTanggal.setBackground(new java.awt.Color(156, 42, 225));
        txtTanggal.setDateFormatString("yyyy-M-d");
        txtTanggal.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        txtJam.setForeground(new java.awt.Color(255, 255, 255));
        txtJam.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        cmbTujuan.setBackground(new java.awt.Color(156, 42, 225));
        cmbTujuan.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        cmbTujuan.setForeground(new java.awt.Color(255, 255, 255));
        cmbTujuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Cikarang - Jakarta", "Jakarta - Bandung" }));

        btnTambah.setBackground(new java.awt.Color(21, 184, 90));
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahMouseClicked(evt);
            }
        });

        lblTambah.setBackground(new java.awt.Color(21, 184, 90));
        lblTambah.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
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

        lblSimpan.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addComponent(cmbTujuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3)
                            .addComponent(txtIdJadwal)
                            .addComponent(jSeparator2)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator4)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel4))
                                    .addComponent(jLabel7)
                                    .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(23, 23, 23))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(txtIdJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, 0)
                        .addComponent(cmbTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(156, 42, 225));

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        tbDataJadwal.setForeground(new java.awt.Color(51, 51, 51));
        tbDataJadwal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID Jadwal", "Tanggal", "Jam", "Tujuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDataJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataJadwalMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataJadwal);

        btnHapus.setBackground(new java.awt.Color(240, 50, 95));
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });

        lblHapus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHapus.setForeground(new java.awt.Color(255, 255, 255));
        lblHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/source/ic_delete_white_18dp.png"))); // NOI18N

        javax.swing.GroupLayout btnHapusLayout = new javax.swing.GroupLayout(btnHapus);
        btnHapus.setLayout(btnHapusLayout);
        btnHapusLayout.setHorizontalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHapusLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(lblHapus)
                .addGap(3, 3, 3))
        );
        btnHapusLayout.setVerticalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHapusLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblHapus)
                .addGap(5, 5, 5))
        );

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tabel Data Jadwal : klik 2x untuk ubah/hapus");

        lblRecord.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblRecord.setText("Record : ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRecord)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addComponent(lblRecord)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
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

    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        if (lblTambah.getText().equals("Tambah")) {
            clearInput();
            enableInput();
            jadwal.createAutoID();
            txtIdJadwal.setText(jadwal.vid_jadwal);
            txtTanggal.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240,50,95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_cancel_white_18dp.png")));
        }else{
            clearInput();
            disableInput();
            lblTambah.setText("Tambah"); 
            btnTambah.setBackground(new java.awt.Color(21,184,90));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_add_white_18dp.png")));
        }
    }//GEN-LAST:event_btnTambahMouseClicked

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        aksiSimpan();
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        Home.frJadwal = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtIdJadwalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdJadwalKeyTyped
        if (txtIdJadwal.getText().length()==5) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdJadwalKeyTyped

    private void tbDataJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataJadwalMouseClicked
        if (evt.getClickCount() == 1) {
            int row = tbDataJadwal.getSelectedRow();
            jadwal.vid_jadwal         = tbDataJadwal.getValueAt(row, 0).toString();
            jadwal.vtujuan      = tbDataJadwal.getValueAt(row, 3).toString();
            
            txtIdJadwal.setText(jadwal.vid_jadwal);
            cmbTujuan.setSelectedItem(jadwal.vtujuan);
            
            enableInput();
            txtIdJadwal.setEditable(false);
            btnHapus.setEnabled(true);
            lblSimpan.setText("Ubah");
            txtTanggal.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240, 50, 95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/source/ic_cancel_white_18dp.png")));
        }
    }//GEN-LAST:event_tbDataJadwalMouseClicked

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        aksiHapus();
    }//GEN-LAST:event_btnHapusMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnHapus;
    private javax.swing.JPanel btnSimpan;
    private javax.swing.JPanel btnTambah;
    private javax.swing.JComboBox<String> cmbTujuan;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblHapus;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JLabel lblSimpan;
    private javax.swing.JLabel lblTambah;
    private javax.swing.JTable tbDataJadwal;
    private javax.swing.JTextField txtIdJadwal;
    private lu.tudor.santec.jtimechooser.JTimeChooser txtJam;
    private com.toedter.calendar.JDateChooser txtTanggal;
    // End of variables declaration//GEN-END:variables
}
