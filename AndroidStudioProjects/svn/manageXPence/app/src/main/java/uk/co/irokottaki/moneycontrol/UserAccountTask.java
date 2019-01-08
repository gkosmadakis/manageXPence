package uk.co.irokottaki.moneycontrol;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserAccountTask extends AsyncTask<Void, Void, FullAccount> {

    private final DbxClientV2 dbxClient;
    private final TaskDelegate delegate;
    private Exception error;

    public interface TaskDelegate {
        void onAccountReceived(FullAccount account);

        void onError();
    }

    public UserAccountTask(DbxClientV2 dbxClient, TaskDelegate delegate) {
        this.dbxClient = dbxClient;
        this.delegate = delegate;
    }

    @Override
    protected FullAccount doInBackground(Void... params) {
        try {
            //get the users FullAccount
            return dbxClient.users().getCurrentAccount();
        } catch (DbxException e) {
            Log.e("DbxException", e.getMessage());
            error = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(FullAccount account) {
        super.onPostExecute(account);

        if (account != null && error == null) {
            //User Account received successfully
            delegate.onAccountReceived(account);
        } else {
            // Something went wrong
            delegate.onError();
        }
    }

}
