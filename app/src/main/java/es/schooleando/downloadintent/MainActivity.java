package es.schooleando.downloadintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Añade en el interfaz un botón y un TextView, como mínimo.

        // cuando pulsemos el botón deberemos crear un Intent que contendrá un Bundle con:
        // una clave "url" con la dirección de descarga asociada.
        // una clave "receiver" con un objeto ResultReceiver.
        //
        // El objeto ResultReceiver contendrá el callback que utilizaremos para recibir
        // mensajes del IntentService.

        // después deberás llamar al servicio con el intent.
    }
}
