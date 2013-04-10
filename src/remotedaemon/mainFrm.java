/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotedaemon;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.*;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.net.*;


/**
 *
 * @author Lin
 */
public class mainFrm extends javax.swing.JFrame implements UDPDelegate {

    /*
     variables definition
     */
    Robot robot = null;
    UDPServer server;
    HashMap<Integer, Boolean> pressedKeys;
    int MouseX, MouseY;
    /**
     * Creates new form mainFrm
     */
    public mainFrm() {
        initComponents();
        try {
            robot = new Robot();
            server = new UDPServer("localserver", 9528, this);
            server.start();
            jLabel1.setText("Server running at port: 9527");
            pressedKeys = new HashMap<Integer, Boolean>();
        } catch (AWTException ex) {
            Logger.getLogger(mainFrm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException sex) {
             Logger.getLogger(mainFrm.class.getName()).log(Level.SEVERE, null, sex);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("The server is not running");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(mainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrm().setVisible(true);
            }
        });
    }
   
    @Override
    public void OnDatagramPacket(DatagramPacket packet)
    {
        try {
            String message = new String(packet.getData(), "utf8");
            message = message.trim();
            message += "\t" + packet.getAddress().getHostAddress() + "\t" 
                    + packet.getPort();
            OnMessage(message);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(mainFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public void OnMessage(String message)
    {
        System.out.println(message);
        //jTextArea1.append(message + "\n");
        //Logger.getLogger(mainFrm.class.getName()).log(Level.SEVERE, null, message);
        String[] parameters = message.split("\t");
        try
        {
            int packetType = Integer.parseInt(parameters[0]);
            switch(packetType)
            {
                case StringCommandBuilder.MULTICAST_PACKET:
                    byte[] data = StringCommandBuilder.BuildMulticastResponsePacket();
                    server.send(data, parameters[2], Integer.parseInt(parameters[3]));
                    break;
                    
                case StringCommandBuilder.KEY_PRESS_PACKET:
                    int keyToPress = Integer.parseInt(parameters[1]);
                  //  if(!pressedKeys.containsKey(keyToPress))
                //    {
                 //       pressedKeys.put(keyToPress, true);
                        robot.keyPress(keyToPress);
                        System.out.println("press: " + keyToPress );
                 //   }
                    break;
                    
                case StringCommandBuilder.KEY_RELEASE_PACKET:
                    int keyToRelease = Integer.parseInt(parameters[1]);
                    robot.keyRelease(keyToRelease);
                    System.out.println("release: " + keyToRelease );
                    break;
                    
                case StringCommandBuilder.MOUSE_MOVEPACKET:
                    robot.mouseMove(Integer.parseInt(parameters[1]), 
                            Integer.parseInt(parameters[2]));
                    break;
                    
                case StringCommandBuilder.MOUSE_CLICK_PACKET:
                    robot.mousePress(Integer.parseInt(parameters[1]));    
                    break;
                    
                case StringCommandBuilder.MOUSE_RELEASE_PACKET:
                    robot.mouseRelease(Integer.parseInt(parameters[1]));
                    break;
                    
                default:
                    //should not be here
                    
                    break;
            }
        }
        catch(Exception ex)
        {
            Logger.getLogger(mainFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}