package ifm3b.miniproject11.skyhotels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ifm3b.miniproject11.skyhotels.R;

public class amenitiesAdapter extends RecyclerView.Adapter<amenitiesAdapter.AdapterViewHolder> {

    private ArrayList<String> amenities;

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.amenityitem,parent, false);
        AdapterViewHolder avh = new AdapterViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        String currentAmenity = amenities.get(position);
        holder.tvAmenity.setText(currentAmenity);
    }

    @Override
    public int getItemCount() {
        return amenities.size();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView tvAmenity;

        public AdapterViewHolder(View itemView){
            super(itemView);
            tvAmenity = itemView.findViewById(R.id.tvAmenity);
        }
    }

    public amenitiesAdapter(ArrayList<String> amenitiesList){
        amenities = amenitiesList;
    }

}
