package apps.webbisswift.dealsbazaar.ui.viewmodels;

import java.util.List;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryVM {

    private String title;
    private String icon;
    private String key;

    private boolean isSubItem;

    private boolean isExpanded = false;

    public void setSubItem(boolean subItem) {
        isSubItem = subItem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private List<CategoryVM> subCategories;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<CategoryVM> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryVM> subCategories) {
        this.subCategories = subCategories;
    }

    public boolean hasSubCategories() {
        return (subCategories != null && subCategories.size() > 0);
    }

    public boolean isSubItem(){
        return this.isSubItem;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
