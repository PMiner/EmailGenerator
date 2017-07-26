package tk.pminer.emailgenerator;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by PMiner on 7/26/2017.
 * EmailGenerator
 */

public class EmailJSON
{
    public void addJSONObjToFile(String filename, String item, String add, Context context)
    {
        String json;
        try
        {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
