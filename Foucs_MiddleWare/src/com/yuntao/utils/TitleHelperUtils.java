package com.yuntao.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yuntao.R;

/**
 * 
 * @author jomeslu
 * 
 */
public class TitleHelperUtils {

	private TextView leftView;
	private TextView centerView;
	private TextView rightView;
	public final int TEXT_LEFT_ID = R.id.title_left;
	public final int TEXT_RIGHT_ID = R.id.title_right;
	public final int TEXT_CENTER_ID = R.id.title_center;
	private Context mContext;


	public TitleHelperUtils(View view) {

		leftView = (TextView) view.findViewById(TEXT_LEFT_ID);
		centerView = (TextView) view.findViewById(TEXT_CENTER_ID);
		rightView = (TextView) view.findViewById(TEXT_RIGHT_ID);
	}

	public TitleHelperUtils( View view, Context mContext ) {

		leftView = ( TextView )view.findViewById( TEXT_LEFT_ID );
		centerView = ( TextView )view.findViewById( TEXT_CENTER_ID );
		rightView = ( TextView )view.findViewById( TEXT_RIGHT_ID );
		this.mContext = mContext;
	}
	public TitleHelperUtils(Activity context) {

		leftView = (TextView) context.findViewById(TEXT_LEFT_ID);
		centerView = (TextView) context.findViewById(TEXT_CENTER_ID);
		rightView = (TextView) context.findViewById(TEXT_RIGHT_ID);
	}

	/** left view setup **/
	public void setLeftText(String str) {

		if (leftView != null) {
			leftView.setText(str == null ? "" : str);
		}
	}

	public void setLeftVisable(int visable) {

		if (leftView != null) {
			leftView.setVisibility(visable);
		}
	}

	public void setLeftOnClickLisenter(OnClickListener l) {

		if (leftView != null) {
			leftView.setOnClickListener(l);
		}
	}

	/** center view setup **/
	public void setCenterText(String str) {

		if (centerView != null) {
			centerView.setText(str == null ? "" : str);
		}
	}

	public void setCenterOnClickLisenter(OnClickListener l) {

		if (centerView != null) {
			centerView.setOnClickListener(l);
		}
	}

	/** right view setup **/
	public void setRightText(String str) {

		if (rightView != null) {
			rightView.setText(str == null ? "" : str);
		}
	}

	public void setRightOnClickLisenter(OnClickListener l) {

		if (rightView != null) {
			rightView.setOnClickListener(l);
		}
	}

	public void setRightVisibility(int visable) {

		if (rightView != null) {
			rightView.setVisibility(visable);
		}
	}

}
