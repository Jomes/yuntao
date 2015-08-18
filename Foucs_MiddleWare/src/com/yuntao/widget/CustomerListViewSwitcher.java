package com.yuntao.widget;

import android.widget.RelativeLayout;

/**
 * 
 * @author jomeslu
 * 
 */
public class CustomerListViewSwitcher  {

//	private LayoutParams mLayoutParams;
//	private View mLoddingView;
//	private View mFailedView;
//	private View mNoDataView;
//	private MyListView mSuccessView;
//
//	private ImageView mFailedImage;
//	private TextView mFailedTextTile;
//	private View mNoMoreDataFooter;
//	private OnGetScoreClickListener mListener;
//
//	private Context mContext;
//
//
//	public void setmListener( OnGetScoreClickListener mListener ) {
//
//		this.mListener = mListener;
//	}
//
//
//	public CustomerListViewSwitcher( Context context ) {
//
//		super( context );
//		init( context );
//	}
//
//
//	public CustomerListViewSwitcher( Context context, AttributeSet attrs ) {
//
//		super( context, attrs );
//		init( context );
//	}
//
//
//	private void init( Context context ) {
//
//		mContext = context;
//		mLayoutParams = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT );
//		mNoMoreDataFooter = LayoutInflater.from( context ).inflate( R.layout.layout_list_footer_end, null );
//		addNoDataView();
//		addSuccessView();
//		addLoddingView();
//		addFailedView();
//		showOnLoddingView();
//	}
//
//
//	private void addNoDataView() {
//
//		mNoDataView = LayoutInflater.from( getContext() ).inflate( R.layout.customer_no_data, null );
//		mNoDataView.setLayoutParams( mLayoutParams );
//		View btn = mNoDataView.findViewById( R.id.tv_add );
//		btn.setOnClickListener( new OnClickListener() {
//
//			@Override
//			public void onClick( View v ) {
//
//				if( mListener != null )
//					mListener.onclick();
//				else
//					Toast.makeText( mContext, "请添加监听器", 1 ).show();
//			}
//		} );
//		addView( mNoDataView );
//	}
//
//
//	private void addFailedView() {
//
//		mFailedView = LayoutInflater.from( getContext() ).inflate( R.layout.layout_failed_view, null );
//		mFailedView.setLayoutParams( mLayoutParams );
//		mFailedImage = ( ImageView )mFailedView.findViewById( R.id.failed_imageview );
//		mFailedTextTile = ( TextView )mFailedView.findViewById( R.id.default_refresh_tip1 );
//		mFailedTextTile.getPaint().setFakeBoldText( true );
//		mFailedView.setVisibility( View.GONE );
//		addView( mFailedView );
//	}
//
//
//	private void addLoddingView() {
//
//		mLoddingView = LayoutInflater.from( getContext() ).inflate( R.layout.layout_loding_view, null );
//		mLoddingView.setLayoutParams( mLayoutParams );
//		mLoddingView.setVisibility( View.GONE );
//		addView( mLoddingView );
//	}
//
//
//	private void addSuccessView() {
//
//		mSuccessView = ( MyListView )LayoutInflater.from( getContext() ).inflate( R.layout.layout_success_view, null );
//		mSuccessView.setLayoutParams( mLayoutParams );
//		mSuccessView.setVisibility( View.GONE );
//		addView( mSuccessView );
//	}
//
//
//	public void setFailedText( int titleText, int contentText ) {
//
//		if( titleText != 0 ) {
//			mFailedTextTile.setText( titleText );
//		} else {
//			mFailedTextTile.setVisibility( GONE );
//		}
//	}
//
//
//	/**
//	 * Show refreshing view and hide any other view attached
//	 */
//	public void showOnLoddingView() {
//
//		if( mLoddingView.getVisibility() == View.VISIBLE )
//			return;
//		mLoddingView.setVisibility( View.VISIBLE );
//		mFailedView.setVisibility( GONE );
//		mNoDataView.setVisibility( View.GONE );
//	}
//
//
//	/**
//	 * Show refreshing view and hide any other view attached
//	 */
//	public void showOnNoDataView() {
//
//		if( mNoDataView.getVisibility() == View.VISIBLE )
//			return;
//		mNoDataView.setVisibility( View.VISIBLE );
//		mLoddingView.setVisibility( View.GONE );
//		mFailedView.setVisibility( View.GONE );
//	}
//
//
//	/**
//	 * Show failed view and hide any other view attached
//	 */
//	public void showOnFailedView() {
//
//		if( mFailedView.getVisibility() == View.VISIBLE )
//			return;
//		mLoddingView.setVisibility( View.GONE );
//		mNoDataView.setVisibility( View.GONE );
//		mFailedView.setVisibility( VISIBLE );
//		mFailedImage.setImageResource( R.drawable.logo_default );
//	}
//
//
//	/**
//	 * 自定义FailedView样式，一般用于没有数据的情况下
//	 * 
//	 * @param imageResId
//	 *            Failed view image resource id
//	 * @param failTitleResId
//	 *            Failed view title text resource id
//	 */
//	public void showOnFailedView( String failTitleResId ) {
//
//		if( mFailedView.getVisibility() == View.VISIBLE )
//			return;
//		if( failTitleResId != null ) {
//			mFailedTextTile.setVisibility( VISIBLE );
//			mFailedTextTile.setText( failTitleResId );
//		} else {
//			mFailedTextTile.setVisibility( GONE );
//		}
//		mNoDataView.setVisibility( View.GONE );
//		mLoddingView.setVisibility( View.GONE );
//		mFailedView.setVisibility( VISIBLE );
//
//	}
//
//
//	/**
//	 * 自定义FailedView样式，一般用于没有数据的情况下
//	 * 
//	 * @param imageResId
//	 *            Failed view image resource id
//	 * @param failTitleResId
//	 *            Failed view title text resource id
//	 */
//	public void showOnFailedView( int imageResId, int failTitleResId ) {
//
//		if( mFailedView.getVisibility() == View.VISIBLE )
//			return;
//		if( imageResId != 0 ) {
//			mFailedImage.setImageResource( imageResId );
//		}
//		if( failTitleResId != 0 ) {
//			mFailedTextTile.setVisibility( VISIBLE );
//			mFailedTextTile.setText( failTitleResId );
//		} else {
//			mFailedTextTile.setVisibility( GONE );
//		}
//		mNoDataView.setVisibility( View.GONE );
//		mLoddingView.setVisibility( View.GONE );
//		mFailedView.setVisibility( VISIBLE );
//
//	}
//
//
//	/**
//	 * Show success view and hide any other view attached
//	 */
//	public void showOnSuccessView() {
//
//		mSuccessView.setVisibility( View.VISIBLE );
//		mLoddingView.setVisibility( View.GONE );
//		mFailedView.setVisibility( View.GONE );
//		mNoDataView.setVisibility( View.GONE );
//	}
//
//
//	public View getRefreshView() {
//
//		return mLoddingView;
//	}
//
//
//	public MyListView getSuccessView() {
//
//		return mSuccessView;
//	}
//
//
//	public void JudgePageState( boolean hasNext ) {
//
//		mSuccessView.onRefreshComplete();
//		if( !hasNext ) {
//			mSuccessView.hideFooderView();
//			mSuccessView.addFooterView( mNoMoreDataFooter, null, false );
//		} else {
//			mSuccessView.showFooderView();
//			mSuccessView.removeFooterView( mNoMoreDataFooter );
//		}
//	}
//
//
//	public void cancelTextBold() {
//
//		mFailedTextTile.getPaint().setFakeBoldText( false );
//	}
//
//
//	public interface OnGetScoreClickListener {
//
//		void onclick();
//	}
}
