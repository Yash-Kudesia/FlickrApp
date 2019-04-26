package org.example.yashkudesia.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecycleViewAdapter extends RecyclerView.Adapter<FlickrRecycleViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecycleViewAdapte";
    private List<Photo> photoList;
    private Context context;

    public FlickrRecycleViewAdapter(Context context, List<Photo> photoList) {
        this.photoList = photoList;
        this.context = context;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        //called by the laoyout manager whenever a new view is created
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse, viewGroup, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        //called by layout manager when it wants the new data in a existing row
        Photo photIte = photoList.get(position);
        Log.d(TAG, "onBindViewHolder: " + photIte.getTitle() + "-->" + position);


        Picasso.get().load(photIte.getImage())      //the method with is renamed as get and now no context is required
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
        holder.title.setText(photIte.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount-:" + photoList.size());
        return ((photoList != null) && (photoList.size() != 0) ? photoList.size() : 0);
    }

    void loadNewData(List<Photo> photos) {
        photoList = photos;
        notifyDataSetChanged(); //notify recycle view tha data has changed
    }

    public Photo getPhoto(int position) {
        return ((photoList != null) && (photoList.size() != 0) ? photoList.get(position) : null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
        }
    }
}
