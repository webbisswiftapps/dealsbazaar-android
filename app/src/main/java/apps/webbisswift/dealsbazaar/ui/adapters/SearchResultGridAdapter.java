package apps.webbisswift.dealsbazaar.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 01/04/2017.
 */

public class SearchResultGridAdapter extends RecyclerView.Adapter<SearchResultGridAdapter.ProductGridHolder> {

    ArrayList<ProductVM> products;
    Context mContext;




    public SearchResultGridAdapter(Context mContext) {
        this.products = new ArrayList<>();
        this.mContext = mContext;
    }



    @Override
    public SearchResultGridAdapter.ProductGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_item, null);
        SearchResultGridAdapter.ProductGridHolder mh = new SearchResultGridAdapter.ProductGridHolder(v, mContext);
        return mh;
    }

    @Override
    public void onBindViewHolder(SearchResultGridAdapter.ProductGridHolder holder, int position) {
        ProductVM product = products.get(position);

        holder.productTitle.setText(product.getProductName());
        String discount = product.getProductDiscount();
        if(discount!=null && !discount.isEmpty()){
            holder.productDiscount.setText(discount);
            holder.productDiscount.setVisibility(View.VISIBLE);
        }else holder.productDiscount.setVisibility(View.GONE);


        String oldPrice = product.getProductOldPrice();
        if(oldPrice!=null && !oldPrice.isEmpty()){
            holder.oldProductPrice.setVisibility(View.VISIBLE);
            holder.oldProductPrice.setText(oldPrice);
        }else holder.oldProductPrice.setVisibility(View.GONE);

        holder.productPrice.setText(product.getProductPrice());
        Glide.with(mContext).load(product.getProductImageURL())
                .override(120, 100)
                .centerCrop()
                .into(holder.productImage);

        if(product.getProductStoreLogo()!=null && !product.getProductStoreLogo().isEmpty()) {
            Log.d("SearchResultGridAdapter","Loading store logo.. :"+product.getProductName());
            Glide.with(mContext).load(product.getProductStoreLogo()).thumbnail(0.3f).into(holder.storeLogo);
        }else{
            Log.d("SearchResultGridAdapter","Not Loading store logo.. :"+product.getProductStoreLogo());
        }

        holder.mainView.setTag(product);
    }

    @Override
    public int getItemCount() {
        return (null != products ? products.size() : 0);
    }


    public void addProduct(ProductVM product){
        products.add(product);
        this.notifyDataSetChanged();
    }



    static  class ProductGridHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productTitle)
        public CTextView productTitle;
        @BindView(R.id.productDiscount)
        public CTextView productDiscount;
        @BindView(R.id.oldProductPrice)
        public CTextView oldProductPrice;
        @BindView(R.id.productPrice)
        public CTextView productPrice;
        @BindView(R.id.productImage)
        public ImageView productImage;
        @BindView(R.id.storeLogo)
        public ImageView storeLogo;

        public View mainView;

        Context context;


        public ProductGridHolder(View view, Context ctx) {
            super(view);

            mainView = view;
            this.context = ctx;
            ButterKnife.bind(this, view);
            oldProductPrice.setPaintFlags(oldProductPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductVM product = (ProductVM) v.getTag();
                    Intent i = new Intent(context, WebViewActivity.class);
                    i.putExtra("LINK", product.getProductURL());
                    context.startActivity(i);
                }
            });
        }

    }


}