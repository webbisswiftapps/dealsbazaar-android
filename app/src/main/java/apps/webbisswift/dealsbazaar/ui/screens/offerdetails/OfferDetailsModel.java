package apps.webbisswift.dealsbazaar.ui.screens.offerdetails;

import apps.webbisswift.dealsbazaar.domain.net.exceptions.NoProductsFoundInOfferException;
import apps.webbisswift.dealsbazaar.domain.net.model.PageResponse;
import apps.webbisswift.dealsbazaar.domain.net.model.Product;
import apps.webbisswift.dealsbazaar.domain.net.model.Results;
import apps.webbisswift.dealsbazaar.domain.repo.base.OfferDetailsRepository;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by biswas on 29/03/2017.
 */

public class OfferDetailsModel implements OfferDetailsMVPContract.Model {

    OfferDetailsRepository repository;
    String nextPageURL;
    String storePath;

    public OfferDetailsModel(OfferDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ProductVM> loadProducts(String url, String storePath) {
        this.storePath = storePath;
        return this.repository.loadOffer(url, storePath)
                .flatMap(new Function<PageResponse, ObservableSource<Results>>() {
                    @Override
                    public ObservableSource<Results> apply(PageResponse pageResponse) throws Exception{
                        return Observable.just(pageResponse.getResults());
                    }
                }).flatMap(new Function<Results, ObservableSource<Product>>() {
                    @Override
                    public ObservableSource<Product> apply(Results results) throws Exception {

                        if(results.getCount()>0) {
                            nextPageURL = results.getNextPageURL();
                            return Observable.fromIterable(results.getProducts());
                        }else return Observable.error(new NoProductsFoundInOfferException());
                    }
                }).flatMap(new Function<Product, ObservableSource<ProductVM>>() {
                    @Override
                    public ObservableSource<ProductVM> apply(Product p) throws Exception {
                        ProductVM pvm = new ProductVM();
                        pvm.setProductName(p.getProductName());
                        pvm.setProductDiscount(p.getProductDiscount());
                        pvm.setProductImageURL(p.getProductImage());

                        if(p.getProductDiscount() != null && !p.getProductDiscount().isEmpty()){
                            try {
                                int value = Integer.parseInt(p.getProductDiscount().replaceAll("[^\\.0123456789]", ""));
                                pvm.setProductDiscountValue(value);
                                pvm.increaseWeight(value);
                            }catch (Exception e){

                            }
                        }

                        if (p.getProductOldPrice() != null) {
                            pvm.setProductPrice(p.getProductNewPrice());
                            pvm.setProductOldPrice(p.getProductOldPrice());
                        } else {
                            pvm.setProductPrice(p.getProductPrice());
                            pvm.setProductOldPrice(null);
                        }
                        pvm.setProductURL(p.getProductLink());

                        return Observable.just(pvm);
                    }
                });
    }

    @Override
    public Observable<ProductVM> loadNextPage() {
            return loadProducts(nextPageURL, storePath);

    }

    @Override
    public boolean hasNextPage() {
        return (nextPageURL != null && !nextPageURL.isEmpty());
    }
}
