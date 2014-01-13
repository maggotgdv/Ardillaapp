package com.avellana.napo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Perfil_Negocio extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil__negocio);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil__negocio, menu);
		return true;
	}

}
