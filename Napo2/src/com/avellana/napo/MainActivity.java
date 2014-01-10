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
import android.view.Menu;

public class MainActivity extends FragmentActivity implements LocationListener {
	 GoogleMap googlemap;// creacion de atributos
     private LocationManager locationManager;
     private static final long MIN_TIME = 400;
     private static final float MIN_DISTANCE = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);//saca el mapa para manipularlo
	     googlemap = mf.getMap();//obtiene el mapa
	     googlemap.setMyLocationEnabled(true);//pone el boton de ubicacion
	    
	     //googlemap.setOnMapClickListener((OnMapClickListener) this);
	    googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 googlemap.getUiSettings().setZoomGesturesEnabled(false);
		 googlemap.getUiSettings().setCompassEnabled(true);
		 googlemap.getUiSettings().setMyLocationButtonEnabled(true);
		 googlemap.getUiSettings().setRotateGesturesEnabled(true);
		 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//activa el servicio de localizacion
		 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //lee las nuevas posiciones del punto





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

}
