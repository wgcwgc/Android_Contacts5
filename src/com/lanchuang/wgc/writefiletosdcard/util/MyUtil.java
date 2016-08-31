package com.lanchuang.wgc.writefiletosdcard.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 工具类
 * 
 */
public class MyUtil
{
	/**
	 * 使用Toast显示提示信息
	 * 
	 * @param context
	 * @param text
	 */
	public static void showMsg(Context context , String text )
	{
		Toast.makeText(context ,text ,Toast.LENGTH_SHORT).show();
	}
}
