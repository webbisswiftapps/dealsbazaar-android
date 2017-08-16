package apps.webbisswift.dealsbazaar.domain.repo;

import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchHistoryRepository;
import io.reactivex.Observable;

/**
 * Created by biswas on 28/05/2017.
 */

public class SearchHistoryRepo implements SearchHistoryRepository {

    @Override
    public Observable<Boolean> insertHistory(String query) {
        return SearchHistory.addHistory(query);
    }

    @Override
    public Observable<List<SearchHistory>> getHistory() {
        return SearchHistory.getSearchHistoryAll();
    }
}
