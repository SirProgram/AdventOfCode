package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ExceriseBase {

    public List<String> readFile(String path) throws IOException {
        InputStream resourceAsStream = ExceriseBase.class.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
        return reader.lines().collect(Collectors.toList());

    }

}
