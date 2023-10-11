package antonia.alarcon.as_ejemplobinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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