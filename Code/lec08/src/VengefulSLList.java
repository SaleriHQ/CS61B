public class VengefulSLList<Item> extends SLList<Item> {
    SLList<Item> deletedItem;

    public VengefulSLList() {
        super();
        deletedItem = new SLList<>();
    }

    public VengefulSLList(Item x) {
        super(x);
        deletedItem = new SLList<>();
    }

    public void printLostItems() {
        deletedItem.print();
    }

    @Override
    public Item removeLast() {
        Item x = super.removeLast();
        deletedItem.addLast(x);
        return x;
    }
}
