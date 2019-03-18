package com.example.mdjahirulislam.shishupoththo.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.mdjahirulislam.shishupoththo.Adapter.AllViewPagerAdapter;
import com.example.mdjahirulislam.shishupoththo.Fragment.BabyDragCalculationFragment;
import com.example.mdjahirulislam.shishupoththo.Fragment.BabyInfoFragment;
import com.example.mdjahirulislam.shishupoththo.R;

public class BabyHomeActivity extends AppCompatActivity implements SetActiveImage{

    private static final String TAG = BabyHomeActivity.class.getSimpleName();
    private ViewPager pager;
    private AllViewPagerAdapter adapter;

    private ImageView firstIcon;
    private ImageView secondIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_baby_home );

        firstIcon = findViewById( R.id.firstIcon );
        secondIcon = findViewById( R.id.secondIcon );

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,new BabyInfoFragment()).commit();

//        pager = findViewById( R.id.babyHomePager );
//        adapter = new AllViewPagerAdapter( getSupportFragmentManager() );
//
//        adapter.addFragment( new BabyInfoFragment(), "Lectures" );
//        adapter.addFragment( new BabyDragCalculationFragment(), "More" );
//        pager.setAdapter( adapter );






//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            public void onPageScrollStateChanged(int state) {
//                Log.d( TAG, "onPageScrollStateChanged: " +state);
//            }
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d( TAG, "onPageScrolled: " + position);
//            }
//
//            public void onPageSelected(int position) {
//                // Check if this is the page you want.
//                Log.d( TAG, "onPageSelected: " +position);
//
//                if (position == 1){
//                    firstIcon.setImageResource( R.drawable.ic_marker_inactive );
//                    secondIcon.setImageResource( R.drawable.ic_marker_active );
//                }else {
//
//                    firstIcon.setImageResource( R.drawable.ic_marker_active );
//                    secondIcon.setImageResource( R.drawable.ic_marker_inactive );
//
//
//                }
//            }
//        });


//        final View byId = findViewById(R.id.babyHomePager);
//        pager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });
//
//        pager.setCurrentItem( 0 );
    }



    @Override
    public void setActiveImage(boolean flag) {

        if (flag){
            firstIcon.setImageResource( R.drawable.ic_marker_inactive );
            secondIcon.setImageResource( R.drawable.ic_marker_active );
        }else {

            firstIcon.setImageResource( R.drawable.ic_marker_active );
            secondIcon.setImageResource( R.drawable.ic_marker_inactive );


        }
    }
}
