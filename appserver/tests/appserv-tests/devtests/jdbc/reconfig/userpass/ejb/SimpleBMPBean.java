/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.s1asdev.jdbc.reconfig.userpass.ejb;

import javax.ejb.*;
import javax.naming.*;
import javax.sql.*;
import java.rmi.*;
import java.util.*;
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class SimpleBMPBean
    implements EntityBean
{

    protected DataSource ds;
    int id;

    public void setEntityContext(EntityContext entityContext) {
	Context context = null;
	try {
	    context    = new InitialContext();
	    ds = (DataSource) context.lookup("java:comp/env/DataSource1");
	} catch (NamingException e) {
	    e.printStackTrace();
	    throw new EJBException("cant find datasource");
	}
    }

    public Integer ejbCreate() throws CreateException {
	return new Integer(1);
    }

    public boolean test1(String user, String password, String tableName) {
        //access User1's table and push some data then read it out
	boolean passed = false;
	Connection conn = null;
	try {
	    System.out.println("Called with " + user + ":"+password);
	    conn = ds.getConnection(user, password);
	    //conn = ds.getConnection();
	    insertData( conn, tableName );
	    queryTable( conn, tableName );
	    //emptyTable( conn, tableName );
            conn.close();
	    passed = true;
	} catch (Exception e) {
	   //e.printStackTrace(); 
	} finally {
	    if ( conn != null ) {
	        try {
	            conn.close();
		} catch( Exception e1) {}    
	    }
	}

	return passed;
    }
    

    public void ejbLoad() {}
    public void ejbStore() {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void unsetEntityContext() {}
    public void ejbPostCreate() {}

    //Insert some arbitrary data into the table
    private void insertData( Connection con, String tableName ) 
            throws SQLException
    {
        PreparedStatement stmt = con.prepareStatement(
	    "insert into " + tableName + " values (?, ?)" );

	for (int i = 0; i < 5; i++ ) {
            stmt.setInt(1, i);
	    stmt.setString(2, "abcd-"+i);
	    stmt.executeUpdate();
	}

	stmt.close();
    }

    private void emptyTable( Connection con, String tableName ) 
            throws SQLException
    {
        try {
            Statement stmt = con.createStatement();
            
	    stmt.execute("delete * from "+ tableName);
	    stmt.close();
        } catch( Exception e) {
	}
	    
    }

    private void queryTable( Connection con, String tableName ) 
            throws SQLException
    {
        try {
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery("select * from "+ tableName);
	    while( rs.next() ) { 
	        System.out.println( rs );
	    }	
	    rs.close();
	} catch( Exception e) {
	}
    }

}
