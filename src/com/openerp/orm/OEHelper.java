/*
 * OpenERP, Open Source Management Solution
 * Copyright (C) 2012-today OpenERP SA (<http:www.openerp.com>)
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
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 * 
 */
package com.openerp.orm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import openerp.OEArguments;
import openerp.OEDomain;
import openerp.OpenERP;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.openerp.App;
import com.openerp.MainActivity;
import com.openerp.orm.OEFieldsHelper.OERelationData;
import com.openerp.support.OEUser;
import com.openerp.util.Base64Helper;
import com.openerp.util.OEDate;
import com.openerp.util.PreferenceManager;

import com.openerp.base.ir.Ir_model;

public class OEHelper {

	public static String sbu = "";
	public static final String TAG = "com.openerp.orm.OEHelper";
	Context mContext = null;
	OEDatabase mDatabase = null;
	OEUser mUser = null;
	PreferenceManager mPref = null;
	int mAffectedRows = 0;
	List<Long> mResultIds = new ArrayList<Long>();
	List<OEDataRow> mRemovedRecordss = new ArrayList<OEDataRow>();
	OpenERP mOpenERP = null;
	App mApp = null;
	boolean withUser = true;
	boolean mAllowSelfSignedSSL = false;
	
	
	
	
	public static String selectedsourceid = null;

	public static String assetid_from_stock_production_lot = null;
	
	
	// public static List<String> menufecturing_order_lis;

	JSONObject obj;
	
	//below field use hotel.restaurant.order
	
	public static List<String> table_order_no = new ArrayList<String>();
	
	
	
	//below field use assets.assets obj
	
	public static List<String> qr_equip_name = new ArrayList<String>();
	public static List<String> qr_equip_serial = new ArrayList<String>();
	
	//public static List<Bitmap> image_medium = new ArrayList<Bitmap>();
	public static List<Bitmap> image_of_asset =new ArrayList<Bitmap>();
	public static List<Bitmap> image_of_barcode = new ArrayList<Bitmap>();
	
	public static List<String> qr_equip_asset_mark= new ArrayList<String>();
	public static List<String> qr_equip_asset_model = new ArrayList<String>();

	
	public static List<String> code = new ArrayList<String>();
	public static List<String> qr_equip_asset_bar_code = new ArrayList<String>();
	public static List<String> qr_equip_asset_barcode_Label = new ArrayList<String>();
	
	public static List<String> qr_equip_asset_qr_code = new ArrayList<String>();
	//public static List<String> qr_equip_asset_id = new ArrayList<String>();
	public static List<String> id = new ArrayList<String>();
	
	
	public static List<String> sop_selected_id_from_assets = new ArrayList<String>();
	
	public static List<String> location_name = new ArrayList<String>();
	public static List<String> property_stock_asset = new ArrayList<String>();
	
	public static List<String> category_name = new ArrayList<String>();
	public static List<String> category_id = new ArrayList<String>();
	
	public static List<String> equipment_name = new ArrayList<String>();
	public static List<String> equipment_type_id = new ArrayList<String>();
	
	public static List<String> originequipment_name = new ArrayList<String>();
	public static List<String> equipment_origin_id = new ArrayList<String>();
	
	public static List<String> qr_equip_asset_no  = new ArrayList<String>();

	
	// public static String selectmo_id_from_wo;
	
	public static String selected_moname_from_WO;
	public static String selected_Assets_id;
	
	public static String check_connection_exit=null;
	
	
	int flag_of_loop_of_related_location_id = 0;

	public static String last_order_id="";
	
	public static String cat_id="";
	public static ArrayList<String> quotation_id;
	
	
	
	public OEHelper(Context context, OEDatabase oeDatabase) {
		this(context, oeDatabase, false);
	}

	public OEHelper(Context context, OEDatabase oeDatabase,
			boolean allowSelfSignedSSL) {
		Log.d(TAG, "OEHelper->OEHelper()");
		try {
			mAllowSelfSignedSSL = allowSelfSignedSSL;
			init();
			mContext = context;
			mDatabase = oeDatabase;
			mApp = (App) context.getApplicationContext();
			mOpenERP = mApp.getOEInstance();
			mUser = OEUser.current(context);
			if (mOpenERP == null && mUser != null) {
				mUser = login(mUser.getUsername(), mUser.getPassword(),
						mUser.getDatabase(), mUser.getHost());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public OEHelper(Context context) {
		try {
			
			mAllowSelfSignedSSL = true;
			
			init();
			
			mContext = context;
			mApp = (App) context.getApplicationContext();
			mOpenERP = mApp.getOEInstance();
			mUser = OEUser.current(context);
			if (mUser != null) {
				Log.d("log...in" + MainActivity.check_dashboard_call_or_other,"ok...y");
				
				mUser = login(mUser.getUsername(), mUser.getPassword(),mUser.getDatabase(), mUser.getHost());
				
			} else
			{
				Log.d("not ..log...in", "ohh..no.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public OEHelper(Context context, boolean withUser) {
		this(context, withUser, false);
	}

	public OEHelper(Context context, boolean withUser,
			boolean allowSelfSignedSSL) {
		try {
			mAllowSelfSignedSSL = allowSelfSignedSSL;
			init();
			mContext = context;
			mApp = (App) context.getApplicationContext();
			mOpenERP = mApp.getOEInstance();
			this.withUser = withUser;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void init()
	{
		
		
	}

	public OEUser login(String username, String password, String database,
			String serverURL) {
		Log.d(TAG, "OEHelper->login()");
		OEUser userObj = null;
		try {
			check_connection_exit = "0";

			mOpenERP = new OpenERP(serverURL, mAllowSelfSignedSSL); 
			
			check_connection_exit = "1";
			JSONObject response = mOpenERP.authenticate(username, password,
					database);

			int userId = 0;
			if (response.get("uid") instanceof Integer) {
				mApp.setOEInstance(mOpenERP);
				if (OEUser.current(mContext) == null || !withUser) {
					Log.i("enter if=", "dhattteriki.. stop");
					userId = response.getInt("uid");

					OEFieldsHelper fields = new OEFieldsHelper(new String[] {
							"partner_id", "tz", "image", "company_id" });
					OEDomain domain = new OEDomain();
					domain.add("id", "=", userId);
					
					JSONObject res = mOpenERP
							.search_read("res.users", fields.get(),
									domain.get()).getJSONArray("records")
							.getJSONObject(0);

					userObj = new OEUser();
					userObj.setAvatar(res.getString("image"));

					userObj.setDatabase(database);
					userObj.setHost(serverURL);
					userObj.setIsactive(true);
					userObj.setAndroidName(androidName(username, database));
					userObj.setPartner_id(res.getJSONArray("partner_id")
							.getInt(0));
					userObj.setTimezone(res.getString("tz"));
					userObj.setUser_id(userId);
					userObj.setUsername(username);
					userObj.setPassword(password);
					userObj.setAllowSelfSignedSSL(mAllowSelfSignedSSL);
					String company_id = new JSONArray(
							res.getString("company_id")).getString(0);
					userObj.setCompany_id(company_id);
				} else {
					userObj = OEUser.current(mContext);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return userObj;
	}

	public OEUser getUser() {
		return mUser;
	}

	private String androidName(String username, String database) {
		StringBuffer android_name = new StringBuffer();
		android_name.append(username);
		android_name.append("[");
		android_name.append(database);
		android_name.append("]");
		return android_name.toString();
	}

	public boolean syncWithServer() {
		return syncWithServer(false, null, null, false, -1, false);
	}

	public boolean syncWithServer(boolean removeLocalIfNotExists) {
		return syncWithServer(false, null, null, false, -1,
				removeLocalIfNotExists);
	}

	public boolean syncWithServer(OEDomain domain,
			boolean removeLocalIfNotExists) {
		return syncWithServer(false, domain, null, false, -1,
				removeLocalIfNotExists);
	}

	public boolean syncWithServer(OEDomain domain) {
		return syncWithServer(false, domain, null, false, -1, false);
	}

	public boolean syncWithServer(boolean twoWay, OEDomain domain,
			List<Object> ids) {
		return syncWithServer(twoWay, domain, ids, false, -1, false);
	}

	public int getAffectedRows() {
		return mAffectedRows;
	}

	public List<OEDataRow> getRemovedRecords() {
		return mRemovedRecordss;
	}

	public List<Integer> getAffectedIds() {
		List<Integer> ids = new ArrayList<Integer>();
		for (Long id : mResultIds) {
			ids.add(Integer.parseInt(id.toString()));
		}
		return ids;
	}

	public boolean syncWithMethod(String method, OEArguments args) {
		return syncWithMethod(method, args, false);
	}

	public boolean syncWithMethod(String method, OEArguments args,
			boolean removeLocalIfNotExists) {
		Log.d(TAG, "OEHelper->syncWithMethod()");
		Log.d(TAG, "Model: " + mDatabase.getModelName());
		Log.d(TAG, "User: " + mUser.getAndroidName());
		Log.d(TAG, "Method: " + method);
		boolean synced = false;
		OEFieldsHelper fields = new OEFieldsHelper(
				mDatabase.getDatabaseColumns());
		try {
			JSONObject result = mOpenERP.call_kw(mDatabase.getModelName(),
					method, args.getArray());
			if (result.getJSONArray("result").length() > 0)
				mAffectedRows = result.getJSONArray("result").length();
			synced = handleResultArray(fields, result.getJSONArray("result"),
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return synced;
	}

	public boolean syncWithServer(boolean twoWay, OEDomain domain,
			List<Object> ids, boolean limitedData, int limits,
			boolean removeLocalIfNotExists) {
		boolean synced = false;
		Log.d(TAG, "OEHelper->syncWithServer()");
		Log.d(TAG, "Model: " + mDatabase.getModelName());
		if (mUser != null)
			Log.d(TAG, "User: " + mUser.getAndroidName());
		OEFieldsHelper fields = new OEFieldsHelper(
				mDatabase.getDatabaseColumns());
		try {
			if (domain == null) {
				domain = new OEDomain();
			}
			if (ids != null) {
				domain.add("id", "in", ids);
			}
			if (limitedData) {
				mPref = new PreferenceManager(mContext);
				int data_limit = mPref.getInt("sync_data_limit", 60);
				domain.add("create_date", ">=",
						OEDate.getDateBefore(data_limit));
			}

			if (limits == -1) {
				limits = 50;
			}
			JSONObject result = mOpenERP.search_read(mDatabase.getModelName(),
					fields.get(), domain.get(), 0, limits, null, null);
			mAffectedRows = result.getJSONArray("records").length();
			synced = handleResultArray(fields, result.getJSONArray("records"),
					removeLocalIfNotExists);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(TAG, mDatabase.getModelName() + " synced");

		return synced;
	}

	private boolean handleResultArray(OEFieldsHelper fields, JSONArray results,
			boolean removeLocalIfNotExists) {
		boolean flag = false;
		try {
			fields.addAll(results);
			// Handling many2many and many2one records
			List<OERelationData> rel_models = fields.getRelationData();
			for (OERelationData rel : rel_models) {
				OEHelper oe = rel.getDb().getOEInstance();
				oe.syncWithServer(false, null, rel.getIds(), false, 0, false);
			}

			List<Long> result_ids = mDatabase.createORReplace(
					fields.getValues(), removeLocalIfNotExists);
			mResultIds.addAll(result_ids);

			Log.i("mResultIds!!!!!!!!!!!!!!!" + mResultIds,
					"mResultIds!!!!!!!!!");
			mRemovedRecordss.addAll(mDatabase.getRemovedRecords());
			if (result_ids.size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isModelInstalled(String model) {
		boolean installed = true;
		Ir_model ir_model = new Ir_model(mContext);
		try {
			OEFieldsHelper fields = new OEFieldsHelper(new String[] { "model" });
			OEDomain domain = new OEDomain();
			domain.add("model", "=", model);
			JSONObject result = mOpenERP.search_read(ir_model.getModelName(),
					fields.get(), domain.get());
			if (result.getInt("length") > 0) {
				installed = true;
				JSONObject record = result.getJSONArray("records")
						.getJSONObject(0);
				OEValues values = new OEValues();
				values.put("id", record.getInt("id"));
				values.put("model", record.getString("model"));
				values.put("is_installed", installed);
				int count = ir_model.count("model = ?", new String[] { model });
				if (count > 0)
					ir_model.update(values, "model = ?", new String[] { model });
				else
					ir_model.create(values);
			} else {
				installed = false;
			}
		} catch (Exception e) {
			Log.d(TAG, "OEHelper->isModuleInstalled()");
			Log.e(TAG, e.getMessage() + ". No connection with OpenERP server");
		}
		return installed;
	}

	public List<OEDataRow> search_read_remain() {
		Log.d(TAG, "OEHelper->search_read_remain()");
		return search_read(true);
	}

	private OEDomain getLocalIdsDomain(String operator) {
		OEDomain domain = new OEDomain();
		JSONArray ids = new JSONArray();
		for (OEDataRow row : mDatabase.select()) {
			ids.put(row.getInt("id"));
		}
		domain.add("id", operator, ids);
		return domain;
	}

	public static void getTable(OEDatabase oedb) {

		SQLiteDatabase db = oedb.getReadableDatabase();
		// Cursor cursor;
		// cursor = db.rawQuery("SELECT city FROM res_partner",null);
		// String table = cursor.getString(1);
		//
		// Log.v("tablename..."+table,"new...!!!");

		Log.v("tablename..." + oedb.tableName(), "new...!!!");

	}

	private List<OEDataRow> search_read(boolean getRemain) {
		List<OEDataRow> rows = new ArrayList<OEDataRow>();
		try {
			OEFieldsHelper fields = new OEFieldsHelper(
					mDatabase.getDatabaseServerColumns());
			JSONObject domain = null;
			if (getRemain)
				domain = getLocalIdsDomain("not in").get();
			JSONObject result = mOpenERP.search_read(mDatabase.getModelName(),
					fields.get(), domain, 0, 100, null, null);
			for (int i = 0; i < result.getJSONArray("records").length(); i++) {
				JSONObject record = result.getJSONArray("records")
						.getJSONObject(i);
				OEDataRow row = new OEDataRow();
				row.put("id", record.getInt("id"));
				for (OEColumn col : mDatabase.getDatabaseServerColumns()) {
					row.put(col.getName(), record.get(col.getName()));

				}
				rows.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	public List<OEDataRow> search_read() {
		Log.d(TAG, "OEHelper->search_read()");
		return search_read(false);
	}

	public void delete(int id) {
		Log.d(TAG, "OEHelper->delete()");
		try {
			mOpenERP.unlink(mDatabase.getModelName(), id);
			mDatabase.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object call_kw(String method, OEArguments arguments) {
		return call_kw(method, arguments, new JSONObject());
	}

	public Object call_kw(String method, OEArguments arguments,
			JSONObject context) {
		return call_kw(null, method, arguments, context, null);
	}

	public Object call_kw(String method, OEArguments arguments,
			JSONObject context, JSONObject kwargs) {
		return call_kw(null, method, arguments, context, kwargs);
	}

	public Object call_kw(String model, String method, OEArguments arguments,
			JSONObject context, JSONObject kwargs) {
		Log.d(TAG, "OEHelper->call_kw()");
		JSONObject result = null;
		if (model == null) {
			model = mDatabase.getModelName();
		}
		try {
			if (context != null) {
				arguments.add(mOpenERP.updateContext(context));
			}
			if (kwargs == null)
				result = mOpenERP.call_kw(model, method, arguments.getArray());
			else
				result = mOpenERP.call_kw(model, method, arguments.getArray(),
						kwargs);
			return result.get("result");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
//------------------------------------------	
	
	
	public Integer create(OEValues values) {
		Log.d(TAG, "OEHelper->create()");
		Integer newId = null;
		try {
			JSONObject result = mOpenERP.createNew(mDatabase.getModelName(),
					generateArguments(values));
			newId = result.getInt("result");
			values.put("id", newId);
			mDatabase.create(values);
			return newId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newId;
	}

	public Boolean update(OEValues values, Integer id) {
		Log.d(TAG, "OEHelper->update()");
		Boolean flag = false;
		try {
			flag = mOpenERP.updateValues(mDatabase.getModelName(),
					generateArguments(values), id);

			if (flag)
				mDatabase.update(values, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	private JSONObject generateArguments(OEValues values) {
		Log.d(TAG, "OEHelper->generateArguments()");
		JSONObject arguments = new JSONObject();
		try {
			for (String key : values.keys()) {
				if (values.get(key) instanceof OEM2MIds) {

					Log.d("call update if", "call");
					OEM2MIds m2mIds = (OEM2MIds) values.get(key);
					JSONArray m2mArray = new JSONArray();
					m2mArray.put(6);
					m2mArray.put(false);
					m2mArray.put(m2mIds.getJSONIds());
					arguments.put(key, new JSONArray("[" + m2mArray.toString()
							+ "]"));
				} else {
					Log.d("call update else", "call");
					arguments.put(key, values.get(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arguments;
	}

	public boolean moduleExists(String name) {
		Log.d(TAG, "OEHelper->moduleExists()");
		boolean flag = false;
		try {
			OEDomain domain = new OEDomain();
			domain.add("name", "ilike", name);
			OEFieldsHelper fields = new OEFieldsHelper(new String[] { "state" });
			JSONObject result = mOpenERP.search_read("ir.module.module",
					fields.get(), domain.get());
			JSONArray records = result.getJSONArray("records");
			if (records.length() > 0
					&& records.getJSONObject(0).getString("state")
							.equalsIgnoreCase("installed")) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public OpenERP openERP() {
		return mOpenERP;
	}

	
	
	// =========================================
	

	//add asset data read from server
	

	public void readDataFromServer1() {

		OEFieldsHelper fields = new OEFieldsHelper(new String[] {"id", "name",
				"mark", "model", "serial","code","barcode_no","barcode_label"});
		OEDomain domain = new OEDomain();
		// domain.add("id", "=", 2);
		JSONArray asset;
		try {
			asset = mOpenERP.search_read("asset.asset", fields.get(),
					domain.get(), 0, 400, "id", "ASC").getJSONArray("records");
			
			JSONObject json_data = null;
			String assetname1;
			String mark1;
			String model1;
			String serial1;
			String code1;
		//	String type1;
			String barcode_no1;
			String barcode_label1;
			String id1;
			
			//String email1;
			//String website1;
			
			
			qr_equip_name.clear();
			qr_equip_asset_mark.clear();
			qr_equip_asset_model.clear();
			qr_equip_serial.clear();
			code.clear();
			id.clear();
			
			qr_equip_asset_bar_code.clear();
			qr_equip_asset_barcode_Label.clear();
			
			
			//equipment_type.clear();
		
			
			//res_id.add("");
			
			
			for(int i = 0; i < asset.length(); i++) {

				json_data = asset.getJSONObject(i);
				
				assetname1 = json_data.getString("name");
				mark1 = json_data.getString("mark");
				model1=json_data.getString("model");
				serial1 = json_data.getString("serial");
				code1=json_data.getString("code");
				barcode_no1=json_data.getString("barcode_no");
				id1=json_data.getString("qr_equip_asset_id");
				//type1=json_data.getString("equipment_type");
				
				// image=json_data.getString("image");
				
				//customer_leads.add(name1);
				qr_equip_name.add(assetname1);
				qr_equip_asset_mark.add(mark1);
				qr_equip_asset_model.add(model1);
				qr_equip_serial.add(serial1);
				code.add(code1);
				qr_equip_asset_bar_code.add(barcode_no1);
				id.add(id1);
				
				
				

				Log.d("asset name=" + assetname1, "Asset_Id..."+id1);
				
				//Log.i("Asset LOcation:"+locationid1,"Location id GOT...");
				//Log.i("Asset LOcation2:"+location_id,"Location id GOT...");
			}
		} catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//update data for asset
	
		public Boolean update_data(OEValues values, Integer id)
		{
			
			
			Boolean flag = false;
			try {
				flag = mOpenERP.updateValues("asset.asset",generateArguments(values), id);
				
				if (flag)
					mDatabase.update(values, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}
		
		
	
	
	//delete asset
		
		public void delete_data(int id) 
		{
			Log.d(TAG, "OEHelper->delete()");
			
			try {
				mOpenERP.unlink("asset.asset", id);
				// mDatabase.delete(id);
			} catch (Exception e) {
				MainActivity.checkfordelete1 = true;

				e.printStackTrace();
			}
		}
	
	
		
	//new asset insert
	
		public Integer insert_asset(OEValues values) {
			Integer newId = null;
			Log.i("The Values " + values, " yes Present");
			try {
				JSONObject result = mOpenERP.createNew("asset.asset",generateArguments(values));
				 
				Log.i("new created Asset id " + result, " yes success");
				
				 //mDatabase.create(val);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return newId;
		}
	
		
		
	
	//List of All Equipment name(assets).	
	
	public List<String> qr_equipmentname() {
		qr_equip_name.clear();
		//image_medium.clear();
		

		try {
			OEFieldsHelper fields2 = new OEFieldsHelper(new String[] { "id",
					"name"});

			OEDomain domain3 = new OEDomain();
			JSONArray result3 = mOpenERP.search_read("asset.asset",fields2.get(), domain3.get(), 0, 0, "id", "ASC").getJSONArray("records");

			for (int i = 0; i < result3.length(); i++) 
			{
				obj = result3.getJSONObject(i);
				
				String id = obj.getString("id");
				String name1 = obj.getString("name");
				//String image1=obj.getString("image_medium");
				// String value1 = obj.getString("value");
			
				qr_equip_name.add("" + name1);
				
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr_equip_name;
	}


	

	//List of All Table Orders name(assets)...................................	
	
	public List<String> table_orders() {
		table_order_no.clear();
	
		

		try {
			OEFieldsHelper fields2 = new OEFieldsHelper(new String[] { "id",
					"table_occupied"});

			OEDomain domain3 = new OEDomain();
			
			
			JSONArray result3 = mOpenERP.search_read("hotel.restaurant.tables",fields2.get(), domain3.get(), 0, 0, "id", "ASC").getJSONArray("records");

			for (int i = 0; i < result3.length(); i++) 
			{
				obj = result3.getJSONObject(i);
				
				String id = obj.getString("id");
				String name1 = obj.getString("table_occupied");
				
			
				table_order_no.add("" + name1);
				
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return table_order_no;
	}

	
	
	
	
	
	
	
	
	
	
	
	//Image of Asset
	
	public List<String> qr_equipmentimage()
	{
		
		
		image_of_asset.clear();
		

		try {
			OEFieldsHelper fields2 = new OEFieldsHelper(new String[] { "id","image_medium"});

			OEDomain domain3 = new OEDomain();
			JSONArray result3 = mOpenERP.search_read("asset.asset",fields2.get(), domain3.get(), 0, 141, "id", "ASC").getJSONArray("records");

			for (int i = 0; i < result3.length(); i++) 
			{
				obj = result3.getJSONObject(i);
				
				
				String image1=obj.getString("image_medium");
				
				
				//byte[] decodedString = Base64.decode(image1, Base64.DEFAULT);
				//Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			
				Bitmap decodedByte =Base64Helper.getBitmapImage(mContext, image1);
						
				if (!obj.getString("image_medium").equals("false")) 
	            {
	                image_of_asset.add(decodedByte);
	            }
				//image_of_asset.add(decodedByte);
				
				Log.i("IMAGES ARE:...."+image_of_asset,"THAT ALL IMAGES OK....");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr_equip_name;
	}

	
	

	//Image for Barcode Reader of Asset
	
	public List<String> qr_barcodeimage()
	{
		
		
		 image_of_barcode.clear();
	
		

		try {
			OEFieldsHelper fields2 = new OEFieldsHelper(new String[] { "id","barcode_label"});

			OEDomain domain3 = new OEDomain();
			JSONArray result3 = mOpenERP.search_read("asset.asset",fields2.get(), domain3.get(), 0, 141, "id", "ASC").getJSONArray("records");

			for (int i = 0; i < result3.length(); i++) 
			{
				obj = result3.getJSONObject(i);
				
				
				 String image2 = obj.getString("barcode_label");
			
			
				Bitmap decodedByte =Base64Helper.getBitmapImage(mContext, image2);
				
				
				if (!obj.getString("barcode_label").equals("false")) 
	            {
	                image_of_barcode.add(decodedByte);
	            }
				//image_of_asset.add(decodedByte);
				
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr_equip_name;
	}
	
	
	
/*//Equipment details for displaying all details of assets.	
	
public List<String> qr_equipment_detail() {

		
		qr_equip_name.clear();
		qr_equip_asset_mark.clear();
		qr_equip_asset_model.clear();
		qr_equip_serial.clear();
		code.clear();
		qr_equip_asset_bar_code.clear();
		

			id.clear();	
		
		//id.clear();
		
		//sop_selected_id_from_assets.clear();

		 image_of_barcode.clear();
		// purchase_ok_from_templateproduct.clear();

		try {
			OEFieldsHelper fields2 = new OEFieldsHelper(new String[] {"id","name","mark",
					"model", "serial", "code", "barcode_no" ,"qr_image"});// ,"qr_image"

			OEDomain domain3 = new OEDomain();
			JSONArray result3 = mOpenERP.search_read("asset.asset",
					fields2.get(), domain3.get(), 0, 0, "id", "ASC")
					.getJSONArray("records");

			for (int i = 0; i < result3.length(); i++) 
			{
				obj = result3.getJSONObject(i);
				
				String id1 = obj.getString("id");
				String name1 = obj.getString("name");
				String mark1=obj.getString("mark");
				String serial1 = obj.getString("serial");
				String model1 = obj.getString("model");
				String code1 = obj.getString("code");
				String barcode_no1=obj.getString("barcode_no");
				String qr_code1 = obj.getString("qr_code");
				
				 String image1 = obj.getString("qr_image");
			
				//Log.i("sop_selected_id_from_assets="+ sop_selected_id_from_assets, " ok ");

				qr_equip_name.add(name1);
				qr_equip_serial.add(serial1);
				qr_equip_asset_mark.add(mark1);
				qr_equip_asset_model.add(model1);
				code.add(code1);
				qr_equip_asset_bar_code.add(barcode_no1);
			
				id.add(id1);
				
				 byte[] decodedString = Base64.decode(image1, Base64.DEFAULT);
				 Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
				 
				//Log.i("bitmap=" + qr_code1, "ok..................");
				
				 image_of_barcode.add(decodedByte);

				
				

			}
			// MainActivity.data.add(""+result2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr_equip_name;

	}
	*/
	
	
	
	
//This Function use to display all the details of Assets at Equipment_detail page.	
	
	public void Qr_Equipment_Detail()
	{
		
		qr_equip_name.clear();
		qr_equip_asset_mark.clear();
		qr_equip_asset_model.clear();
		qr_equip_serial.clear();
		code.clear();
		
		qr_equip_asset_bar_code.clear();
		id.clear();
		
		property_stock_asset.clear();
		category_id.clear();
		
		equipment_type_id.clear();
		equipment_origin_id.clear();
		
	
			try {
				OEFieldsHelper fields4 = new OEFieldsHelper(new String[] {"id", "name","mark",
						"model", "serial", "code","barcode_no","property_stock_asset","category_id","equipment_type_id","equipment_origin_id"});//,section_id
			//stage_id
			OEDomain domain4 = new OEDomain();
			JSONArray result4 = mOpenERP.search_read("asset.asset",
					fields4.get(), domain4.get(), 0, 0, "id", "ASC")
					.getJSONArray("records");
			
			for (int i = 0; i < result4.length(); i++) {

				obj = result4.getJSONObject(i);
			
				String id1 = obj.getString("id");
				String name1 = obj.getString("name");
				String mark1=obj.getString("mark");
				String serial1 = obj.getString("serial");
				String model1 = obj.getString("model");
				String code1 = obj.getString("code");
				String barcode_no1=obj.getString("barcode_no");
				String location1=obj.getString("property_stock_asset");
				String category1=obj.getString("category_id");
				
				
				String equipment1=obj.getString("equipment_type_id");
				String originequipment1=obj.getString("equipment_origin_id");
				
				//String state_id1 = obj.getString("state_id");
				//String country_id1 = obj.getString("country_id");
				
				Log.i("location is:"+location1,"OKKKKKKKK");
				
				if (location1.contains("\""))
				{
					int index = location1.indexOf("\"");
					
					location1 = location1.substring(index +1  , location1.length()-2 );
					
				}
				
				if (category1.contains("\"")) 
				{
					int ind1 = category1.indexOf("\"");
					category1 = category1.substring(ind1 + 1,
							category1.length() - 2);
				
				}
				
			
				if (equipment1.contains("\"")) {
					int ind1 = equipment1.indexOf("\"");
					equipment1 = equipment1.substring(ind1 + 1,
							equipment1.length() - 2);

				}
				
				if (originequipment1.contains("\"")) {
					int ind1 = originequipment1.indexOf("\"");
					originequipment1 = originequipment1.substring(ind1 + 1,
							originequipment1.length() - 2);

				}
				
				
				qr_equip_name.add(""+name1);
				qr_equip_serial.add(""+serial1);
				qr_equip_asset_mark.add(""+mark1);
				qr_equip_asset_model.add(""+model1);
				code.add(""+code1);
				qr_equip_asset_bar_code.add(""+barcode_no1);
				category_id.add(""+category1);
				property_stock_asset.add(""+location1);
				id.add(id1);
				
				equipment_type_id.add(""+equipment1);
				equipment_origin_id.add(""+originequipment1);
				
				
			
				
				
				// Log.d("stageid"+stageid, "@@@ ok....");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	

	
	
	
	//Spinner location for asset
	
	public void res_location()
	{
		location_name.clear();
		location_name.add("Location");

		property_stock_asset.clear();
		property_stock_asset.add("");
		
		
		try {
			OEFieldsHelper fields4 = new OEFieldsHelper(new String[] { "id",
					"name", });
			
			OEDomain domain4 = new OEDomain();
			
			domain4.add("usage", "=", "asset");
			//domain4.add("name","=",location_id);
			
		
			JSONArray result4 = mOpenERP.search_read("stock.location",fields4.get(), domain4.get(), 0, 0, "id", "ASC").getJSONArray("records");
			
				
			
			for (int i = 0; i < result4.length(); i++) 
			{
				

				obj = result4.getJSONObject(i);
			
				String id1 = obj.getString("id");
				String name1 = obj.getString("name");
				
				
				
					location_name.add(name1);
					property_stock_asset.add(id1);
					
					Log.d("property_stock_asset="+id1, "ok..");
				
			
			
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//category spinner for asset
		
			public void res_category()
			{
				category_name.clear();
				category_name.add("Category");
				category_id.clear();
				category_id.add("");
			
				try {
					OEFieldsHelper fields4 = new OEFieldsHelper(new String[] { "id",
							"name" });
					
					OEDomain domain4 = new OEDomain();
					
					JSONArray result4 = mOpenERP.search_read("asset.asset.category",fields4.get(), domain4.get(), 0, 0, "id", "ASC").getJSONArray("records");

					for (int i = 0; i < result4.length(); i++) 
					{
						

						obj = result4.getJSONObject(i);
					
						String id1 = obj.getString("id");
						String name1 = obj.getString("name");
						                                                                                                                                                                   
						
						
							category_name.add(name1);
							category_id.add(id1);
							
							Log.d("category_id="+id1, "ok..");
						
					
					
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//Spinner equipment family...
			
			public void res_equipment()
			{
				
				equipment_name.clear();
				equipment_name.add("Equipment Family");
				equipment_type_id.clear();
				equipment_type_id.add("");
			
				
				
				try {
					OEFieldsHelper fields4 = new OEFieldsHelper(new String[] { "id" , "name" });
					
					OEDomain domain4 = new OEDomain();
					
					JSONArray result4 = mOpenERP.search_read("asset.equipment.family" ,fields4.get(), domain4.get(), 0, 0, "id", "ASC").getJSONArray("records");
				
					
					for (int i = 0; i < result4.length(); i++) 
					{
						

						obj = result4.getJSONObject(i);
					
						String id1 = obj.getString("id");
						String name1 = obj.getString("name");
						
						equipment_name.add(name1);
					    equipment_type_id.add(id1);
							
							Log.d("equipment_type_id ="+id1, "ok..");
						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
		// Spinner Origin of equipment ...
			
			public void origin_equipment()
			{
				
				originequipment_name.clear();
				originequipment_name.add("Origin Of Equipment");
				equipment_origin_id.clear();
				equipment_origin_id.add("");
			
				
				
				try {
					OEFieldsHelper fields4 = new OEFieldsHelper(new String[] { "id" , "name" });
					
					OEDomain domain4 = new OEDomain();
					
					JSONArray result4 = mOpenERP.search_read("asset.equipment.origin" ,fields4.get(), domain4.get(), 0, 0, "id", "ASC").getJSONArray("records");
				
					
					for (int i = 0; i < result4.length(); i++) 
					{
						

						obj = result4.getJSONObject(i);
					
						String id1 = obj.getString("id");
						String name1 = obj.getString("name");
						
						originequipment_name.add(name1);
					    equipment_origin_id.add(id1);
							
							Log.d("equipment_origin_id ="+id1, "ok..");
						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
			
			
			

}


