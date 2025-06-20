import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Writer {
    public static void writeResultToFile(String outputFile, List<Group> sortedGroups) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("Групп с более чем одним элементом: " + sortedGroups.size());
            writer.println();

            for (int i = 0; i < sortedGroups.size(); i++) {
                writer.println("Группа " + (i + 1));
                for (List<Long> groupLine : sortedGroups.get(i).getLines()) {
                    String line = groupLine.stream()
                            .map(v -> v == null ? "" : v.toString())
                            .collect(Collectors.joining(";"));
                    writer.println(line);
                }
                writer.println();
            }
        }
    }
}
