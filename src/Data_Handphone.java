/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author RIZKI
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Data_Handphone extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Data_Handphone.class.getName());

    /**
     * Creates new form data_handphone
     */
    private static Connection mysqlconfig;
    private String kodeHpLama;
    
    public static Connection configDB() throws SQLException {
    try {

        String url = "jdbc:mysql://localhost:3306/db_handphone";
        String user = "root";
        String pass = "";

        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        mysqlconfig = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            System.err.println("Koneksi Gagal " + e.getMessage());
        }

        return mysqlconfig;
    }
    
    private void load_table() {
    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("No");
    model.addColumn("Kode HP");
    model.addColumn("Nama HP");
    model.addColumn("Merek");
    model.addColumn("Tipe");
    model.addColumn("Warna");
    model.addColumn("RAM");
    model.addColumn("Storage");
    model.addColumn("Harga");
    model.addColumn("Stok");
    model.addColumn("Garansi");

    try {
        int no = 1;
        String sql = "SELECT * FROM handphone";
        java.sql.Connection conn = Data_Handphone.configDB();
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet res = stm.executeQuery(sql);
        
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');

        DecimalFormat rupiah = new DecimalFormat("#,###", simbol);
        while (res.next()) {
            model.addRow(new Object[]{
                no++,
                res.getString("kode_hp"),
                res.getString("nama_hp"),
                res.getString("merek"),
                res.getString("tipe"),
                res.getString("warna"),
                res.getString("ram"),
                res.getString("storage"),
                "Rp." + rupiah.format(res.getDouble("harga")),
                res.getString("stok"),
                res.getString("garansi")
            });
        }

        jTable1.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void cariData() {

    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("No");
    model.addColumn("Kode HP");
    model.addColumn("Nama HP");
    model.addColumn("Merek");
    model.addColumn("Tipe");
    model.addColumn("Warna");
    model.addColumn("RAM");
    model.addColumn("Storage");
    model.addColumn("Harga");
    model.addColumn("Stok");
    model.addColumn("Garansi");

    try {

        int no = 1;

        Connection conn = configDB();

        String sql = "SELECT * FROM handphone WHERE "
        + "kode_hp LIKE ? OR "
        + "nama_hp LIKE ? OR "
        + "merek LIKE ? OR "
        + "tipe LIKE ? OR "
        + "warna LIKE ? OR "
        + "ram LIKE ? OR "
        + "storage LIKE ? OR "
        + "harga LIKE ? OR "
        + "stok LIKE ? OR "
        + "garansi LIKE ?";

        PreparedStatement pst = conn.prepareStatement(sql);

        String cari = "%" + jTextField3.getText() + "%";

        for (int i = 1; i <= 10; i++) {
            pst.setString(i, cari);
        }

        ResultSet rs = pst.executeQuery();

        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');

        DecimalFormat rupiah = new DecimalFormat("#,###", simbol);
        
        while (rs.next()) {

            model.addRow(new Object[]{
                no++,
                rs.getString("kode_hp"),
                rs.getString("nama_hp"),
                rs.getString("merek"),
                rs.getString("tipe"),
                rs.getString("warna"),
                rs.getString("ram"),
                rs.getString("storage"),
                "Rp." + rupiah.format(rs.getDouble("harga")),
                rs.getString("stok"),
                rs.getString("garansi")
            });

        }

        jTable1.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

}
    
    private void filterMerek() {

    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("No");
    model.addColumn("Kode HP");
    model.addColumn("Nama HP");
    model.addColumn("Merek");
    model.addColumn("Tipe");
    model.addColumn("Warna");
    model.addColumn("RAM");
    model.addColumn("Storage");
    model.addColumn("Harga");
    model.addColumn("Stok");
    model.addColumn("Garansi");

    try {

        int no = 1;

        Connection conn = configDB();

        String sql;

        if (jComboBox7.getSelectedIndex() == 0) {
            sql = "SELECT * FROM handphone";
        } else {
            sql = "SELECT * FROM handphone WHERE merek=?";
        }

        PreparedStatement pst = conn.prepareStatement(sql);

        if (jComboBox7.getSelectedIndex() != 0) {
            pst.setString(1, jComboBox7.getSelectedItem().toString());
        }

        ResultSet rs = pst.executeQuery();
        
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');

        DecimalFormat rupiah = new DecimalFormat("#,###", simbol);
        
        while (rs.next()) {

            model.addRow(new Object[]{
                no++,
                rs.getString("kode_hp"),
                rs.getString("nama_hp"),
                rs.getString("merek"),
                rs.getString("tipe"),
                rs.getString("warna"),
                rs.getString("ram"),
                rs.getString("storage"),
                "Rp " + rupiah.format(rs.getDouble("harga")),
                rs.getString("stok"),
                rs.getString("garansi")
            });

        }

        jTable1.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

}
   
    private void kosong() {
        jTextField1.setText(null); // kode hp
        jTextField2.setText(null); // nama hp
        jTextField4.setText(null); // harga
        jTextField5.setText(null); // stok

        jComboBox1.setSelectedIndex(0); // merek
        jComboBox2.setSelectedIndex(0); // tipe
        jComboBox3.setSelectedIndex(0); // warna
        jComboBox4.setSelectedIndex(0); // ram
        jComboBox5.setSelectedIndex(0); // storage
        jComboBox6.setSelectedIndex(0); // garansi
    }
    
    public Data_Handphone() {
        initComponents();
        
        load_table();
        kosong();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField5 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("DATA HANDPHONE");

        jLabel2.setText("Kode Hp");

        jLabel3.setText("Nama Hp");

        jLabel4.setText("Merek");

        jLabel5.setText("Tipe");

        jLabel6.setText("Warna");

        jLabel7.setText("RAM");

        jLabel8.setText("Storage");

        jLabel9.setText("Harga (Rp)");

        jLabel10.setText("Stok");

        jLabel11.setText("Garansi");

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jTextField2.setText("jTextField2");

        jTextField4.setText("jTextField4");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Merek", "Samsung", "Xiaomi", "Oppo", "Vivo", "Realme", "Infinix", "iPhone" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Warna", "Hitam", "Putih", "Biru", "Merah", "Silver", "Gold" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih RAM", "4 GB", "6 GB", "8 GB", "12 GB", "16 GB" }));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Storage", "64 GB", "128 GB", "256 GB", "512 GB", "1 TB" }));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Garansi", "3 Bulan", "6 Bulan", "1 Tahun", "2 Tahun" }));

        jButton1.setText("Tambah");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Edit");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Hapus");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Clear");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Nama", "Merek", "Tipe", "Warna", "RAM", "Storage", "Harga", "Stok", "Garansi"
            }
        ));
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTextField5.setText("jTextField5");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Tipe", "Android", "iOS" }));

        jButton5.setText("Transaksi");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jTextField3.addActionListener(this::jTextField3ActionPerformed);

        jLabel13.setText("Filter");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua Merek", "Samsung", "Xiaomi", "Oppo", "Vivo", "Realme", "Infinix", "iPhone" }));
        jComboBox7.addActionListener(this::jComboBox7ActionPerformed);

        jButton6.setText("search");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(15, 15, 15)))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 146, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jButton6)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
    if (kodeHpLama == null) {
        JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
        return;
    }

    int jawab = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus data ini?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);

    if (jawab == JOptionPane.YES_OPTION) {

        try {

            Connection conn = configDB();
            
            // Cek apakah HP sudah pernah ditransaksikan
        String cek = "SELECT * FROM transaksi WHERE kode_hp=?";
        PreparedStatement cekPst = conn.prepareStatement(cek);
        cekPst.setString(1, kodeHpLama);

        ResultSet rs = cekPst.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(
                this,
                "Data Handphone tidak dapat dihapus karena sudah memiliki transaksi!"
            );
            return;
        }

            String sql = "DELETE FROM handphone WHERE kode_hp=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, kodeHpLama);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");

            load_table();
            kosong();
            kodeHpLama = null;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }
    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {

        Connection conn = configDB();

            if (jTextField1.getText().isEmpty()
            || jTextField2.getText().isEmpty()
            || jTextField4.getText().isEmpty()
            || jTextField5.getText().isEmpty()
            || jComboBox1.getSelectedIndex() == 0
            || jComboBox2.getSelectedIndex() == 0
            || jComboBox3.getSelectedIndex() == 0
            || jComboBox4.getSelectedIndex() == 0
            || jComboBox5.getSelectedIndex() == 0
            || jComboBox6.getSelectedIndex() == 0) {

        JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
        return;
    }
            
        // Validasi harga harus angka
        try {
            Double.parseDouble(jTextField4.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!");
            jTextField4.requestFocus();
            return;
        }

        // Validasi stok harus angka
        try {
            Integer.parseInt(jTextField5.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!");
            jTextField5.requestFocus();
            return;
        }
        
        // Cek kode HP sudah ada atau belum
        String cek = "SELECT * FROM handphone WHERE kode_hp=?";
        PreparedStatement ps = conn.prepareStatement(cek);
        ps.setString(1, jTextField1.getText());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(null, "Kode HP sudah digunakan!");
            return;
        }

        
        
        String sql = "INSERT INTO handphone(kode_hp,nama_hp,merek,tipe,warna,ram,storage,harga,stok,garansi) VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, jTextField1.getText());
        pst.setString(2, jTextField2.getText());
        pst.setString(3, jComboBox1.getSelectedItem().toString());
        pst.setString(4, jComboBox2.getSelectedItem().toString());
        pst.setString(5, jComboBox3.getSelectedItem().toString());
        pst.setString(6, jComboBox4.getSelectedItem().toString());
        pst.setString(7, jComboBox5.getSelectedItem().toString());
        pst.setDouble(8, Double.parseDouble(jTextField4.getText()));
        pst.setInt(9, Integer.parseInt(jTextField5.getText()));
        pst.setString(10, jComboBox6.getSelectedItem().toString());

        pst.executeUpdate();

        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");

        load_table();
        kosong();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int baris = jTable1.getSelectedRow();

        String kode = jTable1.getValueAt(baris,1).toString();

        kodeHpLama = kode;

        jTextField1.setText(kode);
        jTextField2.setText(jTable1.getValueAt(baris,2).toString());

        jComboBox1.setSelectedItem(jTable1.getValueAt(baris,3).toString());
        jComboBox2.setSelectedItem(jTable1.getValueAt(baris,4).toString());
        jComboBox3.setSelectedItem(jTable1.getValueAt(baris,5).toString());
        jComboBox4.setSelectedItem(jTable1.getValueAt(baris,6).toString());
        jComboBox5.setSelectedItem(jTable1.getValueAt(baris,7).toString());

        jTextField4.setText(jTable1.getValueAt(baris,8).toString());
        jTextField5.setText(jTable1.getValueAt(baris,9).toString());

        jComboBox6.setSelectedItem(jTable1.getValueAt(baris,10).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed


    if (kodeHpLama==null) {
        JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
        return;
    }

    if (jTextField1.getText().isEmpty()
            || jTextField2.getText().isEmpty()
            || jTextField4.getText().isEmpty()
            || jTextField5.getText().isEmpty()
            || jComboBox1.getSelectedIndex() == 0
            || jComboBox2.getSelectedIndex() == 0
            || jComboBox3.getSelectedIndex() == 0
            || jComboBox4.getSelectedIndex() == 0
            || jComboBox5.getSelectedIndex() == 0
            || jComboBox6.getSelectedIndex() == 0) {

        JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
        return;
    }
    
    // Validasi harga harus angka
        try {
            Double.parseDouble(jTextField4.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!");
            jTextField4.requestFocus();
            return;
        }

        // Validasi stok harus angka
        try {
            Integer.parseInt(jTextField5.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!");
            jTextField5.requestFocus();
            return;
        }

    try {

        Connection conn = configDB();
        
        String cek = "SELECT * FROM handphone WHERE kode_hp=? AND kode_hp<>?";
        PreparedStatement cekKode = conn.prepareStatement(cek);

        cekKode.setString(1, jTextField1.getText());
        cekKode.setString(2, kodeHpLama);

        ResultSet rs = cekKode.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Kode HP sudah digunakan!");
            return;
        }

        String sql = "UPDATE handphone SET "
                + "kode_hp=?,"
                + "nama_hp=?,"
                + "merek=?,"
                + "tipe=?,"
                + "warna=?,"
                + "ram=?,"
                + "storage=?,"
                + "harga=?,"
                + "stok=?,"
                + "garansi=? "
                + "WHERE kode_hp=?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, jTextField1.getText());
        pst.setString(2, jTextField2.getText());
        pst.setString(3, jComboBox1.getSelectedItem().toString());
        pst.setString(4, jComboBox2.getSelectedItem().toString());
        pst.setString(5, jComboBox3.getSelectedItem().toString());
        pst.setString(6, jComboBox4.getSelectedItem().toString());
        pst.setString(7, jComboBox5.getSelectedItem().toString());
        pst.setDouble(8, Double.parseDouble(jTextField4.getText()));
        pst.setInt(9, Integer.parseInt(jTextField5.getText()));
        pst.setString(10, jComboBox6.getSelectedItem().toString());

        pst.setString(11, kodeHpLama);

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Data berhasil diubah");

        load_table();
        kosong();
        kodeHpLama = null;

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    kosong();
    kodeHpLama = null;
    jTable1.clearSelection();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Data_Transaksi transaksi = new Data_Transaksi();
        transaksi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
         if (jTextField3.getText().trim().isEmpty()) {
        load_table();
        return;
    }

    cariData();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed

    filterMerek();

    }//GEN-LAST:event_jComboBox7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Data_Handphone().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
