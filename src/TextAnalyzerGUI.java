import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextAnalyzerGUI {
    private JFrame frame;
    private JTextArea articleTextArea;
    private JTextField filePathField;
    private JTextField searchField;
    private JTextArea resultArea;
    private String text;

    public TextAnalyzerGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Text Analysis Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        filePathField = new JTextField(20);
        JButton analyzeButton = new JButton("Analyze");
        JButton showElementButton = new JButton("Show Elements");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        inputPanel.add(new JLabel("File Name:"));
        inputPanel.add(filePathField);
        inputPanel.add(analyzeButton);
        inputPanel.add(showElementButton);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);

        resultArea = new JTextArea(20, 50);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        articleTextArea = new JTextArea(20, 40);
        articleTextArea.setWrapStyleWord(true);
        articleTextArea.setLineWrap(true);
        articleTextArea.setEditable(false);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultScrollPane, BorderLayout.CENTER);

        JFrame frame1 = new JFrame("Article Elements: \n");
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLocation(100, 400);
        frame1.add(new JScrollPane(articleTextArea), BorderLayout.CENTER);
        frame1.pack();

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeButtonAction();
            }
        });

        showElementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showElementButtonAction();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButtonAction();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void analyzeButtonAction() {
        String filePath = filePathField.getText();
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException ex) {
            resultArea.setText("An error occurred while reading the file: " + ex.getMessage());
            return;
        }
        text = stringBuilder.toString();

        WordCount countWord = new WordCount(text);
        int wordCount = countWord.countWords();
        ParagraphCount countPara = new ParagraphCount(text);
        int paragraphCount = countPara.paragraphCounter();

        resultArea.append("\nTotal number of words: " + wordCount + "\n" +
                "Total number of paragraphs: " + paragraphCount);
    }

    private void showElementButtonAction() {
        JFrame frame1 = new JFrame("Article Elements");
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLocation(100, 400);
        frame1.add(new JScrollPane(articleTextArea), BorderLayout.CENTER);
        frame1.pack();
        articleTextArea.setText(text);
        frame1.setVisible(true);
    }

    private void searchButtonAction() {
        String searchItem = searchField.getText();
        DefaultHighlighter highlighter =
                (DefaultHighlighter) articleTextArea.getHighlighter();
        Highlighter.HighlightPainter painter =
                new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

        highlighter.removeAllHighlights();

        if (!searchItem.isEmpty()) {
            int index = text.indexOf(searchItem);
            while (index >= 0) {
                try {
                    int endIndex = index + searchItem.length();
                    highlighter.addHighlight(index, endIndex, painter);
                    index = text.indexOf(searchItem, endIndex);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "No search item entered",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            });
        }
    }


}
