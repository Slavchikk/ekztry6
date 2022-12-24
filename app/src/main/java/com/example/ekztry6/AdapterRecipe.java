package com.example.ekztry6;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Base64;
import java.util.List;

public class AdapterRecipe extends BaseAdapter {
    Context mContext;
    List<Recipe> recipeList;

    public AdapterRecipe(Context mContext, List<Recipe> recipeList) {
        this.mContext = mContext;
        this.recipeList =  recipeList;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recipeList.get(position).getIdRecipe();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v = View.inflate(mContext,R.layout.activity_recipe,null );
        TextView fullRes = v.findViewById(R.id.txtFull);
        TextView calor = v.findViewById(R.id.txtCalory);
        TextView times = v.findViewById(R.id.txtTime);
        ImageView imageView = v.findViewById(R.id.imageView2);

        Recipe recipe = recipeList.get(position);
        DecodeImage decodeImage = new DecodeImage(mContext);
        imageView.setImageBitmap(decodeImage.getUserImage(recipe.getImage()));
        fullRes.setText(recipe.getFullRecipe());
        calor.setText(recipe.getCalory());
        times.setText(recipe.getTimeGot());



        return v;
    }
}
