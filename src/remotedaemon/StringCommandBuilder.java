/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotedaemon;

import java.io.UnsupportedEncodingException;
import java.awt.*;

/**
 * Packet factory
 * @author Lin
 */
public class StringCommandBuilder {
    public static final int UNKNOWN_PACKET = 0;
    public static final int MULTICAST_PACKET = 1;
    public static final int MULTICAST_RESPONSE_PACKET = 2;
    public static final int KEY_PRESS_PACKET = 3;
    public static final int KEY_RELEASE_PACKET = 4;
    public static final int MOUSE_MOVEPACKET = 5;
    public static final int MOUSE_CLICK_PACKET = 6;
    public static final int MOUSE_RELEASE_PACKET = 7;
    
    public static byte[] BuildMulticastResponsePacket() throws UnsupportedEncodingException
    {
        String strCmd = "2\t" 
                + Toolkit.getDefaultToolkit().getScreenSize().width
                + "\t"
                + Toolkit.getDefaultToolkit().getScreenSize().height;
        byte[] bytes = strCmd.getBytes("utf8");
        return bytes;
    }
}
