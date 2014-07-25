package com.example.aesdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.AESEncryptor;

public class MainActivity extends Activity {

	private EditText src;
	private TextView des1;
	private TextView des2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		src = (EditText) findViewById(R.id.et_src);
		des1 = (TextView) findViewById(R.id.tv_des1);
		des2 = (TextView) findViewById(R.id.tv_des2);
	}

	/**
	 * 解密
	 * 
	 * @param view
	 */
	public void decryption(View view) {
		Toast.makeText(getApplicationContext(), "开始解密", Toast.LENGTH_SHORT).show();
		String text = des1.getText().toString().trim();
		String decrypt = null;
		try {
			decrypt = AESEncryptor.decrypt("liujigang", text);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "解密发生异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		des2.setText(decrypt);
	}

	/**
	 * 加密
	 * 
	 * @param view
	 */
	public void encryption(View view) {
		Toast.makeText(getApplicationContext(), "开始加密", Toast.LENGTH_SHORT).show();
		String text = src.getText().toString().trim();
		String encrypt = null;
		
		try {
			encrypt = AESEncryptor.encrypt("liujigang", text);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "加密出现异常", Toast.LENGTH_SHORT).show();
		}
		
		des1.setText(encrypt);
	}
}
