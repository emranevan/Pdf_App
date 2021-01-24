package com.example.pdfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdfapp.listeners.OnPdfFileSelectListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnPdfFileSelectListener {

    private PdfAdapter pdfAdapter;

    private List<File> pdfLists;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        runtimePermission();
    }

    public void runtimePermission()
    {
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayPdf();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Permission Required!!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();

    }

    public ArrayList<File> findPdf (File file)
    {
        ArrayList<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles();

        for (File singlefile: files)
        {
            if (singlefile.isDirectory() && !singlefile.isHidden())
            {
                arrayList.addAll(findPdf(singlefile));
            }
            else
            {
                if (singlefile.getName().endsWith(".pdf"))
                {
                    arrayList.add(singlefile);
                }
            }
        }
        return arrayList;
    }

    public void displayPdf(){
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        pdfLists = new ArrayList<>();
        pdfLists.addAll(findPdf(Environment.getExternalStorageDirectory()));
        pdfAdapter = new PdfAdapter(this,pdfLists,this);
        recyclerView.setAdapter(pdfAdapter);
    }

    /*void displayPdf()
    {

        final ArrayList<File> myPdf = findPdf(Environment.getExternalStorageDirectory());

        items = new String[myPdf.size()];
        for (int i = 0; i<myPdf.size();i++)
        {
            items[i] = myPdf.get(i).getName().toString().replace(".pdf", "");

        }
        *//*ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(myAdapter);*//*
    }*/

    @Override
    public void onPdfSelected(File file) {
        //Toast.makeText(MainActivity.this, file.getName(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, DocumentActivity.class)
                .putExtra("path",file.getAbsolutePath()));
    }
}