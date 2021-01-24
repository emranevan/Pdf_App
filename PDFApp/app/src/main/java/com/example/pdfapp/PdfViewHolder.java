package com.example.pdfapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PdfViewHolder extends RecyclerView.ViewHolder {
    public TextView tvFileName;
    public CardView container;
    public PdfViewHolder(@NonNull View itemView) {
        super(itemView);

        tvFileName = itemView.findViewById(R.id.txtsongname);
        container = itemView.findViewById(R.id.cardview_container);
    }
}
