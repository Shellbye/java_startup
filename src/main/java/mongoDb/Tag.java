package mongoDb;

/**
 * Created by shellbye on 10/31/14.
 */
public class Tag implements Comparable<Tag> {
    private String key;
    private int count;
    private int rank;

    public Tag(Object count, Object rank, Object key) {
        super();
        this.count = (int)count;
        this.rank = (int)rank;
        this.key = (String)key;
    }

    public int compareTo(Tag compareTag) {
        int compareCount = compareTag.getCount();
        if (compareCount == this.getCount()){
            return compareTag.getKey().compareTo(this.getKey());
        }
        // DES
        return compareCount - this.getCount();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
