package com.stupidpeople.rhplus;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DrawerActivityDocs extends BaseActivity {

    String urlPlora = "https://www.dropbox.com/s/7zyiwovdkm676fv/Plora.pdf?dl=1";
    String imagePlora = "http://a5.mzstatic.com/us/r30/Purple3/v4/84/b2/49/84b2493c-3a64-66a1-12cc-9be3d9d96c7e/icon128-2x.png";
    String urlHuasca = "https://www.dropbox.com/s/bno8kl0m4pk6evj/La%20Huasca.pdf?dl=1";
    private RecyclerView mRecyclerView;
    private DocAdapter adapter;
    private String urlImageHuasca = "http://icons.iconarchive.com/icons/designbolts/free-movie-folder/128/Adventure-icon.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_docs);

        try {
            mRecyclerView = new RecyclerView(this);
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.hasFixedSize();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));


            List<PDFItem> lista = new ArrayList<>();
            lista.add(new PDFItem(urlPlora, imagePlora, "Plora", "Descripción de concepto innovador para la navegación de documentos"));
            lista.add(new PDFItem(urlHuasca, urlImageHuasca, "La Huasca", "Más que un libro, una experiencia que realza el verdadero sentido de la vida y la amistad"));

            adapter = new DocAdapter(this, lista);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PDFItem tag = (PDFItem) v.getTag();
                    downloadPresentation(tag);
                }
            });

            mRecyclerView.setAdapter(adapter);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        Initalize();
    }

    private void downloadPresentation(PDFItem pdfItem) {
        try {
            Toast.makeText(this, "Downloading presentation\n\"" + pdfItem.getName() + "\"", Toast.LENGTH_SHORT).show();

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfItem.getDropboxUrl()));
            // in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setTitle("RH+ :" + pdfItem.getName())
                        .setDescription(pdfItem.getDescription())
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pdfItem.getName() + ".pdf");//TODO consider other file formats

            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
