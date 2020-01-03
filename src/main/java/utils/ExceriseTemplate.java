package utils;

import java.io.IOException;
import java.util.List;

public class ExceriseTemplate extends ExceriseBase {

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);
    }

}
