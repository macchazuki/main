package planmysem.model.slot;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

import planmysem.common.Utils;

/**
 * A read-only immutable interface for a Slot in the Planner.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlySlot {
    String getName();
    String getLocation();
    String getDescription();
    int getDuration();
    LocalTime getStartTime();

    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the slot's internal tags.
     */
    Set<String> getTags();

    /**
     * Returns true if the values inside this object is same as
     * those of the other (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlySlot other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && Objects.equals(other.getLocation(), this.getLocation())
                && Objects.equals(other.getDescription(), this.getDescription())
                && other.getStartTime().equals(this.getStartTime())
                && other.getDuration() == this.getDuration()
                && other.getTags().equals(this.getTags()));
    }

    /**
     * Formats the slot as text, showing all slot details.
     */
    default String getAsTextShowAll() {
        final StringBuilder sb = new StringBuilder();

        sb.append("Slot Details:");

        sb.append("\nName: ");
        sb.append(getName());

        sb.append("\nLocation: ");
        sb.append(getLocation());

        sb.append("\nDescription: ");
        sb.append(getDescription());

        sb.append("\nTime: ");
        sb.append(getStartTime());

        sb.append(" to ");
        sb.append(Utils.getEndTime(getStartTime(), getDuration()));

        sb.append("\nDuration: ");
        sb.append(getDuration());

        sb.append("\nTags: ");

        int count = 1;
        for (String tag : getTags()) {
            sb.append("\n");
            sb.append("\t");
            sb.append(count);
            sb.append(".\t");
            sb.append(tag);
            count++;
        }
        return sb.toString();
    }
}
