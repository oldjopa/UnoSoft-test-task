import lombok.Getter;

import java.util.*;

@Getter
public class Group {
    private final Set<List<String>> lines = new HashSet<>();
    public void addLine(List<String> line) {
        lines.add(line);
    }
}
