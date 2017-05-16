package com.dineshr.www.rsapublicprivatekeygenerator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    BottomNavigationView navigation = null;
    private int mSelectedItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // item.setChecked(true);
            //mMenuId = item.getItemId();
           /* for (int i = 0; i < navigation.getMenu().size(); i++) {
                MenuItem menuItem = navigation.getMenu().getItem(i);
                boolean isChecked = menuItem.getItemId() == item.getItemId();
                menuItem.setChecked(isChecked);
            }*/
            switch (item.getItemId()) {
                case R.id.navigation_gk:
                    switchFragment(new KeyFragment());
                    return true;
                case R.id.navigation_e:
                    switchFragment(new EncryptFragment());
                    return true;
                case R.id.navigation_d:
                    switchFragment(new DecryptFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView)findViewById(R.id.textView5);
        /*FragmentManager fragmentManager = getFragmentManager();
        KeyFragment keyFragment = null;
        keyFragment = new KeyFragment();

        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(android.R.id.content, keyFragment);
                ft.commit();
            }
        }*/
       /* FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(android.R.id.content, keyFragment);
        ft.commit();*/
        //mTextMessage = (TextView) findViewById(R.id.message);
        //bottomNavigationView.getMenu().getItem(menuItemIndex).setChecked(true)
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        /*navigation.getMenu().getItem(0).;
        MenuItem actionRestart = (MenuItem) findViewById(R.id.);
        onOptionsItemSelected(actionRestart);*/
        navigation.setSelectedItemId(R.id.navigation_gk);
        //navigation.setc
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    protected void switchFragment(Fragment fragment) {

        mTextMessage.setVisibility(View.GONE);
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.content, fragment);
                //ft.add(R.id.content_frame, newFragment.newInstance(context), "Profile");
                //ft.addToBackStack(null);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }


}
