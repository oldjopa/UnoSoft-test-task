import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Writer {
    public static void writeResultToFile(String outputFile, List<Group> sortedGroups) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("Групп с более чем одним элементом: " + sortedGroups.size());
            writer.println();

            for (int i = 0; i < sortedGroups.size(); i++) {
                writer.println("Группа " + (i + 1));
                for (List<String> groupLine : sortedGroups.get(i).getLines()) {
                    writer.println(toLine(groupLine));
                }
                writer.println();
            }
        }
    }

    private static String toLine(List<String> line) {
        return String.join(";", line.stream().map(s -> s == null ? "" : s).toList());
    }
}
