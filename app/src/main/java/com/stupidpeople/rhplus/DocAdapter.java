package com.stupidpeople.rhplus;

/**
 * Created by Milenko on 04/06/2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;


public class DocAdapter extends RecyclerView.Adapter<DocHolder> implements View.OnClickListener {

    private List<PDFItem> pdfItems;
    private Context mContext;
    private View.OnClickListener listener;

    public DocAdapter(Context context, List<PDFItem> pdfItems) {
        this.pdfItems = pdfItems;
        this.mContext = context;
    }

    @Override
    public DocHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        v.setOnClickListener(this);
        DocHolder dh = new DocHolder(v);

        return dh;
    }

    @Override
    public void onBindViewHolder(DocHolder docHolder, int i) {
        PDFItem pdfItem = pdfItems.get(i);

        String imageUrl = pdfItem.getImageUrl();

        Picasso.with(mContext).load(imageUrl)
                .error(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
                .placeholder(R.mipmap.ic_launcher)
                .into(docHolder.thumbnail);

        docHolder.title.setText(pdfItem.getName());
        docHolder.description.setText(pdfItem.getDescription());
        docHolder.itemView.setTag(pdfItem);
    }

    @Override
    public int getItemCount() {
        return (null != pdfItems ? pdfItems.size() : 0);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

}