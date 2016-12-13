package es.schooleando.downloadintent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadIntentService extends IntentService {
    // definimos tipos de mensajes que utilizaremos en ResultReceiver
    public static final int PROGRESS = 0;
    public static final int FINSISHED = 1;
    public static final int ERROR = 2;


    private static final String TAG = "DownloadIntentService";

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Ejemplo de como logear
        Log.d(TAG, "Servicio arrancado!");

        // Aquí deberás descargar el archivo y notificar a la Activity mediante un ResultReceiver que recibirás en el intent.

        // Deberamos obtener el ResultReceiver del intent
        // intent.getParcelableExtra("receiver");

        // Es importante que definas el contenido de los Intent.

        // Por ejemplo:
        //  - que enviarás al IntentService como parámetro inicial (url a descargar)
        //         intent.getgetStringExtra("url")
        //  - que enviarás a ResultReceiver para notificarle incrementos en el porcentaje de descarga (número de 0 a 100)
        //         receiver.send(PROGRESS, Bundle);
        //  - que enviarás a ResultReceiver cuando se haya finalizado la descarga (nombre del archivo temporal)
        //         receiver.send(FINISHED, Bundle);




    }
}
