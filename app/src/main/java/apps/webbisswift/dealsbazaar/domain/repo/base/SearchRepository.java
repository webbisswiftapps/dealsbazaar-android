package apps.webbisswift.dealsbazaar.domain.repo.base;

import apps.webbisswift.dealsbazaar.domain.net.model.SearchResponse;
import io.reactivex.Observable;

/**
 * Created by biswas on 01/04/2017.
 */

public interface SearchRepository {
    public Observable<SearchResponse> search(String query);
    public Observable<SearchResponse> search(String query, String storePath);
}
