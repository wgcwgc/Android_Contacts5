package com.lanchuang.wgc.android_contacts5;

import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.lanchuang.wgc.writefiletosdcard.service.FileOperate;
import com.lanchuang.wgc.writefiletosdcard.util.MyUtil;

public class MainActivity extends Activity
{
	String TAG = "LOG";
	String FileName = "contacts";

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getContactsInformation();

		listView = (ListView) findViewById(R.id.listView1);
		listView.setBottom(TRIM_MEMORY_BACKGROUND);
		
	}

	public void getContactsInformation()
	{
		String id;
		String mimetype;
		String str = "";
		String contentString = "";
		ContentResolver contentResolver = this.getContentResolver();
		// ֻ��Ҫ��Contacts�л�ȡID�������Ķ����Բ�Ҫ��ͨ���鿴���������SQL��䣬���Կ������ڶ�������
		// ���ó�null��Ĭ�Ϸ��ص��зǳ��࣬��һ����Դ�˷ѡ�
		Cursor cursor = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI ,new String []
		{ android.provider.ContactsContract.Contacts._ID } ,null ,null ,null);
		while(cursor.moveToNext())
		{
			id = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));

			// ��һ��Cursor��ȡ���е���Ϣ
			Cursor contactInfoCursor = contentResolver.query(android.provider.ContactsContract.Data.CONTENT_URI ,new String []
			{ android.provider.ContactsContract.Data.CONTACT_ID, android.provider.ContactsContract.Data.MIMETYPE, android.provider.ContactsContract.Data.DATA1 } ,android.provider.ContactsContract.Data.CONTACT_ID + " = " + id ,null ,null);

			while(contactInfoCursor.moveToNext())
			{
				mimetype = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(android.provider.ContactsContract.Data.MIMETYPE));
				String value = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(android.provider.ContactsContract.Data.DATA1));
				if(mimetype.contains("/email"))
				{
					str = "���� = " + value;
					Log.d(TAG ,str);
				}
				else
					if(mimetype.contains("/nickname"))
					{
						str = "�ǳ� = " + value;
						Log.d(TAG ,str);
					}
					else
						if(mimetype.contains("/organization"))
						{
							str = "������λ = " + value;
							Log.d(TAG ,str);
						}
						else
							if(mimetype.contains("/phone"))
							{
								str = "�绰���� = " + value;
								Log.d(TAG ,str);
							}
							else
								if(mimetype.contains("/name"))
								{
									str = "���� = " + value;
									Log.d(TAG ,str);
								}
								else
									if(mimetype.contains("/postal"))
									{
										str = "סַ = " + value;
										Log.d(TAG ,str);
									}
									else
										if(mimetype.contains("/contact"))
										{
											str = "�������� = " + value;
											Log.d(TAG ,str);
										}
										else
											if(mimetype.contains("/note"))
											{
												str = "��ע = " + value;
												Log.d(TAG ,str);
											}
											else
												if(mimetype.contains("/website"))
												{
													str = "��ַ = " + value;
													Log.d(TAG ,str);
												}
												else
													if(mimetype.contains("/relation"))
													{
														str = "������ϵ = " + value;
														Log.d(TAG ,str);
													}

				contentString = contentString + str + "\n";
			}
			str = "\n*********\n\n*********\n";
			contentString = contentString + str + "\n";
			Log.d(TAG ,str);
			contactInfoCursor.close();
		}
		printLog(FileName ,contentString);
		cursor.close();
	}

	public void printLog(String fileName , String contents )
	{
		fileName = fileName + "_wgcwgc.txt";
		if(fileName != null && fileName.length() > 0 && contents.length() > 0 && contents != null)
		{
			FileOperate fileOperate = new FileOperate(MainActivity.this);
			try
			{
				fileOperate.writeToSdcard(fileName ,contents);
				// MyUtil.showMsg(MainActivity.this
				// ,getString(R.string.success));
				MyUtil.showMsg(MainActivity.this ,"�ļ�����ɹ���");
			}
			catch(IOException e)
			{
				// MyUtil.showMsg(MainActivity.this
				// ,getString(R.string.failed));
				MyUtil.showMsg(MainActivity.this ,"�ļ�����ʧ�ܣ�");
			}

		}
		else
		{
			// MyUtil.showMsg(MainActivity.this ,getString(R.string.file_null));
			MyUtil.showMsg(MainActivity.this ,"�ļ�����������Ϊ�գ�");
		}
	}

}
