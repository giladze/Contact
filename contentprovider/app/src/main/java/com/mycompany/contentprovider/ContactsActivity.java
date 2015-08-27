package com.mycompany.contentprovider;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        TextView contactView = (TextView) findViewById(R.id.contactview);
        String nEmail, phone;
        long newId, oldId = -1;
        contactView.setMovementMethod(new ScrollingMovementMethod());
        Cursor cursor = getContacts();
        if(cursor.getCount() > 0)
        while (cursor.moveToNext()){
            newId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_PRIMARY));
            String MimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
            switch (MimeType.toString())
            {
                case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                    nEmail = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                    contactView.append("Name: ");
                    contactView.append(displayName);
                    contactView.append(" ");
                    contactView.append(nEmail);
                    contactView.append("\n");
                    break;

                case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                    phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactView.append("Name: ");
                    contactView.append(displayName);
                    contactView.append(" ");
                    contactView.append(phone);
                    contactView.append("\n");
                    break;

                case ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE:
                    contactView.append("Name: ");
                    contactView.append(displayName);
                    contactView.append(" ");
                    contactView.append("Photo");
                    contactView.append("\n");
                    break;
            }

        }
    }

    private Cursor getContacts()
    {
        // run query
        String[] selection ={ContactsContract.Contacts._ID, ContactsContract.Data.MIMETYPE, ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.Contacts.Photo.PHOTO};
        Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                selection, ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=? OR "
                + ContactsContract.Data.MIMETYPE + "=?" , new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE},
                ContactsContract.Data.CONTACT_ID + " ASC");
        return  cursor;
        // SELECT sort_key, photo_uri, status_updates.status_label AS status_label, status_updates.status_ts AS status_ts,
        // status_updates.status_res_package AS status_res_package, name_verified, display_name,
        // data_usage_stat.last_time_used AS last_time_used, mimetype, phonebook_label_alt, data6, version, photo_id, data3,
        // custom_ringtone, times_contacted, account_type_and_data_set, dirty, data7, data15, raw_contact_is_user_profile,
        // data_set, phonebook_label, data10, res_package, account_type, data11, display_name_alt, lookup, phonetic_name,
        // last_time_contacted, contact_last_updated_timestamp, data13, in_visible_group,
        // presence.chat_capability AS chat_capability, data9, data_sync1, sort_key_alt, agg_presence.mode AS contact_presence,
        // data_version, phonetic_name_style, name_raw_contact_id, raw_contact_id, send_to_voicemail, data4, data12,
        // contacts_status_updates.status AS contact_status, contacts_status_updates.status_label AS contact_status_label,
        // pinned, status_updates.status_icon AS status_icon, status_updates.status AS status, data1, phonebook_bucket,
        // data_sync2, contacts_status_updates.status_res_package AS contact_status_res_package, in_default_directory, _id,
        // is_super_primary, data5, contact_id, data8, is_primary, data_sync4, has_phone_number, display_name_source,
        // photo_file_id, data_sync3, data14, contacts_status_updates.status_ts AS contact_status_ts, phonebook_bucket_alt,
        // presence.mode AS mode, data2, group_sourceid, starred, photo_thumb_uri, data_usage_stat.times_used AS times_used,
        // contacts_status_updates.status_icon AS contact_status_icon, agg_presence.chat_capability AS contact_chat_capability,
        // account_name, sourceid FROM view_data data LEFT OUTER JOIN agg_presence ON
        // (contact_id = agg_presence.presence_contact_id) LEFT OUTER JOIN status_updates contacts_status_updates ON
        // (status_update_id=contacts_status_updates.status_update_data_id) LEFT OUTER JOIN presence ON
        // (presence_data_id=data._id) LEFT OUTER JOIN status_updates ON (status_updates.status_update_data_id=data._id)
        // LEFT OUTER JOIN (SELECT data_usage_stat.data_id as STAT_DATA_ID, SUM(data_usage_stat.times_used) as times_used,
        // MAX(data_usage_stat.last_time_used) as last_time_used FROM data_usage_stat GROUP BY data_usage_stat.data_id)
        // as data_usage_stat ON (STAT_DATA_ID=data._id) WHERE (1) AN
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
