package com.adeleke.samad.eddandega;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.adeleke.samad.eddandega.Contract.ContractClass;
import com.adeleke.samad.eddandega.Contract.ContractInterface;
import com.adeleke.samad.eddandega.Fragments.AnthropometryFragment;
import com.adeleke.samad.eddandega.Fragments.FluidFragment;
import com.adeleke.samad.eddandega.Fragments.LMPFragment;
import com.adeleke.samad.eddandega.Presenters.LMPPresenter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private LMPFragment lmpFragment;
    private AnthropometryFragment anthropometryFragment;
    private FluidFragment fluidFragment;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean nightModeIsOn;





@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        lmpFragment = new LMPFragment();
        anthropometryFragment = new AnthropometryFragment();
        fluidFragment = new FluidFragment();

        sharedPreferences = getSharedPreferences("com.adeleke.samad.eddandega", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        nightModeIsOn = sharedPreferences.getBoolean("nightMode", false);

        if(nightModeIsOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdaper viewPagerAdapter = new ViewPagerAdaper(getSupportFragmentManager(), 0);



        viewPagerAdapter.addFragment(lmpFragment, "EDD");
        viewPagerAdapter.addFragment(anthropometryFragment, "Weight");
        viewPagerAdapter.addFragment(fluidFragment, "Fluid");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_pregnant_woman);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_child);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_water_drop);

//    BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
//    badgeDrawable.setVisible(true)
//    badgeDrawable.setNumber(2);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position  =tab.getPosition();
                switch (position) {
                    case 0:
                        setupLMPFrag();
                    break;
                    case 1:
                        setUoFluidFrag();
                    break;
                    case 2:
                        setUpAnthropometryFrag();
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (savedInstanceState == null){
            setupLMPFrag();
        }

}




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if (item.getItemId() == R.id.toggle_night){
          if (nightModeIsOn){
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
              nightModeIsOn = false;

          }else{
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
              nightModeIsOn = true;
          }
           editor.putBoolean("nightMode", nightModeIsOn);
           editor.apply();
           return super.onOptionsItemSelected(item);
        }
       return false;
    }


    public void setupLMPFrag(){
        LMPFragment lmpFragment = new LMPFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, lmpFragment, "MESSAGE_FRAG");
        fragmentTransaction.replace(R.id.fragment_container,lmpFragment).commit();
    }


    public void setUoFluidFrag(){
        FluidFragment fluidFragment = new FluidFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, fluidFragment, "FLUID_FRAG");
        fragmentTransaction.replace(R.id.fragment_container,fluidFragment).commit();
    }

    public void setUpAnthropometryFrag(){
        AnthropometryFragment anthropometryFragment = new AnthropometryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, anthropometryFragment, "FLUID_FRAG");
        fragmentTransaction.replace(R.id.fragment_container,anthropometryFragment).commit();
    }


    private class ViewPagerAdaper extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdaper(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}




