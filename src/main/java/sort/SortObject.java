package sort;

import java.util.Arrays;

/**
 * Created by shellbye on 10/31/14.
 */
public class SortObject {
    public static void main(String[] arg){
        // 1.创建比较对象
        Tag[] tags = new Tag[3];
        tags[0] = new Tag(0, "a");
        tags[1] = new Tag(1, "a");
        tags[2] = new Tag(2, "a");

        // 2.对象排序
        Arrays.sort(tags);

        // 3.存储排序结果
        int j = 0;
        for (Tag t : tags){
            System.out.println(j + ": " + " Count " + t.getCount() + " Key " + t.getKey());
            j++;
        }
    }
}
