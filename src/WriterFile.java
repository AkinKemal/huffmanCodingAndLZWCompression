import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class WriterFile {
    public WriterFile() {

    }

    public void writeBinaryHuffmanCodeToFile(HashMap<Character, String> hashMap, String text, String fileName) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            // binary veriyi dosyaya yaz
            StringBuilder binaryString = new StringBuilder();
            for (int i = 0; i < text.length(); ++i) {
                /*
                 text adlı bir String'in her karakteri for döngüsü ile taranıyor.
                 Her karakter için, hashMap nesnesindeki get() yöntemi kullanılarak karakterin Huffman kodu alınıyor
                 ve StringBuilder ile tutuluyor.
                 */
                StringBuilder huffmanCode = new StringBuilder(hashMap.get(text.charAt(i))); // i. karakterin Huffman code'yi al
                int padLengthHuffmanCode = (8 - huffmanCode.length() % 8) % 8; // Huffman code'nin uzunluğunu 8'in katı yapmak için gereken sıfırların sayısını hesapla
                if (padLengthHuffmanCode % 8 != 0) {
                    for (int j = 0; j < padLengthHuffmanCode; j++) {
                        huffmanCode.append("0"); // Sıfırları huffman code ekle
                    }
                }
                binaryString.append(huffmanCode); // Huffman code'yi binary string'e ekle
            }
        /*
        NOT
        Neden StringBuilder tercih ettim:
        StringBuilder sınıfı, String sınıfının değiştirilemez olmasına karşın, değiştirilebilir bir karakter dizisi sağlar.
        Bu nedenle, bir StringBuilder nesnesi oluşturularak her karakterin Huffman kodu bu nesne içine eklenir.
        StringBuilder'ın append() yöntemi, nesne içindeki var olan karakter dizisine verilen yeni karakterleri ekleyerek StringBuilder'ı günceller.
        Bu, performansı artırır çünkü String'lerin her güncellemesi bir kopya oluşturur ve bu gereksiz bellek tüketir.
        Bu nedenle, değiştirilebilir bir karakter dizisi gerektiğinde, StringBuilder daha iyi bir seçimdir.
         */

            int length = binaryString.length();
            int padLength = (8 - length % 8) % 8; // Binary string uzunluğunu 8'in katı yapmak için gereken sıfırların sayısını hesapla
            for (int i = 0; i < padLength; i++) {
                binaryString.append("0"); // Sıfırları binary string'e ekle
            }

            byte[] bytes = new byte[binaryString.length() / 8]; // Binary veriyi depolamak için byte tipinde array oluştur
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) Integer.parseInt(binaryString.substring(i * 8, (i + 1) * 8), 2); // 8-bit binary string segmentlerini byte'lara dönüştür ve byte array'de depola
            }
            outputStream.write(bytes); // Byte array'i binary dosyaya yaz
            System.out.println("Binary dosya başarıyla oluşturuldu.");
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
    }
    /*
    1.writeBinaryHuffmanCodeToFile metodu, Huffman kodlarını temsil eden Character anahtarları ve String değerleri içeren bir HashMap ve kodlanacak orijinal metni içeren bir String text alır.
    2.fileName adında bir String değişkeni tanımlanır ve "filename.bin" değeri atanır. Bu, oluşturulacak binary dosyanın adıdır.
    3.Bir try-with-resources bloğu kullanarak, fileName tarafından belirtilen dosyaya binary veri yazacak olan bir DataOutputStream oluşturulur. Bu işlem, argüman olarak fileName string'ini alan bir FileOutputStream kullanılarak yapılır.
    4.Binary veriyi depolayacak olan yeni bir StringBuilder binaryString oluşturulur.
    5.text içindeki her karakter üzerinde dönen bir döngü kullanılır. Her karakterin Huffman kodu hashMap içinden alınır ve Huffman kodu binaryString'e eklenir.
    6.binaryString'in uzunluğu hesaplanır ve 8'in katı olacak şekilde binaryString'i tamamlamak için gereken sıfırların sayısı, mod alma işlemi kullanılarak hesaplanır. Sonuç padLength değişkeninde sak
     */

    public void writeBinaryHashMapToFile(HashMap<Character, String> hashMap, String fileName) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            // HashMap'te bulunan her karakterin Huffman kodunu dosyaya yazmak için döngü oluşturulur.
            for (Character key : hashMap.keySet()) {
                // Karakter anahtar dosyaya yazılır.
                outputStream.writeChar(key);
                // Anahtar karakterin Huffman kodunun uzunluğu hesaplanır ve dosyaya yazılır.
                StringBuilder hashMapCode = new StringBuilder(hashMap.get(key)); // i. karakterin Huffman code'yi al
                int padLengthHashMapCode = (8 - (hashMapCode.length() % 8)) % 8; // Huffman code'nin uzunluğunu 8'in katı yapmak için gereken sıfırların sayısını hesapla
                for (int j = 0; j < padLengthHashMapCode; j++) {
                    hashMapCode.append("0"); // Sıfırları huffman code ekle
                }
                int length = hashMapCode.length();
                outputStream.writeInt(length);
                // Huffman kodu ikili byte dizisine dönüştürülür ve dosyaya yazılır.
                outputStream.write(getBytesFromBinaryString(hashMapCode));
            }
            System.out.println("Binary dosya başarıyla oluşturuldu.");
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
    }

    private byte[] getBytesFromBinaryString(StringBuilder binaryString) {
        // Huffman kodu 8 bitlik parçalara bölünür ve her 8 bitlik parça bir byte olarak işlenir.
        // Ancak Huffman kodunun uzunluğu 8'e tam bölünmüyorsa, kalan bitler sıfırlarla doldurulur.
        int padding = (8 - binaryString.length() % 8) % 8;
        StringBuilder stringBuilder = new StringBuilder(binaryString);
        for (int i = 0; i < padding; i++) {
            stringBuilder.append('0');
        }
        String paddedBinaryString = stringBuilder.toString();
        int length = paddedBinaryString.length() / 8;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            String byteString = paddedBinaryString.substring(i * 8, (i + 1) * 8);
            // Her 8 bitlik parça, ikili olarak işlenir ve bir byte dizisine dönüştürülür.
            bytes[i] = (byte) Integer.parseInt(byteString, 2);
        }
        return bytes;
    }

    /*
    1.writeBinaryHashMapToFile() yöntemi, bir HashMap nesnesi ve bir dosya adı alır.
    2.HashMap nesnesindeki her bir karakter, Huffman kodu ile birlikte dosyaya yazılır. Karakter, writeChar() yöntemi ile, Huffman kodunun uzunluğu ise writeInt() yöntemi ile yazılır.
    3.Huffman kodu, getBytesFromBinaryString() yöntemi kullanılarak bir byte dizisine dönüştürülür ve DataOutputStream nesnesinin write() yöntemi kullanılarak dosyaya yazılır.
    4.getBytesFromBinaryString() yöntemi, Huffman kodunu 8 bitlik parçalara bölerek her 8 bitlik parçayı bir byte olarak işler ve kalan bitleri sıfırlarla doldurur. Daha sonra, parseInt() yöntemi kullan
     */

    public void writeTextToFile(String text, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
    }

    public void writeLZWAlgorithm(HashMap<String, Integer> hashMap, String fileName) {
        try {
            // Dosya yazma işlemi için yeni bir BufferedWriter nesnesi oluşturuluyor ve belirtilen dosya adı kullanılarak dosya açılıyor.
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            // Hash map'in anahtarları (String'ler) üzerinde dönmek için bir döngü oluşturuluyor.
            for (Iterator<String> it = hashMap.keySet().iterator(); it.hasNext(); writer.newLine()) {

                String key = it.next(); // Anahtarları tek tek elde ediyoruz.
                if (key.equals("\n")) { // Eğer anahtar "\n" (yeni satır karakteri) ise
                    Object value = hashMap.get(key); // Bu anahtara karşılık gelen değeri alıyoruz.
                    writer.write("\\n" + value); // Yeni satır karakteri yerine "\\n" kullanarak, bu değeri dosyaya yazıyoruz.
                } else {
                    writer.write(key + ":" + hashMap.get(key)); // Diğer durumlarda, anahtar ve değerini dosyaya yazıyoruz.
                }
            }
            writer.close(); // Dosya yazma işlemi bittiği için dosya kapatılıyor.
        } catch (Exception e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
    }

}