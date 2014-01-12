package com.avellana.napo;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;

public class preferencias extends PreferenceActivity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.opciones);
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
			this.finish();

		return true;
	}
}
