/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consulta.detran.view;

import com.DeathByCaptcha.Exception;
import consulta.detran.ParserAcess;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author Torres
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Login() {
        this.setLocationRelativeTo(null);
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        autenticando = new javax.swing.JProgressBar();
        logo = new javax.swing.JLabel();
        login = new javax.swing.JLabel();
        senha = new javax.swing.JLabel();
        senhafield = new javax.swing.JPasswordField();
        loginfield = new javax.swing.JTextField();
        acessar = new javax.swing.JButton();

        autenticando.setSize(146, 17);
        autenticando.setValue(100);
        autenticando.setString("Autenticando...");
        autenticando.setStringPainted(true);
        autenticando.setVisible(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hungaro - Consulta Detran");
        setAlwaysOnTop(true);
        setResizable(false);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/hu.jpg"))); // NOI18N
        logo.setText("jLabel3");

        login.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        login.setText("Login :");

        senha.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        senha.setText("Senha :");

        acessar.setRolloverEnabled(true);
        acessar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        acessar.setText("Acessar");
        acessar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acessarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(senha))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loginfield, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(senhafield, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(acessar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(login)
                        .addGap(37, 37, 37)
                        .addComponent(senha))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loginfield, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(senhafield, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(acessar)
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void acessarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acessarActionPerformed
        System system = null;
        Toolkit.getDefaultToolkit().beep();
        this.setVisible(false);
//        autenticando.setVisible(true);
//        criaFrameBar();
//        FileInputStream file = (FileInputStream) getClass().getResourceAsStream("/planilha/modelo.xlsx");
//        JOptionPane.showMessageDialog(null, getClass().getResourceAsStream("/planilha/modelo.xlsx").toString());
        try {
            system = new System();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
//        load.setVisible(true);
        ParserAcess autentic = new ParserAcess(loginfield.getText(), senhafield.getText());

        try {
            if (autentic.autentication()) {
//                system.setLogin("khelmo");
                system.setLogin(loginfield.getText());
//                system.setSenha("fluzao4torrada");
                system.setSenha(senhafield.getText());
//                load.dispose();
//                frameBarra.setVisible(false);
                system.setVisible(true);
                this.dispose();
            } else {
                this.dispose();
                JOptionPane.showMessageDialog(null, "Acesso Negado!");
                Login login = new Login();
                login.setLocationRelativeTo(null);
                login.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_acessarActionPerformed
    public void criaFrameBar() {
        JFrame frameBarra = new JFrame();
        frameBarra.setLayout(getLayout());
        frameBarra.add(autenticando);
        autenticando.setSize(200, 50);
        autenticando.setVisible(true);
        frameBarra.setTitle("Consulta");
        frameBarra.setSize(400, 80);
        frameBarra.setResizable(false);
        frameBarra.setLocationRelativeTo(null);
        frameBarra.setVisible(true);

    }

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
                    javax.swing.UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login loginFrame = new Login();
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
//                

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acessar;
    private javax.swing.JProgressBar autenticando;
    private javax.swing.JLabel login;
    private javax.swing.JTextField loginfield;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel senha;
    private javax.swing.JTextField senhafield;
    // End of variables declaration//GEN-END:variables
}