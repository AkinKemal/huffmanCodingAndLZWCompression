import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

// Bu class, JFrame sınıfını genişletir ve ActionListener arabirimini uygular.
public class HuffmanAlgorithmAndLZWAlgorithmGUI extends JFrame implements ActionListener {

    // GUI Components
    // JButton sınıfından dört farklı düğme oluşturulur
    // Her buton için bir dosya seçme ActionListener oluşturulur.
    public JButton button1, button2, button3, button4;
    public JLabel label, imageLabel;
    public JPanel mainPanel, contentsPanel, buttonPanel, panelTextAndImage, panelText, panelImage, panelOutput;
    double huffmanTotalTime1 = 0.0, lzwTotalTime1 = 0.0, huffmanTotalTime2 = 0.0, lzwTotalTime2 = 0.0;
    public JTextField outputField;

    // Huffman algoritması ve LZW algoritmasının nesnelerini oluşturuyoruz.
    HuffmanAlgorithm huffmanAlgorithm = new HuffmanAlgorithm();
    LZWAlgorithm lzwAlgorithm = new LZWAlgorithm();
    // Okuma ve yazma işlemlerinde kullanılacak nesneleri oluşturuyoruz.
    ReadFile readFile = new ReadFile();
    WriterFile writerFile = new WriterFile();
    /*
    HuffmanAlgorithm, LZWAlgorithm, ReadFile ve WriterFile sınıflarından nesneler oluşturulur.
    Bu nesneler, sıkıştırma ve çıkarma işlemlerinin gerçekleştirilmesinde kullanılacak yöntemleri sağlar.
    */

    public HuffmanAlgorithmAndLZWAlgorithmGUI() {

        // Set JFrame properties
        setTitle("Doftdare Company");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize GUI Components
        button1 = new JButton("File > Huffman Algorithm");
        button2 = new JButton("File > LZW Algorithm");
        button3 = new JButton("Huffman Algorithm > File");
        button4 = new JButton("LZW Algorithm > File");

        // Set font and color for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        button1.setFont(buttonFont);
        button2.setFont(buttonFont);
        button3.setFont(buttonFont);
        button4.setFont(buttonFont);

        button1.setBackground(new Color(240, 128, 128));
        button1.setForeground(new Color(211, 211, 211));
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button2.setBackground(new Color(0, 100, 200));
        button2.setForeground(new Color(211, 211, 211));
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button3.setBackground(new Color(240, 128, 128));
        button3.setForeground(new Color(211, 211, 211));
        button3.setBorderPainted(false);
        button3.setFocusPainted(false);
        button4.setBackground(new Color(0, 100, 200));
        button4.setForeground(new Color(211, 211, 211));
        button4.setBorderPainted(false);
        button4.setFocusPainted(false);

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 150);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);

        // Set button margins
        Insets buttonMargin = new Insets(10, 10, 10, 10);
        button1.setMargin(buttonMargin);
        button2.setMargin(buttonMargin);
        button3.setMargin(buttonMargin);
        button4.setMargin(buttonMargin);

        // Add buttons to a JPanel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        // Set ActionListener for buttons
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);

        label = new JLabel("Select the action you want to perform:");
        label.setHorizontalAlignment(JLabel.CENTER);

        // Set font and color for label
        Font font = new Font("Arial", Font.BOLD, 20);
        label.setFont(font);
        label.setForeground(Color.DARK_GRAY);

        // Add image to the panel
        ImageIcon imageIcon = new ImageIcon("doftdare.png");
        Image image = imageIcon.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(image);
        imageLabel = new JLabel(scaledIcon);
        imageLabel.setPreferredSize(new Dimension(500, 500));

        // Add GUI components to the panel
        panelText = new JPanel();
        panelText.add(label);

        panelImage = new JPanel();
        panelImage.add(imageLabel);

        panelTextAndImage = new JPanel();
        panelTextAndImage.setLayout(new BorderLayout());
        panelTextAndImage.add(panelImage, BorderLayout.NORTH);
        panelTextAndImage.add(panelText, BorderLayout.CENTER);

        contentsPanel = new JPanel();
        contentsPanel.setLayout(new BorderLayout());
        contentsPanel.add(panelTextAndImage, BorderLayout.NORTH);
        contentsPanel.add(buttonPanel, BorderLayout.CENTER);

        outputField = new JTextField(10);
        outputField.setEditable(false);
        outputField.setFont(font);
        outputField.setHorizontalAlignment(JLabel.CENTER);

        panelOutput = new JPanel();
        panelOutput.add(outputField);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(contentsPanel, BorderLayout.NORTH);
        mainPanel.add(panelOutput, BorderLayout.CENTER);

        add(mainPanel);

        // Set window properties
        pack();

        setVisible(true);
        // Set window properties
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        /*
        Kullanıcının seçtiği dosyanın adını bir String değişkeninde tutmak için JFileChooser sınıfının getSelectedFile() yöntemini kullanırdı.
        Bu yöntem, kullanıcının seçtiği dosyayı temsil eden File nesnesini döndürür. Ardından File nesnesinin getName() yöntemini kullanarak dosya adını alındı.
         */

        // Create a file chooser dialog for File 1
        // Determine which button was pressed
        if (e.getSource() == button1) {
            String fileName = "text.txt";
            // Create a file chooser dialog for File 1
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileName = selectedFile.getName();
                System.out.println("Selected File 1: " + selectedFile.getAbsolutePath());
            }

            // Sıkıştırma yapılacak olan dosyanın içeriği okunarak atanır.
            String mainText = readFile.readerFromMainFile(fileName);

            // time Huffman Algoritma Start
            long huffmanStartTime = System.currentTimeMillis();

            // Huffman algoritması kullanılarak dosyanın sıkıştırılması için gerekli işlemler yapılır.
            huffmanAlgorithm.addNodesToLinkedList(mainText);
            huffmanAlgorithm.sortNodes();
            huffmanAlgorithm.createTree();
            huffmanAlgorithm.createCodeHashMap(huffmanAlgorithm.rootNode, "");
            huffmanAlgorithm.createHuffmanCodeAndHashMap(mainText, fileName);

            // time Huffman Algoritma End
            long huffmanEndTime = System.currentTimeMillis();
            long huffmanEstimatedTime = huffmanEndTime - huffmanStartTime;
            huffmanTotalTime1 = (double) huffmanEstimatedTime / 1000.0;
            outputField.setText(Double.toString(huffmanTotalTime1) + "sn");
        } else if (e.getSource() == button2) {
            String fileName = "text.txt";
            // Create a file chooser dialog for File 2
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileName = selectedFile.getName();
                System.out.println("Selected File 2: " + selectedFile.getAbsolutePath());
            }

            // Sıkıştırma yapılacak olan dosyanın içeriği okunarak atanır.
            String mainText = readFile.readerFromMainFile(fileName);

            // time LZW Algoritma Start
            long lzwStartTime = System.currentTimeMillis();

            // LZW algoritması için gerekli adımları sırasıyla gerçekleştiriyoruz.
            lzwAlgorithm.createDictionary(mainText);
            String output = lzwAlgorithm.compress(mainText);
            lzwAlgorithm.writeCompressedData(output, "hashMapLZWAlgorithm" + fileName, "LZWAlgorithm" + fileName);

            //time LZW Algoritma End
            long lzwEndTime = System.currentTimeMillis();
            long lzwEstimatedTime = lzwEndTime - lzwStartTime;
            lzwTotalTime1 = (double) lzwEstimatedTime / 1000.0;
            outputField.setText(Double.toString(lzwTotalTime1) + "sn");
        } else if (e.getSource() == button3) {
            String fileName = null;
            // Create a file chooser dialog for File 3
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileName = selectedFile.getName();
                System.out.println("Selected File 3: " + selectedFile.getAbsolutePath());
            }

            // time Huffman Algoritma Start
            long huffmanStartTime = System.currentTimeMillis();

            // Huffman kodlaması sonucu oluşan ikili kodları ve bu kodların karakterlere karşılık gelen değerlerini huffmancode.bin ve hashmap.bin dosyalarından okuyoruz.
            String newText = readFile.readBinaryHuffmanCodeFromFile(fileName, "hashMap" + fileName);

            // Okunan ikili kodları yeni dosyaya yazıyoruz.
            writerFile.writeTextToFile(newText, "newHuffmanAlgorithm.txt");

            // time Huffman Algoritma End
            long huffmanEndTime = System.currentTimeMillis();
            long huffmanEstimatedTime = huffmanEndTime - huffmanStartTime;
            huffmanTotalTime2 = (double) huffmanEstimatedTime / 1000.0;
            outputField.setText(Double.toString(huffmanTotalTime2) + "sn");
        } else if (e.getSource() == button4) {
            String fileName = null;
            // Create a file chooser dialog for File 4
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileName = selectedFile.getName();
                System.out.println("Selected File 4: " + selectedFile.getAbsolutePath());
            }
            // time LZW Algoritma Start
            long lzwStartTime = System.currentTimeMillis();

            // LZW kodlaması sonucu oluşan ikili kodları ve bu kodların karakterlere karşılık gelen değerlerini lzwAlgorithmCode.txt ve lzwAlgorithm.txt dosyalarından okuyoruz.
            String lzwText = readFile.readerFromMainFile(fileName);
            HashMap<Integer, String> lzwHashMap = readFile.readerFromLZWAlgorithmCode("hashMap" + fileName);

            // LZW algoritması sonucu oluşan ikili kodları karakterlere dönüştürerek "newLZW.txt" dosyasına yazıyoruz.
            lzwAlgorithm.decompress(lzwText, lzwHashMap);

            //time LZW Algoritma End
            long lzwEndTime = System.currentTimeMillis();
            long lzwEstimatedTime = lzwEndTime - lzwStartTime;
            lzwTotalTime2 = (double) lzwEstimatedTime / 1000.0;
            outputField.setText(Double.toString(lzwTotalTime2) + "sn");
        }

    }
}