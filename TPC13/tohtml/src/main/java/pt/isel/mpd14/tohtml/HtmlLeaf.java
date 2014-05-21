/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.isel.mpd14.tohtml;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Cadete
 */
public class HtmlLeaf implements HtmlNode{

    final String name;
    final Map<String, Object> attributes;
    
    public HtmlLeaf(String name){
        this.name = name;
        //this.ident += " ";    
        attributes = new HashMap<>();
    }
        
    public void addAttribute(String name, Object value){
        attributes.put(name, value);
    }
    
    public String print() {
       String res = ident +"<" + name;
       res += attributes.entrySet().stream().map((attribute) -> 
               " "+attribute.getKey()+"=\""+attribute.getValue()+"\"").reduce(res, String::concat);       
       res +="\\>\n";
       return res;
    }
    
}
