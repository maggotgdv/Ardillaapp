package com.avellana.napo;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements LocationListener, TextWatcher {
	 GoogleMap googlemap;// creacion de atributos
	 TextView lugar;
	 ImageButton categ;
	 AutoCompleteTextView buscar;
	 String[] items={"argentina","alabania","alemania","argelia","berlin","bolivia","mexico"};
     private LocationManager locationManager;
     private static final long MIN_TIME = 400;
     private static final float MIN_DISTANCE = 1000;
     ListView listview ;
     String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
         "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
         "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
         "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
         "Android", "iPhone", "WindowsMobile" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);//saca el mapa para manipularlo
	     googlemap = mf.getMap();//obtiene el mapa
	     googlemap.setMyLocationEnabled(true);//pone el boton de ubicacion
	     listview = (ListView) findViewById(R.id.catlist);
	     listview.setVisibility(View.INVISIBLE);
	     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);
	     listview.setAdapter(adapter);
	     //googlemap.setOnMapClickListener((OnMapClickListener) this);
	     googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 googlemap.getUiSettings().setZoomGesturesEnabled(false);
		 googlemap.getUiSettings().setCompassEnabled(true);
		 googlemap.getUiSettings().setMyLocationButtonEnabled(true);
		 googlemap.getUiSettings().setRotateGesturesEnabled(true);
		 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//activa el servicio de localizacion
		 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //lee las nuevas posiciones del punto
		 lugar = (TextView)findViewById(R.id.lugares);
		 buscar = (AutoCompleteTextView)findViewById(R.id.busco);
		 buscar.addTextChangedListener(this);
		 buscar.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,items));
		 buscar.setVisibility(View.INVISIBLE);
		 categ = (ImageButton) findViewById(R.id.categorias);
		  




	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	  	 LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());//consigue mi latitud y longitud
 	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);// le pone en el centro con un zoom 16x
 	    googlemap.animateCamera(cameraUpdate);//lo hace animado
 	    locationManager.removeUpdates(this);//elimina las actualizaciones antiguas

		
	}
	int ban=0;
	public void Buscar (View view){
		Animation arequip = AnimationUtils.loadAnimation(this, R.anim.buscar_dos);
		Animation animbusq = AnimationUtils.loadAnimation(this, R.anim.buscar);
		
		if(ban==0)
		{
			arequip.reset();
			animbusq.reset();
			lugar.startAnimation(arequip);
			buscar.startAnimation(animbusq);
			buscar.setVisibility(View.VISIBLE);
			categ.setVisibility(View.INVISIBLE);
			ban=1;
		}
		else
		{
			arequip.reset();
			animbusq.reset();
			lugar.startAnimation(animbusq);
			buscar.startAnimation(arequip);
			categ.setVisibility(View.VISIBLE);
			buscar.setVisibility(View.INVISIBLE);
			Thread timer = new Thread(){
				 public void run(){
				 try{
				 sleep(500);
				 }catch(InterruptedException e){
				 e.printStackTrace();
				 }finally{
					 lugar.clearAnimation();
						buscar.clearAnimation();
				 }
				 }
				 };
				 timer.start();
			
			ban=0;
		}
		

		
	}
	int band2=0;
	public void catego (View view){
		Animation arequip = AnimationUtils.loadAnimation(this, R.anim.translate_in);
		Animation animbusq = AnimationUtils.loadAnimation(this, R.anim.translate_out);
		
		if(band2==0)
		{
			
			arequip.reset();
			animbusq.reset();
			listview.startAnimation(arequip);
			listview.setVisibility(View.VISIBLE);
			
			band2=1;
		}
		else
		{
			arequip.reset();
			animbusq.reset();
			listview.startAnimation(animbusq);
			listview.setVisibility(View.INVISIBLE);
			Thread timer = new Thread(){
				 public void run(){
				 try{
				 sleep(500);
				 }catch(InterruptedException e){
				 e.printStackTrace();
				 }finally{
					 listview.clearAnimation();
				 }
				 }
				 };
				 timer.start();
			
			
			band2=0;
		}
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
	
		
		
	}

}
