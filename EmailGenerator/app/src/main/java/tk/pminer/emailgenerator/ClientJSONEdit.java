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
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by PMiner on 7/26/2017.
 * EmailGenerator
 */

public class ClientJSONEdit
{
    public static String json;
    private static JSONObject jsonObject;

    public static void addJSONObjToFile(String filename, String add, Context context)
    {

        try
        {
            InputStream is = context.openFileInput(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
            jsonObject = new JSONObject(json);
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("name", add);
            jsonObject.getJSONArray("clients").put(tmpJson);
            jsonObject.put(add, new JSONArray());
            Log.e("ClientJSONEdit", jsonObject.toString());
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        catch (NullPointerException ex)
        {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("clients");

            OutputStreamWriter outputStreamWriter = null;
            try {
                outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                outputStreamWriter.write(jsonArray.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException ex)
        {
            Log.e("occurring", "Nullointeererer");
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("clients");

            OutputStreamWriter outputStreamWriter = null;
            try {
                outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                outputStreamWriter.write(jsonArray.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeJSONObjFromFile(String filename, String[] name, int[] Int,  Context context)
    {

        try
        {
            InputStream is = context.openFileInput(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
            jsonObject = new JSONObject(json);
            for(int i = 0; i < Int.length; i++)
            {
                jsonObject.getJSONArray("clients").remove(Int[i]);
            }
            for(int i = 0; i < name.length; i++)
            {
                jsonObject.remove(name[i]);
            }
            Log.e("ClientJSONEdit", jsonObject.toString());
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
