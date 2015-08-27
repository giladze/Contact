package com.mycompany.contentprovider;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        ListView listView = (ListView)findViewById(R.id.contact_list);

        List<ContactItem> contactItemList = getContactsList();
        final ContactRowAdapter rowAdapter = new ContactRowAdapter(this, contactItemList);
        listView.setAdapter(rowAdapter);
    }

    private List<ContactItem> getContactsList()
    {
        List<ContactItem> contactItemList = new ArrayList<>();
        String name = "", phone = "", nEmail = "", mimeType, accountName;
        Bitmap photo = null;
        long newId, oldId = -1;
        Cursor cursor = getContacts();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext())
            {
                newId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                if(newId == oldId)
                {
                    accountName = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME));
                    if (accountName.equals("WhatsApp"))
                    {
                        continue;
                    }
                    switch (mimeType.toString())
                    {
                        case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:

                            if(phone.equals(""))
                            {
                                phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            else
                            {
                                phone += " " + cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            break;

                        case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                            nEmail += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                            break;

                        case ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE:
                            byte[] photoData = cursor.getBlob(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
                            if (photoData != null)
                            {
                                photo = BitmapFactory.decodeStream(new ByteArrayInputStream(photoData));
                            }
                            break;
                    }
                }
                else
                {
                    if(oldId != -1)
                    {
                        if(! name.equals(nEmail))
                        {
                            ContactItem contactItem= new ContactItem(oldId, name, phone, nEmail, photo);
                            contactItemList.add(contactItem);
                        }
                    }
                    oldId = newId;
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_PRIMARY));

                    switch (mimeType.toString())
                    {
                        case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            nEmail = new String();
                            photo = null;
                            break;

                        case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                            nEmail = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                            phone = new String();
                            photo = null;
                            break;

                        case ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE:
                            byte[] photoData = cursor.getBlob(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
                            if (photoData != null)
                            {
                                photo = BitmapFactory.decodeStream(new ByteArrayInputStream(photoData));
                            }
                            else
                            {
                                photo = null;
                            }
                            phone = new String();
                            nEmail = new String();
                            break;
                    }
                }
            }
            cursor.close();
        }
        return contactItemList;
    }

    private Cursor getContacts()
    {
        // run query
        String[] selection ={ContactsContract.Data.CONTACT_ID, ContactsContract.Data.MIMETYPE, ContactsContract.RawContacts.ACCOUNT_NAME,
                ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Photo.PHOTO};
        Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                selection, ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=? OR "
                + ContactsContract.Data.MIMETYPE + "=?" , new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE},
                ContactsContract.Data.CONTACT_ID + " ASC");
        return  cursor;
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
