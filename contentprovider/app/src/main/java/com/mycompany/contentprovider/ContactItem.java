package com.mycompany.contentprovider;

import android.graphics.Bitmap;

/**
 * Created by USER on 27/08/2015.
 */
public class ContactItem
{
    private Long mId;
    private Bitmap mContactImage;
    private String mContactName;
    private String mContactPhone;
    private String mContactEmail;

    public ContactItem(Long id, String contactName, String contactPhone, String contactEmail, Bitmap contactImage)
    {
        mId = id;
        mContactName = contactName;
        mContactPhone = contactPhone;
        mContactEmail = contactEmail;
        mContactImage = contactImage;
    }

    public Long getid() {return mId;}
    public String getContactName() {return mContactName;}
    public String getContactPhone() {return mContactPhone;}
    public String getContactEmail() {return mContactEmail;}
    public Bitmap getContactImage() {return mContactImage;}
}
