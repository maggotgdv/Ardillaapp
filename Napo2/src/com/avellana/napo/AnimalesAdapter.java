package com.avellana.napo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Clase Adapter que personaliza las vistas de cada ítem a mostrar en el
 * ListView.
 * 
 * @author ProyectoSimio
 * 
 */
public class AnimalesAdapter extends ArrayAdapter<Animal> {
	private Context context;
	private ArrayList<Animal> datos;

	/**
	 * Constructor del Adapter.
	 * 
	 * @param context
	 *            context de la Activity que hace uso del Adapter.
	 * @param datos
	 *            Datos que se desean visualizar en el ListView.
	 */
	public AnimalesAdapter(Context context, ArrayList<Animal> datos) {
		super(context, R.layout.categorias , datos);
		// Guardamos los parámetros en variables de clase.
		this.context = context;
		this.datos = datos;
	}
	public void setDatos(ArrayList<Animal> datos){
		this.datos = datos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		AnimalesHolder holder;

		if (item == null) {
			item = LayoutInflater.from(context).inflate(R.layout.categorias ,
					null);
			// Inicializamos el holder y guardamos las referencias a los
			// controles.
			holder = new AnimalesHolder();
			holder.imgAnimal = (ImageView) item.findViewById(R.id.imgAnimal);
			holder.tvContent = (TextView) item.findViewById(R.id.tvContent);
			//holder.tvtoggleb = (ToggleButton) item.findViewById(R.id.tvtoggleb);

			// Almacenamos el holder en el Tag de la vista.
			item.setTag(holder);
		}
		// Recuperamos el holder del Tag de la vista.
		holder = (AnimalesHolder) item.getTag();

		// A partir del holder, asignamos los valores que queramos a los
		// controles.
		// Le asignamos una foto al ImegeView.
		holder.imgAnimal.setImageResource(datos.get(position)
				.getDrawableImageID());

		// Asignamos los textos a los TextView.
		holder.tvContent.setText(datos.get(position).getNombre());
		//holder.tvField.setText(String.valueOf(position));

		// Devolvemos la vista para que se muestre en el ListView.
		return item;
	}

}
