package com.avellana.napo;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.avellana.napo.R;
import com.avellana.napo.Animal;
import com.avellana.napo.AnimalesAdapter;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements LocationListener, TextWatcher, OnItemClickListener {
	 GoogleMap googlemap;// creacion de atributos
	 TextView lugar;
	 ImageButton categ;
	 AutoCompleteTextView buscar;
	 private ArrayList<Animal> animales;
	 private ArrayList<Animal> subanimales;
	private AnimalesAdapter adapter;
	private AnimalesAdapter subadapter;
	 String[] items={"argentina","alabania","alemania","argelia","berlin","bolivia","mexico"};
     private LocationManager locationManager;
     private static final long MIN_TIME = 400;
     private static final float MIN_DISTANCE = 1000;
     ListView listview ;
     ListView subList;
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
		 buscar.setDropDownBackgroundResource(R.color.color_busqueda);
		 buscar.setDropDownWidth(300);
		 animales = new ArrayList<Animal>();
		rellenarArrayList();
		adapter = new AnimalesAdapter(this, animales);
		listview.setVisibility(View.INVISIBLE);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener((OnItemClickListener) this);
		
		subanimales = new ArrayList<Animal>();
		rellenarsubList();
		subadapter = new AnimalesAdapter(this, subanimales);
		subList = (ListView) findViewById(R.id.subcatlist);
		subList.setVisibility(View.INVISIBLE);
		subList.setAdapter(subadapter);
		subList.setOnItemClickListener((OnItemClickListener) this);

	}
	private void rellenarArrayList() {
		animales.add(new Animal("Comida", R.drawable.napo_logo));
		animales.add(new Animal("Vestimenta", R.drawable.napo_logo));
		animales.add(new Animal("Salud", R.drawable.napo_logo));
		animales.add(new Animal("Vehículos", R.drawable.napo_logo));
		animales.add(new Animal("Educación", R.drawable.napo_logo));
		animales.add(new Animal("Viajes", R.drawable.napo_logo));
		animales.add(new Animal("Belleza", R.drawable.napo_logo));
		animales.add(new Animal("Entretenimiento", R.drawable.napo_logo));
		animales.add(new Animal("Música", R.drawable.napo_logo));
		animales.add(new Animal("Servicios Comunicación", R.drawable.napo_logo));
		animales.add(new Animal("Medios Comunicación", R.drawable.napo_logo));
		animales.add(new Animal("Construcción", R.drawable.napo_logo));
		animales.add(new Animal("Vivienda", R.drawable.napo_logo));
		animales.add(new Animal("Eventos", R.drawable.napo_logo));
		animales.add(new Animal("Tecnología", R.drawable.napo_logo));
		animales.add(new Animal("Artefactos", R.drawable.napo_logo));
		animales.add(new Animal("Social", R.drawable.napo_logo));
		animales.add(new Animal("Niños", R.drawable.napo_logo));
		animales.add(new Animal("Profesionales", R.drawable.napo_logo));
	}
	private void rellenarsubList() {
		subanimales.add(new Animal("Atras", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat1", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat2", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat3", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat4", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat5", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat6", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat7", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat8", R.drawable.napo_logo));
		subanimales.add(new Animal("SubCat9", R.drawable.napo_logo));
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
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
	int band3=0;
	int i=0;
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
			i=0;
		}
		else
		{
			arequip.reset();
			animbusq.reset();
			listview.startAnimation(animbusq);
			subList.startAnimation(animbusq);
			Thread timer = new Thread(){
				 public void run(){
				 try{
				 sleep(500);
				 }catch(InterruptedException e){
				 e.printStackTrace();
				 }finally{
					 listview.clearAnimation();
					 subList.clearAnimation();
				 }
				 }
				 };
				 timer.start();
				 listview.setVisibility(View.INVISIBLE);
					subList.setVisibility(View.INVISIBLE);
			
			band2=0;
			band3=0;
		}
	}
	
	public void subcatego (){
		Animation arequip = AnimationUtils.loadAnimation(this, R.anim.translate_in);
		Animation animbusq = AnimationUtils.loadAnimation(this, R.anim.translate_out);
		
		if(band3==0)
		{
			
			arequip.reset();
			animbusq.reset();
			subList.startAnimation(arequip);
			subList.setVisibility(View.VISIBLE);
			
			band3=1;
		}
		else
		{
			arequip.reset();
			animbusq.reset();
			subList.startAnimation(animbusq);
			subList.setVisibility(View.INVISIBLE);
			Thread timer = new Thread(){
				 public void run(){
				 try{
				 sleep(500);
				 }catch(InterruptedException e){
				 e.printStackTrace();
				 }finally{
					 subList.clearAnimation();
				 }
				 }
				 };
				 timer.start();
			
			
			band3=0;
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
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long ID) {
		// vNombre.setText(animales.get(position).getNombre());
		
		String atras;
		if(i==1){
			atras=subanimales.get(position).getNombre();
			if(atras=="Atras")
			{
				subcatego ();
				i=0;
			}
		}else{
			subcatego ();
			i=1;
		}
		
	}

}
