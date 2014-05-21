/*
 * Copyright (C) 2014 Cadete
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pt.isel.mpd14.probe;

/**
 *
 * @author Cadete
 */
public class BindUpperCase<T> implements BindMember<T>{

    private final BindMember bindMember;
    
    public BindUpperCase(BindMember bindMember) {
        if(bindMember instanceof BindUpperCase)
            throw new IllegalArgumentException("BindMember cannot be an instance of BindUpperCase");
        this.bindMember = bindMember;
    }
    
    private static Object upperCaseString(Object o){
        if(o != null && o.getClass().equals(String.class))
            return o.toString().toUpperCase();
        return o;
    }
    
    @Override
    public boolean bind(T target, String name, Object v) {
        if (v == null) {
            return false;
        }
        return bindMember.bind(target, name, upperCaseString(v));

    }
    
}
