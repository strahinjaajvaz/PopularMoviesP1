package com.example.strahinja.popularmovies.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.strahinja.popularmovies.Model.Movie;
import com.example.strahinja.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Movie> {

    private Activity mActivity;

    public ImageAdapter(Activity activity, List<Movie> list) {
        super(activity, 0, list);
        mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_image, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.grid_view_image);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Movie currentItem = getItem(position);
        Picasso.with(mActivity).load(currentItem.getImageUrl()).error(R.drawable.noimagefound).into(viewHolder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
