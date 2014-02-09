package com.avellana.napo;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;

public class Perfil_Negocio extends TabActivity{

    // Divide 1.0 by # of tabs needed
    // In this case: 1.0/2 => 0.5
    private static final LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.3f);

    private static TabHost tabHost;
    private static TabHost.TabSpec spec;
    private static Intent intent;
    private static LayoutInflater inflater;

    private View tab;
    private TextView label;
    private TextView divider;
	RatingBar rb;
	String titulo="";
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil__negocio);
		rb = (RatingBar) findViewById(R.id.ratingBar1);
		rb.setRating(3.5f);
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
		 titulo = extras.getString("empresa");
		}
inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
ratingBar.setOnTouchListener(new OnTouchListener() {

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
    });
ratingBar.setFocusable(false);
// Get tab manager
        tabHost = getTabHost();
        
        // This converts the custom tab view we created and injects it into the tab widget
        tab = inflater.inflate(R.layout.tab, getTabWidget(), false);
        // Mainly used to set the weight on the tab so each is equally wide
        tab.setLayoutParams(params);
        // Add some text to the tab
        label = (TextView) tab.findViewById(R.id.tabLabel);
        label.setText("Promociones");
        // Show a thick line under the selected tab (there are many ways to show
        // which tab is selected, I chose this)
        divider = (TextView) tab.findViewById(R.id.tabSelectedDivider);
        divider.setVisibility(View.VISIBLE);
        // Intent whose generated content will be added to the tab content area
        intent = new Intent(Perfil_Negocio.this, TabContentActivity.class);
        // Just some data for the tab content activity to use (just for demonstrating changing content)
        intent.putExtra("content", "No contamos con promociones por el momento");
        // Finalize the tabs specification
        spec = tabHost.newTabSpec("promociones").setIndicator(tab).setContent(intent);
        // Add the tab to the tab manager
        tabHost.addTab(spec);
        
        TextView titulo_nuevo = (TextView)findViewById(R.id.titulos);
        titulo_nuevo.setText(titulo);
        // Add another tab
        tab = inflater.inflate(R.layout.tab, getTabWidget(), false);
        tab.setLayoutParams(params);
        label = (TextView) tab.findViewById(R.id.tabLabel);
        label.setText("Comentarios");
        intent = new Intent(Perfil_Negocio.this, TabContentActivity.class);
        intent.putExtra("content", "La empresa no cuenta con comentarios");
        spec = tabHost.newTabSpec("comentarios").setIndicator(tab).setContent(intent);
        tabHost.addTab(spec);
        
     // Add another tab
        tab = inflater.inflate(R.layout.tab, getTabWidget(), false);
        tab.setLayoutParams(params);
        label = (TextView) tab.findViewById(R.id.tabLabel);
        label.setText("Info");
        intent = new Intent(Perfil_Negocio.this, TabContentActivity.class);
        intent.putExtra("content", "No se ha podido conseguir ninguna información de la empresa");
        spec = tabHost.newTabSpec("info").setIndicator(tab).setContent(intent);
        tabHost.addTab(spec);
        
        
        // Listener to detect when a tab has changed. I added this just to show 
        // how you can change UI to emphasize the selected tab
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tag) {
                // reset some styles
                clearTabStyles();
                View tabView = null;
                // Use the "tag" for the tab spec to determine which tab is selected
                if (tag.equals("promociones")) {
                    tabView = getTabWidget().getChildAt(0);
                }
                else if (tag.equals("comentarios")) {
                    tabView = getTabWidget().getChildAt(1);
                } else if (tag.equals("info")) {
                    tabView = getTabWidget().getChildAt(2);
                }
                tabView.findViewById(R.id.tabSelectedDivider).setVisibility(View.VISIBLE);
            }       
        });
    }
    
    private void clearTabStyles() {
        for (int i = 0; i < getTabWidget().getChildCount(); i++) {
            tab = getTabWidget().getChildAt(i);
            tab.findViewById(R.id.tabSelectedDivider).setVisibility(View.GONE);
        }
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 prefe();
		 return true;
	}

	public void preferencias(View view){
		prefe();
		
	}
	public void prefe(){
     	startActivity(new Intent(Perfil_Negocio.this,preferencias.class));
     }

}
