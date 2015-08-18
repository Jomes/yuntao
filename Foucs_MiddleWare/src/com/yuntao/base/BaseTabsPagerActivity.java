package com.yuntao.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

import com.yuntao.R;

public abstract class BaseTabsPagerActivity extends BaseActivity implements
		OnTabChangeListener {
	protected TabHost mTabHost;
	protected TabWidget mTabWidget;
	protected int currentIndex;
	private FrameLayout container;

	private List<Fragment> fragments;
	private List<FrameLayout> containers;
	private List<Integer> containerIDs;
	private List<Boolean> isLoadContents;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		container = (FrameLayout) findViewById(R.id.simple_fragment);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabWidget = mTabHost.getTabWidget();
		mTabWidget.setDividerDrawable(null);
		mTabHost.setOnTabChangedListener(this);

		fragments = new ArrayList<Fragment>();
		isLoadContents = new ArrayList<Boolean>();
		containerIDs = new ArrayList<Integer>();
		containers = new ArrayList<FrameLayout>();

		addTabs();

		if (savedInstanceState != null) {
			currentIndex = savedInstanceState.getInt("currentIndex");
			if (currentIndex != 0) {
				setTabChange(currentIndex);
			}
		}

	}

	protected void setContentView() {
		setContentView(R.layout.lib_fragment_tabs_pager_activity);
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("currentIndex", currentIndex);
		super.onSaveInstanceState(outState);
	}

	/**
	 * 设置Tab切换
	 * 
	 * @param tabIndex
	 *            切换的Tab下标
	 */
	protected void setTabChange(int tabIndex) {
		mTabHost.setCurrentTab(tabIndex);
	}

	/**
	 * 获取当前Tab下标
	 * 
	 * @return Tab下标
	 */
	protected int getTabPosition() {
		return currentIndex;
	}

	/**
	 * 子类需要实现add tab的方法
	 * 如：mTabsAdapter.addTab(mTabHost.newTabSpec("custom").setIndicator
	 * ("Custom"), LoaderCourseListSupport.CourseListFragment.class, null);
	 */
	protected abstract void addTabs();

	/**
	 * 添加Tab子页面
	 * 
	 * @param tabText
	 *            Tab导航名称
	 * @param type
	 *            Tab类型
	 * @param cls
	 *            Tab页Class对象
	 * @param bundle
	 *            传递参数
	 */
	protected void addTab(View tabView, Fragment fragment, Bundle bundle,
			Integer containerID) {
		isLoadContents.add(true);
		containerIDs.add(containerID);

		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setId(containerID);
		frameLayout.setVisibility(View.GONE);
		containers.add(frameLayout);
		container.addView(frameLayout, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		fragment.setArguments(bundle);
		fragments.add(fragment);
		TabSpec tabSpec = mTabHost.newTabSpec(fragment.getClass()
				.getSimpleName());
		tabSpec.setIndicator(tabView);
		tabSpec.setContent(new DummyTabFactory(this));
		mTabHost.addTab(tabSpec);

		replaceFragment(containerID, fragment, false);
	}

	public void onTabChanged(String tabId) {
		currentIndex = mTabHost.getCurrentTab();

		for (int i = 0; i < isLoadContents.size(); i++) {
			if (currentIndex == i) {
				if (!isLoadContents.get(i)) {
					replaceFragment(containerIDs.get(i),
							fragments.get(currentIndex), false);
					isLoadContents.set(i, true);
				}
				containers.get(i).setVisibility(View.VISIBLE);
			} else {
				containers.get(i).setVisibility(View.GONE);
			}
		}

	}

	public static class DummyTabFactory implements TabHost.TabContentFactory {
		private final Context mContext;

		public DummyTabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}
}
