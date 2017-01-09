package es.schooleando.downloadintent;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadIntentService extends IntentService {
    // definimos tipos de mensajes que utilizaremos en ResultReceiver
    public static final int PROGRESS = 0;
    public static final int FINISHED = 1;
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
        // Deberemos obtener el ResultReceiver del intent
        ResultReceiver receiver= intent.getParcelableExtra("receiver");
        String ruta = intent.getStringExtra("url");
        // Es importante que definas el contenido de los Intent.
        //  - que enviarás al IntentService como parámetro inicial (url a descargar)
        //String url= intent.getStringExtra("url");
        //intent.putExtra("aaa", "aaa");
        //Bundle parameters = intent.getExtras();
        Bundle b = new Bundle();
        //b.putInt("progreso",3);
        //  - que enviarás a ResultReceiver para notificarle incrementos en el porcentaje de descarga (número de 0 a 100)
        //receiver.send(PROGRESS, b);
        //  - que enviarás a ResultReceiver cuando se haya finalizado la descarga (nombre del archivo temporal)
        //receiver.send(FINISHED, b);
        //receiver.send(ERROR, b);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            URL url= null;
            try {
                //ruta="http://tresbits.es/miwordpress/wp-content/uploads/2015/06/tresbits2.png";
                url = new URL(ruta);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("HEAD");
                con.connect();

                int size = con.getContentLength();

                InputStream in = url.openStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                byte[] by = new byte[1024];

                for (int i; (i = in.read(by)) != -1; ) {

                    out.write(by, 0, i);
                    if (size>0) {
                        b.putInt("progreso",out.size() * 100 / size);
                    }else{
                        b.putInt("progreso",i*-1);
                    }
                    receiver.send(PROGRESS, b);
                }


                String[] data= ruta.split("/");
                String[] f= data[data.length-1].split("\\.");

                if (f.length<2) f= new String[]{"unknown","jpg"};

                File outputDir = getExternalCacheDir();//getCacheDir();

                File outputFile = File.createTempFile(f[0],"."+ f[1], outputDir);
                outputFile.deleteOnExit();

                FileOutputStream fos = new FileOutputStream(outputFile);
                fos.write(out.toByteArray());

                b.putString("ruta",outputFile.getPath());
                receiver.send(FINISHED, b);

                out.close();
                in.close();


            } catch (Exception e) {
                b.putString("mensaje","URL incorrecta "+e.getMessage());
                receiver.send(ERROR, b);
            }

        } else {
            b.putString("mensaje","No hay conexión");
            receiver.send(ERROR, b);
        }


    }
}
