package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    private TabLayout tabLayout;
    private List<CommonFragment> mFragmentList = new ArrayList<CommonFragment>();
    private int mCurrentFragmentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //ImageView refresh = (ImageView)toolbar.findViewById(R.id.refreshid);
        //refresh.setOnClickListener(this);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
        initData();
    }

    private void initData()
    {
        CommonFragment basicInfo = new BasicInfoFragment();
        mFragmentList.add(basicInfo);

        CommonFragment basicInfo2 = new NetDetectorFragment();
        mFragmentList.add(basicInfo2);

        CommonFragment basicInfo3 = new OtherToolFragment();
        mFragmentList.add(basicInfo3);

        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount()
            {
                return mFragmentList.size();
            }
        };

        viewPager.setAdapter(pagerAdapter);

        if (true)
        {
            tabLayout.addTab(tabLayout.newTab().setText("基本信息"));
            tabLayout.addTab(tabLayout.newTab().setText("网络诊断"));
            tabLayout.addTab(tabLayout.newTab().setText("其他工具"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
            {
                @Override
                public void onTabSelected(TabLayout.Tab tab)
                {
                    mCurrentFragmentIndex = tab.getPosition();
                    viewPager.setCurrentItem(mCurrentFragmentIndex);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab)
                {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab)
                {
                }
            });

            //default the first is selected.
            mCurrentFragmentIndex = 0;
        }
        else
        {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void initView()
    {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Toast.makeText(MainActivity.this, "This is menu response", Toast.LENGTH_LONG).show();
            return true;
        }

        /*
        else if (id == R.id.menu_share)
        {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(
                    "file:///" + mFragmentList.get(mCurrentFragmentIndex).saveToFile().getAbsolutePath()));
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }
	
	public void onClick()
	{
	  Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(
                    "file:///" + mFragmentList.get(mCurrentFragmentIndex).saveToFile().getAbsolutePath()));
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));

			
			}
}
