package packt.util.test;

import packt.util.SortUtil;

import java.util.Arrays;
import java.util.List;

public class SortUtilTestMain {

    public static void main(String[] args) {
        SortUtil sortUtil = new SortUtil();
        List out = sortUtil.sortList(Arrays.asList("b", "a", "c"));
        assert out.size() == 3;
        assert "a".equals(out.get(0));
        assert "b".equals(out.get(1));
        assert "c".equals(out.get(2));
    }
}
