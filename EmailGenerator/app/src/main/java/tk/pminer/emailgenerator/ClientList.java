package tk.pminer.emailgenerator;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by PMiner on 20/7/2017.
 * EmailGenerator
 */

class ClientList {
    public String name;
    static ArrayList<ClientList> getClientsFromFile(String filename, Context context)
    {
        final ArrayList<ClientList> clientList = new ArrayList<>();
        try
        {
            String jsonString = loadJsonFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray clients = json.getJSONArray("clients");
            for(int i = 0; i < clients.length(); i++){
                ClientList client = new ClientList();
                client.name = clients.getJSONObject(i).getString("name");
                clientList.add(client);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return clientList;
    }
    private static String loadJsonFromAsset(String filename, Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}

