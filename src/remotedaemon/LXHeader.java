/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotedaemon;

/**
 *
 * @author Lin
 */
enum PacketType { MULTICAST_PACKET,KEYPRESS_PACKET }
public class LXHeader {
    public PacketType packetType; 
}
