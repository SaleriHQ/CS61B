public class RotatingSLList<Item> extends SLList<Item> {

    public void rotateRight() {
        addFirst(removeLast());
    }

}
