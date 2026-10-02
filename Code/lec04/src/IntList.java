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

    public int get(int index) {
        if (index == 0)
            return first;
        IntList p = rest;
        for (int i = 1; i < index; ++i)
            p = p.rest;
        return p.first;
    }

    public static IntList incrList(IntList L, int x) {
        IntList res = new IntList(L.first + x, null);
        IntList res_p = res;
        IntList L_p = L.rest;
        while (L_p != null) {
            res_p.rest = new IntList(L_p.first + x, null);
            L_p = L_p.rest;
            res_p = res_p.rest;
        }
        return res;
    }

    public static IntList dincrlist(IntList L, int x) {
        IntList p = L;
        while (p != null) {
            p.first += x;
            p = p.rest;
        }
        return L;
    }
}
