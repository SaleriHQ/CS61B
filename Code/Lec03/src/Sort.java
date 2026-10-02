public class Sort {
    public static void sort(String[] S, int start) {
        if (start == S.length)
            return;
        int smallestIndex = findSmallLest(S, start);
        Swap(S, start, smallestIndex);
        sort(S, start + 1);
    }

    public static void Swap(String[] S, int a, int b) {
        if (a < 0 || b >= S.length)
            return;
        String tmp = S[a];
        S[a] = S[b];
        S[b] = tmp;
    }

    public static int findSmallLest(String[] s, int start) {
        int res = start;
        for (int j = start; j < s.length; ++j)
            if (s[j].compareTo(s[res]) < 0)
                res = j;
        return res;
    }

    public static void main(String[] args) {
        String[] input = { "i", "hava", "an", "egg" };

        Sort.sort(input, 0);

        for (String s : input) {
            System.out.print(s + ' ');
        }

        System.out.println();

    }
}
