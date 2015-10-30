package com.openerp.addons.idea;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;








import android.R.menu;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mirasense.demos.assetQR_scan_from_scandit;
import com.openerp.R;
import com.openerp.MainActivity;
import com.openerp.orm.OEDataRow.IdName;
import com.openerp.orm.OEDataRow;
//import com.openerp.addons.idea.product.LoginUser;
import com.openerp.orm.OEHelper;
import com.openerp.orm.OESQLiteHelper;
import com.openerp.support.BaseFragment;
import com.openerp.support.OEDialog;
import com.openerp.support.fragment.FragmentListener;
import com.openerp.support.listview.OEListAdapter;
import com.openerp.util.Base64Helper;
import com.openerp.util.drawer.DrawerItem;






public class Table_Orders extends BaseFragment implements OnItemClickListener {

	
	
	 static int checkqtycallornot=0;
	 static int checkforfragment = 0;
	boolean check_back_or_not=false;
	public static int  position1=0;
	
	ListView mListView = null;
	OEListAdapter mListAdapter = null;
	static boolean checkforeditornot=false;
	
	public  List<String> record = new ArrayList<String>();
	public  List<String> header = new ArrayList<String>();
	
	public static  List<String> name = new ArrayList<String>();
	private String[] c;
	
	List<Object> items= new ArrayList<Object>();
	

	String assetname1, mark1,location1,category1,model1,serialno1,code1,equipment1,barcode_no1,barcode_label1;
	public  static List<String> ids = new ArrayList<String>();
	
	
	public static Bitmap image_of_produc1t;
	Button btn,create,btn1;
	OEHelper oehelper;
	TextView tvloading;
	ProgressBar bar;
	ProgressDialog predailog;
	
	
	public Table_Orders() {
        // Empty constructor required for fragment subclasses
    }
	
   @Override
	public Object databaseHelper(Context context) {
		// TODO Auto-generated method stub
	  // return new IdeaDBHelper(context);
		return null;
	}

	@Override
	public List<DrawerItem> drawerMenus(Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		setHasOptionsMenu(true);
		
		
		check_back_or_not=false;
		
	View rootView = inflater.inflate(R.layout.tableorderlist, container,false);
	
	getActivity().setTitle("Table Orders");
	
	MainActivity.global=2;
	
	 
	
		mListView = (ListView) rootView.findViewById(R.id.listforqr_equip);
		oehelper=new OEHelper(getActivity());	
		
		//List<String> k= oehelper.qr_equipmentname();
		List<String> k= oehelper.table_orders();
				
		
		
		
		// items= new ArrayList<Object>(getItems());
		 
		 			
				  //mListAdapter = new OEListAdapter(getActivity(),R.layout.qr_equip_item, items)
		 
					mListView.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.tableorder_item, oehelper.table_order_no) 
					{			
				
			
						public View getView(int position, View convertView, ViewGroup parent) 
						{
							View mView = convertView;
		
							if (mView == null)
								mView = getActivity().getLayoutInflater().inflate(R.layout.tableorder_item, parent, false);
			
				//IMAGE...
			/*
					OEDataRow row = (OEDataRow) items.get(position);
			
					 
					 ImageView imgPic = (ImageView) mView.findViewById(R.id.imgPic);
					 Bitmap row1 =OEHelper.image_of_asset.get(QR_Equipment.position1);
			 		 
					 imgPic.setImageBitmap(row1);
					
					*/
					
			TextView txv = (TextView) mView.findViewById(R.id.qr_name);
			Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Georgia.ttf");
			txv.setTypeface(font);
			txv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
			
			txv.setTextColor(Color.rgb(84,84,84));
			
			if(OEHelper.table_order_no.size()!=0 && OEHelper.table_order_no.size()>position)
			{
			txv.setText(""+OEHelper.table_order_no.get(position));
			}
			//txv.setTextAppearance(getActivity(), android.R.attr.textAppearance);
			
			Log.i("TABLE ORDER NO IS....:"+OEHelper.table_order_no,"THAT IS OK...NAMe..");
			return mView;
		}
				

	});
	
						
			
	mListView.setOnItemClickListener(this);
	// mListView.setAdapter(mListAdapter);
	return rootView;
	
	
	}
	
	
	
	
	
	// menu for add assets....
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.add_asset, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId())
		{

		case R.id.add_asset:
			

			Log.i("The Button Clicked..: " +item , "OK.........");
			
			//Toast.makeText(getActivity(), "You clicked..", Toast.LENGTH_SHORT).show();
			
			
			Bundle args = new Bundle();
			menu_add_asset menuasset=new menu_add_asset();
			
			
			args.putString("name",assetname1);
			args.putString("mark", mark1);
			args.putString("model", model1);
			args.putString("serialno", serialno1);
			args.putString("code", code1);
		
			args.putString("barcode_no",barcode_no1);
			
			
			args.putString("property_stock_asset",location1);
			args.putString("category_id",category1);
			args.putString("equipment_type", equipment1);
			
		
			
			Log.i("The VAlue Is ADD ASSET....,"+assetname1+""+mark1+""+model1+""+location1,"OK.....Valuess");
			
			
	
			//menuedit.setArguments(args);
			menuasset.setArguments(args);
			
			
			FragmentListener mfrag = (FragmentListener) getActivity();
			android.support.v4.app.FragmentManager frm = getActivity().getSupportFragmentManager();
		 
		   
			mfrag.startMainFragment(menuasset, true);
			
			return true; 
		
		}
		
		return true;
	}

	
	//Asset item on click appear the QR_equip_details.. 
	    
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	
	{
		
		
		
		OEHelper oehelper1=new OEHelper(getActivity());	
		oehelper1.Qr_Equipment_Detail();
		
		checkforeditornot=false;
		position1=0;
		position1=position;
		
		
		
		OEHelper.selected_Assets_id="";
		if(OEHelper.id.size()>position1)
		{
			OEHelper.selected_Assets_id=OEHelper.id.get(position1);
		}
		
		
		QR_equip_detail qr_detail = new QR_equip_detail();
		
		FragmentListener frag1 = (FragmentListener) getActivity();
		android.support.v4.app.FragmentManager fm1 = getActivity().getSupportFragmentManager();

		frag1.startDetailFragment(qr_detail);
		
	}
	
	@Override	
	public void onPause() 
	{
		
		if(check_back_or_not==false)
		{
//			Dash_Board db=new Dash_Board();
//			FragmentListener frag = (FragmentListener) getActivity();
//			frag.startDetailFragment(db);
			
		}
		
	super.onPause();
	}
	
	
	
}
