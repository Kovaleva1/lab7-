package models;

public class Label {
    private int sales;
    private int creatorId;



    public Label(int sales) {
        this.sales = sales;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }


    public  boolean validate() {
        if (sales <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Label{" +
                "sales=" + sales +
                '}';
    }
}
