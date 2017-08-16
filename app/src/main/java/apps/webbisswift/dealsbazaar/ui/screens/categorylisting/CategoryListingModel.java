package apps.webbisswift.dealsbazaar.ui.screens.categorylisting;

import java.util.ArrayList;
import java.util.List;

import apps.webbisswift.dealsbazaar.domain.net.model.Category;
import apps.webbisswift.dealsbazaar.domain.repo.base.ValidCategoriesRepository;
import apps.webbisswift.dealsbazaar.ui.viewmodels.CategoryVM;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryListingModel implements CategoryListingMVPContract.Model {

    ValidCategoriesRepository categoriesRepo;

    public CategoryListingModel(ValidCategoriesRepository repository){
        this.categoriesRepo = repository;
    }

    @Override
    public Observable<List<CategoryVM>> getCategories(String forKey) {
        return  this.categoriesRepo.getCategories(forKey)
                .flatMap(new Function<List<Category>, ObservableSource<List<CategoryVM>>>() {
                    @Override
                    public ObservableSource<List<CategoryVM>> apply(List<Category> categories) throws Exception {
                        ArrayList<CategoryVM> categoryVMs = new ArrayList<CategoryVM>();

                        for(Category cat:categories){
                            CategoryVM cvm = new CategoryVM();
                            cvm.setTitle(cat.getName());
                            cvm.setKey(cat.getKey());
                            cvm.setIcon(cat.getIcon());
                            cvm.setSubItem(false);
                            if(cat.getSubCategories() != null && cat.getSubCategories().size() > 0){
                                ArrayList<CategoryVM> subs = new ArrayList<CategoryVM>();
                                for(Category catx:cat.getSubCategories()){
                                    CategoryVM cvmx = new CategoryVM();
                                    cvmx.setIcon(catx.getIcon());
                                    cvmx.setTitle(catx.getName());
                                    cvmx.setKey(catx.getKey());
                                    cvmx.setSubCategories(null);
                                    cvmx.setSubItem(true);
                                    subs.add(cvmx);
                                }
                                cvm.setSubCategories(subs);
                            }else cvm.setSubCategories(null);
                            categoryVMs.add(cvm);
                        }
                        return Observable.just((List<CategoryVM>) categoryVMs);
                    }
                });
    }
}
