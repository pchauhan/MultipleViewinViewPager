package com.pc.multipleviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pc.demo.R;
public class MainActivity extends Activity
{

	PagerContainer	mContainer;
	Activity activity ;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this ;
		mContainer = (PagerContainer) findViewById(R.id.pager_container);

		ViewPager pager = mContainer.getViewPager();
		pager.setClipToPadding(false);
		PagerAdapter adapter = new MyPagerAdapter();
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(adapter.getCount());
	}

	private class MyPagerAdapter extends PagerAdapter
	{
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			View view = activity.getLayoutInflater().inflate(R.layout.pagerlayout, null);
			TextView tv = (TextView)view.findViewById(R.id.tv);
			tv.setText("View " + position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

		@Override
		public int getCount()
		{
			return 25;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return (view == object);
		}
	}
}
