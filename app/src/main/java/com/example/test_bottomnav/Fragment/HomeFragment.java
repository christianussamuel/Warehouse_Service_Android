package com.example.test_bottomnav.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.test_bottomnav.CrudProduct.MainActivityCrud;
import com.example.test_bottomnav.ImgSlider.SlidingImage_Adapter;
import com.example.test_bottomnav.ListWarehouse;
import com.example.test_bottomnav.AddWarehouse;
import com.example.test_bottomnav.R;
import com.example.test_bottomnav.MyProduct;
import com.example.test_bottomnav.Webview.MainActivityWebview;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private CardView mMyProduct, mAddWarehouse, mListWarehouse, mMyOrder;
    private MyProduct myProduct;

    private Button mWebview;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private String[] urls = new String[] {"https://demonuts.com/Demonuts/SampleImages/W-03.JPG", "https://demonuts.com/Demonuts/SampleImages/W-08.JPG", "https://demonuts.com/Demonuts/SampleImages/W-10.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-13.JPG", "https://demonuts.com/Demonuts/SampleImages/W-17.JPG", "https://demonuts.com/Demonuts/SampleImages/W-21.JPG"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMyProduct = getView().findViewById(R.id.myProduct);
        mAddWarehouse = getView().findViewById(R.id.addWarehouse);
        mListWarehouse = getView().findViewById(R.id.listWarehouse);
        mMyOrder = getView().findViewById(R.id.myOrder);
        mWebview = getView().findViewById(R.id.btn_webview);

        init();

        mMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProduct.class);
                startActivity(intent);

            }
        });

        mWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivityWebview.class);
                startActivity(intent);
            }
        });


        mAddWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWarehouse.class);
                startActivity(intent);
            }
        });

        mListWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListWarehouse.class);
                startActivity(intent);
            }
        });

        mMyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivityCrud.class );
                startActivity(intent);
            }
        });
    }
    private void init() {

        mPager = (ViewPager) getView().findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(getActivity(),urls));

        CirclePageIndicator indicator = (CirclePageIndicator)
                getView().findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = urls.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

}
