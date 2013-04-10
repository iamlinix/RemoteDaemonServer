/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotedaemon;
import java.net.*;
/**
 *
 * @author Lin
 */
public interface UDPDelegate {
    public void OnDatagramPacket(DatagramPacket packet);
}
