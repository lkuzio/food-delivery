package xyz.javista.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class CustomPageableQuery implements Pageable{

    private int size;
    private int offset;
    private Sort sort;

    public CustomPageableQuery(int size, int offset) {
        this.size = size;
        this.offset = offset;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
