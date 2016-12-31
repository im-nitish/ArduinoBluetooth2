package com.example.android.bluetoothchat;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static java.security.AccessController.getContext;

/**
 * Created by Nitish on 31-Dec-16.
 */

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;



    int[] mResources = {
            R.drawable.blue_car,
            R.drawable.stop_vtn,
    };

    String [] Bottles = {
      "Bottle 1", "Bottle 2"
    };

    String [] Cquan = {
            "40%", "60%"
    };

    String [] LD = {
            "12 litres", "5 litres"
    };

    String [] Stat = {
            "Good", "Need to improve"
    };



    public CustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);
        TextView CQuality = (TextView)itemView.findViewById(R.id.editText);
        TextView LDAYQ = (TextView)itemView.findViewById(R.id.editText2);
        TextView SQ = (TextView)itemView.findViewById(R.id.editText3);
        TextView CQualityA = (TextView)itemView.findViewById(R.id.editText4);
        TextView LDAYA = (TextView)itemView.findViewById(R.id.editText5);
        TextView SA = (TextView)itemView.findViewById(R.id.editText6);
        TextView bottle = (TextView)itemView.findViewById(R.id.textView);

        Typeface typeface= Typeface.createFromAsset(CQuality.getContext().getAssets(), "fonts/OpenSansRegular.ttf");
        CQuality.setTypeface(typeface);
        LDAYQ.setTypeface(typeface);
        LDAYA.setTypeface(typeface);
        SQ.setTypeface(typeface);
        SA.setTypeface(typeface);
        bottle.setTypeface(typeface);
        CQualityA.setTypeface(typeface);



        CQuality.setText("Current Quantity");
        LDAYQ.setText("Last Day Cons.");
        SQ.setText("Status");
        CQualityA.setText(Cquan[position]);
        LDAYA.setText(LD[position]);
        SA.setText(Stat[position]);
        bottle.setText(Bottles[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }



}