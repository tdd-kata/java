package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.iterator;

import org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.social.media.Profile;

public interface ProfileIterator {
    boolean hasNext();

    Profile getNext();

    void reset();
}
