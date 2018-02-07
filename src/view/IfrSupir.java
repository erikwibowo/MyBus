
package view;
import java.awt.Dimension;
import java.awt.Toolkit;
import models.Supir;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IfrSupir extends javax.swing.JInternalFrame {
    
    Supir supir = new Supir();
  
    public IfrSupir() {
        initComponents();
        
        formTengah();
        
        clearInput();
        disableInput();
        setTabelSupir();
        showDataSupir();
    }

    private void clearInput(){
        txtIdSupir.setText("");
        txtNamaSupir.setText("");
        txtNoSim.setText("");
        cmbJK.setSelectedIndex(0);
        txtNoTelp.setText("");
        txtAlamat.setText("");
        lblTambah.setText("Tambah");
        btnTambah.setBackground(new java.awt.Color(21,184,90));
        lblSimpan.setText("Simpan");
        lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_add_box_white_18dp.png")));
    
    }
    
    private void disableInput(){
        txtIdSupir.setEditable(false);
        txtNamaSupir.setEditable(false);
        txtNoSim.setEditable(false);
        cmbJK.setEnabled(false);
        txtNoTelp.setEditable(false);
        txtAlamat.setEditable(false);
    }
    
    private void enableInput(){
        txtIdSupir.setEditable(true);
        txtNamaSupir.setEditable(true);
        txtNoSim.setEditable(true);
        cmbJK.setEnabled(true);
        txtNoTelp.setEditable(true);
        txtAlamat.setEditable(true);
    }
     
    private void setTabelSupir(){
       String[] kolom1 = {"ID. Supir", "Nama Supir", "No. Sim", "No. Hp", "Jenis Kelamin", "Alamat"};
        supir.tblsupir = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            //agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col){
                int cola = supir.tblsupir.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataSupir.setModel(supir.tblsupir);
        tbDataSupir.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataSupir.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbDataSupir.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbDataSupir.getColumnModel().getColumn(3).setPreferredWidth(25);
        tbDataSupir.getColumnModel().getColumn(4).setPreferredWidth(100);
        tbDataSupir.getColumnModel().getColumn(5).setPreferredWidth(100);
    }
    
    private void clearTabelInput(){
        int row = supir.tblsupir.getRowCount(); //variabel row diberi nilai jumlah baris pada tabel(model) jurusan
        for(int i=0; i<row; i++){
            supir.tblsupir.removeRow(0); //menghapus record/baris
        }
    }
       
    private void showDataSupir(){
        tbDataSupir.setModel(supir.tblsupir);
        int row = supir.tblsupir.getRowCount();
        for(int i=0; i<row; i++){
            supir.tblsupir.removeRow(0);
        }
        supir.select();
        supir.list.stream().forEach((obj) -> {
            supir.tblsupir.addRow((Object[]) obj);
        });
        lblRecord.setText("Record : " + tbDataSupir.getRowCount());
    }
        
    private void aksiSimpan(){
        if(txtIdSupir.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon lengkapi ID. Supir", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNamaSupir.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon lengkapi Nama Anda", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNoSim.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data No. Sim", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNoTelp.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data No. Telp", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbJK.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data No. Telp", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon lengkapi data Alamat", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            supir.isUpdate = !lblSimpan.getText().equals("Simpan");
            supir.vid_supir = txtIdSupir.getText();
            supir.vnama_supir = txtNamaSupir.getText();
            supir.vnosim = txtNoSim.getText();
            supir.vjk = (String) cmbJK.getSelectedItem();
            supir.vtelp = txtNoTelp.getText();
            supir.valamat = txtAlamat.getText();
            supir.insert_update();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            clearInput();disableInput();showDataSupir();
        }
    }
    
    private void aksiHapus(){
        if(txtIdSupir.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus !", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            int jawab = JOptionPane.showConfirmDialog(this, "Apakah Anda akan menghapus data ini? Kode : "+supir.vid_supir, 
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(jawab == JOptionPane.YES_OPTION);
            supir.delete(supir.vid_supir);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            clearInput(); disableInput(); showDataSupir();
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtIdSupir = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNamaSupir = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        cmbJK = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtNoTelp = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        txtNoSim = new javax.swing.JTextField();
        btnTambah = new javax.swing.JPanel();
        lblTambah = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JPanel();
        lblSimpan = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataSupir = new javax.swing.JTable();
        btnHapus = new javax.swing.JPanel();
        lblHapus = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();

        setClosable(true);
        setResizable(true);
        setTitle("Form data supir");
        setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/source/Driver_18px_1.png"))); // NOI18N
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

        txtIdSupir.setBackground(new java.awt.Color(156, 42, 225));
        txtIdSupir.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtIdSupir.setForeground(new java.awt.Color(255, 255, 255));
        txtIdSupir.setBorder(null);
        txtIdSupir.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdSupir.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIdSupir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdSupirActionPerformed(evt);
            }
        });
        txtIdSupir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdSupirKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID Supir");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Supir");

        txtNamaSupir.setBackground(new java.awt.Color(156, 42, 225));
        txtNamaSupir.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtNamaSupir.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaSupir.setBorder(null);
        txtNamaSupir.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNamaSupir.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtNamaSupir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSupirActionPerformed(evt);
            }
        });
        txtNamaSupir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNamaSupirKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("No. SIM");

        cmbJK.setBackground(new java.awt.Color(156, 42, 225));
        cmbJK.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        cmbJK.setForeground(new java.awt.Color(255, 255, 255));
        cmbJK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "L", "P" }));
        cmbJK.setBorder(null);
        cmbJK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJKActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("No. Telp");

        txtNoTelp.setBackground(new java.awt.Color(156, 42, 225));
        txtNoTelp.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtNoTelp.setForeground(new java.awt.Color(255, 255, 255));
        txtNoTelp.setBorder(null);
        txtNoTelp.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNoTelp.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtNoTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoTelpActionPerformed(evt);
            }
        });
        txtNoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoTelpKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("JK");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Alamat");

        txtAlamat.setBackground(new java.awt.Color(156, 42, 225));
        txtAlamat.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtAlamat.setForeground(new java.awt.Color(255, 255, 255));
        txtAlamat.setBorder(null);
        txtAlamat.setCaretColor(new java.awt.Color(255, 255, 255));
        txtAlamat.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });
        txtAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlamatKeyTyped(evt);
            }
        });

        txtNoSim.setBackground(new java.awt.Color(156, 42, 225));
        txtNoSim.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtNoSim.setForeground(new java.awt.Color(255, 255, 255));
        txtNoSim.setBorder(null);
        txtNoSim.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNoSim.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtNoSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoSimActionPerformed(evt);
            }
        });
        txtNoSim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoSimKeyTyped(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoTelp)
                            .addComponent(jSeparator3)
                            .addComponent(jSeparator4)
                            .addComponent(txtNoSim)
                            .addComponent(txtIdSupir)
                            .addComponent(jSeparator1)
                            .addComponent(txtNamaSupir, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2)
                            .addComponent(cmbJK, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel4))
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(txtIdSupir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel3)
                        .addGap(0, 0, 0)
                        .addComponent(txtNamaSupir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(txtNoSim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addGap(0, 0, 0)
                .addComponent(cmbJK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, 0)
                        .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(156, 42, 225));

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        tbDataSupir.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        tbDataSupir.setForeground(new java.awt.Color(51, 51, 51));
        tbDataSupir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Supir", "Nama Supir", "No. SIM", "L/P", "No. Telepon", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDataSupir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSupirMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataSupir);

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

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tabel Data Supir : klik 2x untuk ubah/hapus");

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
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
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
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addGap(9, 9, 9))
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

    private void txtIdSupirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdSupirActionPerformed
        txtNamaSupir.requestFocus(true);
    }//GEN-LAST:event_txtIdSupirActionPerformed

    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        if (lblTambah.getText().equals("Tambah")) {
            clearInput();
            enableInput();
            supir.createAutoID();
            txtIdSupir.setText(supir.vid_supir);
            txtNamaSupir.requestFocus(true);
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

    private void tbDataSupirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSupirMouseClicked
        if(evt.getClickCount()==1){
            int row = tbDataSupir.getSelectedRow();
            supir.vid_supir = tbDataSupir.getValueAt(row, 0).toString();
            supir.vnama_supir = tbDataSupir.getValueAt(row, 1).toString();
            supir.vnosim = tbDataSupir.getValueAt(row, 2).toString();
            supir.vjk = tbDataSupir.getValueAt(row, 4).toString();
            supir.vtelp = tbDataSupir.getValueAt(row, 3).toString();
            supir.valamat = tbDataSupir.getValueAt(row, 5).toString();
            
            txtIdSupir.setText(supir.vid_supir);
            txtNamaSupir.setText(supir.vnama_supir);
            txtNoSim.setText(supir.vnosim);
            cmbJK.setSelectedItem(supir.vjk);
            txtNoTelp.setText(supir.vtelp);
            txtAlamat.setText(supir.valamat);
            
            enableInput();
            txtIdSupir.setEditable(false);
            btnHapus.setEnabled(true);
            lblSimpan.setText("Ubah");
            txtNamaSupir.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240,50,95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/source/ic_cancel_white_18dp.png")));
        }
    }//GEN-LAST:event_tbDataSupirMouseClicked

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        Home.frSupir = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtNamaSupirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSupirActionPerformed
        txtNoSim.requestFocus(true);
    }//GEN-LAST:event_txtNamaSupirActionPerformed

    private void txtNoSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoSimActionPerformed
        cmbJK.requestFocus(true);
    }//GEN-LAST:event_txtNoSimActionPerformed

    private void cmbJKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJKActionPerformed
        txtNoTelp.requestFocus(true);
    }//GEN-LAST:event_cmbJKActionPerformed

    private void txtNoTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoTelpActionPerformed
        txtAlamat.requestFocus(true);
    }//GEN-LAST:event_txtNoTelpActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        aksiHapus();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void txtIdSupirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdSupirKeyTyped
        if (txtIdSupir.getText().length()==5) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdSupirKeyTyped

    private void txtNamaSupirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaSupirKeyTyped
        if (txtNamaSupir.getText().length()==50) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNamaSupirKeyTyped

    private void txtNoSimKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoSimKeyTyped
        if (txtNoSim.getText().length()==12) {
            evt.consume();
        }
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtNoSimKeyTyped

    private void txtNoTelpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTelpKeyTyped
        if (txtNoTelp.getText().length()==15) {
            evt.consume();
        }
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtNoTelpKeyTyped

    private void txtAlamatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatKeyTyped

    }//GEN-LAST:event_txtAlamatKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnHapus;
    private javax.swing.JPanel btnSimpan;
    private javax.swing.JPanel btnTambah;
    private javax.swing.JComboBox<String> cmbJK;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblHapus;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JLabel lblSimpan;
    private javax.swing.JLabel lblTambah;
    private javax.swing.JTable tbDataSupir;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtIdSupir;
    private javax.swing.JTextField txtNamaSupir;
    private javax.swing.JTextField txtNoSim;
    private javax.swing.JTextField txtNoTelp;
    // End of variables declaration//GEN-END:variables
}
