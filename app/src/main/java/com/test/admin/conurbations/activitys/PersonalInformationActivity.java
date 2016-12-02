package com.test.admin.conurbations.activitys;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.admin.conurbations.R;
import com.test.admin.conurbations.adapter.PersonalInformationAdapter;
import com.test.admin.conurbations.listeners.AppBarStateChangeListener;
import com.test.admin.conurbations.views.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zhouqiong on 2016/11/29.
 */

public class PersonalInformationActivity extends AppCompatActivity {

    private PersonalInformationAdapter mInfomationAdapter;
    @Bind(R.id.personal_info_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.personal_info_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.title_photo_img)
    ImageView mPhotoImageView;
    @Bind(R.id.title_info)
    TextView mTitleTextView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.fab_img)
    CircleImageView circleImageView;

    Bitmap bitmap;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();

        if (intent != null) {
            bitmap = intent.getParcelableExtra("photoBundle");
            circleImageView.setImageBitmap(bitmap);
            mPhotoImageView.setImageBitmap(bitmap);
        } else {
            circleImageView.setBackgroundResource(R.mipmap.ic_launcher);
            mPhotoImageView.setBackgroundResource(R.mipmap.ic_launcher);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mInfomationAdapter = new PersonalInformationAdapter(this);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(mInfomationAdapter);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mPhotoImageView.setVisibility(View.GONE);
                    circleImageView.setVisibility(View.VISIBLE);
                    mTitleTextView.setText("");
                    mToolbarLayout.setTitle("个人信息");
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mPhotoImageView.setVisibility(View.VISIBLE);
                    circleImageView.setVisibility(View.GONE);
                    mTitleTextView.setText(R.string.guard_msg);

                } else {
                    //中间状态
                    mPhotoImageView.setVisibility(View.GONE);
                    circleImageView.setVisibility(View.VISIBLE);
                    mTitleTextView.setText("");
                }
            }
        });
    }


    @OnClick({R.id.fab_img, R.id.title_photo_img})
    void onClick(View view) {
        Intent intent = new Intent(PersonalInformationActivity.this, BigAvatarActivity.class);
        intent.putExtra("photoBundle", bitmap);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}