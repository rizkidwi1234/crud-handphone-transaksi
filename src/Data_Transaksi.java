/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author RIZKI
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;

public class Data_Transaksi extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Data_Transaksi.class.getName());

    /**
     * Creates new form Data_Transaksi
     */
    private int idTransaksi;
    private int jumlahLama;
    
    public Data_Transaksi() {
        initComponents();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    jTextField9.setText(sdf.format(new Date()));

    jTextField7.setEditable(false);
    jTextField8.setEditable(false);
    jTextField9.setEditable(false);
    jTextField12.setEditable(false);

    loadCombo();
    load_table();
    kosong();
}
    
    private void loadCombo() {
        try {
            Connection conn = Data_Handphone.configDB();
            String sql = "SELECT kode_hp FROM handphone";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            jComboBox1.removeAllItems();
            jComboBox1.addItem("Pilih Kode HP");
            
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("kode_hp"));
            }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    
    private void cariData() {

    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("No");
    model.addColumn("Kode HP");
    model.addColumn("Nama HP");
    model.addColumn("Pembeli");
    model.addColumn("Jumlah");
    model.addColumn("Tanggal");
    model.addColumn("Total");

    try {

        int no = 1;

        Connection conn = Data_Handphone.configDB();

        String sql =
        "SELECT transaksi.*, handphone.nama_hp " +
        "FROM transaksi " +
        "JOIN handphone ON transaksi.kode_hp = handphone.kode_hp " +
        "WHERE transaksi.kode_hp LIKE ? " +
        "OR handphone.nama_hp LIKE ? " +
        "OR transaksi.nama_pembeli LIKE ? " +
        "OR transaksi.tanggal LIKE ?";

        PreparedStatement pst = conn.prepareStatement(sql);

        String cari = "%" + jTextField1.getText() + "%";

        pst.setString(1, cari);
        pst.setString(2, cari);
        pst.setString(3, cari);
        pst.setString(4, cari);

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
                rs.getString("nama_pembeli"),
                rs.getInt("jumlah"),
                rs.getString("tanggal"),
                "Rp " + rupiah.format(rs.getDouble("total_harga"))
            });

        }

        jTable1.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

}
    private void filterData() {

    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("No");
    model.addColumn("Kode HP");
    model.addColumn("Nama HP");
    model.addColumn("Pembeli");
    model.addColumn("Jumlah");
    model.addColumn("Tanggal");
    model.addColumn("Total");

    try {

        int no = 1;

        Connection conn = Data_Handphone.configDB();

        String sql = "SELECT transaksi.*, handphone.nama_hp "
                + "FROM transaksi "
                + "JOIN handphone ON transaksi.kode_hp = handphone.kode_hp "
                + "WHERE 1=1";

        if (jComboBox2.getSelectedIndex() != 0) {
            sql += " AND MONTH(tanggal)=?";
        }

        if (jComboBox3.getSelectedIndex() != 0) {
            sql += " AND YEAR(tanggal)=?";
        }

        PreparedStatement pst = conn.prepareStatement(sql);

        int i = 1;

        if (jComboBox2.getSelectedIndex() != 0) {
            pst.setInt(i++, jComboBox2.getSelectedIndex());
        }

        if (jComboBox3.getSelectedIndex() != 0) {
            pst.setInt(i++, Integer.parseInt(jComboBox3.getSelectedItem().toString()));
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
                rs.getString("nama_pembeli"),
                rs.getInt("jumlah"),
                rs.getString("tanggal"),
                "Rp " + rupiah.format(rs.getDouble("total_harga"))
            });

        }

        jTable1.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

}
    private void kosong() {

        jComboBox1.setSelectedIndex(0);

        jTextField7.setText("");
        jTextField8.setText("");

        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jTextField9.setText(sdf.format(new Date()));
        
        idTransaksi = 0;
        jumlahLama = 0;

    }
    
    private void load_table() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("No");
        model.addColumn("Kode HP");
        model.addColumn("Nama HP");
        model.addColumn("Pembeli");
        model.addColumn("Jumlah");
        model.addColumn("Tanggal");
        model.addColumn("Total");

        try {

            int no = 1;

            Connection conn = Data_Handphone.configDB();

            String sql =
            "SELECT transaksi.*, handphone.nama_hp " +
            "FROM transaksi " +
            "JOIN handphone ON transaksi.kode_hp = handphone.kode_hp";

            PreparedStatement pst = conn.prepareStatement(sql);

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
        rs.getString("nama_pembeli"),
        rs.getString("jumlah"),
        rs.getString("tanggal"),
        "Rp " + rupiah.format(rs.getDouble("total_harga"))
    });



            }

            jTable1.setModel(model);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());

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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("DATA TRANSAKSI");

        jLabel2.setText("Kode HP");

        jLabel3.setText("Nama HP");

        jLabel4.setText("harga");

        jLabel5.setText("Tanggal");

        jLabel6.setText("Nama Pembeli");

        jLabel7.setText("Jumlah");

        jLabel8.setText("Total");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jButton1.setText("Tambah");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Clear");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Edit");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Hapus");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Kembali");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Hp", " Nama Hp", "Pembeli", "Jumlah", "Tanggal Pembelian", "Total Harga"
            }
        ));
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTextField7.setText("jTextField7");

        jTextField8.setText("jTextField8");
        jTextField8.addActionListener(this::jTextField8ActionPerformed);

        jTextField9.setText("jTextField9");

        jTextField10.setText("jTextField10");

        jTextField11.setText("jTextField11");
        jTextField11.addActionListener(this::jTextField11ActionPerformed);
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField11KeyReleased(evt);
            }
        });

        jTextField12.setText("jTextField12");
        jTextField12.addActionListener(this::jTextField12ActionPerformed);
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField12KeyReleased(evt);
            }
        });

        jButton6.setText("Search");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua Bulan", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua Tahun", "2024", "2025", "2026", "2027", "2028" }));
        jComboBox3.addActionListener(this::jComboBox3ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(365, 365, 365)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addComponent(jLabel8))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton2)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(62, 62, 62)
                                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton3)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton4)))
                                        .addGap(228, 228, 228)
                                        .addComponent(jButton5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jComboBox1, 0, 282, Short.MAX_VALUE)
                                            .addComponent(jTextField7)
                                            .addComponent(jTextField8))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(22, 22, 22)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField9)
                                            .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                            .addComponent(jTextField11)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {

            if (jComboBox1.getSelectedIndex() == 0
                    || jTextField10.getText().isEmpty()
                    || jTextField11.getText().isEmpty()
                    || jTextField12.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Semua data harus diisi!");
                return;
            }

            Connection conn = Data_Handphone.configDB();

            int jumlah = Integer.parseInt(jTextField11.getText());

            // =======================
            // CEK STOK
            // =======================
            String cek = "SELECT stok FROM handphone WHERE kode_hp=?";
            PreparedStatement ps = conn.prepareStatement(cek);
            ps.setString(1, jComboBox1.getSelectedItem().toString());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                int stok = rs.getInt("stok");

                if(jumlah > stok){
                    JOptionPane.showMessageDialog(null,"Stok tidak mencukupi!");
                    return;
                }

            }

            // =======================
            // SIMPAN TRANSAKSI
            // =======================
            String sql = "INSERT INTO transaksi(kode_hp,tanggal,nama_pembeli,jumlah,total_harga) VALUES (?,?,?,?,?)";

            PreparedStatement pst = conn.prepareStatement(sql);

           pst.setString(1, jComboBox1.getSelectedItem().toString());
pst.setString(2, jTextField9.getText());
pst.setString(3, jTextField10.getText());
pst.setInt(4, jumlah);

// Ambil harga dari textbox
String harga = jTextField8.getText()
        .replace("Rp", "")
        .replace(".", "")
        .replace(",", "")
        .trim();

double hargaSatuan = Double.parseDouble(harga);
double total = hargaSatuan * jumlah;

pst.setDouble(5, total);

            pst.executeUpdate();

            // =======================
            // KURANGI STOK
            // =======================
            String update = "UPDATE handphone SET stok = stok - ? WHERE kode_hp=?";

            PreparedStatement pst2 = conn.prepareStatement(update);

            pst2.setInt(1, jumlah);
            pst2.setString(2, jComboBox1.getSelectedItem().toString());

            pst2.executeUpdate();

            JOptionPane.showMessageDialog(null,"Transaksi berhasil");

            load_table();
            kosong();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        kosong();
        idTransaksi = 0;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(idTransaksi==0){
        JOptionPane.showMessageDialog(null,"Pilih data yang akan diedit!");
        return;
        }

        try{

            Connection conn = Data_Handphone.configDB();

            int jumlahBaru = Integer.parseInt(jTextField11.getText());

            // =====================
            // Kembalikan stok lama
            // =====================
            String kembali = "UPDATE handphone SET stok = stok + ? WHERE kode_hp=?";
            PreparedStatement pst1 = conn.prepareStatement(kembali);

            pst1.setInt(1, jumlahLama);
            pst1.setString(2, jComboBox1.getSelectedItem().toString());

            pst1.executeUpdate();

            // =====================
            // Cek stok lagi
            // =====================
            String cek = "SELECT stok FROM handphone WHERE kode_hp=?";
            PreparedStatement pst2 = conn.prepareStatement(cek);

            pst2.setString(1, jComboBox1.getSelectedItem().toString());

            ResultSet rs = pst2.executeQuery();

            if(rs.next()){

                int stok = rs.getInt("stok");

                if(jumlahBaru > stok){

                    JOptionPane.showMessageDialog(null,"Stok tidak mencukupi!");

                    // balikin lagi seperti semula
                    String batal = "UPDATE handphone SET stok = stok - ? WHERE kode_hp=?";
                    PreparedStatement pstBatal = conn.prepareStatement(batal);

                    pstBatal.setInt(1, jumlahLama);
                    pstBatal.setString(2, jComboBox1.getSelectedItem().toString());

                    pstBatal.executeUpdate();

                    return;
                }

            }

            // =====================
            // Update transaksi
            // =====================
            String sql = "UPDATE transaksi SET tanggal=?, nama_pembeli=?, jumlah=?, total_harga=? WHERE id_transaksi=?";

            PreparedStatement pst3 = conn.prepareStatement(sql);

            pst3.setString(1, jTextField9.getText());
pst3.setString(2, jTextField10.getText());
pst3.setInt(3, jumlahBaru);

// Ambil harga satuan
String harga = jTextField8.getText()
        .replace("Rp", "")
        .replace(".", "")
        .replace(",", "")
        .trim();

double hargaSatuan = Double.parseDouble(harga);
double total = hargaSatuan * jumlahBaru;

pst3.setDouble(4, total);
pst3.setInt(5, idTransaksi);

            pst3.executeUpdate();

            // =====================
            // Kurangi stok lagi
            // =====================
            String update = "UPDATE handphone SET stok = stok - ? WHERE kode_hp=?";

            PreparedStatement pst4 = conn.prepareStatement(update);

            pst4.setInt(1, jumlahBaru);
            pst4.setString(2, jComboBox1.getSelectedItem().toString());

            pst4.executeUpdate();

            JOptionPane.showMessageDialog(null,"Data berhasil diubah");

            load_table();
            kosong();
            idTransaksi = 0;

        }catch(Exception e){

            JOptionPane.showMessageDialog(null,e.getMessage());

        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Data_Handphone hp = new Data_Handphone();
        hp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(idTransaksi==0){
            JOptionPane.showMessageDialog(null,"Pilih data yang akan dihapus");
            return;
        }
        
            int jawab = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus data ini?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);
            
        if (jawab == JOptionPane.YES_OPTION) {

        try{

            Connection conn=Data_Handphone.configDB();

            String kembali="UPDATE handphone SET stok=stok+? WHERE kode_hp=?";
            PreparedStatement pst1=conn.prepareStatement(kembali);

            pst1.setInt(1,jumlahLama);
            pst1.setString(2,jComboBox1.getSelectedItem().toString());

            pst1.executeUpdate();

            String hapus="DELETE FROM transaksi WHERE id_transaksi=?";
            PreparedStatement pst2=conn.prepareStatement(hapus);

            pst2.setInt(1,idTransaksi);

            pst2.executeUpdate();

            JOptionPane.showMessageDialog(null,"Data berhasil dihapus");

            load_table();
            kosong();

            idTransaksi=0;

        }catch(Exception e){

            JOptionPane.showMessageDialog(this,e.getMessage());

        }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedItem() == null) {
        return;
    }

        if (jComboBox1.getSelectedIndex() == 0) {
        jTextField7.setText("");
        jTextField8.setText("");
        return;
    }

    try {
        Connection conn = Data_Handphone.configDB();
        String sql = "SELECT * FROM handphone WHERE kode_hp=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, jComboBox1.getSelectedItem().toString());
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {

    jTextField7.setText(rs.getString("nama_hp"));

    DecimalFormatSymbols simbol = new DecimalFormatSymbols();
    simbol.setGroupingSeparator('.');
    simbol.setDecimalSeparator(',');

    DecimalFormat rupiah = new DecimalFormat("#,###", simbol);

    jTextField8.setText("Rp " + rupiah.format(rs.getDouble("harga")));
}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyReleased

    }//GEN-LAST:event_jTextField12KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int baris = jTable1.getSelectedRow();

        jComboBox1.setSelectedItem(jTable1.getValueAt(baris,1).toString());
        jTextField7.setText(jTable1.getValueAt(baris,2).toString());
        jTextField10.setText(jTable1.getValueAt(baris,3).toString());
        jTextField11.setText(jTable1.getValueAt(baris,4).toString());
        jTextField9.setText(jTable1.getValueAt(baris,5).toString());
        jTextField12.setText(jTable1.getValueAt(baris,6).toString());

        try{

            Connection conn = Data_Handphone.configDB();

            String sql="SELECT id_transaksi,jumlah FROM transaksi WHERE kode_hp=? AND tanggal=? AND nama_pembeli=?";

            PreparedStatement pst=conn.prepareStatement(sql);

            pst.setString(1,jComboBox1.getSelectedItem().toString());
            pst.setString(2,jTextField9.getText());
            pst.setString(3,jTextField10.getText());

            ResultSet rs=pst.executeQuery();

            if(rs.next()){

                idTransaksi=rs.getInt("id_transaksi");
                jumlahLama=rs.getInt("jumlah");

            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(null,e.getMessage());

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
            // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyReleased
        try {

            // Hilangkan tulisan "Rp " dan titik
            String harga = jTextField8.getText()
            .replace("Rp", "")
            .replace(".", "")
            .trim();

            double hrg = Double.parseDouble(harga);

            int jumlah = Integer.parseInt(jTextField11.getText());

            double total = hrg * jumlah;

            DecimalFormatSymbols simbol = new DecimalFormatSymbols();
            simbol.setGroupingSeparator('.');
            simbol.setDecimalSeparator(',');

            DecimalFormat rupiah = new DecimalFormat("#,###", simbol);

            jTextField12.setText("Rp " + rupiah.format(total));

        } catch (Exception e) {
            jTextField12.setText("");
        }
    }//GEN-LAST:event_jTextField11KeyReleased

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (jTextField1.getText().trim().isEmpty()) {
        load_table();
    } else {
        cariData();
    }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
         filterData();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        filterData();
    }//GEN-LAST:event_jComboBox3ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Data_Transaksi().setVisible(true));
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
