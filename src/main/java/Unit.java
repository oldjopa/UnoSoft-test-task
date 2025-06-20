import java.util.Objects;

public record Unit(Integer index, Long value) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(value, unit.value) && Objects.equals(index, unit.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, value);
    }
}
