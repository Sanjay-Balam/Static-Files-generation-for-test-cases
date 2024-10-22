import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileGenerator {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Take base directory name as user input
            System.out.print("Enter the base directory name: ");
            String baseDir = scanner.nextLine().trim();

            // Create the directory structure
            createDirectoryStructure(baseDir);

            // Generate the JSON content for configuration
            writeToFile(baseDir + "/conf/config.json", generateConfigJson());

            // Generate header file content for Solver
            String solverHeaderContent = generateSolverHeader(baseDir);
            writeToFile(baseDir + "/CPP/include/" + baseDir + "Solver.hpp", solverHeaderContent);

            // Generate header file content for Test
            String testHeaderContent = generateTestHeader(baseDir);
            writeToFile(baseDir + "/CPP/include/" + baseDir + "Test.hpp", testHeaderContent);

            // Generate source file content for Solver
            String solverSourceContent = generateSolverSource(baseDir);
            writeToFile(baseDir + "/CPP/src/" + baseDir + "Solver.cpp", solverSourceContent);

            // Generate source file content for Test
            String testSourceContent = generateTestSource(baseDir);
            writeToFile(baseDir + "/CPP/src/" + baseDir + "Test.cpp", testSourceContent);

            // Generate the json.hpp file content from json_content.txt
            writeToFile(baseDir + "/CPP/include/json.hpp", readJsonHeaderFromFile("json_content.txt"));

            // Create the empty testcases.json file
            createEmptyTestcasesFile(baseDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectoryStructure(String baseDir) throws IOException {
        Files.createDirectories(Path.of(baseDir + "/conf"));
        Files.createDirectories(Path.of(baseDir + "/CPP/include"));
        Files.createDirectories(Path.of(baseDir + "/CPP/src"));
        Files.createDirectories(Path.of(baseDir + "/resources"));
    }

    private static void writeToFile(String filePath, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }

    private static void createEmptyTestcasesFile(String baseDir) throws IOException {
        writeToFile(baseDir + "/resources/testcases.json", ""); // Create an empty JSON file
    }

    private static String generateConfigJson() {
        return "{\n" +
                "    \"testcasesFile\": \"resources/testcases.json\",\n" +
                "    \"CppResultFile\": \"resources/cpp_result.json\"\n" +
                "}";
    }

    private static String generateSolverHeader(String baseDir) {
        return "#ifndef " + baseDir.toUpperCase() + "_SOLVER_HPP\n" +
                "#define " + baseDir.toUpperCase() + "_SOLVER_HPP\n\n" +
                "#include <vector>\n\n" +
                "class " + baseDir + "Solver {\n" +
                "public:\n" +
                "    std::vector<std::vector<int>> solveSudoku(std::vector<std::vector<int>>& board);\n" +
                "};\n\n" +
                "#endif";
    }

    private static String generateTestHeader(String baseDir) {
        return "#ifndef " + baseDir.toUpperCase() + "_TEST_HPP\n" +
                "#define " + baseDir.toUpperCase() + "_TEST_HPP\n\n" +
                "#include <vector>\n\n" +
                "class " + baseDir + "Tester {\n" +
                "public:\n" +
                "    bool test(std::vector<std::vector<int>>& board, std::vector<std::vector<int>>& expectedSolution);\n" +
                "};\n\n" +
                "#endif";
    }

    private static String generateSolverSource(String baseDir) {
        return "#include \"../include/" + baseDir + "Solver.hpp\"\n\n" +
                "std::vector<std::vector<int>> " + baseDir + "Solver::solveSudoku(std::vector<std::vector<int>>& board) {\n" +
                "    // Implementation of the Sudoku solving algorithm goes here.\n" +
                "    std::vector<std::vector<int>> solution;\n" +
                "    return solution;\n" +
                "}";
    }

    private static String generateTestSource(String baseDir) {
        return "#include \"../include/" + baseDir + "Test.hpp\"\n\n" +
                "bool " + baseDir + "Tester::test(std::vector<std::vector<int>>& board, std::vector<std::vector<int>>& expectedSolution) {\n" +
                "    // Implementation of the test case checking goes here.\n" +
                "    return true; // Replace with actual testing logic.\n" +
                "}";
    }

    private static String readJsonHeaderFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath)) {
            int ch;
            while ((ch = fileReader.read()) != -1) {
                content.append((char) ch);
            }
        }
        return content.toString();
    }
}
