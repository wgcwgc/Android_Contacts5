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
		// 只需要从Contacts中获取ID，其他的都可以不要，通过查看上面编译后的SQL语句，可以看出将第二个参数
		// 设置成null，默认返回的列非常多，是一种资源浪费。
		Cursor cursor = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI ,new String []
		{ android.provider.ContactsContract.Contacts._ID } ,null ,null ,null);
		while(cursor.moveToNext())
		{
			id = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));

			// 从一个Cursor获取所有的信息
			Cursor contactInfoCursor = contentResolver.query(android.provider.ContactsContract.Data.CONTENT_URI ,new String []
			{ android.provider.ContactsContract.Data.CONTACT_ID, android.provider.ContactsContract.Data.MIMETYPE, android.provider.ContactsContract.Data.DATA1 } ,android.provider.ContactsContract.Data.CONTACT_ID + " = " + id ,null ,null);

			while(contactInfoCursor.moveToNext())
			{
				mimetype = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(android.provider.ContactsContract.Data.MIMETYPE));
				String value = contactInfoCursor.getString(contactInfoCursor.getColumnIndex(android.provider.ContactsContract.Data.DATA1));
				if(mimetype.contains("/email"))
				{
					str = "邮箱 = " + value;
					Log.d(TAG ,str);
				}
				else
					if(mimetype.contains("/nickname"))
					{
						str = "昵称 = " + value;
						Log.d(TAG ,str);
					}
					else
						if(mimetype.contains("/organization"))
						{
							str = "工作单位 = " + value;
							Log.d(TAG ,str);
						}
						else
							if(mimetype.contains("/phone"))
							{
								str = "电话号码 = " + value;
								Log.d(TAG ,str);
							}
							else
								if(mimetype.contains("/name"))
								{
									str = "姓名 = " + value;
									Log.d(TAG ,str);
								}
								else
									if(mimetype.contains("/postal"))
									{
										str = "住址 = " + value;
										Log.d(TAG ,str);
									}
									else
										if(mimetype.contains("/contact"))
										{
											str = "出生日期 = " + value;
											Log.d(TAG ,str);
										}
										else
											if(mimetype.contains("/note"))
											{
												str = "备注 = " + value;
												Log.d(TAG ,str);
											}
											else
												if(mimetype.contains("/website"))
												{
													str = "网址 = " + value;
													Log.d(TAG ,str);
												}
												else
													if(mimetype.contains("/relation"))
													{
														str = "亲属关系 = " + value;
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
				MyUtil.showMsg(MainActivity.this ,"文件保存成功！");
			}
			catch(IOException e)
			{
				// MyUtil.showMsg(MainActivity.this
				// ,getString(R.string.failed));
				MyUtil.showMsg(MainActivity.this ,"文件保存失败！");
			}

		}
		else
		{
			// MyUtil.showMsg(MainActivity.this ,getString(R.string.file_null));
			MyUtil.showMsg(MainActivity.this ,"文件名或者内容为空！");
		}
	}

}
