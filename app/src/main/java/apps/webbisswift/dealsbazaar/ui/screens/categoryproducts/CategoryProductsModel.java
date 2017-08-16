package apps.webbisswift.dealsbazaar.ui.screens.categoryproducts;

import java.util.ArrayList;
import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.ValidStore;
import apps.webbisswift.dealsbazaar.domain.net.exceptions.NoProductsFoundInOfferException;
import apps.webbisswift.dealsbazaar.domain.net.model.PageResponse;
import apps.webbisswift.dealsbazaar.domain.net.model.Product;
import apps.webbisswift.dealsbazaar.domain.net.model.Results;
import apps.webbisswift.dealsbazaar.domain.repo.base.CategoryProductsRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryProductsModel implements CategoryProductsMVPContract.Model {

    CategoryProductsRepository repository;
    StoreRepository storeRepository;

    public CategoryProductsModel(CategoryProductsRepository repository, StoreRepository storeRepository){
        this.repository = repository;
        this.storeRepository = storeRepository;
    }

    @Override
    public Observable<DealSectionVM> loadProducts(final String main, final String sub) {
        return storeRepository.getAllStoresAtOnce()
                .flatMap(new Function<List<ValidStore>, ObservableSource<DealSectionVM>>() {
                    @Override
                    public ObservableSource<DealSectionVM> apply(List<ValidStore> validStores) throws Exception {
                        Observable<DealSectionVM> mainObservable = null;
                        for(ValidStore vs: validStores){
                            if(mainObservable == null){
                                mainObservable = loadProducts(main, sub, vs.getStorePath());
                            }else{
                                mainObservable = Observable.mergeDelayError(mainObservable, loadProducts(main,sub, vs.getStorePath()));
                            }

                        }
                        return mainObservable;
                    }
                });
    }

    private Observable<DealSectionVM> loadProducts(String main, final String sub, final String store) {
        return repository.loadProducts(main, sub, store)
                .filter(new Predicate<PageResponse>() {
                    @Override
                    public boolean test(PageResponse pageResponse) throws Exception {
                        return pageResponse.getSuccess();
                    }
                })
                .flatMap(new Function<PageResponse, ObservableSource<Results>>() {
                    @Override
                    public ObservableSource<Results> apply(PageResponse pageResponse) throws Exception {
                        return Observable.just(pageResponse.getResults());
                    }
                }).filter(new Predicate<Results>() {
                    @Override
                    public boolean test(Results results) throws Exception {
                        return results.getCount() > 0;
                    }
                }).flatMap(new Function<Results, ObservableSource<DealSectionVM>>() {
                    @Override
                    public ObservableSource<DealSectionVM> apply(Results results) throws Exception {

                        if (results.getCount() > 0) {
                            DealSectionVM vm = new DealSectionVM();
                            vm.setItemCount(results.getCount());
                            vm.setStoreName(results.getStore());
                            vm.setStoreLogoURL(results.getStoreLogo());
                            vm.setDealTitle("");
                            vm.setNextPageURL(results.getNextPageURL());
                            vm.setStorePath(store);

                            ArrayList<ProductVM> pvms = new ArrayList<ProductVM>();
                            for (Product p : results.getProducts()) {
                                ProductVM pvm = new ProductVM();
                                vm.increaseWeight(1);
                                pvm.setProductName(p.getProductName());
                                pvm.setProductDiscount(p.getProductDiscount());
                                pvm.setProductDiscount(p.getProductDiscount());
                                if (p.getProductDiscount() != null && !p.getProductDiscount().isEmpty()) {
                                    try {
                                        int value = Integer.parseInt(p.getProductDiscount().replaceAll("[^\\.0123456789]", ""));
                                        pvm.setProductDiscountValue(value);
                                        vm.increaseWeight(value);
                                        //pvm.increaseWeight(value);
                                    } catch (Exception e) {

                                    }
                                }

                                pvm.setProductImageURL(p.getProductImage());

                                if (p.getProductOldPrice() != null) {
                                    pvm.setProductPrice(p.getProductNewPrice());
                                    pvm.setProductOldPrice(p.getProductOldPrice());
                                } else {
                                    pvm.setProductPrice(p.getProductPrice());
                                    pvm.setProductOldPrice(null);
                                }
                                pvm.setProductURL(p.getProductLink());
                                pvms.add(pvm);
                            }

                            vm.setItems(pvms);

                            return Observable.just(vm);
                        } else return Observable.error(new NoProductsFoundInOfferException());
                    }
                });
    }
}
