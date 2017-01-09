package es.schooleando.downloadintent;

import android.app.IntentService;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb;
    private TextView tv;
    private EditText txtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recoger variables del layout
        pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        tv = (TextView)findViewById(R.id.barPercent);
        txtUrl =(EditText) findViewById(R.id.txtUrl);


        // Añade en el interfaz un botón y un TextView, como mínimo.

        // cuando pulsemos el botón deberemos crear un Intent que contendrá un Bundle con:
        // una clave "url" con la dirección de descarga asociada.
        // una clave "receiver" con un objeto ResultReceiver.
        //
        // El objeto ResultReceiver contendrá el callback que utilizaremos para recibir
        // mensajes del IntentService.

        // después deberás llamar al servicio con el intent.
    }

    public void descargarUrl(View v){
        if (!txtUrl.getText().toString().trim().equals("")){
            pb.setVisibility(View.VISIBLE);
            Intent i = new Intent(this,DownloadIntentService.class);
            i.putExtra("url",txtUrl.getText().toString());
            ResultReceiver receiver = new ResultReceiver(new Handler()) {
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    //procesar resultados en UI Thread
                    switch (resultCode) {
                        case DownloadIntentService.PROGRESS:
                            int progreso = resultData.getInt("progreso");
                            pb.setIndeterminate(progreso < 0);
                            if (progreso > 0) tv.setText("" + progreso + "%");
                            pb.setProgress(progreso);
                            break;
                        case DownloadIntentService.FINISHED:
                            String ruta = resultData.getString("ruta");
                            pb.setVisibility(View.INVISIBLE);
                            tv.setText("");
                            File f =new File(ruta);
                            if (f.exists()) {

                                MimeTypeMap mime = MimeTypeMap.getSingleton();
                                //String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                                String ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(f).toString());
                                String type = mime.getMimeTypeFromExtension(ext);
                                try {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "com.your.package.fileProvider", f);
                                        intent.setDataAndType(contentUri, type);
                                    } else {
                                        intent.setDataAndType(Uri.fromFile(f), type);
                                    }
                                    startActivityForResult(intent,0);
                                } catch (ActivityNotFoundException anfe) {
                                    Toast.makeText(MainActivity.this, "No activity found to open this attachment.", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this,"No existe el fichero descargado en " + ruta,Toast.LENGTH_SHORT).show();
                            }



                                /*Intent i = new Intent(Intent.ACTION_VIEW, Uri.fromFile(f));
                                i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(i);*/

                            break;
                        case DownloadIntentService.ERROR:
                            String mensaje = resultData.getString("mensaje");
                            pb.setVisibility(View.INVISIBLE);
                            tv.setText("");
                            Toast.makeText(MainActivity.this,mensaje,Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
            i.putExtra("receiver",receiver);
            startService(i);
        }else {
            Toast.makeText(this,"Escriba una Url",Toast.LENGTH_SHORT).show();
        }
    }
}
