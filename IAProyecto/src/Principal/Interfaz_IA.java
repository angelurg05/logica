package Principal;

import javax.swing.*;
import java.io.*;
import java.io.File;
import net.sourceforge.tess4j.*;

/**
 *
 * @author Angel Uraga
 */
public class Interfaz_IA extends javax.swing.JFrame {

    Imagen img = new Imagen();
    Logica objeto = new Logica();
    JFileChooser seleccionado = new JFileChooser();
    File archivo;
    byte[] bytesImg;

    /**
     * Creates new form Interfaz_IA
     */
    public Interfaz_IA() {
        initComponents();
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
        jScrollPane3 = new javax.swing.JScrollPane();
        ecuacion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        btnImagen = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblImagen = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compuertas Lógicas IA");
        setBackground(java.awt.Color.orange);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ecuacion.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jScrollPane3.setViewportView(ecuacion);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 590, 90));

        jLabel2.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("NOT = !   AND = &  OR = #    Condicional =  ->  Bicondicional =  <->");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        txtArea.setRows(5);
        jScrollPane2.setViewportView(txtArea);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 590, 240));

        btnImagen.setText("Añadir Imagen");
        btnImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagenActionPerformed(evt);
            }
        });
        jPanel1.add(btnImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Meiryo", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Funciones Lógicas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 480, 60));

        jScrollPane1.setViewportView(lblImagen);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 590, 150));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/FondoIA.jpg"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, -10, 1030, 750));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagenActionPerformed
        // TODO add your handling code here:
        if (seleccionado.showDialog(this, "Abrir Imagen") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionado.getSelectedFile();
            if (archivo.canRead()) {
                if (archivo.getName().endsWith("jpg") || archivo.getName().endsWith("png")) {
                    bytesImg = img.AbrirImagen(archivo);
                    System.out.println(archivo);
                    lblImagen.setText("");
                    lblImagen.setIcon(new ImageIcon(bytesImg));
                    File imageFile = new File(archivo.toString());
                    Tesseract tesseract = new Tesseract();
                    try {
                        tesseract.setDatapath("C:\\Users\\angel\\Downloads\\Tess4J\\tessdata");
                        String result = tesseract.doOCR(imageFile);
                        ecuacion.setText("");
                        ecuacion.setText(result);
                        StringBuilder salida = new StringBuilder();
                        salida = objeto.ExpLogica(result);
                        txtArea.setText("");
                        txtArea.setText(""+salida);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No es una imagen");
                }
            }
        }
    }//GEN-LAST:event_btnImagenActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz_IA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz_IA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz_IA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz_IA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz_IA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImagen;
    private javax.swing.JLabel ecuacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTextArea txtArea;
    // End of variables declaration//GEN-END:variables
}
