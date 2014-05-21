/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package probe;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cadete
 */
public class Utils {
    final private static Map<Class,Class> primitiveMap = new HashMap();
    static {
        primitiveMap.put(boolean.class, Boolean.class);
        primitiveMap.put(byte.class, Byte.class);
        primitiveMap.put(char.class, Character.class);
        primitiveMap.put(short.class, Short.class);
        primitiveMap.put(int.class, Integer.class);
        primitiveMap.put(long.class, Long.class);
        primitiveMap.put(float.class, Float.class);
        primitiveMap.put(double.class, Double.class);
    }
    
    public static Class getPrimitiveWrappedClass(Class type){
        return primitiveMap.get(type);
    }
}
