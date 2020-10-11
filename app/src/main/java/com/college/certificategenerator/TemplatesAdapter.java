package com.college.certificategenerator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.TemplateViewHolder> {

    private ArrayList<StorageReference> storageList;
    private Context context;

    public static class TemplateViewHolder extends RecyclerView.ViewHolder {
        public ImageView templateImage;
        public MaterialButton selectTemplate;

        public TemplateViewHolder(View itemView) {
            super(itemView);
            templateImage = itemView.findViewById(R.id.templateImageView);
            selectTemplate = itemView.findViewById(R.id.selectTemplateButton);
        }

        public Context getContext() {
            return itemView.getContext();
        }
    }

    public TemplatesAdapter(ArrayList<StorageReference> exampleList) {
        storageList = exampleList;
    }
    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_cardview, parent, false);
        TemplateViewHolder evh = new TemplateViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(TemplateViewHolder holder, int position) {
        context = holder.templateImage.getContext();
        StorageReference currentItem = storageList.get(position);
        Glide.with(context).load(currentItem).into(holder.templateImage);
        // holder.templateImage.setImageResource(currentItem.getImageResource());
        holder.selectTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Pratyush's backend here
                // 1. "details" HashMap needs to be obtained from ChooseTemplates.java
                // 2. Use "currentItem" to find the URL of the item and append the URL in String format to "details"
                // 3. Send "details" via your backend.
            }
        });

    }
    @Override
    public int getItemCount() {
        return storageList.size();
    }
}
