package com.netfricks.wafflepirate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        // TODO: Run getPermissionToReadUserCalendar() when calender settings switch is turned on.
        // TODO: Check for calendar permission when page is loaded and if the switch is turned on already.
        getPermissionToReadUserCalendar();
    }

    // what is 'final'?
    private static final int READ_CALENDAR_PERMISSION_REQUEST = 1;

    public void getPermissionToReadUserCalendar() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmellow
        // 2) Always check for permissions (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)){
                // TODO: Show own UI to explain to the user why we need the calendar permission
                // Show our own UI to explain to the user why we need to read the calendar
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standar permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},
                    READ_CALENDAR_PERMISSION_REQUEST);
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          @NonNull String permissions[],
                                          @NonNull int[] grantResults){
        // Make sure it's our original READ_CALENDAR request
        if (requestCode == READ_CALENDAR_PERMISSION_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Calendar permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // showRational = false if user clicks Never Ask Again, otherwise true
                boolean showRational = shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR);

                if (showRational) {
                    Toast.makeText(this, "do something here to handle degraded mode?", Toast.LENGTH_SHORT).show();
                    // do something here to handle degraded mode?
                } else {
                    Toast.makeText(this, "Read Calendar permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
