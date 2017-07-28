package tk.pminer.emailgenerator;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Created by PMiner on 7/26/2017.
 * EmailGenerator
 */

class ClientJSONEdit
{
    private static String json;
    private static JSONObject jsonObject;

    static void addJSONObjToFile(String add, Context context)
    {

        try
        {
            InputStream is = context.openFileInput("clients.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            jsonObject = new JSONObject(json);
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("name", add);
            jsonObject.getJSONArray("clients").put(tmpJson);
            jsonObject.put(add, new JSONArray());
        }
        catch (JSONException | NullPointerException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("clients.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();
        } catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
        }
    }
    static void removeJSONObjFromFile(String name, int Int, Context context)
    {

        try
        {
            InputStream is = context.openFileInput("clients.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            jsonObject.getJSONArray("clients").remove(Int);
            jsonObject.remove(name);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("clients.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
