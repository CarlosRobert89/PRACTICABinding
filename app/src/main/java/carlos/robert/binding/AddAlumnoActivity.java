package carlos.robert.binding;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import carlos.robert.binding.Modelos.Alumno;
import carlos.robert.binding.databinding.ActivityAddAlumnoBinding;

public class AddAlumnoActivity extends AppCompatActivity {

    private ActivityAddAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_alumno);
        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //crear alumno y añadir los datos de la vista
                Alumno alumno = crearAlumno();
                if(alumno==null){
                    Toast.makeText(AddAlumnoActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }else{
                    //enviar info al principal junto con result OK
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO",alumno);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);

                    //terminar
                    finish();
                }
            }
        });
    }

    private Alumno crearAlumno() {
        String nombre = binding.txtNombreAddAlumno.getText().toString();
        String apellidos = binding.txtApellidosAddAlumno.getText().toString();
        String ciclo = binding.spCiclosAddAlumno.getSelectedItem().toString();
        char grupo = 'v';
        RadioButton rb = findViewById(binding.rgGrupoAddAlumno.getCheckedRadioButtonId());
        grupo = rb.getText().charAt(rb.getText().length()-1);

        if(nombre.isEmpty() || apellidos.isEmpty() || ciclo.isEmpty() || grupo=='v'){
            return null;
        }else{
            return new Alumno(nombre, apellidos, ciclo, grupo);
        }
    }
}