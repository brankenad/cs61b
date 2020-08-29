public interface Deque<Item> {
    default public boolean isEmpty() {
        return size() == 0;
    }
    public int size();
    public Item get(int index);
    public void addFirst(Item item);
    public Item removeFirst();
    public void addLast(Item item);
    public Item removeLast();
    public void printDeque();
}
