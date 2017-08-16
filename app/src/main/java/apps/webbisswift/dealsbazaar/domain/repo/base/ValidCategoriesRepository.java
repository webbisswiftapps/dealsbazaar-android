package apps.webbisswift.dealsbazaar.domain.repo.base;

import java.util.List;

import apps.webbisswift.dealsbazaar.domain.net.model.Category;
import io.reactivex.Observable;

/**
 * Created by biswas on 14/05/2017.
 */

public interface ValidCategoriesRepository {

    public Observable<List<Category>> getCategories(String main);

}
