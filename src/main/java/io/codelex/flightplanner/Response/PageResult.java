package io.codelex.flightplanner.Response;

import java.util.List;

public class PageResult<F> {
    private int page;
    private int totalItems;
    private List<F> items;

    public PageResult(int page, int totalItems, List<F> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<F> getItems() {
        return items;
    }

    public void setItems(List<F> items) {
        this.items = items;
    }
}
