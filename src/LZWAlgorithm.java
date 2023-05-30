import java.util.HashMap;

public class LZWAlgorithm {
    public WriterFile writerFile = new WriterFile(); // import edilmiş WriterFile class'ından nesne oluşturuluyor
    public HashMap<String, Integer> dictionary = new HashMap<>(); // String anahtarları olan ve Integer değerleri olan bir HashMap oluşturuluyor

    public LZWAlgorithm() { // Constructor
    }

    // Bu fonksiyon, verilen string girdisi üzerinden bir LZW sözlüğü oluşturur.
    public void createDictionary(String input) {
        int counter = 1;
        String temp = "";

        // Döngü, girdi dizisinin her bir karakterine tek tek bakar.
        // Her karakter için, o karakteri temp karakter dizisine atar.
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            // Eğer karakter bir satır başı karakteri ise (\n), karakteri \\n olarak değiştirir.
            if (c == '\n') {
                temp = "\\n";
            } else {
                temp = String.valueOf(c);
            }

            if (!dictionary.containsKey(temp)) {
                // Eğer sözlük yapısında temp anahtarı yoksa, temp anahtarını sözlük yapısına ekler ve değer olarak counter'ı ekler.
                // counter daha sonra bir artırılır.
                dictionary.put(temp, counter++);
            }

            // Böylece, sıkıştırma işlemi sırasında, her karakter için sözlükte bir anahtar ve karşılık gelen bir sayı değeri vardır.
        }
    }

    // Bu fonksiyon, verilen string girdisini LZW sıkıştırma algoritması kullanarak sıkıştırır.
    public String compress(String input) {
        StringBuilder result = new StringBuilder();

        //  mevcut karakter dizisi current
        String current = "";

        // geçici bir karakter dizisi olan temp
        String temp = "";

        // Döngü, girdi dizisinin her bir karakterine tek tek bakar.
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            // Her karakter için, o karakteri temp karakter dizisine atar.
            temp = String.valueOf(c);

            // Eğer karakter bir satır başı karakteri ise (\n), karakteri \\n olarak değiştirir.
            if (c == '\n') {
                temp = "\\n";
            }

            if (dictionary.containsKey(current + temp)) {

                // Eğer sözlük yapısında bu kombinasyon mevcutsa, current değişkenine temp karakterini ekler.
                current += temp;
            } else {

                // Yoksa, result nesnesine sözlük yapısındaki current anahtarının karşılığı olan değeri ekler
                result.append(dictionary.get(current)).append("-");
                dictionary.put(current + temp, dictionary.size() + 1);

                // current değişkeni temp değeriyle güncellenir.
                current = temp;
            }
        }

        result.append(dictionary.get(current));
        return result.toString();
    }

    // Bu fonksiyon, sıkıştırılmış veriyi dosyaya yazar ve ayrıca dictionary de dosyaya yazar.
    public void writeCompressedData(String compressedData, String lzwAlgorithm, String lzwAlgorithmCode) {
        writerFile.writeTextToFile(compressedData, lzwAlgorithmCode);
        writerFile.writeLZWAlgorithm(dictionary, lzwAlgorithm);
    }

    // Bu fonksiyon, verilen sıkıştırılmış veriyi, verilen sözlük kullanarak LZW sıkıştırma algoritmasına göre açar ve açılan veriyi dosyaya yazar.
    public void decompress(String compressedData, HashMap<Integer, String> dictionary) {

        // LZW sıkıştırma algoritmasında kullanılan kod kelimelerinin çıktısı
        StringBuilder line = new StringBuilder();

        // Orijinal metnin depolanacağı çıktı
        StringBuilder decompressedData = new StringBuilder();

        //  Döngü başlatılır ve girdi dizisi karakterlerine tek tek bakılır.
        //  '-' karakteri, sıkıştırılmış girdi dizisinde bir anahtar-değer çiftini gösterir.
        //  Bu nedenle, '-' karakteri okunduğunda, line nesnesindeki karakterler birleştirilerek,
        // ilgili anahtar-değer çiftini depolayan sözlükten ilgili anahtar çözülür
        for (int i = 0; i < compressedData.length(); ++i) {
            if (compressedData.charAt(i) != '-') {
                line.append(compressedData.charAt(i));
            } else {
                // İlgili değer, decompressedData'ya eklenir.
                decompressedData.append(dictionary.get(Integer.parseInt(line.toString())));

                //  line nesnesi temizlenir (setLength) ve bir sonraki kod kelimesi için hazırlanır.
                line.setLength(0);
            }
        }

        writerFile.writeTextToFile(decompressedData.toString(), "newLZWAlgorithm.txt");
    }

    /*
    Bu metot, LZW algoritması ile sıkıştırılmış bir girdi dizisini
    ve sıkıştırma için kullanılan sözlük yapılarını alır.
    Bu sözlük yapıları, önceden sıkıştırılmış verilerin anahtar-değer çiftlerini içerir.
    Metot, girdi dizisini işleyerek orijinal veriyi yeniden oluşturur ve yeni bir dosyaya yazar.
     */
}