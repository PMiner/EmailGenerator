package tk.pminer.emailgenerator;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by PMiner on 20/7/2017.
 */

public class EmailList {
    public ArrayList emails;
    public String name;
    public static ArrayList<EmailList> getClientsFromFile(String filename, String name, Context context)
    {
        final ArrayList<EmailList> clientList = new ArrayList<>();
        try
        {
            String jsonString = loadJsonFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray clients = json.getJSONArray(name);
            for(int i = 0; i < clients.length(); i++)
            {
                EmailList client = new EmailList();
                client.name = name;
                if(clients.getJSONObject(i).getString("name") == name)
                {
                    JSONArray jsonObject;
                    jsonObject = clients.getJSONObject(i).getJSONArray("emails");

                    ArrayList<String> listdata = new ArrayList<String>();
                    JSONArray jArray = (JSONArray)jsonObject;
                    if (jArray != null) {
                        for (int x=0;i<jArray.length();i++){
                            listdata.add(jArray.getString(i));
                        }
                    }

                    client.emails = listdata;
                }


                clientList.add(client);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return clientList;
    }
    public static ArrayList<String> getArrayFromFile(String filename, String name, Context context) {
        ArrayList<String> listdata = null;
        try {
            String jsonString = loadJsonFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray clients = json.getJSONArray(name);
            for (int i = 0; i < clients.length(); i++) {
                EmailList client = new EmailList();
                client.name = name;
                if (clients.getJSONObject(i).getString("name") == name) {
                    JSONArray jsonObject;
                    jsonObject = clients.getJSONObject(i).getJSONArray("emails");

                    listdata = new ArrayList<String>();
                    JSONArray jArray = (JSONArray) jsonObject;
                    if (jArray != null) {
                        for (int x = 0; i < jArray.length(); i++) {
                            listdata.add(jArray.getString(i));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listdata;
    }
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

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

