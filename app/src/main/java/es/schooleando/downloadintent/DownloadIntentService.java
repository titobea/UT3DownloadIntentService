package es.schooleando.downloadintent;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadIntentService extends IntentService {

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Aquí deberás descargar el archivo y notificar a la Activity mediante un ResultReceiver

        // Es importante que definas el contenido de los Intent.

        // Por ejemplo:
        //  - que enviarás al IntentService como parámetro inicial.
        //  - que enviarás a ResultReceiver para notificarle incrementos en el porcentaje de descarga.
        //  - que enviarás a ResultReceiver cuando se haya finalizado la descarga.



    }
}
