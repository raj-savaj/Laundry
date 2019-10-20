package com.AV.laundry;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.AV.laundry.fragment.AccountTab;
import com.AV.laundry.fragment.ClothesTab;
import com.AV.laundry.fragment.Info;
import com.AV.laundry.fragment.MemberTab;

import java.util.ArrayList;
import java.util.List;

public class Tab extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1)
                {
                    ClothesTab c= (ClothesTab) adapter.getItem(position);
                    c.getClintname();
                }
                else if (position==2)
                {
                    AccountTab c= (AccountTab) adapter.getItem(position);
                    c.getClintname();
                }
                else  if (position==3)
                {
                    Info info=(Info)adapter.getItem(position);
                    info.initdata();
                }
                else{

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MemberTab(), "ONE");
        adapter.addFragment(new ClothesTab(), "TWO");
        adapter.addFragment(new AccountTab(), "THREE");
        adapter.addFragment(new Info(), "Four");
        viewPager.setAdapter(adapter);
    }

    public void setupTabIcons(){
        tabLayout.getTabAt(0).setText("Member");
        tabLayout.getTabAt(1).setText("Cloth");
        tabLayout.getTabAt(2).setText("Account");
        tabLayout.getTabAt(3).setText("Info");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

}
