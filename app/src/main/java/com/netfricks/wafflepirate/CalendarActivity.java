package com.netfricks.wafflepirate;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

// TODO: Query Calendar(s) for events marked as busy
// TODO: Turn on DND when an event is marked as busy

public class CalendarActivity extends AppCompatActivity {

    Switch calendarActivitySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Read preference file to get switch status.
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Read file
        final int defaultValue = getResources().getInteger(R.integer.saved_calendar_switch_state_default);
        long switchState = sharedPref.getInt(getString(R.string.saved_calendar_switch_state), defaultValue);

        // If the switch is off, only check for permission when the switch is turned on.
        calendarActivitySwitch = (Switch) findViewById(R.id.switchActivityCalendar);

        if (switchState == 1) {
            getPermissionToReadUserCalendar(Manifest.permission.READ_CALENDAR,1);
            calendarActivitySwitch.setChecked(true);
            // TODO: Show UI for Calendar DND Rules

        }

        calendarActivitySwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (calendarActivitySwitch.isChecked()){
                    // Check for Calendar Permission
                    getPermissionToReadUserCalendar(Manifest.permission.READ_CALENDAR,1);

                    // Write to file
                    setCalendarActivitySwitchState(1);
                    // TODO: Show UI for Calendar DND Rules

                } else {
                    // TODO: Hide UI for Calendar DND Rules

                    // Write to file
                    setCalendarActivitySwitchState(0);

                }
            }
        });
    }

    private static final int READ_CALENDAR_PERMISSION_REQUEST = 1;

    public void getPermissionToReadUserCalendar(final String permissionManifest, final int permissionRequest) {
        if (ContextCompat.checkSelfPermission(this, permissionManifest) != PackageManager.PERMISSION_GRANTED) {

            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.

            if (shouldShowRequestPermissionRationale(permissionManifest)){

                // Show UI to explain to the user why we need the calendar permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message_READ_CALENDAR)
                        .setTitle(R.string.dialog_title_READ_CALENDAR);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        requestPermissions(new String[]{permissionManifest},
                                permissionRequest);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          @NonNull String permissions[],
                                          @NonNull int[] grantResults){
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Make sure it's our original READ_CALENDAR request
        if (requestCode == READ_CALENDAR_PERMISSION_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Calendar permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // showRational = false if user clicks Never Ask Again, otherwise true
                boolean showRational = shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR);

                if (showRational) {
                    Toast.makeText(this, "Read Calendar permission denied", Toast.LENGTH_SHORT).show();

                    // turn switch off.
                    calendarActivitySwitch.setChecked(false);

                    // Save switch status to off in preference file.
                    setCalendarActivitySwitchState(0);
                } else {
                    Toast.makeText(this, "To enable this feature, go to Settings to allow Calender permission", Toast.LENGTH_LONG).show();

                    // turn switch off.
                    calendarActivitySwitch.setChecked(false);

                    // Save switch status to off in preference file.
                    setCalendarActivitySwitchState(0);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setCalendarActivitySwitchState(int newState) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        // Save switch status in preference file.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_calendar_switch_state), newState);
        editor.commit();
    }


/*
    // TODO: Query list of calendars

    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            Calendars._ID,                   // 0
            Calendars.ACCOUNT_NAME,          // 1
            Calendars.CALENDAR_DISPLAY_NAME, // 2
            Calendars.OWNER_ACCOUNT          // 3
    };

    // The indicies for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    // Run query
//    Cursor cur = null;
    ContentResolver cr = getContentResolver();
    Uri uri = Calendars.CONTENT_URI;
    String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                            + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                            + Calendars.OWNER_ACCOUNT + " = ?))";
    String[] selectionArgs = new String[] {"","",""};

    // Submit the query and get a Cursor object back.
    Cursor cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

    // Use the cursor to step through the returned records
    // TODO: why does "moveToNext()" not resolve?

    while (cur.moveToNext()) {
        long calID = 0;
        String displayName = null;
        String accountName = null;
        String ownerName = null;

        // Get the field values
        calID = cur.getLong(PROJECTION_ID_INDEX);
        displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
        accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
        ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

        // Do something with the values...
    }
    */

    // TODO: Get _ID and ACCOUNT_NAME for each Calendar
    // TODO: Display all ACCOUNT_NAMEs in a dropdown list or checkbox list
    // TODO: Get all events marked busy from selected Calendar(s) within a date range, how often to update?
    // TODO: Create background intents to turn on DND during busy events



}
