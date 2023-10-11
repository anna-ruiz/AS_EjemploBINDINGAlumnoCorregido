package antonia.alarcon.as_ejemplobinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import antonia.alarcon.as_ejemplobinding.databinding.ActivityEditAlumnoBinding;
import antonia.alarcon.as_ejemplobinding.modelos.Alumno;

public class EditAlumnoActivity extends AppCompatActivity {

    private ActivityEditAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HAY Q COMENTARLA O BORRARLA
        //setContentView(R.layout.activity_edit_alumno);

        binding = ActivityEditAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Alumno alumno = (Alumno) bundle.getSerializable("ALUMNO");
        //Toast.makeText(this,alumno.toString(),Toast.LENGTH_SHORT).show();

        rellenarAlumno(alumno);

        //GUardamos los datos actualizados cuando introduzca el user los datos y pulse el btn actualizar
        binding.btnActualizarEditAlummno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recoger datos del alumno
                Alumno alumno = crearAlumno();

                if (alumno != null) {
                    //hacer el intent
                    Intent intent = new Intent();

                    //poner el bundle
                    Bundle bundle = new Bundle(); //cuando estoy de vuelta no pongo la ruta
                    bundle.putSerializable("ALUMNO", alumno);
                    intent.putExtras(bundle);

                    //comunicar resultado correcto
                    setResult(RESULT_OK, intent);

                    //terminar
                    finish();
                } else {
                    Toast.makeText(EditAlumnoActivity.this, "FALTA INFORMACIÓN", Toast.LENGTH_SHORT).show();
                }
                //Enviarlos de vuelta y cerrar
            }
        });

        binding.btnBorrarEditAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setResult(RESULT_OK);
                finish();
            }
        });

    }
    private Alumno crearAlumno() {
        if (binding.txtNombreEditAlumno.getText().toString().isEmpty()) {
            return null;
        }

        if (binding.txtApellidosEditAlumno.getText().toString().isEmpty()) {
            return null;
        }

        if (binding.spCiclosEditAlumno.getSelectedItemPosition() == 0) {
            return null;
        }

        if (!binding.rbGrupoAEditAlumno.isChecked() && !binding.rbGrupoBEditAlumno.isChecked()
                && !binding.rbGrupoCEditAlumno.isChecked()) {
            return null;
        }

        RadioButton rb = findViewById(binding.rgGrupoEditAlumno.getCheckedRadioButtonId());
        //como es solo un caracter, del segundo split (aunque es obvio) le tenemos que decir que coja el primer chart con la posición 0
        char grupo = rb.getText().toString().split(" ")[1].charAt(0);

        Alumno alumno = new Alumno(
                binding.txtNombreEditAlumno.getText().toString(),
                binding.txtApellidosEditAlumno.getText().toString(),
                binding.spCiclosEditAlumno.getSelectedItem().toString(),
                grupo
        );

        return alumno;
    }

    private void rellenarAlumno(Alumno alumno) {

        binding.txtNombreEditAlumno.setText(alumno.getNombre());
        binding.txtApellidosEditAlumno.setText(alumno.getApellidos());

        //Hacemos un switch para sacar la posicion del ciclo seleccionado en el desplegable
        int posicion = 0;
        switch (alumno.getCiclo()){
            case "SMR": posicion = 1;
            break;
            case "DAM": posicion = 2;
            break;
            case "DAW": posicion = 3;
            break;
            case "3D": posicion = 4;
            break;
            case "MARKETING": posicion = 5;
            break;
        }
        binding.spCiclosEditAlumno.setSelection(posicion);

        //Radiobuttons, hacemos switch segun el seleccionado
        switch (alumno.getGrupo()){
            case 'A': binding.rbGrupoAEditAlumno.setChecked(true);
            break;
            case 'B': binding.rbGrupoBEditAlumno.setChecked(true);
            break;
            case 'C': binding.rbGrupoCEditAlumno.setChecked(true);
        }

    }
}