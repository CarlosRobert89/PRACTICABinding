package carlos.robert.binding;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import carlos.robert.binding.Modelos.Alumno;
import carlos.robert.binding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //si se crea un proyecto Empty View Activity para poder
    //utilizar el binding hay que incorporar en build.gradle
    //debajo de compileOptions{...} -> buildFeatures {viewBinding = true}}
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> launcherAlumno;

    private ArrayList<Alumno> listaAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflate mapea los elementos de la vista y guarda en el binding todas las variables
        //nos evitamos tener que crear todas las variables y la inicialización de vistas
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //cargamos la raíz del binding para poder utilizarlo
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        listaAlumnos = new ArrayList<>();
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
                                listaAlumnos.add(alumno);
                                mostrarAlumnos();
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

    private void mostrarAlumnos() {
        //eliminar lo que haya en el LinearLayout
        binding.contenidoMain.contentMain.removeAllViews();
        //recorremos la lista de alumnos y añadimos a la vista la info de cada alumno
        for(Alumno alumno: listaAlumnos){
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            //al no ser una actividad debemos cargar la vista a través de R.layout...
            View alumnoView = layoutInflater.inflate(R.layout.alumno_fila_view, null);
            TextView txtNombre = alumnoView.findViewById(R.id.lbNombreAlumnoView);
            TextView txtApellidos = alumnoView.findViewById(R.id.lbApellidosAlumnoView);
            TextView txtCiclo = alumnoView.findViewById(R.id.lbCicloAlumnoView);
            TextView txtGrupo = alumnoView.findViewById(R.id.lbGrupoAlumnoView);

            txtNombre.setText(alumno.getNombre());
            txtApellidos.setText(alumno.getApellidos());
            txtCiclo.setText(alumno.getCiclo());
            txtGrupo.setText(String.valueOf(alumno.getGrupo()));

            binding.contenidoMain.contentMain.addView(alumnoView);
        }
    }
}