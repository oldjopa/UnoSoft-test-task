import lombok.Getter;

import java.util.*;

@Getter
public class Group {
    private final Set<List<Long>> lines = new HashSet<>();
    public void addLine(List<Long> line) {
        lines.add(line);
    }
}
