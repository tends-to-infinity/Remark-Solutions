package com.example.remarksolutions.MainArea.HomeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;


import com.example.remarksolutions.R;

import java.util.List;

public class Hot_deals_Adapter extends PagerAdapter {


    private List<Hot_dealsModel> hot_dealsModels;
    private LayoutInflater layoutInflater;
    private Context context;

    public Hot_deals_Adapter(List<Hot_dealsModel> hot_dealsModels, FragmentActivity context) {
        this.hot_dealsModels = hot_dealsModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return hot_dealsModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hot_deals, container, false);

        ImageView imageView = view.findViewById(R.id.hot_deals_images);
        imageView.setImageResource(hot_dealsModels.get(position).getImage());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
