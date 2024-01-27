package carlos.robert.binding;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import carlos.robert.binding.Modelos.Alumno;
import carlos.robert.binding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //si se crea un proyecto Empty View Activity para poder
    //utilizar el binding hay que incorporar en build.gradle
    //debajo de compileOptions{...} -> buildFeatures {viewBinding = true}}
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcherAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflate mapea los elementos de la vista y guarda en el binding todas las variables
        //nos evitamos tener que crear todas las variables y la inicialización de vistas
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //cargamos la raíz del binding para poder utilizarlo
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        inicializarLauncher();
        //accedemos al botón y le asignamos una acción
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lanzar la actividad AddAlumno y que me devuelva info
                launcherAlumno.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));

            }
        });
    }

    private void inicializarLauncher() {
        launcherAlumno = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null && result.getData().getExtras() != null) {
                                Alumno alumno = (Alumno) result.getData().getExtras().getSerializable("ALUMNO");
                                Toast.makeText(MainActivity.this, alumno.toString(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "No llegaron los datos...", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}