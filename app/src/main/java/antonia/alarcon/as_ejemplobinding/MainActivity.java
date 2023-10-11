package antonia.alarcon.as_ejemplobinding;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import antonia.alarcon.as_ejemplobinding.databinding.ActivityMainBinding;
import antonia.alarcon.as_ejemplobinding.modelos.Alumno;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;
    private ActivityResultLauncher<Intent> editAlumnoLauncher;

    private ArrayList<Alumno> listaAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        listaAlumnos = new ArrayList<>(); //inicializar antes de inicializar launcher

        inicializarLauncher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir la actividad add alumno
                    //hay que crear un launcher (arriba)

                addAlumnoLauncher.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));

                //necesitamos inicializar launcher

            }
        });
    }

    private void inicializarLauncher() {
        //preparar el resultado
        addAlumnoLauncher = registerForActivityResult( //necesito dos cosas
                new ActivityResultContracts.StartActivityForResult(), //algo que permita iniciar la actividad
                new ActivityResultCallback<ActivityResult>() { //algo que me permita recogerlo
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData().getExtras() != null) {//significa que ha vuelto con cosas en la maleta
                                Alumno alumno = (Alumno) result.getData().getExtras().getSerializable("ALUMNO");
                                Toast.makeText(MainActivity.this, alumno.toString(), Toast.LENGTH_SHORT).show();

                                listaAlumnos.add(alumno);
                                //después de añadirlo, queremos que lo enseñe
                                mostrarAlumnos();

                                /* PASOS A SEGUIR
                                1. Un elemento para mostrar la información (dentro de la vista) --> TextView
                                2. Un conjunto de datos a mostrar --> Lista de alumnos
                                3. Un contenedor donde mostrar cada unod e los elementos --> Scroll
                                4. La lógica para mostrar los elementos
                                */

                            } else {
                                Toast.makeText(MainActivity.this, "NO HAY INFORMACIÓN", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        editAlumnoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                }
        );

    }

    private void mostrarAlumnos() {
        //mostrar la lista de alumnos dentro de un scroll
        //1. Crear el scroll en el content main y un linear layout vertical dentro del scroll
        //vaya al elemento del content main, al nombre que le hemos puesto, y borre todas las vistas
        binding.contentMain.contenedorMain.removeAllViews();

        for (Alumno alumno : listaAlumnos) {
            /*//por cada elemento de la vista, crearemos un TextView
            TextView tvAlumno = new TextView(MainActivity.this);
            tvAlumno.setText(alumno.toString());

            binding.contentMain.contenedorMain.addView(tvAlumno);*/

            //lo anterior es en modo CUTRE
            //AHORA VIENE LO BUENOOOOOOOOO
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            //le tengo que dar el camino del xml que quiero leer
            View alumnoView = layoutInflater.inflate(R.layout.alumno_fila_view, null);

            //le decimos que poner en cada uno de los textview
            //que vaya a vista y busque por identificador
            //esta vista no está asociada a ninguna actividad por lo que no puedo usar el binding
            TextView lbNombre = alumnoView.findViewById(R.id.lbNombreAlumnoView);
            TextView lbApellidos = alumnoView.findViewById(R.id.lbApellidosAlumnoView);
            TextView lbCiclos = alumnoView.findViewById(R.id.lbCicloAlumnoView);
            TextView lbGrupo = alumnoView.findViewById(R.id.lbGrupoAlumnoView);



            alumnoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, EditAlumnoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO",alumno);
                    intent.putExtras(bundle);
                    editAlumnoLauncher.launch(intent);
                }
            });

            lbNombre.setText(alumno.getNombre());
            lbApellidos.setText(alumno.getApellidos());
            lbCiclos.setText(alumno.getCiclo());
            lbGrupo.setText(String.valueOf(alumno.getGrupo()));

            //añadir esa vista al binding
            binding.contentMain.contenedorMain.addView(alumnoView);
        }
    }

}