/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.isel.mpd14.tohtml;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Miguel Gamboa at CCISEL
 */
public class HtmlElement implements HtmlNode{
    final String name;
    final List<HtmlNode> children;
    String ident;

    public HtmlElement(String name) {
        this.ident = "      ";
        this.name = name;
        children = new LinkedList<>();
    }
    
    public HtmlElement(String name, String content) {
        this.ident = "      ";
        this.name = name;
        children = new LinkedList<>();
        children.add(new TextNode(content));
    }
    
    public void add(HtmlNode elem){
        children.add(elem);
    }
    
    public String print(){
        String res = ident +"<" + name + ">\n";
        for (HtmlNode n : children) {
            res += "    " +n.print();
        }
        res += ident +"</" + name + ">\n";
        return res;
    }
}
