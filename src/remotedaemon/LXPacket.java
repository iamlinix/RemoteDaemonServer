/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotedaemon;

import java.io.*;
/**
 *
 * @author Lin
 */
public class LXPacket extends LXHeader implements Serializable {
    short combinedKeys;
    int[] keys;
    
    LXPacket() {
        this.packetType = PacketType.KEYPRESS_PACKET;
        this.keys = new int[3];
    }
}
