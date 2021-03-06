/*
 * OpenERP, Open Source Management Solution
 * Copyright (C) 2012-today OpenERP SA (<http://www.openerp.com>)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * 
 */
package com.openerp.support;

import javax.net.ssl.SSLPeerUnverifiedException;

import openerp.OEVersionException;
import openerp.OpenERP;

import org.json.JSONArray;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.openerp.MainActivity;
import com.openerp.auth.OpenERPAccountManager;
import com.openerp.base.login.Login;

/**
 * The Class OpenERPServerConnection.
 */
public class OpenERPServerConnection  {

	public static final String TAG = "com.openerp.support.OpenERPServerConnection";
	/** The openerp. */
	public static OpenERP openerp = null;
	
	JSONArray mDbLists = null;
	boolean mAllowSelfSignedSSL = false;
	String databaseName;
	String[] dbs;
	
	SharedPreferences sh;
	public OpenERPServerConnection() {
		mAllowSelfSignedSSL = false;
	}

	public OpenERPServerConnection(boolean allowSelfSignedSSL) {
		mAllowSelfSignedSSL = allowSelfSignedSSL;
	}

	/**
	 * Test connection.
	 * 
	 * @param context
	 *            the context
	 * @param serverURL
	 *            the server url
	 * @param mForceConnect
	 * @return true, if successful
	 * @throws OEVersionException
	 * @throws SSLPeerUnverifiedException
	 */
	public boolean testConnection(Context context, String serverURL)
			throws OEVersionException, SSLPeerUnverifiedException {
		Log.d(TAG, "OpenERPServerConnection->testConnection()");
		if (TextUtils.isEmpty(serverURL)) {
			return false;
		}
		try {
			

			openerp = new OpenERP(serverURL, mAllowSelfSignedSSL);
			
			mDbLists = openerp.getDatabaseList();
			
			
					//	openerp.getTableList();
			Log.d("mDblists="+mDbLists,"json array");
		} catch (SSLPeerUnverifiedException ssl) {
			Log.d(TAG, "Throw SSLPeerUnverifiedException ");
			throw new SSLPeerUnverifiedException(ssl.getMessage());
		} catch (OEVersionException version) {
			throw new OEVersionException(version.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String[] getDatabases() {
		 dbs = new String[mDbLists.length()];
		try {
			for (int i = 0; i < mDbLists.length(); i++)
			{
				dbs[i] = mDbLists.getString(i);
		//	databaseName=mDbLists.getString(0);
			
		//	Log.d("db====================)"+mDbLists.getString(0),"========");
			}
			
		} catch (Exception e) {
		}
		return dbs;
	}
	
	

	/**
	 * Checks if is network available.
	 * 
	 * @param context
	 *            the context
	 * @return true, if is network available
	 * @throws OEVersionException
	 * @throws SSLPeerUnverifiedException
	 */
	public static boolean isNetworkAvailable(Context context)
			throws OEVersionException, SSLPeerUnverifiedException {
		boolean outcome = false;

		OpenERPServerConnection osc = new OpenERPServerConnection();
		outcome = osc.testConnection(context, OpenERPAccountManager
				.currentUser(context).getHost());

		return outcome;
	}

	/**
	 * Checks if is network available.
	 * 
	 * @param context
	 *            the context
	 * @param url
	 *            the url
	 * @return true, if is network available
	 * @throws OEVersionException
	 * @throws SSLPeerUnverifiedException
	 */
	public static boolean isNetworkAvailable(Context context, String url)
			throws OEVersionException, SSLPeerUnverifiedException {
		boolean outcome = false;

		OpenERPServerConnection osc = new OpenERPServerConnection();
		outcome = osc.testConnection(context, url);

		return outcome;
	}

	public CharSequence getTable() {
		// TODO Auto-generated method stub
		Log.d("databasename="+Login.database[0],"ok.....");
		
		
		//String database=Login.database[0];
		
		
		//SQLiteDatabase DB = sqlHelper.getWritableDatabase();
		
	//	String quary="SELECT name FROM sqlite_master WHERE type='table'";
	//	Cursor c = database.rawQuery(quary, null);

//		if (c.moveToFirst()) {
//		    while ( !c.isAfterLast() ) {
//		        Toast.makeText(activityName.this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
//		        c.moveToNext();
//		    }
//		}
		return Login.database[0];
	}
}
