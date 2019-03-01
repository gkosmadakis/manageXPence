package uk.co.irokottaki.moneycontrol;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cousm on 09/10/2016.
 */

public class UploadTask extends AsyncTask {

    private final DbxClientV2 dbxClient;
    private final File file;
    private final Context context;
    private Exception error;

    public UploadTask(DbxClientV2 dbxClient, File file, Context context) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.context = context;
    }


    @TargetApi(19)
    @Override
    protected Object doInBackground(Object[] params) {

        try (InputStream inputStream = new FileInputStream(String.valueOf(file));){
            // Upload to Dropbox
            dbxClient.files().uploadBuilder("/" + file.getName()) //Path in the user's Dropbox to
                    // save the file.
                    .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                    .uploadAndFinish(inputStream);
            Log.d("Upload Status", "Success");
        } catch (DbxException e) {
            Log.e("DbxException",e.getMessage());
            error = e;
        } catch (IOException ex) {
            Log.e("IOException",ex.getMessage());
            error = ex;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (error == null) {
            Toast.makeText(context, "File uploaded successfully", Toast.LENGTH_SHORT).show();
        }
    }
}

