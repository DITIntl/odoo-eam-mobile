package com.openerp.addons.idea;

import java.util.ArrayList;
import java.util.List;

import openerp.OpenERP;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Typeface;
import android.media.audiofx.AudioEffect.OnControlStatusChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mirasense.demos.assetQR_scan_from_scandit;
import com.mirasense.demos.scan_using_zbar_in_fragment;
import com.openerp.MainActivity;
import com.openerp.R;
import com.openerp.base.login.Login;
import com.openerp.orm.OEHelper;
import com.openerp.orm.OESQLiteHelper;
import com.openerp.support.BaseFragment;
import com.openerp.support.fragment.FragmentListener;
import com.openerp.util.drawer.DrawerItem;

public class Dash_Board extends BaseFragment {

	MainActivity mainobject;
	// databaseHelper dbhelper=new databaseHelper(getActivity(), null, null, 1);
	OESQLiteHelper oesqlhelper;
	Context context = getActivity();
	OEHelper oe;
	GridView grid;
	public static boolean checkfirstcall=false;
	static boolean checkloading=false;
	
	
	int myProgress = 0;
	int progressStatus = 0;
	
	String[] web={"Table Orders","Orders"};
	int[] imageId ={R.drawable.table_orders, R.drawable.order};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.gridviewfordashboard,
				container, false);

//		if (db().isEmptyTable()) {
//			IdeaDemoRecords rec = new IdeaDemoRecords(getActivity());
//			rec.createDemoRecords();
//		}
		
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Georgia.ttf");
	//	web[0].getTsetTypeface(font,Typeface.BOLD);
		
		MainActivity.check_dashboard_call_or_other=1;

		final Handler myHandler = new Handler();
		Runnable runnable;
		Thread tread;

		runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (progressStatus == 0) {
					progressStatus = performTask();
					
				}
				/* Hides the Progress bar */
				myHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						progressStatus = 0;

					}
				});

			}

			private int performTask() {
				oe = new OEHelper(getActivity());
				return ++myProgress;
			}
		};
		myProgress = 0;

		tread = new Thread(runnable);
		// new Thread(runnable).start();
		tread.start();
		// Toast.makeText(getActivity(), "dash board", 7).show();
		// ========================
		MainActivity.global = 1;
		
		getActivity().setTitle(R.string.label_dashboard);

		CustomGrid adapter = new CustomGrid(getActivity(), web, imageId);
		grid = (GridView) rootView.findViewById(R.id.grid);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id){

		
	//This will call gridview item and display the asset list...
		
				if (web[+position] == "Table Orders")
				{
					//Toast.makeText(getActivity(), "Check conn...."+OEHelper.check_connection_exit, Toast.LENGTH_LONG).show();
					
					
					if (OEHelper.check_connection_exit.equals("1")) 
					{
						view.setSelected(true);
						MainActivity.check_dashboard_call_or_other=2;
			
						//QR_Equipment qr_equip=new QR_Equipment();
						
						Table_Orders tb=new Table_Orders();
						FragmentListener frag = (FragmentListener) getActivity();
						frag.startDetailFragment(tb);
						
					} 
					else 
					{
						Toast.makeText(getActivity(), "Server Connection Failed...",
								7).show();
					}

				} 
				
			 
	// It will call from the grid			 
				 else if (web[+position] == "BarCode Scanner") 
				{
					if (OEHelper.check_connection_exit.equals("1")) {
						QR_Equipment.checkforfragment = 0;
						checkfirstcall=false;
						checkloading=false;
						view.setSelected(true);
					//	testing camera = new testing();
						MainActivity.check_dashboard_call_or_other=2;
						
						OEHelper oehelper = new OEHelper(getActivity());
						oehelper.Qr_Equipment_Detail();
						
						//cameraQRScanMain detail = new cameraQRScanMain();
						
						assetQR_scan_from_scandit detail =new assetQR_scan_from_scandit();
					
						FragmentListener frag = (FragmentListener) getActivity();
						frag.startDetailFragment(detail);
					} else {
						Toast.makeText(getActivity(), "Server Connection Fail",
								7).show();
					}
				
						}
			}
		});      

		return rootView;
	}   
	public Object databaseHelper(Context context) {

		return new IdeaDBHelper(context);
	}

	public List<DrawerItem> drawerMenus(Context context) {

		List<DrawerItem> menu = new ArrayList<DrawerItem>();
		menu.add(new DrawerItem("idea_home", "", true));
		Dash_Board Dash_Board1 = new Dash_Board();
		Bundle args = new Bundle();
		args.putString("key", "Dash Board");
		Dash_Board1.setArguments(args);
		menu.add(new DrawerItem("idea_home", "Dash Board", 0, 0, Dash_Board1));

		return menu;

	}
	
}
