public class HuffmanAlgorithmNode {

    private Character character;
    private int frequency;
    private HuffmanAlgorithmNode right;
    private HuffmanAlgorithmNode left;

    public HuffmanAlgorithmNode(Character character) {
        this.setCharacter(character);
        this.setFrequency(1);
        this.setRight((HuffmanAlgorithmNode) null);
        this.setLeft((HuffmanAlgorithmNode) null);
    }

    public Character getCharacter() {
        return this.character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public HuffmanAlgorithmNode getRight() {
        return this.right;
    }

    public void setRight(HuffmanAlgorithmNode right) {
        this.right = right;
    }

    public HuffmanAlgorithmNode getLeft() {
        return this.left;
    }

    public void setLeft(HuffmanAlgorithmNode left) {
        this.left = left;
    }
}