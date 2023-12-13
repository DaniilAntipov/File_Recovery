package Maven.file_analyze;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

enum FileType {
    JPEG("FFD8FFE0", "jpg"),
    PNG("89504E47", "png"),
    PDF("25504446", "pdf"),
    BIN("53503031", "bin"),
    TXT("330D0A34", "txt"),
    DOC("D0CF11E0", "doc"),
    XML("3C3F786D", "xml"),
    GIF("47494638", "gif"),
    TIFF("49492A00", "tiff"),
    MP3("FFFAE340", "mp3"),
    WAV("57415645", "wav"),
    RAR("52617221", "rar"),
    ZIP("504B0304", "zip"),
    CHM("49545346", "chm"),
    EXE("4D5A9000", "exe"),
    MP4("00000018", "MP4"),
    INI("FFFE0D00", "ini"),
    PY("696D706F", "py"),
    IML("3C3F786D", "iml"),
    JAVA("7075626C", "java"),
    CPP("EFBBBF23", "cpp"),
    JAR("504B0304", "jar"),
    SLN("EFBBBF0D", "sln"),
    VCXPROJ("3C3F786D", "vcxproj"),
    FILTERS("EFBBBF3C", "filters"),
    LOG("EFBBBF20", "log"),
    PSD("38425053", "psd"),
    HTML("3C21444F", "html"),
    AVIF("0000001C", "avif"),
    MOV("00000014", "mov"),
    CLASS("CAFEBABE", "class"),
    DOCX("504B0304", "docx");

    private final String magicNumber;
    private final String extension;

    FileType(String magicNumber, String extension) {
        this.magicNumber = magicNumber;
        this.extension = extension;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public String getExtension() {
        return extension;
    }
}

public class FileAnalyzer {

    private static final Map<String, String> magicNumbers = new HashMap<>();

    static {
        for (FileType fileType : FileType.values()) {
            magicNumbers.put(fileType.getMagicNumber(), fileType.getExtension());
        }
    }

    public void restoreFileExtension(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[4];
            fileInputStream.read(bytes, 0, 4);
            fileInputStream.close();

            String magicNumber = bytesToHex(bytes);

            if (magicNumbers.containsKey(magicNumber)) {
                FileType fileType = getFileTypeByMagicNumber(magicNumber);
                String correctExtension = fileType.getExtension();
                if (correctExtension.equals(file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length()))) {
                    System.out.println("Файл не поврежден.");
                }
                else {
                    String newFilePath = file.getParent() + File.separator + file.getName().substring(0, file.getName().lastIndexOf('.') + 1) + correctExtension;
                    File restoredFile = new File(newFilePath);

                    if (file.renameTo(restoredFile)) {
                        System.out.println("Файл успешно восстановлен и сохранен с расширением ." + correctExtension);
                        System.out.println("Путь к восстановленному файлу: " + newFilePath);
                    } else {
                        System.out.println("Не удалось сохранить восстановленный файл.");
                    }
                }

            } else {
                System.out.println("Не удалось определить тип файла.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileType getFileTypeByMagicNumber(String magicNumber) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getMagicNumber().equals(magicNumber)) {
                return fileType;
            }
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }

}
