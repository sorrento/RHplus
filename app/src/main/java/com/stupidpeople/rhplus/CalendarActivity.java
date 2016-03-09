package com.stupidpeople.rhplus;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static java.lang.System.currentTimeMillis;

public class CalendarActivity extends AppCompatActivity {

    private Context mContext;

    public static long pushAppointmentsToCalender(Activity curActivity, String title, String addInfo, String place, int status, long startDate, boolean needReminder, boolean needMailService) {
        /***************** Event: note(without alert) *******************/

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);

        long endDate = startDate + 1000 * 60 * 60; // For next 1hr

        eventValues.put("dtstart", startDate);
        eventValues.put("dtend", endDate);

        // values.put("allDay", 1); //If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):
        eventValues.put("eventTimezone", "UTC/GMT +2:00");
   /*Comment below visibility and transparency  column to avoid java.lang.IllegalArgumentException column visibility is invalid error */

    /*eventValues.put("visibility", 3); // visibility to default (0),
                                        // confidential (1), private
                                        // (2), or public (3):
    eventValues.put("transparency", 0); // You can control whether
                                        // an event consumes time
                                        // opaque (0) or transparent
                                        // (1).
      */
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

        Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        if (needReminder) {
            /***************** Event: Reminder(with alert) Adding reminder to event *******************/

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 5); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }

        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

        if (needMailService) {
            String attendeuesesUriString = "content://com.android.calendar/attendees";

            /********
             * To add multiple attendees need to insert ContentValues multiple
             * times
             ***********/
            ContentValues attendeesValues = new ContentValues();

            attendeesValues.put("event_id", eventID);
            attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
            attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee
            // E
            // mail
            // id
            attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
            // Relationship_None(0),
            // Organizer(2),
            // Performer(3),
            // Speaker(4)
            attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
            // Required(2), Resource(3)
            attendeesValues.put("attendeeStatus", 0); // NOne(0), Accepted(1),
            // Decline(2),
            // Invited(3),
            // Tentative(4)

            Uri attendeuesesUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(attendeuesesUriString), attendeesValues);
        }

        return eventID;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mContext = this;
    }

    public void CreateCal(View view) {
        //TODO create calendar "RHplus"
    }

    public void AddEvents(View view) {
        //TODO add two events to the calendar "RHplus
        long delta = 90 * 60 * 1000; //90 mins
        long delay1 = 4 * 24 * 60 * 60 * 1000;//dentro de 4 dias
        CalItem item1 = new CalItem("Hablar con RH", currentTimeMillis() + delay1, currentTimeMillis() + delay1 + delta, "para que te cuente de qué va la cosa", "Oficina !A");
//        mContext.startActivity(item1.getIntent());

        pushAppointmentsToCalender(this, item1);

    }

    private long pushAppointmentsToCalender(Activity calendarActivity, CalItem item1) {
        return pushAppointmentsToCalender(calendarActivity, item1.title, item1.description, item1.location, 1, item1.startInMilli, true, true);
    }

}

class CalItem {
    public String title;

    public long startInMilli;
    public long endInMilli;
    public String description;
    public String location;

    public CalItem(String title, long startInMilli, long endInMilli, String description, String location) {
        this.title = title;
        this.startInMilli = startInMilli;
        this.endInMilli = endInMilli;
        this.description = description;
        this.location = location;
    }

    public Intent getIntent() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.Events.TITLE, title);
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location); //TODO ver como poner un reaminder
//                    intent.putExtra(CalendarContract.Reminders., scItem.location);
//                    intent.putExtra(CalendarContract.Events.HAS_ALARM, 4);//Alert

        intent.putExtra(CalendarContract.Events.DTSTART, startInMilli);//Complete
        intent.putExtra(CalendarContract.Events.DTEND, endInMilli);//Complete

//        intent.putExtra("beginTime", cal.getTimeInMillis() + 60 * 60 * 1000);
//                    intent.putExtra("beginTime", cal.getTimeInMillis() + 60 * 60 * 1000);
//                    intent.putExtra("allDay", false);
//                    intent.putExtra("rrule", "FREQ=YEARLY");
//                    intent.putExtra("endTime", cal.getTimeInMillis() + 2 * 60 * 60 * 1000);

        return intent;
    }
}