package tk.pminer.emailgenerator;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by PMiner on 20/7/2017.
 * EmailGenerator
 */

class ClientList {
    public String name;
    static ArrayList<ClientList> getClientsFromFile(Context context, InputStream is)
    {
        final ArrayList<ClientList> clientList = new ArrayList<>();
        try
        {
            String jsonString = loadJsonFromFile(context);
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
        catch (NullPointerException e)
        {
            try {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("clients.json", Context.MODE_PRIVATE));
                outputStreamWriter.write(json);
                outputStreamWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return clientList;
    }
    private static String loadJsonFromFile(Context context) {
        String json;
        try {
            InputStream is = context.openFileInput("clients.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex)
        {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}

