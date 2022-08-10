package ifm3b.miniproject11.skyhotels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import ifm3b.miniproject11.skyhotels.R;
import ifm3b.miniproject11.skyhotels.models.reservation;
import ifm3b.miniproject11.skyhotels.reservations;

import static ifm3b.miniproject11.skyhotels.MainActivity.URL;

public class reservationAdpater extends RecyclerView.Adapter<reservationAdpater.AdapterViewHolder> {
    private ArrayList<reservation> reservations;
    private OnItemClickListener rListener;

    public reservationAdpater(ArrayList<reservation> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservationitem,parent, false);
        reservationAdpater.AdapterViewHolder avh = new reservationAdpater.AdapterViewHolder(v,rListener);
        return avh;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        rListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.name.setText(reservations.get(position).getName());
        holder.roomNo.setText(reservations.get(position).getRoomNum());
        holder.dateIn.setText(reservations.get(position).getStartDate());
        holder.dateOut.setText(reservations.get(position).getEndDate());
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView roomNo;
        private TextView dateIn;
        private TextView dateOut;

        public AdapterViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.txtResName);
            roomNo = itemView.findViewById(R.id.txtResType);
            dateIn = itemView.findViewById(R.id.txtResCIDate);
            dateOut = itemView.findViewById(R.id.txtResCODate);
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
}