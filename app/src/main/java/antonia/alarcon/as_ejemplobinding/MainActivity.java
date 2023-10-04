package antonia.alarcon.as_ejemplobinding;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import antonia.alarcon.as_ejemplobinding.databinding.ActivityMainBinding;
import antonia.alarcon.as_ejemplobinding.modelos.Alumno;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

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

                                //FALTA LA INFORMACIÓN
                            } else {
                                Toast.makeText(MainActivity.this, "NO HAY INFORMACIÓN", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}