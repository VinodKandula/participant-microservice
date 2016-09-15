package com.amhzing.participant.web.request;

import com.amhzing.participant.web.MeasurableMetric;
import com.google.common.collect.ImmutableSet;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.Validate.noNullElements;

public class ParticipantIds implements MeasurableMetric {

    @NotEmpty
    private Set<String> id;

    public ParticipantIds() {
    }

    public ParticipantIds(final Set<String> id) {
        this.id = noNullElements(id);
    }

    @Override
    public String id() {
        return "participantIds";
    }

    @Override
    public long quantity() {
        return isNotEmpty(id) ? id.size() : 0;
    }

    public Set<String> getId() {
        return ImmutableSet.copyOf(id);
    }

    public void setId(final Set<String> id) {
        this.id = ImmutableSet.copyOf(id);
    }
}
