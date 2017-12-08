package xyz.javista.config

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class CustomPageableResponse<T> extends PageImpl<T> {

    private int number
    private int size
    private int totalPages
    private int numberOfElements
    private long totalElements
    private boolean previousPage
    private boolean first
    private boolean nextPage
    private boolean last
    private List<T> content
    private Sort sort
    
    CustomPageableResponse(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total)
    }

    CustomPageableResponse(List<T> content) {
        super(content)
    }

    CustomPageableResponse() {
        super(new ArrayList<T>())
    }

    public PageImpl<T> pageImpl() {
        return new PageImpl<T>(getContent(), new PageRequest(getNumber(),
                getSize(), getSort()), getTotalElements())
    }

    int getNumber() {
        return number
    }

    void setNumber(int number) {
        this.number = number
    }

    int getSize() {
        return size
    }

    void setSize(int size) {
        this.size = size
    }

    int getTotalPages() {
        return totalPages
    }

    void setTotalPages(int totalPages) {
        this.totalPages = totalPages
    }

    int getNumberOfElements() {
        return numberOfElements
    }

    void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements
    }

    long getTotalElements() {
        return totalElements
    }

    void setTotalElements(long totalElements) {
        this.totalElements = totalElements
    }

    boolean getPreviousPage() {
        return previousPage
    }

    void setPreviousPage(boolean previousPage) {
        this.previousPage = previousPage
    }

    boolean getFirst() {
        return first
    }

    void setFirst(boolean first) {
        this.first = first
    }

    boolean getNextPage() {
        return nextPage
    }

    void setNextPage(boolean nextPage) {
        this.nextPage = nextPage
    }

    boolean getLast() {
        return last
    }

    void setLast(boolean last) {
        this.last = last
    }

    List<T> getContent() {
        return content
    }

    void setContent(List<T> content) {
        this.content = content
    }

    Sort getSort() {
        return sort
    }

    void setSort(Sort sort) {
        this.sort = sort
    }
}
