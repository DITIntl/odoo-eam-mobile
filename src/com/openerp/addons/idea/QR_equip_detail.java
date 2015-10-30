package com.openerp.addons.idea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



















import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.barcode.CameraTestActivity;
import com.barcode.ScanditSDKDemoSimpleforQR_Equip_detail;
import com.openerp.MainActivity;
import com.openerp.R;
import com.openerp.orm.OEDataRow;
import com.openerp.orm.OEHelper;
import com.openerp.support.BaseFragment;
import com.openerp.support.fragment.FragmentListener;
import com.openerp.util.Base64Helper;
import com.openerp.util.drawer.DrawerItem;

public class QR_equip_detail extends BaseFragment  {

	
	
	TextView spinner_location,spinner_category,spinner_equip,spinner_originequipment; 
	ImageView uploadImage,image_of_product2;
	TextView edit_name,edit_mark,edit_model,edit_serialno,edit_barcodeno,edit_code;
	int locationid=0,categoryid=0,equipmentid=0,origionequipmentid=0;
	OEHelper oehelper;
	
	public static String id_asset_selected = null;
	
	public static ArrayList<String> arr=new ArrayList<String>();
	
	
	//int location1;
	String assetname1,mark1,location1,category1,model1,serialno1,code1,equipment1,originequipment1,barcode_no1,barcode_label1;
	
	@Override
	public Object databaseHelper(Context context) {
		// TODO Auto-generated method stub
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
	
		View rootView = inflater.inflate(R.layout.qr_equip_detail, container,false);
		
		
		getActivity().setTitle(R.string.label_equipment_detail);
		
		
		MainActivity.global=2;
		
		
		uploadImage=(ImageView)rootView.findViewById(R.id.uploadImage);
		
		edit_name=(TextView) rootView.findViewById(R.id.edit_name);
		edit_mark=(TextView) rootView.findViewById(R.id.edit_mark);
		edit_model=(TextView) rootView.findViewById(R.id.edit_model);
		edit_serialno=(TextView) rootView.findViewById(R.id.edit_serialno);
		edit_barcodeno=(TextView) rootView.findViewById(R.id.edit_barcodeno);
		edit_code=(TextView) rootView.findViewById(R.id.edit_code);
	
		
		spinner_location=(TextView) rootView.findViewById(R.id.spinner_location);
		spinner_category=(TextView) rootView.findViewById(R.id.spinner_category);
		spinner_equip=(TextView)rootView.findViewById(R.id.spinner_equip);
		spinner_originequipment=(TextView)rootView.findViewById(R.id.spinner_originequipment);
		
		image_of_product2=(ImageView)rootView.findViewById(R.id.imageView1);
		
		
		
	//Show all the details of Asset in this Activity.	
		
		OEHelper oehelper = new OEHelper(getActivity());
		oehelper.Qr_Equipment_Detail();
		
		oehelper.qr_equipmentimage();
		oehelper.qr_barcodeimage();
	
		// image_of_product2.setOnClickListener(this);
		
		if (OEHelper.id.size() != 0)
		{
			if (OEHelper.id.size() > QR_Equipment.position1)
			{
				id_asset_selected = OEHelper.id.get(QR_Equipment.position1);
			}
		}
		
		
		
		Log.i("THIS IS ID...:"+id_asset_selected,"OK....ID");
		

	//It get the Image of barcode image...
		
		if (OEHelper.image_of_barcode.size() != 0)
		{
			if (OEHelper.image_of_barcode.size() > QR_Equipment.position1) 
			{
				image_of_product2.setImageBitmap(OEHelper.image_of_barcode.get(QR_Equipment.position1));
			}
		
		
		}
		
	//It get the image of asset
		
		if (OEHelper.image_of_asset.size() != 0)
		{
			if (OEHelper.image_of_asset.size() > QR_Equipment.position1) 
			{
				uploadImage.setImageBitmap(OEHelper.image_of_asset.get(QR_Equipment.position1));
				uploadImage.setVisibility(View.VISIBLE);
			}
		
		
		}
		
		
		
		
 /**Get all the record of asset and set to the field in detail view of asset....*
  */				
  
		
			edit_name.setText(""+OEHelper.qr_equip_name.get(QR_Equipment.position1));
			edit_mark.setText(""+OEHelper.qr_equip_asset_mark.get(QR_Equipment.position1));
			edit_model.setText(""+OEHelper.qr_equip_asset_model.get(QR_Equipment.position1));
			edit_serialno.setText(""+OEHelper.qr_equip_serial.get(QR_Equipment.position1));
			edit_code.setText(""+OEHelper.code.get(QR_Equipment.position1));
			edit_barcodeno.setText(""+OEHelper.qr_equip_asset_bar_code.get(QR_Equipment.position1));
			
			
			
			spinner_location.setText(""+OEHelper.property_stock_asset.get(QR_Equipment.position1));
			spinner_category.setText(""+OEHelper.category_id.get(QR_Equipment.position1));
			spinner_equip.setText(""+OEHelper.equipment_type_id.get(QR_Equipment.position1));
			spinner_originequipment.setText(""+OEHelper.equipment_origin_id.get(QR_Equipment.position1));
			
		    getActivity().setTitle(R.string.label_CRM_view);
	        //MainActivity.global = 2;
		
		
		return rootView;
	}
		
		
		
		
		
	
	//menu for Edit details of Qr_equipment detail 
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_edit, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId())
		{

		case R.id.edit:
		

			Log.i("The Button Clicked..: " +item , "OK.........");
			
			//Toast.makeText(getActivity(), "You clicked..", Toast.LENGTH_SHORT).show();
			
			
			edit_asset editasset=new edit_asset();
			
			FragmentListener frag1 = (FragmentListener) getActivity();
			android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();

			frag1.startDetailFragment(editasset);
			
			
			
			//mFragment.startMainFragment(editasset);
			
			return true; 
		
	
	//call Delete for remove the asset record...		
		case R.id.delete:
			
			OEHelper.qr_equip_name.clear();
			
			 final OEHelper oehelper=new OEHelper(getActivity());
			
			if( MainActivity.checkfordelete1==false)
			{
				
			/**generate dialog box for asking are u want to delete this record yes or no
			 * call the delete|_data() for deleting selected id record..
			 */
				
              	AlertDialog.Builder alert1 = new AlertDialog.Builder(getActivity());
	            alert1.setTitle("Alert!!");
	            alert1.setMessage("Are you sure to delete record?");
	            
	            alert1.setCancelable(true);
	          
	            
	            alert1.setPositiveButton("YES", new  DialogInterface.OnClickListener()  {

	                public void onClick(DialogInterface dialog, int id)
	                {
	                         
	                		oehelper.delete_data(Integer.parseInt(id_asset_selected));
	              
	                }
	            });
	            
	            alert1.setNegativeButton("NO", new  DialogInterface.OnClickListener()  
	            {

	                public void onClick(DialogInterface dialog, int which) {

	                    dialog.dismiss();
	                }
	            });
	            
	            
	           AlertDialog alert2=alert1.create();
	            alert2.show();  
	           
			}
			
				oehelper.qr_equip_name.clear();
			
		           if(MainActivity.checkfordelete1==true)
		          {
		        	  Toast.makeText(getActivity(), "Can NOt Delete This Record...", Toast.LENGTH_LONG).show();
							
		          }
			
			 	//oehelper.delete_data(Integer.parseInt(id_asset_selected));
	       		oehelper.readDataFromServer1();
		
	       		
				//open the Qr_equipment activity after deleting an record...
	       		
					QR_Equipment qr_equip=new QR_Equipment();
					FragmentListener frag = (FragmentListener) getActivity();
					
					frag.startDetailFragment(qr_equip);
				
					
				return true; 
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

		
		
		
	

	protected void finish() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
			

}
