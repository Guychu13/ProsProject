package com.example.pros;

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

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Skin> skins;

    public RecyclerAdapter(Context context, ArrayList<Skin> skins) {
        this.context = context;
        this.skins = skins;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.recycler_view_skins_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textViewSkinName.setText(skins.get(position).getName());
        holder.textViewSkinMission.setText(skins.get(position).getMission());
        holder.image.setImageResource(skins.get(position).getImage());
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainScreenActivity.class);
                intent.putExtra("chosenSkinImageId", skins.get(position).getImage());
                intent.putExtra("isFromSkinsScreen", true);
                context.startActivity(intent);
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
}
