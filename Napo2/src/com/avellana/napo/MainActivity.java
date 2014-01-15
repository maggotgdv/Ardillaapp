/**
 * Proyecto Napo - Avellana Produccion Creativa
 * @autor alejandro Arguirre
 * 
 * */

package com.avellana.napo;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.avellana.napo.R;
import com.avellana.napo.Animal;
import com.avellana.napo.AnimalesAdapter;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements LocationListener, TextWatcher, OnItemClickListener, OnMapLongClickListener, OnInfoWindowClickListener {
	
	
	class MyInfoWindowAdapter implements InfoWindowAdapter{
		private final View myContentsView;
		MyInfoWindowAdapter(){
			myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
		}
		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
			tvTitle.setText(marker.getTitle());
			TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
			tvSnippet.setText(marker.getSnippet());
			return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	GoogleMap googlemap;// creacion de atributos
	TextView lugar;
	ImageButton categ;
	AutoCompleteTextView buscar;
	private ArrayList<Animal> animales;
	public ArrayList<Animal> subanimales;
	private AnimalesAdapter adapter;
	private AnimalesAdapter subadapter;
	String[] items={"argentina","alabania","alemania","argelia","berlin","bolivia","mexico","chile","China","corea","uruguay","arequipa"};
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    ListView listview ;
    int es_buscado=0;
    ListView subList;
    SharedPreferences prf;
    final int RQS_GooglePlayServices = 1;
    //TextView tvLocInfo;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);//saca el mapa para manipularlo
	    //tvLocInfo = (TextView)findViewById(R.id.)
		googlemap = mf.getMap();//obtiene el mapa
	    googlemap.setMyLocationEnabled(true);//pone el boton de ubicacion
	    listview = (ListView) findViewById(R.id.catlist);
	    googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googlemap.getUiSettings().setZoomGesturesEnabled(true);
		googlemap.getUiSettings().setCompassEnabled(true);
		googlemap.getUiSettings().setMyLocationButtonEnabled(true);
		googlemap.getUiSettings().setRotateGesturesEnabled(true);
		try{
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//activa el servicio de localizacion
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //lee las nuevas posiciones del punto
		}catch(Exception e){};
		lugar = (TextView)findViewById(R.id.lugares);
		buscar = (AutoCompleteTextView)findViewById(R.id.busco);
		buscar.addTextChangedListener(this);
		buscar.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,items));
		buscar.setVisibility(View.INVISIBLE);
		categ = (ImageButton) findViewById(R.id.categorias);
		prf = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		buscar.setDropDownBackgroundResource(R.color.color_busqueda);
		buscar.setDropDownWidth(-1);
		animales = new ArrayList<Animal>();
		rellenarArrayList();
		adapter = new AnimalesAdapter(this, animales);
		listview.setVisibility(View.INVISIBLE);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener((OnItemClickListener) this);
		googlemap.setOnMapLongClickListener(this);
		googlemap.setInfoWindowAdapter(new MyInfoWindowAdapter());
		googlemap.setOnInfoWindowClickListener(this);
		
		subanimales = new ArrayList<Animal>();
		rellenarsubList();
		subadapter = new AnimalesAdapter(this, subanimales);
		subList = (ListView) findViewById(R.id.subcatlist);
		subList.setVisibility(View.INVISIBLE);
		subList.setAdapter(subadapter);
		subList.setOnItemClickListener((OnItemClickListener) this);
		double latitude = -16.4006667;
		double longitude = -71.55035;
		 LatLng point = new LatLng(latitude, longitude);
		// create marker
		MarkerOptions marker = new MarkerOptions().position(point).snippet("Comida");
		 
		// Changing marker icon
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
		
		 
		// adding marker
		Marker mark=googlemap.addMarker(marker);
		mark.setTitle("Argentina");


	}
	private void rellenarArrayList() {
		animales.clear();
		if(prf.getBoolean("comida", true)==true)
			animales.add(new Animal("Comida", R.drawable.napo_logo));
		if(prf.getBoolean("vestimenta", true)==true)
		animales.add(new Animal("Vestimenta", R.drawable.napo_logo));
		if(prf.getBoolean("salud", true)==true)
		animales.add(new Animal("Salud", R.drawable.napo_logo));
		if(prf.getBoolean("vehiculos", true)==true)
		animales.add(new Animal("Vehículos", R.drawable.napo_logo));
		if(prf.getBoolean("educacion", true)==true)
		animales.add(new Animal("Educación", R.drawable.napo_logo));
		if(prf.getBoolean("viajes", true)==true)
		animales.add(new Animal("Viajes", R.drawable.napo_logo));
		if(prf.getBoolean("belleza", true)==true)
		animales.add(new Animal("Belleza", R.drawable.napo_logo));
		if(prf.getBoolean("entretenimiento", true)==true)
		animales.add(new Animal("Entretenimiento", R.drawable.napo_logo));
		if(prf.getBoolean("musica", true)==true)
		animales.add(new Animal("Música", R.drawable.napo_logo));
		if(prf.getBoolean("servicios_comunica", true)==true)
		animales.add(new Animal("Servicios Comunicación", R.drawable.napo_logo));
		if(prf.getBoolean("medios_comunica", true)==true)
		animales.add(new Animal("Medios Comunicación", R.drawable.napo_logo));
		if(prf.getBoolean("construccion", true)==true)
		animales.add(new Animal("Construcción", R.drawable.napo_logo));
		if(prf.getBoolean("vivienda", true)==true)
		animales.add(new Animal("Vivienda", R.drawable.napo_logo));
		if(prf.getBoolean("eventos", true)==true)
		animales.add(new Animal("Eventos", R.drawable.napo_logo));
		if(prf.getBoolean("tecnologia", true)==true)
		animales.add(new Animal("Tecnología", R.drawable.napo_logo));
		if(prf.getBoolean("artefactos", true)==true)
		animales.add(new Animal("Artefactos", R.drawable.napo_logo));
		if(prf.getBoolean("social", true)==true)
		animales.add(new Animal("Social", R.drawable.napo_logo));
		if(prf.getBoolean("ninos", true)==true)
		animales.add(new Animal("Niños", R.drawable.napo_logo));
		if(prf.getBoolean("profesionales", true)==true)
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
	public void preferencias(View view){
		prefe();
	}
	int ban=0;
	public void Buscar (View view){
		Animation arequip = AnimationUtils.loadAnimation(this, R.anim.buscar_dos);
		Animation animbusq = AnimationUtils.loadAnimation(this, R.anim.buscar);
		InputMethodManager teclado = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(ban==0)
		{
			arequip.reset();
			animbusq.reset();
			lugar.startAnimation(arequip);
			buscar.startAnimation(animbusq);
			buscar.setVisibility(View.VISIBLE);
			lugar.setVisibility(View.INVISIBLE);
			categ.setVisibility(View.INVISIBLE);
			ban=1;
			teclado.showSoftInput(buscar, 0);
		}
		else
		{
			quitarTeclado(buscar);
			arequip.reset();
			animbusq.reset();
			lugar.startAnimation(animbusq);
			buscar.startAnimation(arequip);
			categ.setVisibility(View.VISIBLE);
			lugar.setVisibility(View.VISIBLE);
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
	private void quitarTeclado(View v) {
		InputMethodManager teclado = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		teclado.hideSoftInputFromWindow(v.getWindowToken(), 0);
		
	}

	int band2=0;
	int band3=0;
	int i=0;
	public void catego (View view){
		Animation arequip = AnimationUtils.loadAnimation(this, R.anim.translate_in);
		Animation animbusq = AnimationUtils.loadAnimation(this, R.anim.translate_out);
		if(es_buscado==0){
			rellenarArrayList();
			adapter.setDatos(animales);
			listview.setAdapter(adapter);
		}else
		{
			es_buscado=0;
		}
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
			
			if(i==1){
				subList.startAnimation(animbusq);
			}
			else
			{
				listview.startAnimation(animbusq);
			}
			Thread timer = new Thread(){
				 public void run(){
				 try{
				 sleep(500);
				 }catch(InterruptedException e){
				 e.printStackTrace();
				 }finally{
					 listview.clearAnimation();
					 if(i==1){
						 subList.clearAnimation();
					 }
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
			listview.startAnimation(animbusq);
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
				 listview.setVisibility(View.INVISIBLE);
			band3=1;
		}
		else
		{
			arequip.reset();
			animbusq.reset();
			subList.startAnimation(animbusq);
			listview.startAnimation(arequip);
			listview.setVisibility(View.VISIBLE);
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
	int van=0;
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
		lugar.setText(buscar.getText());
		for(int j=0;j<items.length;j++){
			if(items[j].contains(buscar.getText().toString())){
				if(van==0)
					animales.clear();
				animales.add(new Animal(items[j], R.drawable.napo_logo));	
				van++;
			}
		}
		if(van!=0)
		{
		adapter.setDatos(animales);
		listview.setAdapter(adapter);
		van=0;
		es_buscado=1;
		}else{
			es_buscado=0;
		}
		if(buscar.getText().toString().equals("")){
			lugar.setText("Napo");
		}
	}
	public void prefe(){
     	startActivity(new Intent(MainActivity.this, 
	                 preferencias.class));
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
			SacarSubCat(animales.get(position).getNombre());
			subcatego ();
			i=1;
		}
		
	}
	private void SacarSubCat(String cat) {
			subanimales.clear();
			subanimales.add(new Animal("Atras", R.drawable.atras));
			if(cat=="Comida"){
				subanimales.add(new Animal("Pizzería", R.drawable.napo_logo));
				subanimales.add(new Animal("Criolla", R.drawable.napo_logo));
				subanimales.add(new Animal("Cebichería", R.drawable.napo_logo));
				subanimales.add(new Animal("Picantería", R.drawable.napo_logo));
				subanimales.add(new Animal("pollería", R.drawable.napo_logo));
				subanimales.add(new Animal("chicharronería", R.drawable.napo_logo));
				subanimales.add(new Animal("Internacional", R.drawable.napo_logo));
				subanimales.add(new Animal("Dulcerías", R.drawable.napo_logo));
				subanimales.add(new Animal("Pastelerías", R.drawable.napo_logo));
				subanimales.add(new Animal("Panaderías", R.drawable.napo_logo));

			}else if(cat=="Vestimenta"){
				subanimales.add(new Animal("varon", R.drawable.napo_logo));
				subanimales.add(new Animal("damas", R.drawable.napo_logo));
				subanimales.add(new Animal("niños", R.drawable.napo_logo));

			}else if(cat=="Salud"){
				subanimales.add(new Animal("óptica", R.drawable.napo_logo));
				subanimales.add(new Animal("farmacia", R.drawable.napo_logo));
				subanimales.add(new Animal("prótesis", R.drawable.napo_logo));
				subanimales.add(new Animal("clínicas", R.drawable.napo_logo));
				subanimales.add(new Animal("spa", R.drawable.napo_logo));
				subanimales.add(new Animal("Ambulancias", R.drawable.napo_logo));
				subanimales.add(new Animal("Aseguradoras", R.drawable.napo_logo));

			}else if(cat=="Vehículos"){
				subanimales.add(new Animal("Repuestos", R.drawable.napo_logo));
				subanimales.add(new Animal("aceites", R.drawable.napo_logo));
				subanimales.add(new Animal("talleres", R.drawable.napo_logo));
				subanimales.add(new Animal("lavado de vehículos", R.drawable.napo_logo));
				subanimales.add(new Animal("Cocheras", R.drawable.napo_logo));
				
			}else if(cat=="Educación"){
				subanimales.add(new Animal("colegios", R.drawable.napo_logo));
				subanimales.add(new Animal("institutos", R.drawable.napo_logo));
				subanimales.add(new Animal("universidades", R.drawable.napo_logo));
				subanimales.add(new Animal("post grados", R.drawable.napo_logo));
				subanimales.add(new Animal("capacitación contínua", R.drawable.napo_logo));
				subanimales.add(new Animal("book store", R.drawable.napo_logo));
				subanimales.add(new Animal("librerías", R.drawable.napo_logo));
				subanimales.add(new Animal("Academias", R.drawable.napo_logo));
				subanimales.add(new Animal("Talleres", R.drawable.napo_logo));
				
			}else if(cat=="Viajes"){
				subanimales.add(new Animal("Maletas y equipaje", R.drawable.napo_logo));
				subanimales.add(new Animal("agencias de viaje", R.drawable.napo_logo));
				subanimales.add(new Animal("hoteles", R.drawable.napo_logo));
				subanimales.add(new Animal("Empresas de transporte", R.drawable.napo_logo));
				subanimales.add(new Animal("Aerolíneas", R.drawable.napo_logo));
				subanimales.add(new Animal("Turismo", R.drawable.napo_logo));

			}else if(cat=="Belleza"){
				subanimales.add(new Animal("salón", R.drawable.napo_logo));
				subanimales.add(new Animal("maquillaje", R.drawable.napo_logo));
				subanimales.add(new Animal("perfumería", R.drawable.napo_logo));
				
			}else if(cat=="Entretenimiento"){
				subanimales.add(new Animal("discotecas", R.drawable.napo_logo));
				subanimales.add(new Animal("pubs", R.drawable.napo_logo));
				subanimales.add(new Animal("cines", R.drawable.napo_logo));
				subanimales.add(new Animal("artículos relacionados", R.drawable.napo_logo));

			}else if(cat=="Música"){
				subanimales.add(new Animal("discotiendas", R.drawable.napo_logo));
				subanimales.add(new Animal("instrumentos", R.drawable.napo_logo));
				subanimales.add(new Animal("sala de ensayo", R.drawable.napo_logo));
				subanimales.add(new Animal("estudio de grabación", R.drawable.napo_logo));
				subanimales.add(new Animal("Músicos y bandas", R.drawable.napo_logo));

			}else if(cat=="Servicios Comunicación"){
				subanimales.add(new Animal("Publicidad Agencia", R.drawable.napo_logo));
				subanimales.add(new Animal("Audio y Video", R.drawable.napo_logo));
				subanimales.add(new Animal("Imprenta", R.drawable.napo_logo));
				subanimales.add(new Animal("BTL", R.drawable.napo_logo));
				subanimales.add(new Animal("Fotografía", R.drawable.napo_logo));

			}else if(cat=="Medios Comunicación"){
				subanimales.add(new Animal("Radios", R.drawable.napo_logo));
				subanimales.add(new Animal("TV", R.drawable.napo_logo));
				subanimales.add(new Animal("Prensa", R.drawable.napo_logo));

			}else if(cat=="Construcción"){
				subanimales.add(new Animal("Constructoras", R.drawable.napo_logo));
				subanimales.add(new Animal("Ladrilleras", R.drawable.napo_logo));
				subanimales.add(new Animal("Ferreterías", R.drawable.napo_logo));
				subanimales.add(new Animal("diseño de interiores", R.drawable.napo_logo));
				subanimales.add(new Animal("Electricistas", R.drawable.napo_logo));
				subanimales.add(new Animal("Gasfitería", R.drawable.napo_logo));
				subanimales.add(new Animal("Pinturas", R.drawable.napo_logo));
				subanimales.add(new Animal("Dry Wall", R.drawable.napo_logo));

			}else if(cat=="Vivienda"){
				subanimales.add(new Animal("Inmobiliarias", R.drawable.napo_logo));
				subanimales.add(new Animal("Alquiler", R.drawable.napo_logo));
				subanimales.add(new Animal("venta", R.drawable.napo_logo));
				subanimales.add(new Animal("anticresis", R.drawable.napo_logo));
				subanimales.add(new Animal("Muebles", R.drawable.napo_logo));

			}else if(cat=="Eventos"){
				subanimales.add(new Animal("teatro", R.drawable.napo_logo));
				subanimales.add(new Animal("concierto", R.drawable.napo_logo));
				subanimales.add(new Animal("intervención", R.drawable.napo_logo));
				subanimales.add(new Animal("cumpleaños", R.drawable.napo_logo));
				subanimales.add(new Animal("aniversario", R.drawable.napo_logo));

			}else if(cat=="Tecnología"){
				subanimales.add(new Animal("Computadoras", R.drawable.napo_logo));
				subanimales.add(new Animal("celulares", R.drawable.napo_logo));

			}else if(cat=="Artefactos"){
				subanimales.add(new Animal("Tienda", R.drawable.napo_logo));
				subanimales.add(new Animal("Repación", R.drawable.napo_logo));
				subanimales.add(new Animal("Repuestos", R.drawable.napo_logo));

			}else if(cat=="Social"){
				subanimales.add(new Animal("clubes", R.drawable.napo_logo));
				subanimales.add(new Animal("locales", R.drawable.napo_logo));
				subanimales.add(new Animal("ferias", R.drawable.napo_logo));

			}else if(cat=="Niños"){
				subanimales.add(new Animal("Jugueterías", R.drawable.napo_logo));
				subanimales.add(new Animal("Sala de juegos", R.drawable.napo_logo));
				subanimales.add(new Animal("animación", R.drawable.napo_logo));

			}else if(cat=="Profesionales"){
				subanimales.add(new Animal("Médicos", R.drawable.napo_logo));
				subanimales.add(new Animal("Psicólogos", R.drawable.napo_logo));
				subanimales.add(new Animal("Arquitectos", R.drawable.napo_logo));
				subanimales.add(new Animal("Marketeros", R.drawable.napo_logo));
				subanimales.add(new Animal("Contadores", R.drawable.napo_logo));
				subanimales.add(new Animal("economistas", R.drawable.napo_logo));
				subanimales.add(new Animal("Abogados", R.drawable.napo_logo));

			}
			subadapter.setDatos(subanimales);
			subList.setAdapter(subadapter);
		
		
	}
	@Override
    public boolean onPrepareOptionsMenu(Menu menu){
		 prefe();
		 return true;
	 }
	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

}
