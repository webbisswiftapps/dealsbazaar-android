package apps.webbisswift.dealsbazaar.ui.screens.offerdetails;

import android.content.Context;

import javax.inject.Singleton;

import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.repo.APIOfferDetailsRepo;
import apps.webbisswift.dealsbazaar.domain.repo.base.OfferDetailsRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 29/03/2017.
 */

@Module
public class OfferDetailsModule {

    @Provides
    public OfferDetailsMVPContract.Presenter providePresenter(OfferDetailsMVPContract.Model model){
        return new OfferDetailsPresenter(model);
    }

    @Provides
    public OfferDetailsMVPContract.Model provideModel(OfferDetailsRepository repository){
        return new OfferDetailsModel(repository);
    }

    @Singleton
    @Provides
    public OfferDetailsRepository provideOfferDetailsRepo(NepdealsAPI api, Context context){
        return new APIOfferDetailsRepo(api, context);
    }


}
