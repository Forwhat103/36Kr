package com.zhuxiaoming.kr36.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuxiaoming.kr36.R;
import com.zhuxiaoming.kr36.base.BaseActivity;
import com.zhuxiaoming.kr36.find.FindFragment;
import com.zhuxiaoming.kr36.invest.InvestFragment;
import com.zhuxiaoming.kr36.mine.MineFragment;
import com.zhuxiaoming.kr36.news.news.NewsFragment;
import com.zhuxiaoming.kr36.news.earlyitem.EarlyItemFragment;
import com.zhuxiaoming.kr36.news.krtv.KrTvFragment;
import com.zhuxiaoming.kr36.search.SearchActivity;
import com.zhuxiaoming.kr36.setting.SettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是主页
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mainVp;// 创建ViewPager对象
    private TabLayout mainTab;// 创建TabLayout对象
    private List<Fragment> fragments;// 创建碎片集合的对象
    private MainAdapter mainAdapter;// 创建主页适配器的对象
    private DrawerLayout mainDl;// 设置抽屉DrawerLayout对象
    private Toolbar mainTb;// 创建标题栏对象
    private NavigationView mainNv;// 创建侧滑导航对象
    private ImageView searchIv;// 标题栏搜索按钮
    private ImageView backIv;// 抽屉返回按钮
    private View navigationHeadView;// 抽屉的头视图
    private TextView titleTv;// 标题
    private ImageView settingIv;// 我的界面标题栏设置按钮
    private LinearLayout searchFrameLl;// 发现界面Toolbar搜索框

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mainVp = (ViewPager) findViewById(R.id.main_vp);
        mainTab = (TabLayout) findViewById(R.id.main_tab);
        mainDl = (DrawerLayout) findViewById(R.id.main_dl);
        mainTb = (Toolbar) findViewById(R.id.main_tool_bar);
        mainNv = (NavigationView) findViewById(R.id.main_nv);
        searchIv = (ImageView) findViewById(R.id.tool_bar_search_iv);
        settingIv = (ImageView) findViewById(R.id.tool_bar_setting_iv);
        searchFrameLl = (LinearLayout) findViewById(R.id.find_tool_bar_search_ll);
        titleTv = (TextView) findViewById(R.id.tool_bar_tv);
        navigationHeadView = LayoutInflater.from(this).inflate(R.layout.navigation_header, null);
        backIv = (ImageView) navigationHeadView.findViewById(R.id.navigation_header_back_iv);


    }

    @Override
    protected void initData() {
        setSupportActionBar(mainTb);// 设置标题栏Toolbar
        initFragment();// 添加Fragment数据
        mainAdapter = new MainAdapter(getSupportFragmentManager());//初始化适配器
        mainAdapter.setFragments(fragments);// 将Fragment数据添加到适配器
        mainVp.setAdapter(mainAdapter);// 设置ViewPager的适配器
        mainTab.setupWithViewPager(mainVp);// 设置TabLayout的适配器
        initTabs();// 添加Tab数据
        //设置抽屉DrawerLayout
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mainDl, mainTb, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();// 初始化状态
        mainDl.setDrawerListener(mDrawerToggle);
        mainNv.addHeaderView(navigationHeadView);
        // 设置侧滑导航栏的点击事件
        mainNv.setNavigationItemSelectedListener(this);
        // 设置标题栏搜索按钮监听事件
        searchIv.setOnClickListener(this);
        // 设置抽屉返回按钮监听事件
        backIv.setOnClickListener(this);
        // 设置发现界面标题栏搜索框的点击事件
        searchFrameLl.setOnClickListener(this);
        // 设置我的界面设置按钮的监听事件
        settingIv.setOnClickListener(this);
        // 设置ViewPager页面改变监听
        mainVp.addOnPageChangeListener(this);
        // 设置toolbar导航键
        mainTb.setNavigationIcon(R.mipmap.title_bar_menu);
    }

    // 添加Fragment数据
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new FindFragment());
        fragments.add(new InvestFragment());
        fragments.add(new MineFragment());
    }

    // 设置侧滑导航栏的点击事件
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_header_item_all:
                // 点击全部item
                titleTv.setText("新闻");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_vp, new NewsFragment()).commit();
                break;
            case R.id.navigation_header_item_early:
                // 早期项目
                titleTv.setText("早期项目");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_vp, new EarlyItemFragment()).commit();
                Toast.makeText(MainActivity.this, "早期项目", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_header_item_tv:
                // 氪TV
                titleTv.setText("氪TV");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_vp, new KrTvFragment()).commit();
                Toast.makeText(MainActivity.this, "氪TV", Toast.LENGTH_SHORT).show();
                break;
        }
        item.setChecked(false);//点击了把它设为选中状态
        mainDl.closeDrawers();// 关闭抽屉
        return true;
    }

    // 添加TabLayout的Tab数据
    private void initTabs() {
        int[] tabs = {R.drawable.selector_news, R.drawable.selector_find, R.drawable.selector_invest, R.drawable.selector_mine};
        for (int i = 0; i < tabs.length; i++) {
            mainTab.getTabAt(i).setIcon(tabs[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_bar_search_iv:
                // 跳转到搜索界面
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_header_back_iv:
                // 返回到主界面
                mainDl.closeDrawers();// 关闭侧滑
                titleTv.setText("新闻");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_vp, new NewsFragment()).commit();
                break;
            case R.id.find_tool_bar_search_ll:
                // 跳转到搜索界面
                Intent intent1 = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.tool_bar_setting_iv:
                // 跳转到搜索界面
                Intent intent2 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent2);
                break;
        }
    }

    // ViewPager的状态改变监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //这个方法会在屏幕滚动过程中不断被调用。
    }

    @Override
    public void onPageSelected(int position) {
        //这个方法有一个参数position，代表哪个页面被选中。当用手指滑动翻页的时候，
        // 如果翻动成功了（滑动的距离够长），手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
        // 如果直接setCurrentItem翻页，那position就和setCurrentItem的参数一致，这种情况在onPageScrolled执行方法前就会立即执行
        switch (position) {
            case 0:
                // 新闻界面
                titleTv.setText("新闻");
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_vp, new NewsFragment()).commit();
                mainTb.setNavigationIcon(R.mipmap.title_bar_menu);
                settingIv.setVisibility(View.GONE);
                searchIv.setVisibility(View.VISIBLE);
                searchFrameLl.setVisibility(View.GONE);
                mainTb.setBackgroundColor(Color.rgb(250, 250, 250));// 设置ToolBar的背景颜色
                break;
            case 1:
                // 发现界面
//                mainDl.closeDrawers();// 关闭抽屉
                titleTv.setText("");
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_vp, new FindFragment()).commit();
                mainTb.setNavigationIcon(null);
                settingIv.setVisibility(View.GONE);
                searchIv.setVisibility(View.GONE);
                searchFrameLl.setVisibility(View.VISIBLE);
                mainTb.setBackgroundColor(Color.rgb(66, 133, 244));// 设置ToolBar的背景颜色
                break;
            case 2:
                // 股权投资界面
//                mainDl.closeDrawers();// 关闭抽屉
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_vp, new InvestFragment()).commit();
                titleTv.setText("股权投资");
                mainTb.setNavigationIcon(null);
                settingIv.setVisibility(View.GONE);
                searchIv.setVisibility(View.VISIBLE);
                searchFrameLl.setVisibility(View.GONE);
                mainTb.setBackgroundColor(Color.rgb(250, 250, 250));// 设置ToolBar的背景颜色
                break;
            case 3:
                // 我的界面
//                mainDl.closeDrawers();// 关闭抽屉
                mainNv.setVisibility(View.GONE);
                titleTv.setText("");
                mainTb.setNavigationIcon(null);
                settingIv.setVisibility(View.VISIBLE);
                searchIv.setVisibility(View.GONE);
                searchFrameLl.setVisibility(View.GONE);
                mainTb.setBackgroundColor(Color.rgb(66, 133, 244));// 设置ToolBar的背景颜色
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 这个方法在手指操作屏幕的时候发生变化
    }
}
