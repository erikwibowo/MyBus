package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import models.Bus;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrBus extends javax.swing.JInternalFrame {
    
    Bus bus = new Bus();
    
    public IfrBus() {
        initComponents();
        
        formTengah();
        
        clearInput();
        disableInput();
        setTabelBus();
        showDataBus();
    }
    
    private void clearInput(){
        txtIdBus.setText("");
        cmbClassBus.setSelectedIndex(0);
        txtHargaTiket.setText("");
        txtNoPolisi.setText("");
        lblTambah.setText("Tambah");
        btnTambah.setBackground(new java.awt.Color(21, 184, 90));
        lblSimpan.setText("Simpan");
        lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/source/ic_add_white_18dp.png")));
    }
    
    private void disableInput(){
        txtIdBus.setEditable(false);
        cmbClassBus.setEnabled(false);
        txtHargaTiket.setEditable(false);
        txtNoPolisi.setEditable(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableInput(){
        txtIdBus.setEditable(true);
        cmbClassBus.setEnabled(true);
        txtHargaTiket.setEditable(true);
        txtNoPolisi.setEditable(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabelBus(){
        String[] kolom1 = {"ID. Bus", "Kelas Bus", "Harga Tiket", "No. Polisi"};
        bus.tblbus = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[] {
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            
            //agar tabel tidak bisa diedit
            public boolean isCellEditable (int row, int col){
                int cola = bus.tblbus.getColumnCount();
                return (col < cola) ? false : true;
            }    
        };
        tblDataBus.setModel(bus.tblbus);
        tblDataBus.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblDataBus.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblDataBus.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblDataBus.getColumnModel().getColumn(3).setPreferredWidth(100);
    }
    
    private void clearTabelBus(){
        int row = bus.tblbus.getRowCount();                                     // variabel row diberi nilai jumlah baris pada tabel (model) jurusan 
        for (int i = 0; i < row; i++) {         
            bus.tblbus.removeRow(0);                                            //menghapus record 
        }
    }
    
    private void showDataBus(){
        tblDataBus.setModel(bus.tblbus);
        int row = bus.tblbus.getRowCount();
        for (int i = 0; i < row; i++) {
            bus.tblbus.removeRow(0);
        }
        bus.select();
        for (Object obj : bus.list) {
            bus.tblbus.addRow((Object[]) obj);
        }
        lblRecord.setText("Record : " + tblDataBus.getRowCount());
    }
    
    private void aksiSimpan(){
        if (txtIdBus.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mohon Isi ID. Bus",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtIdBus.requestFocus(true);
        } else if(cmbClassBus.getSelectedIndex()<=0) {
            JOptionPane.showMessageDialog(this, "Mohon Pilih Kelas Bus",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbClassBus.requestFocus(true);
        } else if(txtHargaTiket.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mohon Isi Harga Tiket",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtHargaTiket.requestFocus(true);
        } else if(txtNoPolisi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Mohon Isi No. Polisi",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtNoPolisi.requestFocus(true);
        } else {
            bus.isUpdate        = !lblSimpan.getText().equals("Simpan");
            bus.vid_bus         = txtIdBus.getText();
            bus.vclass_bus      = (String )cmbClassBus.getSelectedItem();
            bus.vharga_tiket    = txtHargaTiket.getText();
            bus.vno_polisi      = txtNoPolisi.getText();
            bus.insert_update();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            clearInput();
            disableInput();
            showDataBus();
        }
    }
    
    private void aksiHapus(){
        if (txtIdBus.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Informasi",
                    "Anda belum memilih data yang akan dihapus", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda akan menghapus data ini? kode :" + bus.vid_bus,
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (jawab == JOptionPane.YES_OPTION) {
                bus.delete(bus.vid_bus);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
                clearInput();
                disableInput();
                showDataBus();
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
        txtIdBus = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtNoPolisi = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtHargaTiket = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        cmbClassBus = new javax.swing.JComboBox<>();
        btnTambah = new javax.swing.JPanel();
        lblTambah = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JPanel();
        lblSimpan = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDataBus = new javax.swing.JTable();
        btnHapus = new javax.swing.JPanel();
        lblHapus = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Form Bus");
        setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/source/Bus_18px_1.png"))); // NOI18N
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
        jLabel3.setText("ID Bus");

        txtIdBus.setBackground(new java.awt.Color(156, 42, 225));
        txtIdBus.setFont(new java.awt.Font("Ubuntu Light", 0, 12)); // NOI18N
        txtIdBus.setForeground(new java.awt.Color(255, 255, 255));
        txtIdBus.setBorder(null);
        txtIdBus.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdBus.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIdBus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdBusKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kelas Bus");

        txtNoPolisi.setBackground(new java.awt.Color(156, 42, 225));
        txtNoPolisi.setFont(new java.awt.Font("Ubuntu Light", 0, 12)); // NOI18N
        txtNoPolisi.setForeground(new java.awt.Color(255, 255, 255));
        txtNoPolisi.setBorder(null);
        txtNoPolisi.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNoPolisi.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtNoPolisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoPolisiActionPerformed(evt);
            }
        });
        txtNoPolisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoPolisiKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("No. Polisi");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Harga Tiket");

        txtHargaTiket.setBackground(new java.awt.Color(156, 42, 225));
        txtHargaTiket.setFont(new java.awt.Font("Ubuntu Light", 0, 12)); // NOI18N
        txtHargaTiket.setForeground(new java.awt.Color(255, 255, 255));
        txtHargaTiket.setBorder(null);
        txtHargaTiket.setCaretColor(new java.awt.Color(255, 255, 255));
        txtHargaTiket.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtHargaTiket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaTiketActionPerformed(evt);
            }
        });
        txtHargaTiket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaTiketKeyTyped(evt);
            }
        });

        cmbClassBus.setBackground(new java.awt.Color(156, 42, 225));
        cmbClassBus.setFont(new java.awt.Font("Ubuntu Light", 0, 12)); // NOI18N
        cmbClassBus.setForeground(new java.awt.Color(255, 255, 255));
        cmbClassBus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Kelas --", "Ekonomi", "Bisnis-AC" }));
        cmbClassBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClassBusActionPerformed(evt);
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
                .addContainerGap(14, Short.MAX_VALUE))
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
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator4)
                            .addComponent(txtNoPolisi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdBus, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbClassBus, javax.swing.GroupLayout.Alignment.LEADING, 0, 186, Short.MAX_VALUE)
                            .addComponent(txtHargaTiket, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(txtIdBus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(cmbClassBus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel6)
                .addGap(0, 0, 0)
                .addComponent(txtHargaTiket, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(txtNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(156, 42, 225));

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        tblDataBus.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        tblDataBus.setForeground(new java.awt.Color(51, 51, 51));
        tblDataBus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID Bus", "Kelas Bus", "Harga Tiket", "No. Polisi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDataBus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataBusMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDataBus);

        btnHapus.setBackground(new java.awt.Color(240, 50, 95));
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });

        lblHapus.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblHapus.setForeground(new java.awt.Color(255, 255, 255));
        lblHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/source/ic_delete_white_18dp.png"))); // NOI18N

        javax.swing.GroupLayout btnHapusLayout = new javax.swing.GroupLayout(btnHapus);
        btnHapus.setLayout(btnHapusLayout);
        btnHapusLayout.setHorizontalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHapusLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblHapus))
        );
        btnHapusLayout.setVerticalGroup(
            btnHapusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnHapusLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblHapus))
        );

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tabel Data Bus : klik 2x untuk ubah/hapus");

        lblRecord.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblRecord.setText("Record : ");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(lblRecord)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            bus.createAutoID();
            txtIdBus.setText(bus.vid_bus);
            cmbClassBus.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240, 50, 95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/source/ic_cancel_white_18dp.png")));
        } else {
            clearInput();
            disableInput();
            lblTambah.setText("Tambah");
            btnTambah.setBackground(new java.awt.Color(21, 184, 90));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/source/ic_add_white_18dp.png")));
        }
    }//GEN-LAST:event_btnTambahMouseClicked

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        aksiSimpan();
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        aksiHapus();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void tblDataBusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataBusMouseClicked
        if (evt.getClickCount() == 1) {
            int row = tblDataBus.getSelectedRow();
            bus.vid_bus         = tblDataBus.getValueAt(row, 0).toString();
            bus.vclass_bus      = tblDataBus.getValueAt(row, 1).toString();
            bus.vharga_tiket    = tblDataBus.getValueAt(row, 2).toString();
            bus.vno_polisi      = tblDataBus.getValueAt(row, 3).toString();
            
            txtIdBus.setText(bus.vid_bus);
            cmbClassBus.setSelectedItem(bus.vclass_bus);
            txtHargaTiket.setText(bus.vharga_tiket);
            txtNoPolisi.setText(bus.vno_polisi);
            
            enableInput();
            txtIdBus.setEditable(false);
            btnHapus.setEnabled(true);
            lblSimpan.setText("Ubah");
            txtHargaTiket.requestFocus(true);
            lblTambah.setText("Batal");
            btnTambah.setBackground(new java.awt.Color(240, 50, 95));
            lblTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/source/ic_cancel_white_18dp.png")));
        }
    }//GEN-LAST:event_tblDataBusMouseClicked

    private void txtNoPolisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoPolisiActionPerformed
        aksiSimpan();
    }//GEN-LAST:event_txtNoPolisiActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        Home.frBus = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtIdBusKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdBusKeyTyped
        if (txtIdBus.getText().length()==5) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdBusKeyTyped

    private void txtHargaTiketKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaTiketKeyTyped
        if (txtHargaTiket.getText().length()==6) {
            evt.consume();
        }
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaTiketKeyTyped

    private void txtNoPolisiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoPolisiKeyTyped
        if (txtNoPolisi.getText().length()==9) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNoPolisiKeyTyped

    private void cmbClassBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClassBusActionPerformed
        txtHargaTiket.requestFocus(true);
    }//GEN-LAST:event_cmbClassBusActionPerformed

    private void txtHargaTiketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaTiketActionPerformed
        txtNoPolisi.requestFocus(true);
    }//GEN-LAST:event_txtHargaTiketActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnHapus;
    private javax.swing.JPanel btnSimpan;
    private javax.swing.JPanel btnTambah;
    private javax.swing.JComboBox<String> cmbClassBus;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblHapus;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JLabel lblSimpan;
    private javax.swing.JLabel lblTambah;
    private javax.swing.JTable tblDataBus;
    private javax.swing.JTextField txtHargaTiket;
    private javax.swing.JTextField txtIdBus;
    private javax.swing.JTextField txtNoPolisi;
    // End of variables declaration//GEN-END:variables
}
