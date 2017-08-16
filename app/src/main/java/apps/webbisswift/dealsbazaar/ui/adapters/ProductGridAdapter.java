package apps.webbisswift.dealsbazaar.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 26/03/2017.
 */

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.ProductGridHolder> {

    List<ProductVM> products;
    Context mContext;

    ProductGridActionInterface gridActionInterface;

    public ProductGridAdapter(List<ProductVM> products, Context mContext, ProductGridActionInterface gridActionInterface) {
        this.products = products;
        Collections.sort(this.products, new Comparator<ProductVM>() {
            @Override
            public int compare(ProductVM t1, ProductVM t2) {
                return Integer.valueOf(t2.getWeight()).compareTo(t1.getWeight());
            }
        });
        this.mContext = mContext;
        this.gridActionInterface = gridActionInterface;
    }



    @Override
    public ProductGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_grid_item, null);
        ProductGridHolder mh = new ProductGridHolder(v, mContext, this.gridActionInterface);
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
        Glide.with(mContext)
                .load(product.getProductImageURL())
                .override(120, 100)
                .centerCrop()
                .into(holder.productImage);

        holder.mainView.setTag(product);
    }

    @Override
    public int getItemCount() {
        return (null != products ? getMaxProductCount() : 0);
    }

    private int getMaxProductCount(){
        //Show maximum of 10 products.
        return (products.size() > 10 ? 10 : products.size());
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



        public ProductGridHolder(View view, Context ctx, final ProductGridActionInterface gridActionInterface) {
            super(view);

            mainView = view;
            this.context = ctx;
            ButterKnife.bind(this, view);
            oldProductPrice.setPaintFlags(oldProductPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ProductVM product = (ProductVM) v.getTag();
                    if(gridActionInterface!=null){
                        gridActionInterface.onProductClicked(product.getProductURL(), product.getProductName());
                    }
                }
            });
        }

    }


}
