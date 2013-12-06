package ca.licef.rdfa2rdf;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: amiara
 * Date: 13-12-06
 */
public class Application extends JFrame {

    private final JTextField tfURL;
    private final JTextField tfFile;
    int leftMargin = 100;

    public Application() {
        setTitle("RDFa to RDF");
        setSize(700, 350);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(Box.createVerticalStrut(20));
        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.X_AXIS));
        urlPanel.add(Box.createHorizontalStrut(20));
        JLabel jLabelUrl = new JLabel("URL: ");
        jLabelUrl.setPreferredSize(new Dimension(leftMargin, 10));
        urlPanel.add(jLabelUrl);
        urlPanel.add(Box.createHorizontalStrut(10));
        tfURL = new JTextField();
        tfURL.setFont(new Font("Dialog", Font.PLAIN, 16));
        urlPanel.add(tfURL);
        urlPanel.add(Box.createHorizontalStrut(30));
        contentPanel.add(urlPanel);

        contentPanel.add(Box.createVerticalStrut(20));

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
        filePanel.add(Box.createHorizontalStrut(20));
        JLabel jLabelFile = new JLabel("File: ");
        jLabelFile.setPreferredSize(new Dimension(leftMargin, 10));
        filePanel.add(jLabelFile);
        filePanel.add(Box.createHorizontalStrut(10));
        tfFile = new JTextField();
        tfFile.setEditable(false);
        tfFile.setFont(new Font("Dialog", Font.PLAIN, 16));
        filePanel.add(tfFile);
        filePanel.add(Box.createHorizontalStrut(5));
        JButton fileSelector = new JButton("...");
        filePanel.add(fileSelector);
        filePanel.add(Box.createHorizontalStrut(32));
        contentPanel.add(filePanel);

        contentPanel.add(Box.createVerticalStrut(20));

        JPanel formatPanel = new JPanel();
        formatPanel.setLayout(new BoxLayout(formatPanel, BoxLayout.X_AXIS));
        formatPanel.add(Box.createHorizontalStrut(20));
        JLabel jLabelOutputFormat = new JLabel("Output format: ");
        jLabelOutputFormat.setPreferredSize(new Dimension(leftMargin, 10));
        formatPanel.add(jLabelOutputFormat);
        formatPanel.add(Box.createHorizontalStrut(10));
        JPanel formats = new JPanel();
        formats.setLayout(new BoxLayout(formats, BoxLayout.Y_AXIS));
        formats.add(Box.createVerticalStrut(1));
        formats.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        JRadioButton rdfxml = new JRadioButton("RDF/XML");
        formats.add(rdfxml);
        JRadioButton turtle = new JRadioButton("TURTLE");
        formats.add(turtle);
        JRadioButton n3 = new JRadioButton("N-TRIPLE");
        formats.add(n3);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rdfxml);
        bg.add(turtle);
        bg.add(n3);
        formatPanel.add(formats);
        formatPanel.add(Box.createHorizontalStrut(30));
        contentPanel.add(formatPanel);

        contentPanel.add(Box.createVerticalStrut(20));

        JPanel destFilePanel = new JPanel();
        destFilePanel.setLayout(new BoxLayout(destFilePanel, BoxLayout.X_AXIS));
        destFilePanel.add(Box.createHorizontalStrut(20));
        JLabel jLabelDestFile = new JLabel("Output File: ");
        jLabelDestFile.setPreferredSize(new Dimension(leftMargin, 10));
        destFilePanel.add(jLabelDestFile);
        destFilePanel.add(Box.createHorizontalStrut(10));
        JLabel jLabelSelectedDestFile = new JLabel("select output file");
        jLabelSelectedDestFile.setFont(new Font("Dialog", Font.PLAIN, 12));
        destFilePanel.add(jLabelSelectedDestFile);
        destFilePanel.add(Box.createHorizontalStrut(5));
        JButton destFileSelector = new JButton("...");
        destFilePanel.add(destFileSelector);
        destFilePanel.add(Box.createHorizontalGlue());
        contentPanel.add(destFilePanel);

        contentPanel.add(Box.createVerticalStrut(40));

        JButton goButton = new JButton("Convert");
        goButton.setPreferredSize(new Dimension(200, 30));
        contentPanel.add(goButton);

        add(contentPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Application appl = new Application();
        appl.setVisible(true);
    }
}
