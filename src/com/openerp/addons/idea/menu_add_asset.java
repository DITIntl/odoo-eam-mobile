package com.openerp.addons.idea;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




























import openerp.OpenERP;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;



import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;





























import com.openerp.App;
import com.openerp.MainActivity;
import com.openerp.R;

import com.openerp.orm.OEDataRow;
import com.openerp.orm.OEHelper;
import com.openerp.orm.OEValues;
import com.openerp.support.BaseFragment;
import com.openerp.support.fragment.FragmentListener;
import com.openerp.util.BitmapUtils;
import com.openerp.util.drawer.DrawerItem;




public class menu_add_asset extends BaseFragment   {
	
	
	OpenERP mOpenERP = null;
	App mApp = null;
	OEHelper oehelper=null;
	OEValues values;
	Bundle args;
	Context mContext = null;
	
	 private static final int PICK_IMAGE = 1;
	 boolean isImageFitToScreen;
	 
	 public final String APP_TAG = "MyCustomApp";
	 public String photoFileName = "photo.jpg";
	 private static final int REQUEST_IMAGE = 100;  
	 
	 File destination;
	
	 String ba1;	
	 String encodedImage;
	 
    private String selectedImagePath;
    
    private ProgressDialog dialog = null;
    private Bitmap bitmap;
	
   
    
    
   
    static int checkqtycallornot=0;
	 
	 private String newImage = null;
	 private OEDataRow record = null;
	
    private Button  btnselectpic;
    private ImageView userImage,barcode;
    
    OutputStream outFile = null;

	 ListView mListView = null;
 
	 
	 String sploc="Assets";
	
	Spinner splocation,spcategory,spequipfamily,sporiginequipment;
	String location,category,equipment,originequipment;
	int locationid=0,categoryid=0,equipmentid=0,originequipmentid=0;
	
    Button b;
  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
    {
		View rootView = inflater.inflate(R.layout.add_asset, container,false);
		
	
		MainActivity.global=2;
		args = getArguments();
		mApp = (App) getActivity().getApplicationContext();
		mOpenERP= mApp.getOEInstance();
		oehelper=new OEHelper(getActivity());

	
	  
		String name =   dateToString(new Date(),"yyyy-MM-dd-hh-mm-ss");
        destination = new File(Environment.getExternalStorageDirectory(), name + ".jpg");
		
        
		
		 // find the views
			userImage = (ImageView)rootView.findViewById(android.R.id.icon);
	        //barcode = (ImageView)rootView.findViewById(R.id.barcodeImage);  
	        
	        // on click select an image
	       
	        btnselectpic = (Button)rootView.findViewById(R.id.selectImageButton);
	        b=(Button)rootView.findViewById(R.id.menu_add_button);
	       
        
		 splocation=(Spinner)rootView.findViewById(R.id.spinner_location);
		 spcategory=(Spinner)rootView.findViewById(R.id.spinner_category);
		 spequipfamily=(Spinner)rootView.findViewById(R.id.spinner_equipment_family);
		 sporiginequipment=(Spinner)rootView.findViewById(R.id.spinner_origin_equipment);
		 
		 
		final EditText assetname=(EditText)rootView.findViewById(R.id.editText_assetname);
		final EditText mark=(EditText)rootView.findViewById(R.id.editText_mark); 
		final EditText model=(EditText)rootView.findViewById(R.id.editText_model);
		final EditText serialno=(EditText)rootView.findViewById(R.id.editText_serialno);
		final EditText barcodeno=(EditText)rootView.findViewById(R.id.editText_barcodeno);
		
       
		//mob.setText(args.getString("mobile"));
      
      
		
		oehelper.res_location();
		oehelper.res_category();
		oehelper.res_equipment();
		oehelper.origin_equipment();
		
		oehelper.readDataFromServer1();
		
		  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,OEHelper.location_name);
		  dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  splocation.setAdapter(dataAdapter1);
		 
		  	int spinloc = dataAdapter1.getPosition(sploc);
			splocation.setSelection(spinloc);
		  
		  ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,OEHelper.category_name);
		  dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spcategory.setAdapter(dataAdapter2);
		  
		  ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,OEHelper.equipment_name);
		  dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spequipfamily.setAdapter(dataAdapter3);
		   
		  ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,OEHelper.originequipment_name);
		  dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  sporiginequipment.setAdapter(dataAdapter4);
		   

		  
		// location spinner onitem selected change
		  
		  splocation.setOnItemSelectedListener(new OnItemSelectedListener() {
			   
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
					
				
					 if(arg2!=0)
						
					{
						 
						location=OEHelper.location_name.get(arg2);
						locationid=Integer.parseInt(OEHelper.property_stock_asset.get(arg2));
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					
				}
			});
	
			
			// category spinner onitem selected change
				
			  spcategory.setOnItemSelectedListener(new OnItemSelectedListener() {
				   
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
						
						 if(arg2!=0)
								
							{
						 
							category=OEHelper.category_name.get(arg2);
							categoryid=Integer.parseInt(OEHelper.category_id.get(arg2));
							}
					}

					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						
					}
				});
			  
			// equipment spinner onitem selected change
				
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
						
					}
				});
			  

			// origin equipment spinner onitem selected change
				
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
		
			  
			  
			  
			  //Image select from gallery for insert into asset image.
			  
			  btnselectpic.setOnClickListener(new OnClickListener()
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
			  
			
			  
			//Insert Assets details ...  
				
			  b.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) 
					
					{
											
						OEHelper oehelper=new OEHelper(getActivity());
						
						String assetnm = assetname.getText().toString();
						String assetmark=mark.getText().toString();
						String assetmodel=model.getText().toString();
						String assetserialno=serialno.getText().toString();
						String assetbarcodeno = barcodeno.getText().toString();
						
						OEValues val = new OEValues();
							
							val.put("name", assetnm);
							val.put("mark", assetmark);
							val.put("model", assetmodel);
							val.put("serial", assetserialno);
							val.put("barcode_no", assetbarcodeno);
						
							
							val.put("property_stock_asset",locationid);
							val.put("category_id", categoryid);
							val.put("equipment_type_id", equipmentid);
							val.put("equipment_origin_id", originequipmentid);
							
							if (newImage != null ) 
				        {
				            
							//Image Location Url
								Log.e("path","-----------"+newImage);
								
								
								/*Bitmap bm=BitmapFactory.decodeFile(newImage);
								ByteArrayOutputStream baos = new ByteArrayOutputStream();  
								bm.compress(Bitmap.CompressFormat.PNG, 100, baos); 
								
								//this will convert image to byte[] 
								byte[] byteArrayImage = baos.toByteArray(); 
								
								// this will convert byte[] to string
								 ba1 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);*/
								
								
								 File imagefile = new File(newImage);
								 FileInputStream fis = null;
								 try {
								     fis = new FileInputStream(imagefile);
								     } catch (FileNotFoundException e) {
								     e.printStackTrace();
								 }

								 Bitmap bm = BitmapFactory.decodeStream(fis);
								 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
								 bm.compress(Bitmap.CompressFormat.PNG, 100 , baos);    
								 byte[] b = baos.toByteArray(); 
								 ba1 = Base64.encodeToString(b, Base64.DEFAULT);
								 
								
				        }	
							 val.put("image_medium", ba1);
							
							
							Log.i("ASSET TO BE CREATED: " + assetnm , "OK Assets.........");
							Log.i("mark TO BE CREATED: " + assetmark , "assetmark Ok..");
							Log.i("model TO BE CREATED: " + assetmodel , "model Ok.........");
							Log.i("serial no TO BE CREATED: " + assetserialno , "serial no Ok..");
						
							Log.i("serial no TO BE CREATED: " + assetbarcodeno , "barcode no Ok..");
							
							Log.i("location TO BE CREATED: " + locationid , "location Ok..");
							Log.i("category TO BE CREATED: " + categoryid , "category Ok..");
							Log.i("EQUIPMENT TO BE CREATED: " + equipmentid , "equipment Ok..");
							Log.i("EQUIPMENT ORIGIN TO BE CREATED: " + originequipmentid , "equipment origin Ok..");
							
							Log.i("IMAGE OF ASSET TO BE:"+ba1,"IAMGE OF ASSET OK");

							Log.i("THE NEW ASSET VALUES ARE:"+val,"THIS IS OK...");
							
							
							//Insert record to the server and get the values of all field...
					
							 oehelper.insert_asset(val);
							//oehelper.readDataFromServer1();
							
						
							QR_Equipment qr_equip=new QR_Equipment();
							FragmentListener frag = (FragmentListener) getActivity();
							
							frag.startDetailFragment(qr_equip);
							
				        
						}

					
					
					
					});
			
			  
			 
			  
		  
		return rootView;
	}


    

	
//It will Return the format for image name...

	private String dateToString(Date date, String format) 
	{
		 SimpleDateFormat df = new SimpleDateFormat(format);
	        return df.format(date);
	}







	public Object databaseHelper(Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public List<DrawerItem> drawerMenus(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
/**This method call when we select any image from the gallery
  
 *  it return the resultcode for selecting an image.
 */
	
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
			                
			                //Toast.makeText(getActivity(), "IMAGE PATH FROM CAMERA....:"+newImage, Toast.LENGTH_LONG ).show();
			                
			                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
			                userImage.setImageBitmap(bmp);
			                userImage.setVisibility(View.VISIBLE);
			                
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
			            
			           // Toast.makeText(getActivity(), "PATH OF IMAGE FROM GALLERY....:"+newImage, Toast.LENGTH_LONG ).show();

			            userImage.setImageBitmap(thumbnail);

						
				
				}	 
					 
				
		}
				
		
	 }
	
	
	
	
	


	
}



