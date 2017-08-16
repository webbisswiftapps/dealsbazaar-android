package apps.webbisswift.dealsbazaar.domain.repo.base;

import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import io.reactivex.Observable;

/**
 * Created by biswas on 28/05/2017.
 */

public interface SearchHistoryRepository {

    public Observable<Boolean> insertHistory(String query);
    public Observable<List<SearchHistory>> getHistory();
}
