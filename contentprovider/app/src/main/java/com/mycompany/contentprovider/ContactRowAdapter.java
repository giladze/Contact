package com.mycompany.contentprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by USER on 27/08/2015.
 */
public class ContactRowAdapter extends BaseAdapter
{
    private LayoutInflater mLayoutInflater;
    private List<ContactItem> mContactItemList;

    public ContactRowAdapter(Context context, List<ContactItem> contactEventsList)
    {
        mLayoutInflater = LayoutInflater.from(context);
        mContactItemList = contactEventsList;
    }

    @Override
    public int getCount() {return mContactItemList.size();}

    @Override
    public Object getItem(int position) {return mContactItemList.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ContactItem contact = mContactItemList.get(position);
        convertView = mLayoutInflater.inflate(R.layout.list_contact, parent, false);

        ImageView eventImage = (ImageView)convertView.findViewById(R.id.image_contact);
        TextView tvName = (TextView)convertView.findViewById(R.id.tv_contact_name);
        TextView  tvPhone = (TextView)convertView.findViewById(R.id.tv_phone);
        TextView  tvEmail = (TextView)convertView.findViewById(R.id.tv_email);

        tvName.setText(contact.getContactName());
        tvPhone.setText(contact.getContactPhone());
        tvEmail.setText(contact.getContactEmail());
        if(contact.getContactImage() == null)
        {
            eventImage.setImageResource(R.mipmap.ic_contact);
        }
        else
        {
            eventImage.setImageBitmap(contact.getContactImage());
        }
        return convertView;
    }
}
