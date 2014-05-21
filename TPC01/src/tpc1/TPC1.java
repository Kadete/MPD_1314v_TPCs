package tpc1;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cadete
 */
public class TPC1 {
    
    static class xpto {
        public int a = 10;
        private String c;
        protected Math m;
        public xpto(){
            c = "c";
        }
    }
      /*TPC: Implementar a função Map<String, Object> getFieldsValues(Object o) 
    que retorna um mapa com os pares nome - valor de todos os campos do objecto 
    o recebido por parametro (incluindo campos privados).*/
    public static Map<String, Object> getFieldsValues(Object o)  throws IllegalArgumentException, IllegalAccessException{
        Map<String, Object> ret = new HashMap();
        if(o != null){
            Field[] fields = o.getClass().getDeclaredFields();  
            for(Field fld : fields){
               if(!fld.isAccessible())
                    fld.setAccessible(true);   
                ret.put(fld.getName(),fld.get(o));
            }
        }
        return ret;
    }
    
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        xpto x = new xpto();
        for (Map.Entry<String, Object> field : getFieldsValues(x).entrySet()) {
            System.out.println(field.getKey() + " - " + field.getValue());
        }
    }
}
