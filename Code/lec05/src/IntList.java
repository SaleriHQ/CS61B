public class IntList {
    private int first;
    private IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    public int size() {
        if (rest == null)
            return 1;
        return 1 + rest.size();
    }

    public int iterativeSize() {
        if (rest == null)
            return 1;
        IntList p = rest;
        int res = 1;
        while (p != null) {
            res++;
            p = p.rest;
        }
        return res;
    }

    public void add(int x) {
        IntList p = this;

        while (p.rest != null) {
            p.rest = new IntList(p.first * p.first, p.rest);
            p = p.rest.rest;
        }
        p.rest = new IntList(p.first * p.first, new IntList(x, null));
    }

    public int get(int index) {
        if (index == 0)
            return first;
        IntList p = rest;
        for (int i = 1; i < index; ++i)
            p = p.rest;
        return p.first;
    }

    public void addAdjacent() {
        IntList p = this;

        while (p != null) {
            if (p.rest == null)
                return;
            if (p.first == p.rest.first) {
                p.first *= 2;
                p.rest = p.rest.rest;
                p = p.rest;
            } else {
                p = p.rest;
            }
        }
    }

}
