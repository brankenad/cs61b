public class OffByN implements CharacterComparator {
    private int offByN;

    public OffByN() {
        offByN = 0;
    }

    public OffByN(int N) {
        offByN = N;
    }

    @Override
    public boolean equalChars(char x, char y){
        int comp = Math.abs(x - y);
        if (comp == offByN) {
            return true;
        }
        return false;
    }
}