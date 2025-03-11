package gestion.util;

import java.util.concurrent.atomic.AtomicInteger;
public class CodeGenerator {
    private static final AtomicInteger medCounter = new AtomicInteger(1);
    private static final AtomicInteger patCounter = new AtomicInteger(1);
    private static final String MED_PREFIX = "MED";
    private static final String PAT_PREFIX = "PAT";
    private static final int CODE_LENGTH = 6;

    public static String generateMedCode() {
        return generateCode(MED_PREFIX, medCounter.getAndIncrement());
    }

    public static String generatePatCode() {
        return generateCode(PAT_PREFIX, patCounter.getAndIncrement());
    }

    private static String generateCode(String prefix, int sequence) {
        String numericPart = String.format("%0" + (CODE_LENGTH - prefix.length()) + "d", sequence);
        return prefix + numericPart;
    }
}
