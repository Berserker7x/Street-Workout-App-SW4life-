package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideViewPage extends PagerAdapter {
    Context ctx;
    public SlideViewPage (Context ctx){
        this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull   View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull

    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_screen,container,false);
        ImageView logo=view.findViewById(R.id.logo);
        ImageView ind1=view.findViewById(R.id.ind1);
        ImageView ind2=view.findViewById(R.id.ind2);
        ImageView ind3=view.findViewById(R.id.ind3);
        TextView title=view.findViewById(R.id.title1);
        TextView desc=view.findViewById(R.id.desc2);
        ImageView next=view.findViewById(R.id.next);
        ImageView back=view.findViewById(R.id.back);
        Button btnGetStarted=view.findViewById(R.id.button);
        btnGetStarted.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                 ctx.startActivity(intent);

            }
        });
        next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position+1);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideActivity.viewPager.setCurrentItem(position-1);

            }
        });

        switch (position){
            case 0:
                logo.setImageResource(R.drawable.logo3);
                ind1.setImageResource(R.drawable.selected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Street Workout");
                desc.setText("if you want to fly give up everything that weighs you down ");
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);

                break;
            case 1:
                logo.setImageResource(R.drawable.logo2);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.selected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Static");
                desc.setText("Be a gravity Destroyer");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);


                break;

            case 2:
                logo.setImageResource(R.drawable.logo1);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.selected);

                title.setText("Dynamic");
                desc.setText("Aim High  & Fly Higher  ");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                break;


        }

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull  ViewGroup container, int position, @NonNull  Object object) {
         container.removeView((View) object);
    }
}
