package tk.pminer.emailgenerator;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.text.format.DateFormat.is24HourFormat;

/**
 * Created by PMiner on 7/29/2017.
 * EmailGenerator
 */

public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {
    public static String time;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        int newHour = hourOfDay;
        if(hourOfDay > 12){
             newHour = newHour -12;
        }
        time = Integer.toString(newHour) + ":" + Integer.toString(minute);
        Log.e("time", time);
    }
}
