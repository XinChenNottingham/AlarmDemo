package nottingham.xc.alarmdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    AlarmManager alarm_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarm_manager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public void clickButtonOn(View view){

        // initialise time picker & calendar
        TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
        Calendar calendar = Calendar.getInstance();

        if (Build.VERSION.SDK_INT>=23) {
                    calendar.set(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.getHour(), timePicker.getMinute(), 0);
        }else {
                    calendar.set(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
        }
        setAlarm(calendar.getTimeInMillis());
        Log.d("MDP","alarm set");
    }

    private void setAlarm(long timeInMillis) {

        Intent intent= new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, 0, intent,0);
        alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }

    public void clickButtonOff(View view) {
        Intent intent= new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, 0, intent,0);
        alarm_manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm is cancelled", Toast.LENGTH_SHORT).show();
    }
}
