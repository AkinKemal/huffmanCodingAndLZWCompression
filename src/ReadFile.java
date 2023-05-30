import java.io.*;
import java.util.HashMap;

public class ReadFile {

    public ReadFile() {
    }

    public String readBinaryHuffmanCodeFromFile(String fileNameHuffmanCode, String fileNameHashMap) {
        // Huffman kodlarına göre şifrelenmiş verilerin bulunduğu dosyadan okunan verileri çözmek için bir metot olduğunu belirtmek gerekir.
        // HashMap, anahtar-değer çiftleri içerir. Anahtarlar Huffman kodlarını, değerler ise karşılık gelen karakterleri temsil eder.
        // Çözülen metnin birikmesi için bir StringBuilder oluşturulur.
        // Neden StringBuilder kullandığımı "WriterFile" classında açıklanmıştır.

        HashMap<String, Character> hashMap = readBinaryHashMapFromFile(fileNameHashMap);
        StringBuilder resultBuilder = new StringBuilder();
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileNameHuffmanCode))) {
            // Dosyadan tüm baytları okumak için bir DataInputStream oluşturulur.
            // Okunan baytlar byte dizisi olarak saklanır.
            byte[] bytes = inputStream.readAllBytes();
            // Okunan baytları, 8-bit'lik binary stringlere dönüştürmek için kullanılacak olan StringBuilder nesnesi oluşturulur.
            StringBuilder binaryString = new StringBuilder();
            for (byte b : bytes) {
                // Her bayt, Integer.toBinaryString() yöntemi kullanılarak 8-bit'lik bir binary stringe dönüştürülür.
                binaryString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }
            // Huffman kodlarını aramak için kullanılacak olan StringBuilder nesnesi oluşturulur.
            StringBuilder huffmanString = new StringBuilder();
            for (int i = 0; i < binaryString.length(); i++) {
                // binaryString içindeki her karakter, huffmanString'e eklenir.
                huffmanString.append(binaryString.charAt(i));
                // huffmanString, HashMap'te anahtar olarak bulunabilirse, o anahtarın değeri olan karakter resultBuilder'a eklenir.
                // huffmanString sıfırlanarak yeni bir Huffman kodu aranabilir hale getirilir.
                if (hashMap.containsKey(huffmanString.toString())) {
                    resultBuilder.append(hashMap.get(huffmanString.toString()));
                    huffmanString.setLength(0);
                }
            }
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
        // Son olarak, biriktirilen çözülmüş metin String olarak geri döndürülür.
        return resultBuilder.toString();
    }

    public HashMap<String, Character> readBinaryHashMapFromFile(String fileName) {
        // Bir dosyadan okunan verileri, bir Huffman kod çözme için kullanılacak HashMap'ine aktarır.
        HashMap<String, Character> hashMap = new HashMap<>();
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            while (true) {
                // Huffman koduna karşılık gelen karakter değerini okunur.
                char value = inputStream.readChar();
                // Huffman kodunun uzunluğunu okunur
                int length = inputStream.readInt();
                int byteLength = 0;
                // Uzunluğun kaç byte karşılık geldiğini bulunur.
                for (int i = 0; i < length; i = i + 8) {
                    byteLength++;
                }
                // Huffman kodunu bir byte dizisi olarak okunur.
                byte[] binaryKey = new byte[byteLength];
                inputStream.readFully(binaryKey);
                // Huffman kodunu ikilik bir dizeye dönüştürünür.
                StringBuilder binaryStringBuilder = new StringBuilder();
                for (byte b : binaryKey) {
                    binaryStringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
                }
                String key = binaryStringBuilder.toString();
                // Karakter değeri ve Huffman kodu çiftini HashMap'ine eklenir.
                hashMap.put(key, value);
            }
        } catch (EOFException e) {
            // Dosya sonuna gelindi, hiçbir işlem yapılmaz.
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
        return hashMap;
    }

    public String readerFromMainFile(String fileName) {
        StringBuilder strBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                strBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
        return strBuilder.toString();
    }

    public HashMap<Integer, String> readerFromLZWAlgorithmCode(String fileName) {
        // LZW algoritması için bir hash map oluşturuyoruz
        HashMap<Integer, String> lzwHashMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Dosya okuma işlemini gerçekleştiriyoruz
            while ((line = reader.readLine()) != null) {

                // Satırı ikiye ayırıyoruz
                String[] arraySplit = line.split(":");

                // Satırda en az iki öğe varsa devam ediyoruz
                if (arraySplit.length >= 2) {

                    // İlk öğeyi anahtar olarak, ikinci öğeyi de değer olarak ekliyoruz
                    int key = Integer.parseInt(arraySplit[1]);
                    String value = arraySplit[0];
                    lzwHashMap.put(key, value);
                }
            }
        } catch (IOException e) {

            // Bir hata oluşursa ekrana yazdırıyoruz
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
        return lzwHashMap;
    }

}