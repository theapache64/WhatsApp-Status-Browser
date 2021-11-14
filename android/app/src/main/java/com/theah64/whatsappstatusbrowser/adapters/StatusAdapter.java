package com.theah64.whatsappstatusbrowser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.theah64.whatsappstatusbrowser.R;
import com.theah64.whatsappstatusbrowser.models.Status;

import java.util.List;

/**
 * Created by theapache64 on 16/7/17.
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {


    private final List<Status> statusList;
    private final LayoutInflater inflater;

    private final Callback callback;

    public StatusAdapter(final Context context, List<Status> statusList, Callback callback) {
        this.statusList = statusList;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rowLayout = inflater.inflate(R.layout.status_row, parent, false);
        return new ViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Status status = statusList.get(position);
        holder.ivThumbnail.setImageBitmap(status.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            this.ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.ibSaveToGallery).setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callback.onShare(getLayoutPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ibSaveToGallery) {
                callback.onSaveToGalleryClicked(getLayoutPosition());
            } else {
                callback.onItemClicked(getLayoutPosition());
            }
        }
    }

    public interface Callback {
        void onItemClicked(int position);

        void onShare(int position);

        void onSaveToGalleryClicked(int position);
    }
}
