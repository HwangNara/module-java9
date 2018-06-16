package packt.util.test;

import org.junit.Before;
import org.junit.Test;
import packt.util.SortUtil;

import java.util.Arrays;
import java.util.List;

public class SortUtilTest {

    private SortUtil sortUtil;

    @Before
    public void setUp() {
        sortUtil = new SortUtil();
    }

    @Test
    public void testReturnsSameSize() {
        List out = sortUtil.sortList(Arrays.asList("b", "a", "c"));
        SortUtil sortUtil = new SortUtil();
        assert out.size() == 3;
    }

    @Test
    public void sortsList() {
        List out = sortUtil.sortList(Arrays.asList("b", "a", "c"));
        assert "a".equals(out.get(0));
        assert "b".equals(out.get(1));
        assert "c".equals(out.get(2));
    }


}