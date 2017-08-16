package apps.webbisswift.dealsbazaar.ui.adapters;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.ui.adapters.RowItems.SliderRow;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.adapters.RowItems.NativeAdVM;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 26/03/2017.
 */

public class DealSectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<IRow> deals;
    private Context mContext;
    private ProductGridActionInterface gridActionInterface;


    public DealSectionAdapter(Context context, ProductGridActionInterface gridActionInterface, int adsEvery) {
        this.deals = new ArrayList<>();
        this.mContext = context;
        this.gridActionInterface = gridActionInterface;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType){
            case IRow.TYPE_AD:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_ad_row, null);
                return new AdViewHolder(v, this.calculatedWidth, IRow.TYPE_AD);
            case IRow.TYPE_AD_LARGE:
                View v4 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_ad_row, null);
                return new AdViewHolder(v4, this.calculatedWidth, IRow.TYPE_AD_LARGE);
            case IRow.TYPE_SECTION:
                    View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.deals_row_item, null);
                    return new ItemRowHolder(v2, mContext);
            default:
                View v3 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.general_view_holder, null);
                return new GeneralViewHolder(v3);

        }
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {

        Log.d("DealSectionAdapter","onBindViewHolder: "+position);
        int viewType = getItemViewType(position);


        switch(viewType){

            case IRow.TYPE_SLIDER:

                GeneralViewHolder slideViewHoldr = (GeneralViewHolder) h;
                slideViewHoldr.holder.removeAllViews();

                View slider = ((SliderRow)deals.get(position)).getRoot();

                slideViewHoldr.holder.removeAllViews();
                slideViewHoldr.holder.addView(slider);
                break;

            case IRow.TYPE_SECTION:
                ItemRowHolder holder = (ItemRowHolder) h;
                final DealSectionVM deal = (DealSectionVM) deals.get(position);

                final String sectionName = deal.getDealTitle();

                holder.listingTitle.setText(sectionName);

                ProductGridAdapter itemListDataAdapter = new ProductGridAdapter(deal.getItems(),mContext, this.gridActionInterface);
                holder.gridRecycler.setAdapter(itemListDataAdapter);


                holder.btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(gridActionInterface!=null){
                            gridActionInterface.onViewAllClicked(deal);
                        }
                    }
                });

                if(deal.getItemCount() > 10){
                    holder.btnMore.setVisibility(View.VISIBLE);
                } else holder.btnMore.setVisibility(View.GONE);


                Glide.with(mContext)
                        .load(deal.getStoreLogoURL())
                        .thumbnail(0.1f)
                        .override(100, 60)
                        .into(holder.listingLogo);

                break;
            case IRow.TYPE_AD:
                ((AdViewHolder)h).showAd();
                break;
                
        }

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    @Override
    public int getItemViewType(int position) {
        return deals.get(position).getType();
    }



    public void addRow(IRow row){
        this.deals.add(row);
        this.notifyDataSetChanged();
    }




    int calculatedWidth = 310;

    public void fillAds(int recyclerViewWidth, boolean hasHeader){


        if(deals.size() > 0){

             calculatedWidth = (int)(recyclerViewWidth / Utils.getScreenDensity(mContext));

            if(deals.size() > 2){
                //add 1 add to middle of list and one ad to the end
                NativeAdVM middleAd = new NativeAdVM(IRow.TYPE_AD_LARGE);

                int middle = deals.size() / 2;

                if(hasHeader)
                    middle = middle + 1;

                this.deals.add(middle, middleAd);
            }

            NativeAdVM bottomAd = new NativeAdVM(IRow.TYPE_AD);
            this.deals.add(deals.size(), bottomAd);



            this.notifyDataSetChanged();

        }




    }

    public void clearAll(){
        this.deals.clear();
        this.notifyDataSetChanged();
    }

    /* Static implementation of ViewHolder */

      static class ItemRowHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.listingTitle)
        public TextView listingTitle;
        @BindView(R.id.listingLogo)
        public ImageView listingLogo;
        @BindView(R.id.moreBtn)
        public TextView btnMore;



         @BindView(R.id.recycler_view_list)
         public  RecyclerView gridRecycler;


         ItemRowHolder(View view, Context ctx) {
            super(view);
            ButterKnife.bind(this, view);

            SnapHelper helper = new GravitySnapHelper(GravityCompat.START);
            helper.attachToRecyclerView(gridRecycler);
            gridRecycler.setHasFixedSize(true);
            gridRecycler.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false));

        }

    }


     static class AdViewHolder extends RecyclerView.ViewHolder{

          @BindView(R.id.adHolder)
          public FrameLayout adHolder;

          public NativeExpressAdView adView;



         AdViewHolder(View v, int width, int type){
            super(v);
            ButterKnife.bind(this, v);

             adView = new NativeExpressAdView(adHolder.getContext());
             adHolder.addView(adView);

             if(type == IRow.TYPE_AD_LARGE) {
                 adView.setAdUnitId(adView.getContext().getString(R.string.list_native_large));
                 AdSize size = new AdSize(width - 10, 320);
                 adView.setAdSize(size);
             }else{
                 adView.setAdUnitId(adView.getContext().getString(R.string.banner_native_home));
                 AdSize size = new AdSize(width - 10, adView.getContext().getResources().getInteger(R.integer.banner_height_home));
                 adView.setAdSize(size);
             }


             showAd();

         }


         public void showAd(){

             AdRequest request = new AdRequest.Builder()
                     .addTestDevice("D97506CE44741D62F39273476ECCCA35")
                     .addTestDevice("EFFD38F91EBB8C0FE33D997E91FFB89E")
                     .build();



             this.adView.loadAd(request);

         }


    }


    static class GeneralViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.holder)
        public FrameLayout holder;


        GeneralViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
