package com.cosm.common.event;

import java.time.Instant;
import java.util.Objects;
import javax.json.bind.annotation.JsonbProperty;

public abstract class CosmEvent {

    private EventPeriod eventPeriod;
    private String pscId;

    @JsonbProperty
    private Instant instant;

    public CosmEvent() {

    }

    public CosmEvent(final EventPeriod period, final String pscId) {
        Objects.requireNonNull(period);
        this.eventPeriod = period;

        Objects.requireNonNull(pscId);
        this.pscId = pscId;

        this.instant = Instant.now();
    }

    public CosmEvent(final EventPeriod period, final String pscId, final Instant instant) {
        Objects.requireNonNull(period);
        this.eventPeriod = period;

        Objects.requireNonNull(pscId);
        this.pscId = pscId;

        Objects.requireNonNull(instant);
        this.instant = instant;
    }

    public EventPeriod getEventPeriod() {
        return eventPeriod;
    }

    public void setEventPeriod(EventPeriod ep) {
        this.eventPeriod = ep;
    }

    public String getPscId() {
        return pscId;
    }

    public void setPscId(String id) {
        this.pscId = id;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant time) {
        this.instant = time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventPeriod == null) ? 0 : eventPeriod.hashCode());
        result = prime * result + ((instant == null) ? 0 : instant.hashCode());
        result = prime * result + ((pscId == null) ? 0 : pscId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CosmEvent other = (CosmEvent) obj;
        if (eventPeriod == null) {
            if (other.eventPeriod != null) {
                return false;
            }
        } else if (!eventPeriod.equals(other.eventPeriod)) {
            return false;
        }
        if (instant == null) {
            if (other.instant != null) {
                return false;
            }
        } else if (!instant.equals(other.instant)) {
            return false;
        }
        if (pscId == null) {
            if (other.pscId != null) {
                return false;
            }
        } else if (!pscId.equals(other.pscId)) {
            return false;
        }
        return true;
    }

}
