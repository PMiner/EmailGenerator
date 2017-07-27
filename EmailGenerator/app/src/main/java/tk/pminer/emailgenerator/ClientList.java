package tk.pminer.emailgenerator;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        catch (NullPointerException e)
        {
            e.printStackTrace();
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.close();
                JSONArray jsonArray = new JSONArray();
                jsonArray.put("clients");
                JSONObject object = new JSONObject();
                object.put("clients", (Object) jsonArray);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));

                outputStreamWriter.write(jsonArray.toString());
                outputStreamWriter.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return clientList;
    }
    private static String loadJsonFromAsset(String filename, Context context) {
        String json;
        try {
            InputStream is = context.openFileInput(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}

