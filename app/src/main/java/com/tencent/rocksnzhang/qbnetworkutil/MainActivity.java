package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tencent.rocksnzhang.utils.IProgressChangedListener;
import com.tencent.rocksnzhang.utils.ShareUtils;
import com.tencent.rocksnzhang.utils.ZipHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        IProgressChangedListener
{
    private ViewPager viewPager;
    private ProgressBar titleProgressBar;
    private FragmentPagerAdapter pagerAdapter;

    private TabLayout tabLayout;
    private List<CommonFragment> mFragmentList = new ArrayList<CommonFragment>();
    private int mCurrentFragmentIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData()
    {
        CommonFragment basicInfoFragment = new NetInfoFragment();
        mFragmentList.add(basicInfoFragment);

        CommonFragment netDetectorFragment = new NetDetectorFragment();
        netDetectorFragment.setProgressChangedListener(this);
        mFragmentList.add(netDetectorFragment);

        CommonFragment otherToolFragment = new NetCaptureFragment();
        mFragmentList.add(otherToolFragment);

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
            TabLayout.Tab tab1 = tabLayout.newTab();
            tab1.setText("基本信息");
            tabLayout.addTab(tab1, false);

            TabLayout.Tab tab2 = tabLayout.newTab();
            tab2.setText("网络诊断");
            tabLayout.addTab(tab2, true);

            TabLayout.Tab tab3 = tabLayout.newTab();
            tab3.setText("抓包工具");
            tabLayout.addTab(tab3, false);


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

            //default the center fragment is selected.
            viewPager.setCurrentItem(mCurrentFragmentIndex);
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView wxShareBtn = (ImageView) toolbar.findViewById(R.id.wxbtn_send);
        wxShareBtn.setOnClickListener(this);

        ImageView qqShareBtn = (ImageView) toolbar.findViewById(R.id.qqbtn_send);
        qqShareBtn.setOnClickListener(this);

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

        titleProgressBar = (ProgressBar) findViewById(R.id.progress_spinner);
        hideProgress();
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
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.wxbtn_send)
        {
            new Thread(){
                public void run()
                {
                    saveFragementDataToFile();
                    zipUploadData();
                    ActivityInfo activityInfo = ShareUtils.getWeChatOrMobileQQShareActivity(ShareUtils.SHARE_THROUGH_WECHAT);
                    if (activityInfo == null)
                    {
                        Toast.makeText(MainActivity.this, "没有找到QQ。", Toast.LENGTH_SHORT).show();
                    }

                    if (activityInfo != null)
                    {
                        Intent addIntent = new Intent();
                        addIntent.setAction(Intent.ACTION_SEND);
                        addIntent.setType("text/plain");

                        addIntent.setPackage(activityInfo.packageName);

                        addIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(
                                "file:///" + NetworkUtilApp.getInstance().getSingleFileStoreManager().getAppZipDataFile().getAbsolutePath()));

                        startActivity(addIntent);
                        return;
                    }
                }
            }.start();
        }



        if (v.getId() == R.id.qqbtn_send)
        {
            new Thread(){
                public void run()
                {
                    saveFragementDataToFile();
                    zipUploadData();
                    ActivityInfo activityInfo = ShareUtils.getWeChatOrMobileQQShareActivity(ShareUtils.SHARE_THROUGH_MOBILEQQ);
                    if (activityInfo == null)
                    {
                        Toast.makeText(MainActivity.this, "没有找到微信。", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (activityInfo != null)
                    {
                        Intent addIntent = new Intent();
                        addIntent.setAction(Intent.ACTION_SEND);
                        addIntent.setType("text/plain");

                        addIntent.setPackage(activityInfo.packageName);

                        addIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(
                                "file:///" + NetworkUtilApp.getInstance().getSingleFileStoreManager().getAppZipDataFile().getAbsolutePath()));

                        startActivity(addIntent);
                        return;
                    }
                }
            }.start();
        }
    }

    private void saveFragementDataToFile()
    {
        for(CommonFragment fragment : mFragmentList)
        {
            fragment.saveToFile();
        }
    }


    private void zipUploadData()
    {
        File tmpSaveDir = NetworkUtilApp.getInstance().getSingleFileStoreManager().getAppTmpStoreDirFile();
        String[] files = tmpSaveDir.list();

        final  String tmpSavePathString = tmpSaveDir.getAbsolutePath();
        for (int i = 0; i < files.length ; i++)
        {
            files[i] = tmpSavePathString + File.separator + files[i];

        }

        ZipHelper zipHelper = new ZipHelper(files, NetworkUtilApp.getInstance().getSingleFileStoreManager().getAppZipDataFile().getAbsolutePath());
        zipHelper.Zip();
    }

    @Override
    public void showProgress()
    {
        if (titleProgressBar != null)
        {
            titleProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress()
    {
        if (titleProgressBar != null)
        {
            titleProgressBar.setVisibility(View.GONE);
        }
    }
}
