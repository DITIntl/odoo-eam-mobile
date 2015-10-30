package com.openerp.addons.idea;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import openerp.OEDomain;
import openerp.OpenERP;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mirasense.demos.assetQR_scan_from_scandit;
import com.openerp.App;
import com.openerp.MainActivity;
import com.openerp.R;

import com.openerp.orm.OEDataRow;
import com.openerp.orm.OEFieldsHelper;
import com.openerp.orm.OEHelper;
import com.openerp.orm.OEValues;
import com.openerp.support.BaseFragment;
import com.openerp.support.fragment.FragmentListener;
import com.openerp.util.BitmapUtils;
import com.openerp.util.drawer.DrawerItem;

public class edit_asset extends BaseFragment 
{
	OpenERP mOpenERP = null;
	App mApp = null;
	OEHelper oehelper=null;
	Bundle args;
	
	Button b;
	private static final int PICK_IMAGE = 1;
	
	private static final int REQUEST_IMAGE = 100;   
   // private static final String TAG = "MainActivity";

    File destination;

	
	
	private OEDataRow row= null;
	 private String newImage = null;
	public static String id=null;
	static int checkqtycallornot=0;
	
	public static int  positioncurrentequipmen=0;
	
	public static int posi=QR_Equipment.position1;
	
	 private ImageView userImage,image_of_product2;
	 
	 String ba1;
	 private Bitmap bitmap;
	 private Button selectImageButton;
	 ListView mListView = null;
	 private ProgressDialog dialog;
	 
	 boolean isImageFitToScreen;
	
	public static String id_asset_selected = null;
	
	Spinner splocation,spcategory,spequipfamily,sporiginequipment;
	String location,category,equipment,originequipment;
	
	
		String sploc="Assets";
		//String sploc=OEHelper.property_stock_asset.get(QR_Equipment.position1);
		String spcat =OEHelper.category_id.get(QR_Equipment.position1);
		String spequip=OEHelper.equipment_type_id.get(QR_Equipment.position1);
		String sporigin= OEHelper.equipment_origin_id.get(QR_Equipment.position1);
	
	
	int locationid=0,categoryid=0,equipmentid=0,originequipmentid=0;
	
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.edit_asset, container,false);
		
		getActivity().setTitle("Asset Details");
		
		
		MainActivity.global=2;
		args = getArguments();
		mApp = (App) getActivity().getApplicationContext();
		mOpenERP= mApp.getOEInstance();
		oehelper=new OEHelper(getActivity());
	
		
		
		String name =   dateToString(new Date(),"yyyy-MM-dd-hh-mm-ss");
        destination = new File(Environment.getExternalStorageDirectory(), name + ".jpg");
		
		
		 // find the views
	    userImage = (ImageView)rootView.findViewById(R.id.uploadImage);
	    image_of_product2=(ImageView)rootView.findViewById(R.id.barcodeImage);

	    // on click select an image
	    selectImageButton = (Button)rootView.findViewById(R.id.selectImageButton);
	    final Button b=(Button)rootView.findViewById(R.id.menu_edit_button);
	 
		 splocation=(Spinner)rootView.findViewById(R.id.spinner_location);
		 spcategory=(Spinner)rootView.findViewById(R.id.spinner_category);
		 spequipfamily=(Spinner)rootView.findViewById(R.id.spinner_equipment_family);
		 sporiginequipment=(Spinner)rootView.findViewById(R.id.spinner_origin_equipment);
		 
		
		 
		final EditText assetname=(EditText)rootView.findViewById(R.id.editText_assetname);
		final EditText assetmark=(EditText)rootView.findViewById(R.id.editText_mark); 
		final EditText assetmodel=(EditText)rootView.findViewById(R.id.editText_model);
		final EditText assetserialno=(EditText)rootView.findViewById(R.id.editText_serialno);
		final EditText assetbarcodeno=(EditText)rootView.findViewById(R.id.editText_barcodeno);
		
		
		
	/**This Function call for the get the Details of QR_Equip_detail.
	 * Images call from the qr_equipmentimage() .*/
		
		oehelper.Qr_Equipment_Detail();
		oehelper.qr_equipmentimage();
		oehelper.qr_barcodeimage();
	
		
		if (OEHelper.id.size() != 0)
		{
			if (OEHelper.id.size() > QR_Equipment.position1)
			{
				id_asset_selected = OEHelper.id.get(QR_Equipment.position1);
			}
		}


		
		if (OEHelper.image_of_barcode.size() != 0)
		{
			if (OEHelper.image_of_barcode.size() > QR_Equipment.position1) 
			{
				image_of_product2.setImageBitmap(OEHelper.image_of_barcode.get(QR_Equipment.position1));
			}
		
			
		}
		
		
		if (OEHelper.image_of_asset.size() != 0)
		{
			if (OEHelper.image_of_asset.size() > QR_Equipment.position1) 
			{
				userImage.setImageBitmap(OEHelper.image_of_asset.get(QR_Equipment.position1));
				userImage.setVisibility(View.VISIBLE);
			}
		
		
		}
		
		
		
		   assetname.setText(""+OEHelper.qr_equip_name.get(QR_Equipment.position1));
	       assetmark.setText(""+OEHelper.qr_equip_asset_mark.get(QR_Equipment.position1));
	       assetmodel.setText(""+OEHelper.qr_equip_asset_model.get(QR_Equipment.position1));
	       assetserialno.setText(""+OEHelper.qr_equip_serial.get(QR_Equipment.position1));
	       assetbarcodeno.setText(""+OEHelper.qr_equip_asset_bar_code.get(QR_Equipment.position1));
	    
		
	       
	       	
	       
	    			OEHelper oehelper = new OEHelper(getActivity());
	    			
	    			oehelper.res_location();	
	    			oehelper.res_category();
	    			oehelper.res_equipment();
	    			oehelper.origin_equipment();
	    			
	    			oehelper.readDataFromServer1();
	    			
	    			  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,oehelper.location_name);
	    			  dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    			  splocation.setAdapter(dataAdapter1);
	    			
	    			  int spinloc = dataAdapter1.getPosition(sploc);
		    		  splocation.setSelection(spinloc);
	    			  
	    			   
	    			  ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,oehelper.category_name);
	    			  dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    			  spcategory.setAdapter(dataAdapter2);
	    			 
	    			
	    			  int spinnerPosition = dataAdapter2.getPosition(spcat);
		    		  spcategory.setSelection(spinnerPosition);
	    			 
	    			  
	    			  ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,oehelper.equipment_name);
	    			  dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    			  spequipfamily.setAdapter(dataAdapter3);

	    			  int spineq = dataAdapter3.getPosition(spequip);
		    		  spequipfamily.setSelection(spineq);
	    			  
	    			  
	    			  ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,oehelper.originequipment_name);
	    			  dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    			  sporiginequipment.setAdapter(dataAdapter4);
	    			  
	    			  int spinori = dataAdapter4.getPosition(sporigin);
		    		  sporiginequipment.setSelection(spinori);
	    			  
	    			  
	    			  
		    			// location spinner onitem selected change
		  				  
	    			  splocation.setOnItemSelectedListener(new OnItemSelectedListener() {
	    				   
	    					@Override
	    					public void onItemSelected(AdapterView<?> arg0, View arg1,
	    							int arg2, long arg3) {
	    						
	    						if(arg2!=0)
    								
    							{
	    					
	    							location=OEHelper.location_name.get(arg2);
	    							locationid=Integer.parseInt(OEHelper.property_stock_asset.get(arg2));
	    						
    							}
	    						
	    					}

	    					@Override
	    					public void onNothingSelected(AdapterView<?> arg0) 
	    					{
	    						
	    					}
	    				});
	    		
	    				
	    			  
	    			  
	    				// category spinner onitemselected change
	    					
	    				  spcategory.setOnItemSelectedListener(new OnItemSelectedListener() {
	    					   
	    						@Override
	    						public void onItemSelected(AdapterView<?> arg0, View arg1,
	    								int arg2, long arg3) {
	    							
	    				       
	    							if(arg2!=0)
	    								
	    							{
	    								category=OEHelper.category_name.get(arg2);
	    								categoryid=Integer.parseInt(OEHelper.category_id.get(arg2));
	    							
	    							} 
	    							
	    						}

	    						@Override
	    						public void onNothingSelected(AdapterView<?> arg0)
	    						{
	    							
	    						}
	    					});
	    				  
	    				  
	    				  
	    					// equipment spinner onitemselected change
	    				  
	    				  spequipfamily.setOnItemSelectedListener(new OnItemSelectedListener() {
	    					  
	    			            @Override
	    			            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {

	    							if(arg2!=0)
	    								
	    							{
	    								 equipment=OEHelper.equipment_name.get(arg2);
	    								 equipmentid=Integer.parseInt(OEHelper.equipment_type_id.get(arg2));
	    								
	    							}
	    			            }
	    			 
	    			            @Override
	    			            public void onNothingSelected(AdapterView<?> arg0) {
	    			                // TODO Auto-generated method stub
	    			 
	    			            }
	    			        });
	    			  			
	    		
	    				// origin equipment spinner onitemselected change
	    					
	    				  sporiginequipment.setOnItemSelectedListener(new OnItemSelectedListener() {
	    					   
	    						@Override
	    						public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
	    							
	    							 if(arg2!=0)
	    									
	    								{
	    							
	    								 originequipment=OEHelper.originequipment_name.get(arg2);
	    								originequipmentid=Integer.parseInt(OEHelper.equipment_origin_id.get(arg2));
	    								
	    								}
	    						}

	    						
	    						@Override
	    						public void onNothingSelected(AdapterView<?> arg0) {
	    							
	    						}
	    					});
	    				  
	    	
	    				  
	    				  
	    				//Select image from Gallery  for update....				  
	    				  
	    				  selectImageButton.setOnClickListener(new OnClickListener()
	    				  {

	    				@Override
							public void onClick(View arg0) 
	    				{
								
	    					final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
	    					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    					builder.setTitle("Add Photo!");
	    					builder.setItems(items, new DialogInterface.OnClickListener() 
	    					{
	    						
	    					@Override
	    					public void onClick(DialogInterface dialog, int item) 
	    					{
	    						
	    					if (items[item].equals("Take Photo"))
	    					{
	    						
	    						Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	    		                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
	    		                startActivityForResult(intent, REQUEST_IMAGE); 
	    					
	    					    
	    					} 
	    					
	    					
	    					
	    					else if (items[item].equals("Choose from Library")) 
	    					{
	    						 	
						            
						            Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		                            startActivityForResult(intent, PICK_IMAGE); 
						            
	    						
	    					} 
	    					
	    					else if (items[item].equals("Cancel")) 
	    					{
	    					dialog.dismiss();
	    					}
	    					}

							
	    					});
	    					builder.show();
	    					
	    				
							}
	    					  
	    				  });				  
	    				  
	
	    				
	    				  
	    				  
	    				  
	    	
	/*  Update Assets Details.... */
	    				  
			b.setOnClickListener(new OnClickListener()


			{

				public void onClick(View arg0)
				
				{
					
		        
					OEHelper oehelper=new OEHelper(getActivity());
						
					String assetnm=assetname.getText().toString();
					
					if(assetnm.trim().equals(""))
					{
						Toast.makeText(getActivity(), "Plz Enter Name", 54 ).show();
						return;
					}
					
						OEValues val = new OEValues();
					
						
			          
						val.put("name", assetname.getText().toString());
						val.put("mark", assetmark.getText().toString());
						val.put("model", assetmodel.getText().toString());
						val.put("serial", assetserialno.getText().toString());
						val.put("barcode_no", assetbarcodeno.getText().toString());
					
						val.put("property_stock_asset",locationid);
						val.put("category_id", categoryid);
						val.put("equipment_type_id", equipmentid);
						val.put("equipment_origin_id", originequipmentid);
					
						if(newImage!=null)
						{
							//Image Location Url
							Log.e("path","-----------"+newImage);
							
						
							Bitmap bm=BitmapFactory.decodeFile(newImage);
							ByteArrayOutputStream baos = new ByteArrayOutputStream();  
							bm.compress(Bitmap.CompressFormat.PNG, 100, baos); 
							
							//this will convert image to byte[] 
							byte[] byteArrayImage = baos.toByteArray(); 
							
							// this will convert byte[] to string
							 ba1 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
						
						}
						
						val.put("image_medium", ba1);
						
						
					//Call the update_data() for update all the data of the assets through the selected id of asset..
						
						oehelper.update_data(val,Integer.parseInt(id_asset_selected));
						//oehelper.readDataFromServer1();
					
						Toast.makeText(getActivity(),"Updated Completed...",40).show();
						
						QR_Equipment qr_equip=new QR_Equipment();
						FragmentListener frag = (FragmentListener) getActivity();
						
						frag.startDetailFragment(qr_equip);
						    
				}
			});
		
			
			
	//This Button use to cancel the updation record..
			
			final Button cancle=(Button)rootView.findViewById(R.id.Cancleofedit);
			cancle.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					
					QR_Equipment.checkforeditornot=true;
					
					QR_Equipment detail=new QR_Equipment();
					FragmentListener frag = (FragmentListener) getActivity();
					android.support.v4.app.FragmentManager fm1 = getActivity().getSupportFragmentManager();
					  //  Toast.makeText(getActivity(), "back called",40).show();
					    fm1.popBackStack("QR_Equipment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
					    fm1.popBackStack();
					    
					    android.support.v4.app.FragmentManager fm2 = getActivity().getSupportFragmentManager();
					    fm2.popBackStack("QR_Equipment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
					    fm2.popBackStack();
					 
					frag.startDetailFragment(detail);
				}
			});
			
			return rootView;
		}
	


	private String dateToString(Date date, String format)
	{
		 SimpleDateFormat df = new SimpleDateFormat(format);
	        return df.format(date);
	}



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


/**This method call when we select any image from the gallery it return the resultcode for displaying an image...*/
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	 {
		
	
		if (resultCode == Activity.RESULT_OK) 
		{
		
			
  /**When u select the picture captured by camera 
 * at this time it return the captured image path and show on imageview.*/
			
			 if (requestCode == REQUEST_IMAGE)
			 {

				 try {
		               
					 	FileInputStream in = new FileInputStream(destination);
		                BitmapFactory.Options options = new BitmapFactory.Options();
		                options.inSampleSize = 10;
		                newImage = destination.getAbsolutePath();
		                
		               // in.close();
		               
		                Log.w("path of image from Camera......******************.........", newImage+""); 
		                
		                Toast.makeText(getActivity(), "IMAGE PATH FROM CAMERA....:"+newImage, Toast.LENGTH_LONG ).show();
		                
		                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
		                userImage.setImageBitmap(bmp);
		                
		            }
				 
				 catch (FileNotFoundException e)
				 {
		                e.printStackTrace();
		         } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
		        }
			 
		
			 
  /**This Selected Image From GAllery and display at Imageview
    *  get the Imagepath from the External storage..*/	
			 
		else if (requestCode == PICK_IMAGE) 
		{
			
			
			Uri selectedImage = data.getData();

            String[] filePath = { MediaStore.Images.Media.DATA };

            Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            newImage = c.getString(columnIndex);

            c.close();

            Bitmap thumbnail = (BitmapFactory.decodeFile(newImage));

            Log.w("path of image from gallery......******************.........", newImage+"");

            userImage.setImageBitmap(thumbnail);

            userImage.setVisibility(View.VISIBLE);
			
			
			
			
		}
		
			
		}		
		
	 }	
	
	
	
	
	
	
}



