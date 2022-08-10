package ifm3b.miniproject11.skyhotels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ifm3b.miniproject11.skyhotels.R;
import ifm3b.miniproject11.skyhotels.models.roomItem;

public class browseAdapter extends RecyclerView.Adapter<browseAdapter.AdapterViewHolder>{
    private ArrayList<roomItem> roomItems;
    private OnItemClickListener bListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        bListener = listener;

    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView bTvName;
        public ImageView bIvImage;
        public TextView bTvCapacity;
        public TextView bTvRate;

        public AdapterViewHolder(View itemView,final OnItemClickListener listener){
            super(itemView);
            bTvName = itemView.findViewById(R.id.tvRoomName);
            bIvImage = itemView.findViewById(R.id.ivRoomImg);
            bTvCapacity = itemView.findViewById(R.id.txtCapacity);
            bTvRate = itemView.findViewById(R.id.txtRate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public browseAdapter(ArrayList<roomItem> roomsList){
        roomItems = roomsList;
    }
    @NonNull
    @Override
    public browseAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.roomitem,parent,false);
        AdapterViewHolder avh = new AdapterViewHolder(v,bListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull browseAdapter.AdapterViewHolder holder, int position) {
        roomItem currentItem = roomItems.get(position);
        holder.bTvName.setText(currentItem.getType());
        holder.bTvCapacity.setText("Sleeps "+currentItem.getCapacity());
        holder.bTvRate.setText("R "+String.valueOf((currentItem.getRate())));

        Picasso.get()
                .load(currentItem.getImgUrl())
                .fit()
                .centerCrop()
                .into(holder.bIvImage);
    }

    @Override
    public int getItemCount() {
        return roomItems.size();
    }
}
