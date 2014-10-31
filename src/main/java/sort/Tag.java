package sort;

/**
 * Created by shellbye on 10/31/14.
 */
public class Tag implements Comparable<Tag> {
    private String key;
    private int count;

    public Tag(int count, String key) {
        super();
        this.count = count;
        this.key = key;
    }

    public int compareTo(Tag compareTag) {
        // DES
        return compareTag.getCount() - this.getCount();

        // ASC
//        return this.getCount() - compareTag.getCount();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
