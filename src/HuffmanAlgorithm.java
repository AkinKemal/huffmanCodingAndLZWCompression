import java.util.HashMap;

public class HuffmanAlgorithm {
    public LinkedList nodeList = new LinkedList();
    public WriterFile writerFile = new WriterFile();
    public HashMap<Character, String> codeHashMap = new HashMap<>();
    public static int totalSize = 0;
    public HuffmanAlgorithmNode rootNode = null;

    public void addNodesToLinkedList(String text) {
        for (int i = 0; i < text.length(); ++i) {
            HuffmanAlgorithmNode node = new HuffmanAlgorithmNode(text.charAt(i));
            nodeList.add(node);
        }

        totalSize = nodeList.getSize();
    }

    public void sortNodes() {
        nodeList.sortList();
    }

    // createTree() yöntemi, nodeList listesindeki düğümleri birleştirerek ağacı oluşturur.
    public void createTree() {

        // nodeList'de tek bir düğüm kalana kadar döngü devam eder.
        while (nodeList.getSize() != 1) {

            // nodeList'in ilk elemanı sol düğüm olarak atanır.
            HuffmanAlgorithmNode leftNode = nodeList.head.getHuffmanNode();

            // nodeList'in ikinci elemanı sağ düğüm olarak atanır.
            HuffmanAlgorithmNode rightNode = nodeList.head.getNext().getHuffmanNode();

            // yeni bir düğüm oluşturulur.
            HuffmanAlgorithmNode newNode = new HuffmanAlgorithmNode('*');

            // yeni düğümün frekansı sol ve sağ düğümlerin frekanslarının toplamıdır.
            newNode.setFrequency(leftNode.getFrequency() + rightNode.getFrequency());

            // ilk düğümü listeden sil.
            nodeList.delete();

            // ikinci düğümü listeden sil.
            nodeList.delete();

            // yeni düğümün sol çocuğu sol düğümdür.
            newNode.setLeft(leftNode);

            // yeni düğümün sağ çocuğu sağ düğümdür.
            newNode.setRight(rightNode);

            // yeni düğüm rootNode olarak atanır.
            rootNode = newNode;

            // yeni düğüm nodeList'e eklenir.
            nodeList.add2(newNode);

            // nodeList yeniden sıralanır.
            nodeList.sortList();
        }
    }

    // createCodeHashMap() yöntemi, Huffman ağacında dolaşarak her bir karakterin kodunu belirler.
    // Yöntem, Huffman ağacındaki her bir düğüm için tekrarlanır.
    public void createCodeHashMap(HuffmanAlgorithmNode node, String str) {
        // node parametresi, Huffman ağacındaki düğümleri temsil eder.
        // str parametresi, her bir düğüm için kodlanacak karakterin kodunu içeren bir dizedir.

        // Eğer düğümün sol ve sağ çocukları yoksa
        // o zaman düğümün karakteri ve kodu, codeHashMap adlı HashMap'e eklenir.
        // Bu, Huffman ağacındaki son düğümlere ulaşana kadar devam eder.
        if (node.getLeft() == null && node.getRight() == null) {
            codeHashMap.put(node.getCharacter(), str);
        }

        // Eğer düğümün sol ve sağ çocukları varsa
        // o zaman createCodeHashMap() yöntemi sol ve sağ çocuk düğümlerini tekrar çağırır.
        // Bu, Huffman ağacındaki diğer düğümlere ulaşana kadar devam eder.
        else {

            // Sol çocuk düğümü için, str dizesine "0" eklenir.
            // Sağ çocuk düğümü için, str dizesine "1" eklenir.
            // Bu, her bir düğümün karakter kodunu oluşturmak için kullanılır.
            createCodeHashMap(node.getLeft(), str + "0");
            createCodeHashMap(node.getRight(), str + "1");
        }
    }

    public void createHuffmanCodeAndHashMap(String text, String fileName) {
        writerFile.writeBinaryHashMapToFile(codeHashMap, "hashMapHuffmanAlgorithm" + fileName + ".bin");
        writerFile.writeBinaryHuffmanCodeToFile(codeHashMap, text, "HuffmanAlgorithm" + fileName + ".bin");
    }
}