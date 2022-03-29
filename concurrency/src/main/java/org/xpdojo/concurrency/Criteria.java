package org.xpdojo.concurrency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Criteria implements Iterable<Criterion> {
    private List<Criterion> criteria = new ArrayList<>();

    public void add(Criterion criterion) {
        criteria.add(criterion);
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }
}
