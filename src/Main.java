import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static String text;
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Text Analysis Tool");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Create a panel to hold the input components
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new FlowLayout());

            JTextField filePathField = new JTextField(20);
            JButton analyzeButton = new JButton("Analyze");
            JButton showElementButton = new JButton("Show Elements");
            JTextField searchField= new JTextField(20);
            JButton searchButton= new JButton("search ");
            inputPanel.add(new JLabel("File Name:"));
            inputPanel.add(filePathField);
            inputPanel.add(analyzeButton);
            inputPanel.add(showElementButton);
            inputPanel.add(searchField);
            inputPanel.add(searchButton);

            JTextArea resultArea = new JTextArea(20, 50);
            resultArea.setEditable(false);
            JScrollPane resultScrollPane = new JScrollPane(resultArea);

            frame.add(inputPanel, BorderLayout.NORTH);
            frame.add(resultScrollPane, BorderLayout.CENTER);

            JFrame frame1=new JFrame("Article Elements: \n");
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JTextArea articleTextArea=new JTextArea(20,40);
            articleTextArea.setWrapStyleWord(true);
            articleTextArea.setLineWrap(true);
            articleTextArea.setEditable(false);
            frame1.add(new JScrollPane(articleTextArea),BorderLayout.CENTER);
            frame1.pack();

            analyzeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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


                    /*articleTextArea.setText(text);
                    frame1.add(new JScrollPane(articleTextArea),BorderLayout.CENTER);
                    frame1.pack();
                    frame1.setVisible(true);*/

                    WordCount countWord = new WordCount(text);
                    int wordCount = countWord.countWords();
                    ParagraphCount countPara = new ParagraphCount(text);
                    int paragraphCount = countPara.paragraphCounter();

                    resultArea.append("\nTotal number of words: " + wordCount + "\n" +
                            "Total number of paragraphs: " + paragraphCount);
                }
            });

            //action for 'show element' button:
            showElementButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame1.setVisible(true);
                    frame1.setTitle("Article Elements");
                    articleTextArea.setText(text);
                }
            });

            //Implementation of search and highlight function
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String searchItem= searchField.getText();
                    DefaultHighlighter highlighter =
                            (DefaultHighlighter) articleTextArea.getHighlighter();
                    Highlighter.HighlightPainter painter =
                            new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

                    //clearing prev highlights
                    highlighter.removeAllHighlights();

                    if(!searchItem.isEmpty())
                    {
                        int index=text.indexOf(searchItem);
                        while(index>=0) {
                            try
                            {
                                int endIndex= index + searchItem.length();
                                highlighter.addHighlight(index,endIndex,painter);
                                index=text.indexOf(searchItem,endIndex);
                            }
                            catch(Exception exception)
                            {
                                exception.printStackTrace();
                            }
                        }
                    }

                }
            });

            frame.pack();
            frame.setVisible(true);
        });
    }
}
