package uk.co.irokottaki.moneycontrol;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

/**
 * Created by cousm on 09/10/2016.
 */

public class DropboxClient {

    private DropboxClient(){}

    public static DbxClientV2 getClient(String accessToken) {
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/sample-app", "en_US");
        return new DbxClientV2(config, accessToken);
    }

}
