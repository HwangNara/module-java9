package packt.util.impl.javasort;

import java.util.Collections;
import java.util.List;

public class JavaSortUtilImpl implements packt.util.SortUtil {

    @Override
    public <T extends Comparable> List<T> sortList(List<T> list) {
        Collections.sort(list);
        return list;
    }

    @Override
    public int getIdealMaxInputLength() {
        return Integer.MAX_VALUE;
    }

}
