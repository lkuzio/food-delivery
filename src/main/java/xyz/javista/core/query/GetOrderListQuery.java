package xyz.javista.core.query;


public class GetOrderListQuery extends CustomPageableQuery {

    private String endDate;

    public GetOrderListQuery(int size, int offset) {
        super(size, offset);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
