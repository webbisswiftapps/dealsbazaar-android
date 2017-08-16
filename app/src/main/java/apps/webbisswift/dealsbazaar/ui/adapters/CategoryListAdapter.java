package apps.webbisswift.dealsbazaar.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.ui.viewmodels.CategoryVM;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public interface CategoryListInterface{
        void onCategorySelected(CategoryVM category);
    }

    private static final int VIEW_TYPE_PARENT = 0;
    private static final int VIEW_TYPE_CHILD = 1;

    ArrayList<CategoryVM> shownList;
    List<CategoryVM> main;

    Context mContext;
    CategoryListInterface listInterface;

    public CategoryListAdapter(Context mContext, CategoryListInterface listInterface){
        this.mContext = mContext;
        this.shownList = new ArrayList<>();
        this.listInterface = listInterface;
    }

    public void setItems(List<CategoryVM> mainCats){
        this.main = mainCats;
        shownList.addAll(this.main);
        this.notifyDataSetChanged();

    }

    /* Click Listeners*/
    private View.OnClickListener clickListener= new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            CategoryVM cat = shownList.get(position);

            if(cat.isSubItem() || !cat.hasSubCategories()){
                /* Is child item or a parent item that has no subcategories:: Open category details page! */
                if(listInterface!=null){
                    listInterface.onCategorySelected(cat);
                }
            }else{
                /* Is parent item, with subcategories, expand the parent category if not expanded else collapse */
                if(cat.isExpanded()){
                    /* Collapse*/
                    collapseAndRemoveItems(cat.getSubCategories(), position + 1);
                    cat.setExpanded(false);
                }else{
                    /*Expand*/
                    expandAndAddItems(cat.getSubCategories(), position + 1);
                    cat.setExpanded(true);
                }
            }

        }
    };

    private void expandAndAddItems(List<CategoryVM> subcategories, int at){
            this.shownList.addAll(at, subcategories);
            this.notifyDataSetChanged();

    }

    private void collapseAndRemoveItems(List<CategoryVM> subcategories, int at){
        this.shownList.removeAll(subcategories);
        this.notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case VIEW_TYPE_CHILD:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_sub_item, parent, false);
                return new CategoryChildViewHolder(v2, clickListener);
            default:
            case VIEW_TYPE_PARENT:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.catgory_parent_item, parent, false);
                return new CategoryParentViewHolder(v, clickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryVM cat = shownList.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_CHILD:
                CategoryChildViewHolder ccvh = ((CategoryChildViewHolder)holder);
                ccvh.titleText.setText(cat.getTitle());
                ccvh.mainView.setTag(position);
                break;
            case VIEW_TYPE_PARENT:
                CategoryParentViewHolder cpvh = ((CategoryParentViewHolder)holder);
                cpvh.titleText.setText(cat.getTitle());
                cpvh.mainView.setTag(position);
                if(!cat.hasSubCategories()){
                    cpvh.mParentDropDownArrow.setVisibility(View.GONE);
                }else{
                    cpvh.mParentDropDownArrow.setVisibility(View.VISIBLE);
                }


                if(cat.isExpanded()){
                    cpvh.dropdownImg.setImageResource(R.drawable.ic_category_down);
                }else cpvh.dropdownImg.setImageResource(R.drawable.ic_category_main);



                break;
        }
    }



    @Override
    public int getItemCount() {
        return shownList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return shownList.get(position).isSubItem() ? VIEW_TYPE_CHILD : VIEW_TYPE_PARENT;
    }

    /* View Holder Static Classes */

    public static class CategoryParentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        public TextView titleText;
        @BindView(R.id.parent_list_item_expand_arrow)
        public ImageView mParentDropDownArrow;

        @BindView(R.id.imageView)
        ImageView dropdownImg;

        public View mainView;


        public CategoryParentViewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);
            this.mainView = itemView;

            ButterKnife.bind(this, itemView);

            mainView.setOnClickListener(listener);
        }
    }


    public static class CategoryChildViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        public TextView titleText;

        public View mainView;

        public CategoryChildViewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);
            this.mainView = itemView;
            ButterKnife.bind(this, itemView);
            mainView.setOnClickListener(listener);
        }
    }

}
