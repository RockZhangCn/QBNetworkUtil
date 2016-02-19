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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView refresh;
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;
    private TextView mTab1, mTab2, mTab3;

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
        bundle.putString("title", "BasicInfo");
        basicInfo.setArguments(bundle);
        datas.add(basicInfo);

        Fragment basicInfo2 = new BasicInfoFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("title", "BasicInfo2");
        basicInfo.setArguments(bundle2);
        datas.add(basicInfo2);

        Fragment basicInfo3 = new BasicInfoFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("title", "BasicInfo3");
        basicInfo.setArguments(bundle3);
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

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView()
    {
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        mTab1 = (TextView)findViewById(R.id.tab1_info);
        mTab2 = (TextView)findViewById(R.id.tab2_link);
        mTab3 = (TextView)findViewById(R.id.tab3_tools);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
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
            case R.id.tab1_info:
                break;
            case R.id.tab2_link:
                break;
            case R.id.tab3_tools:
                break;

        }

        //if(view.getId() == R.id.menu_refresh)
            Toast.makeText(MainActivity.this, "This is my response", Toast.LENGTH_LONG).show();
    }
}
