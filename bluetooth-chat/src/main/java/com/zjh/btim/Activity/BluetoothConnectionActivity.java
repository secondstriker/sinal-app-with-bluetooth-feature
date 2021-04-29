package com.zjh.btim.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.zjh.btim.Adapter.ViewPagerAdapter;
import com.zjh.btim.Fragment.HomeFragment;
import com.zjh.btim.Fragment.SettingFragment;
import com.zjh.btim.R;
import com.zjh.btim.Util.XPermissionUtil;


public class BluetoothConnectionActivity extends AppCompatActivity {

    public static final int BLUE_TOOTH_DIALOG = 0x111;
    public static final int BLUE_TOOTH_TOAST = 0x123;
    public static final int BLUE_TOOTH_WRAITE = 0X222;
    public static final int BLUE_TOOTH_READ = 0X333;
    public static final int BLUE_TOOTH_WRAITE_FILE_NOW = 0X511;
    public static final int BLUE_TOOTH_READ_FILE_NOW = 0X996;
    public static final int BLUE_TOOTH_WRAITE_FILE = 0X555;
    public static final int BLUE_TOOTH_READ_FILE = 0X888;
    public static final int BLUE_TOOTH_SUCCESS = 0x444;
    public static final String MOBILE_NUMBER = "mobile_number";
    public static final String LOCAL_NUMBER = "local_number";
    public static final String DEVICE_CONNECTION_MODEL = "device_connection_model";


    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private String mobileNumber;
    private String localNumber;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                viewPager.setCurrentItem(0);
            } else if (itemId == R.id.navigation_setting) {
                viewPager.setCurrentItem(1);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setTitle("Bluetooth instant messaging App");
        mobileNumber = getIntent().getStringExtra(MOBILE_NUMBER);
        localNumber = getIntent().getStringExtra(LOCAL_NUMBER);
        if(mobileNumber == null)
            throw new IllegalArgumentException("mobile number must not be null.");
        if(localNumber == null)
            throw new IllegalArgumentException("local number must not be null.");

        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setupViewPager(viewPager);
        XPermissionUtil.requestPermissions(this, 1,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                new XPermissionUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //权限获取成功
                    }

                    @Override
                    public void onPermissionDenied() {
                        //权限获取失败
                        Snackbar.make(bottomNavigationView, "Please manually give relevant permissions", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(getResources().getColor(R.color.white)).show();
                    }
                });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(localNumber, mobileNumber));
        adapter.addFragment(new SettingFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
