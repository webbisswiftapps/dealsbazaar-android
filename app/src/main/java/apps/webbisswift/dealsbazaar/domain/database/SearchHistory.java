package apps.webbisswift.dealsbazaar.domain.database;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import io.reactivex.Observable;

/**
 * Created by biswas on 28/05/2017.
 */

public class SearchHistory extends SugarRecord{

    @Unique
    String query;
    long searchTimeStamp;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public long getSearchTimeStamp() {
        return searchTimeStamp;
    }

    public void setSearchTimeStamp(long searchTimeStamp) {
        this.searchTimeStamp = searchTimeStamp;
    }

    public SearchHistory(){

    }

    public SearchHistory(String query, long searchTimeStamp){
        this.query = query;
        this.searchTimeStamp = searchTimeStamp;
    }

    public static Observable<List<SearchHistory>> getSearchHistoryAll(){
        List<SearchHistory> slides = SearchHistory.listAll(SearchHistory.class, "SEARCH_TIME_STAMP DESC");
        if(slides.size()>0) {
            System.out.println(slides.size()+" search history items");
            return Observable.just(slides);
        }else return Observable.empty();
    }

    public static Observable<Boolean> addHistory(String query){
        try {
            SearchHistory newEntry = new SearchHistory(query, Utils.getCurrentTimeStamp());
            newEntry.save();
            System.out.println(query+" search history inserted");
            return Observable.just(true);
        }catch (Exception e){
            e.printStackTrace();
            return Observable.just(false);
        }
    }

    @Override
    public String toString() {
        return query;
    }
}
