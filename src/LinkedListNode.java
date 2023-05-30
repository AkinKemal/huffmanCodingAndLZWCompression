public class LinkedListNode {
    private HuffmanAlgorithmNode huffmanNode;
    private LinkedListNode next;

    public LinkedListNode(HuffmanAlgorithmNode huffmanNode) {
        this.setHuffmanNode(huffmanNode);
        this.setNext((LinkedListNode) null);
    }

    public HuffmanAlgorithmNode getHuffmanNode() {
        return this.huffmanNode;
    }

    public void setHuffmanNode(HuffmanAlgorithmNode huffmanNode) {
        this.huffmanNode = huffmanNode;
    }

    public LinkedListNode getNext() {
        return this.next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }
}