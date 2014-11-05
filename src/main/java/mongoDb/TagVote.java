package mongoDb;

/**
 * Created by shellbye on 11/5/14.
 */
public class TagVote implements Comparable<TagVote> {
    private String key;
    private int vote;
    private int rank;

    public TagVote(Object vote, Object rank, Object key) {
        super();
        this.vote = (int) vote;
        this.rank = (int)rank;
        this.key = (String)key;
    }

    public int compareTo(TagVote compareTagVote) {
        int compareVote = compareTagVote.getVote();
        if (compareVote == this.getVote()){
            return compareTagVote.getKey().compareTo(this.getKey());
        }
        // DES
        return compareVote - this.getVote();
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
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
