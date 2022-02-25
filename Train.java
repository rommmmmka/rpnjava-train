import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Train implements Serializable {
    private List<Railcar> railcars;

    public Train() {
        this.railcars = new ArrayList<>();
    }

    public List<Railcar> getRailcars() {
        return railcars;
    }

    @Override
    public String toString() {
        return "Train{" +
                "railcars=" + railcars +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(railcars, train.railcars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(railcars);
    }

    public void addRailcar(Railcar railcar) {
        this.railcars.add(railcar);
    }
}
