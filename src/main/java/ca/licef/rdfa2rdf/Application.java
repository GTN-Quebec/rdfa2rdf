package ca.licef.rdfa2rdf;

import licef.StreamUtil;
import licef.tsapi.Constants;
import licef.tsapi.util.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: amiara
 * Date: 13-12-06
 */
public class Application extends JFrame {

    private final JTextField tfURL;
    private final JTextField tfFile;
    private final JTextArea taText;
    private final JTextArea taResult;

    int leftMargin = 60;
    int space = 15;
    private final ButtonGroup formatGroup;
    private final JButton convertButton;
    private final JButton saveButton;

    int outputFormat = Constants.TURTLE;
    private JFileChooser fileChooser;

    public Application() {
        setTitle("RDFa to RDF");
        setSize(900, 624);
        setLayout(new BorderLayout());

        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder());
        add(mainPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        /***************/
        /* Input stuff */
        /***************/

        JPanel leftPanel = new JPanel(new CardLayout(space, space));

        JPanel inputPanelWrapper = new JPanel(new BorderLayout());

        inputPanelWrapper.setPreferredSize(new Dimension(400, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        //URL
        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.X_AXIS));
        JLabel jLabelUrl = new JLabel("URL: ");
        jLabelUrl.setPreferredSize(new Dimension(leftMargin, 10));
        urlPanel.add(jLabelUrl);
        urlPanel.add(Box.createHorizontalStrut(10));
        tfURL = new JTextField();
        tfURL.setFont(new Font("Dialog", Font.PLAIN, 16));
        urlPanel.add(tfURL);
        inputPanel.add(urlPanel);
        tfURL.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                urlChanged();
            }
        });

        inputPanel.add(Box.createVerticalStrut(10));

        JPanel orPanel = new JPanel();
        orPanel.setLayout(new BoxLayout(orPanel, BoxLayout.X_AXIS));
        JLabel jLabelOr = new JLabel("OR");
        orPanel.add(Box.createHorizontalGlue());
        orPanel.add(jLabelOr);
        orPanel.add(Box.createHorizontalGlue());
        inputPanel.add(orPanel);

        inputPanel.add(Box.createVerticalStrut(10));

        //File
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
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
        inputPanel.add(filePanel);
        fileSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        inputPanel.add(Box.createVerticalStrut(10));

        JPanel or2Panel = new JPanel();
        or2Panel.setLayout(new BoxLayout(or2Panel, BoxLayout.X_AXIS));
        JLabel jLabelOr2 = new JLabel("OR");
        or2Panel.add(Box.createHorizontalGlue());
        or2Panel.add(jLabelOr2);
        or2Panel.add(Box.createHorizontalGlue());
        inputPanel.add(or2Panel);

        inputPanel.add(Box.createVerticalStrut(10));

        //Text area
        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(10, 200));
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        JLabel jLabelText = new JLabel("<html>Text to<br/>paste:</html>");
        jLabelText.setPreferredSize(new Dimension(leftMargin, 10));
        jLabelText.setMaximumSize(new Dimension(leftMargin, 60));
        textPanel.add(jLabelText);
        textPanel.add(Box.createHorizontalStrut(10));
        JScrollPane scrollPaneText = new JScrollPane();
        taText = new JTextArea();
        taText.setLineWrap(true);
        scrollPaneText.getViewport().add(taText);
        textPanel.add(scrollPaneText);
        inputPanel.add(textPanel);
        taText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                textChanged();
            }
        });

        inputPanel.add(Box.createVerticalStrut(20));

        //Formats
        JPanel formatPanel = new JPanel();
        formatPanel.setLayout(new BoxLayout(formatPanel, BoxLayout.X_AXIS));
        formatPanel.add(Box.createHorizontalStrut(leftMargin + 8));
        JPanel formats = new JPanel();
        formats.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray), " Output format "));
        formats.setLayout(new BoxLayout(formats, BoxLayout.Y_AXIS));
        formats.add(Box.createVerticalStrut(1));
        JRadioButton turtle = new JRadioButton("TURTLE");
        turtle.setSelected(true);
        formats.add(turtle);
        JRadioButton n3 = new JRadioButton("N-TRIPLE");
        formats.add(n3);
        JRadioButton rdfxml = new JRadioButton("RDF/XML");
        formats.add(rdfxml);
        JRadioButton json = new JRadioButton("JSON");
        formats.add(json);
        formatGroup = new ButtonGroup();
        formatGroup.add(turtle);
        formatGroup.add(n3);
        formatGroup.add(rdfxml);
        formatGroup.add(json);
        formatPanel.add(formats);
        inputPanel.add(formatPanel);
        turtle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputFormat = Constants.TURTLE;
            }
        });
        n3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputFormat = Constants.N_TRIPLE;
            }
        });
        rdfxml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputFormat = Constants.RDFXML;
            }
        });
        json.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputFormat = Constants.JSON;
            }
        });

        inputPanelWrapper.add(inputPanel, BorderLayout.NORTH);


        //Action
        convertButton = new JButton("Convert");
        convertButton.setEnabled(false);
        inputPanelWrapper.add(convertButton, BorderLayout.SOUTH);
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        });

        leftPanel.add(inputPanelWrapper, "left");
        mainPanel.add(leftPanel);


        /****************/
        /* Output stuff */
        /****************/

        JPanel rightPanel = new JPanel(new CardLayout(space, space));

        JPanel outputPanel = new JPanel(new BorderLayout(0, 5));

        //Result
        JScrollPane scrollPaneResult = new JScrollPane();
        taResult = new JTextArea();
        taResult.setBackground(new Color(222, 222, 222));
        taResult.setFont(new Font("Arial", Font.PLAIN, 14));
        taResult.setForeground(new Color(25, 25, 112));
        taResult.setEditable(false);
        taResult.setLineWrap(true);
        scrollPaneResult.getViewport().add(taResult);
        outputPanel.add(scrollPaneResult, BorderLayout.CENTER);

        //Save
        saveButton = new JButton("Save...");
        saveButton.setEnabled(false);
        outputPanel.add(saveButton, BorderLayout.SOUTH);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        rightPanel.add(outputPanel, "right");
        mainPanel.add(rightPanel);
    }

    private void selectFile() {
        if (fileChooser == null)
            fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this);
        File f = fileChooser.getSelectedFile();
        if (f != null) {
            tfFile.setText(f.toString());
            fileChanged();
        }
    }

    private void urlChanged() {
        tfFile.setText(null);
        taText.setText(null);
        convertButton.setEnabled(!"".equals(tfURL.getText()));
    }

    private void fileChanged() {
        tfURL.setText(null);
        taText.setText(null);
        convertButton.setEnabled(!"".equals(tfFile.getText()));
    }

    private void textChanged() {
        tfURL.setText(null);
        tfFile.setText(null);
        convertButton.setEnabled(!"".equals(taText.getText()));
    }

    private void convert() {
        try {
            InputStream is;
            String baseUri = "http://localhost";
            String url = tfURL.getText();
            if (!"".equals(url)) {
                URL _url = new URL(url);
                is = _url.openStream();
                baseUri = url;
                if (_url.getFile().contains("."))
                    baseUri = url.substring(0, url.lastIndexOf('/'));
            }
            else if (!"".equals(tfFile.getText())) {
                is = new FileInputStream(tfFile.getText());
            }
            else
                is = StreamUtil.getStream(taText.getText());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Class.forName("net.rootdev.javardfa.jena.RDFaReader");
            Translator.convert(is, baseUri, Constants.HTML, outputFormat, os);
            taResult.setText(os.toString());
            taResult.setCaretPosition(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void save() {

    }

    public static void main(String[] args) {
        Application appl = new Application();
        appl.setVisible(true);
    }
}
