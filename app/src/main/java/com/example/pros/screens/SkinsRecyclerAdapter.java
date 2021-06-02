package com.example.pros.screens;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pros.R;
import com.example.pros.model.Skin;
import com.example.pros.model.User;

import java.util.ArrayList;

/**
 * מחלקה זו היא המחלקה האחראית על תפקוד רשימת הסקינים במסך בחירת הסקינים.
 */
public class SkinsRecyclerAdapter extends RecyclerView.Adapter<SkinsRecyclerAdapter.ViewHolder> {
    private Context context;
    /**
     * רשימה המכילה את כל הסקינים במשחק, המכילה עצמים מטיפוס Skin.
     */
    private ArrayList<Skin> skins;
    private ItemClickListener listener;

    public SkinsRecyclerAdapter(Context context, ArrayList<Skin> skins, ItemClickListener listener) {
        this.context = context;
        this.skins = skins;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.recycler_view_skins_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * פעולה זו היא הפעולה האחראית על השמת הנתונים המתאימים בכל שורה ברשימה וזיהוי לחיצה על שורה ברשימה.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textViewSkinName.setText(skins.get(position).getName());
        holder.textViewSkinMission.setText(skins.get(position).getMission());
        holder.image.setImageResource(skins.get(position).getImage());
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSkinName;
        TextView textViewSkinMission;
        ImageView image;
        ConstraintLayout rowLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSkinName = itemView.findViewById(R.id.textView_skinsRow_name);
            textViewSkinMission = itemView.findViewById(R.id.textView_skinsRow_mission);
            image = itemView.findViewById(R.id.imageView_skinsRow_image);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }

    public interface ItemClickListener {

        void onClick(int position);
    }
}
