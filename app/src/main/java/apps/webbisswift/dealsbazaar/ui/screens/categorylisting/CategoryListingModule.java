package apps.webbisswift.dealsbazaar.ui.screens.categorylisting;

import apps.webbisswift.dealsbazaar.domain.repo.base.ValidCategoriesRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 15/05/2017.
 */

@Module
public class CategoryListingModule {

    @Provides
    public CategoryListingMVPContract.Presenter providePresenter(CategoryListingMVPContract.Model model){
        return new CategoryListingPresenter(model);
    }

    @Provides
    public CategoryListingMVPContract.Model provideModel(ValidCategoriesRepository categoriesRepository){
        return new CategoryListingModel(categoriesRepository);
    }

}
