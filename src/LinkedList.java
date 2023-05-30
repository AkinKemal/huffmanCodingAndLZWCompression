public class LinkedList {

    LinkedListNode head = null;

    public LinkedList() {
    }

    public void add(HuffmanAlgorithmNode huffmanNode) {
        LinkedListNode newNode = new LinkedListNode(huffmanNode);
        if (this.comparison(huffmanNode.getCharacter())) {
            if (this.head == null) {
                this.head = newNode;
                this.head.setNext((LinkedListNode) null);
            } else {
                newNode.setNext(this.head);
                this.head = newNode;
            }
        }

    }

    public void add2(HuffmanAlgorithmNode huffmanNode) {
        LinkedListNode newNode = new LinkedListNode(huffmanNode);
        if (this.head == null) {
            this.head = newNode;
            this.head.setNext((LinkedListNode) null);
        } else {
            newNode.setNext(this.head);
            this.head = newNode;
        }

    }

    public void delete() {
        if (this.head != null) {
            LinkedListNode temp = this.head;
            this.head = this.head.getNext();
            temp.setNext((LinkedListNode) null);
        }

    }

    private boolean comparison(Character character) {
        LinkedListNode walk = this.head;

        boolean control;
        for (control = true; walk != null; walk = walk.getNext()) {
            if (walk.getHuffmanNode().getCharacter().equals(character)) {
                control = false;
                int box = walk.getHuffmanNode().getFrequency();
                walk.getHuffmanNode().setFrequency(box + 1);
            }
        }

        return control;
    }

    public void sortList() {
        LinkedListNode walk = this.head;
        LinkedListNode index = null;
        if (this.head == null) {
            System.out.println("Sıralanacak eleman bulunamadı...");
        } else {
            while (walk != null) {
                for (index = walk.getNext(); index != null; index = index.getNext()) {
                    if (walk.getHuffmanNode().getFrequency() > index.getHuffmanNode().getFrequency()) {
                        int tempInt = walk.getHuffmanNode().getFrequency();
                        Character tempChar = walk.getHuffmanNode().getCharacter();
                        HuffmanAlgorithmNode tempLeft = walk.getHuffmanNode().getLeft();
                        HuffmanAlgorithmNode tempRight = walk.getHuffmanNode().getRight();
                        walk.getHuffmanNode().setFrequency(index.getHuffmanNode().getFrequency());
                        walk.getHuffmanNode().setCharacter(index.getHuffmanNode().getCharacter());
                        walk.getHuffmanNode().setLeft(index.getHuffmanNode().getLeft());
                        walk.getHuffmanNode().setRight(index.getHuffmanNode().getRight());
                        index.getHuffmanNode().setFrequency(tempInt);
                        index.getHuffmanNode().setCharacter(tempChar);
                        index.getHuffmanNode().setLeft(tempLeft);
                        index.getHuffmanNode().setRight(tempRight);
                    }
                }

                walk = walk.getNext();
            }
        }

    }

    public int getSize() {
        LinkedListNode walk = this.head;

        int counter;
        for (counter = 0; walk != null; walk = walk.getNext()) {
            ++counter;
        }

        return counter;
    }
}