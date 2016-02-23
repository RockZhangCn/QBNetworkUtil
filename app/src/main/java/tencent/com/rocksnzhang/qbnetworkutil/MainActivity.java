package tencent.com.rocksnzhang.qbnetworkutil;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView refresh;
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    private TabLayout tabLayout;
    private List<Fragment> datas = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
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
        Fragment basicInfo = new BasicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "BasicInfo1");
        basicInfo.setArguments(bundle);
        datas.add(basicInfo);

        Fragment basicInfo2 = new NetDetectorFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("title", "BasicInfo2");
        basicInfo2.setArguments(bundle2);
        datas.add(basicInfo2);

        Fragment basicInfo3 = new OtherToolFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("title", "BasicInfo3");
        basicInfo3.setArguments(bundle3);
        datas.add(basicInfo3);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return datas.get(position);
            }

            @Override
            public int getCount()
            {
                return datas.size();
            }
        };

        viewPager.setAdapter( pagerAdapter);

        if(true)
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

                    viewPager.setCurrentItem(tab.getPosition());
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

           // tabLayout.getTabAt(0).set
        }
        else
        {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setupWithViewPager(viewPager);
        }
        tabLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Toast.makeText(MainActivity.this, "Tab is selected", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView()
    {
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
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
        else if(id == R.id.menu_refresh)
        {
            Toast.makeText(MainActivity.this, "This is refresh response", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.menu_refresh:
                break;


        }

        //if(view.getId() == R.id.menu_refresh)
            Toast.makeText(MainActivity.this, "This is my response", Toast.LENGTH_LONG).show();
        Log.i("TEST", "--- DVB Mac Address : ");
        new Thread(new Runnable(){
            @Override
            public void run()
            {
                Log.i("TEST", "--- DVB Mac Address : " );
                getMacAddress();
            }
        }).start();
    }


    private String getMacAddress()
    {
        String strMacAddr = "";
        byte[] b;
        try
        {
            NetworkInterface NIC = NetworkInterface.getByName("wlan0");
            b = NIC.getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++)
            {
                if (i != 0)
                {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        Log.i("TEST", "--- DVB Mac Address : " + strMacAddr);

        return strMacAddr;
    }

}
