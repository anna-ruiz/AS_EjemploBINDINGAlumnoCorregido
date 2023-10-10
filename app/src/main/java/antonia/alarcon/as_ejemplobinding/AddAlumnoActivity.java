package antonia.alarcon.as_ejemplobinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import antonia.alarcon.as_ejemplobinding.databinding.ActivityAddAlumnoBinding;
import antonia.alarcon.as_ejemplobinding.modelos.Alumno;

public class AddAlumnoActivity extends AppCompatActivity {
    //solo se puede hacer binding en las actividades
    private ActivityAddAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Me ahorro el paso de inicilizar todas las variables de la vista e inicializar la vista

        super.onCreate(savedInstanceState);
        /*siguiente línea no sirve
        setContentView(R.layout.activity_add_alumno);*/

        //inicializar variable
        //que vaya al layout de esa actividad, que lea el layout xml y muestre el binding
        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());

        //cargar la vista con el binding, su raíz deel xml
        setContentView(binding.getRoot());

        //programar los botones
        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddAlummno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recoger la información para crear alumno
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
                    Toast.makeText(AddAlumnoActivity.this, "FALTA INFORMACIÓN", Toast.LENGTH_SHORT).show();
                }

            }

            private Alumno crearAlumno() {
                if (binding.txtNombreAddAlumno.getText().toString().isEmpty()) {
                    return null;
                }

                if (binding.txtApellidosAddAlumno.getText().toString().isEmpty()) {
                    return null;
                }

                if (binding.spCiclosAddAlumno.getSelectedItemPosition() == 0) {
                    return null;
                }

                if (!binding.rbGrupoAAddAlumno.isChecked() && !binding.rbGrupoBAddAlumno.isChecked()
                        && !binding.rbGrupoCAddAlumno.isChecked()) {
                    return null;
                }

                RadioButton rb = findViewById(binding.rgGrupoAddAlumno.getCheckedRadioButtonId());
                //como es solo un caracter, del segundo split (aunque es obvio) le tenemos que decir que coja el primer chart con la posición 0
                char grupo = rb.getText().toString().split(" ")[1].charAt(0);

                Alumno alumno = new Alumno(
                        binding.txtNombreAddAlumno.getText().toString(),
                        binding.txtApellidosAddAlumno.getText().toString(),
                        binding.spCiclosAddAlumno.getSelectedItem().toString(),
                        grupo
                );

                return alumno;
            }
        });
    }
}