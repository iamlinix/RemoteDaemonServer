/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotedaemon;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lin
 */

public class UDPServer extends Thread{
    
    private DatagramSocket socket;
    private byte[] buf;
    private UDPDelegate broker;
    
    
    
    public UDPServer(String name, int port, UDPDelegate delegate) throws SocketException
    {
        super(name);
        socket = new DatagramSocket(port);
        broker = delegate;
    }
    
    public void run()
    {
        while(true)
        {
            try
            {
                buf = new byte[1024];
                // receive request
                DatagramPacket packet;packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet); 
                broker.OnDatagramPacket(packet);
                 
            }
            catch(Exception ex)
            {
                
            }
        }
    }
    
    public void send(byte[] data, String ipAddress, int port)
    {
        DatagramPacket packet = new DatagramPacket(data, data.length);
        SocketAddress sa = new InetSocketAddress(ipAddress, port);
        packet.setSocketAddress(sa);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
