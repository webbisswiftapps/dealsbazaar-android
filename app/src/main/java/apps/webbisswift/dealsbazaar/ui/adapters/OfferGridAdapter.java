package apps.webbisswift.dealsbazaar.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewActivity;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 29/03/2017.
 */

public class OfferGridAdapter extends RecyclerView.Adapter<OfferGridAdapter.ProductGridHolder> {

    ArrayList<ProductVM> products;
    Context mContext;


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    OnLoadMoreListener listener;

    private boolean loadMoreEnabled = false;

    public OfferGridAdapter(Context mContext, OnLoadMoreListener listener) {
        this.products = new ArrayList<>();
        this.mContext = mContext;
        this.listener = listener;
    }


    public boolean isLoadMoreEnabled() {
        return loadMoreEnabled;
    }


    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadMoreEnabled = loadMoreEnabled;
    }

    @Override
    public ProductGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_grid_item, null);
        ProductGridHolder mh = new ProductGridHolder(v, mContext);
        return mh;
    }

    @Override
    public void onBindViewHolder(ProductGridHolder holder, int position) {
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

        holder.mainView.setTag(product);

        if(loadMoreEnabled) {
            if (position == products.size() - 1) {
            /* If last item, then load more items */
                if (listener != null)
                    listener.onLoadMore();
            }
        }
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
                    i.putExtra("TITLE", product.getProductName());
                    context.startActivity(i);
                }
            });
        }

    }


}