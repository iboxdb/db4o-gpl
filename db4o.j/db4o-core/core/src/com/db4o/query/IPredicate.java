/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db4o.query;

import java.io.Serializable;

/**
 *
 * @author user
 */
public interface IPredicate<T> extends Serializable{

    public boolean match(T candidate);

}
