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
public class Serializer {
    
    public static byte[] object2Byte(Object obj) throws IOException{
        ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
        ObjectOutputStream out = new ObjectOutputStream(bout); 
        out.writeObject(obj); 
        out.flush(); 
        byte[] bytes = bout.toByteArray(); 
        bout.close(); 
        out.close(); 
        return bytes;
    }
    
    public static Object byte2Object(byte[] bytes) throws IOException, 
            ClassNotFoundException {
        ObjectInputStream oi;
        Object obj;
        try (ByteArrayInputStream bi = new ByteArrayInputStream(bytes)) {
            oi = new ObjectInputStream(bi); 
            obj = oi.readObject();
        }
        oi.close(); 
        return obj;
    }
}
