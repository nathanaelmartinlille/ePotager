package com.example.potago;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

class Utils {

	  public static void showYesNoPrompt(Context _context, String title, String message, OnClickListener onYesListener, OnClickListener onNoListener) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
	    builder.setTitle(title);
	    builder.setIcon(android.R.drawable.ic_dialog_info); // lame icon
	    builder.setMessage(message);
	    builder.setCancelable(false);
	    builder.setPositiveButton("Yes", onYesListener);
	    builder.setNegativeButton("No", onNoListener);
	    builder.show();
	  }
	  
}
