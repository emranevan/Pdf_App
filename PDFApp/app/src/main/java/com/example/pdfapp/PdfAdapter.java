package com.example.pdfapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdfapp.listeners.OnPdfFileSelectListener;

import java.io.File;
import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfViewHolder> {
    private Context context;
    private List<File> pdfFiles;
    private OnPdfFileSelectListener listener;
    public PdfAdapter(Context context, List<File> pdfFiles, OnPdfFileSelectListener listener) {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PdfViewHolder(LayoutInflater.from(context).inflate(R.layout.element_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
            holder.tvFileName.setText(""+pdfFiles.get(position).getName());
            holder.tvFileName.setSelected(true);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onPdfSelected(pdfFiles.get(position));

                    /*File file = pdfFiles.get(position);

                    //Toast.makeText(MainActivity.this, songName, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, DocumentActivity.class)
                            .putExtra("songs", "")
                            .putExtra("songname", "")
                            .putExtra("pos", position)
                            .putExtra("path",file.getAbsolutePath()));*/
                }
            });
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }
}
