package scu.genius.dummiescrawler.crawler.executor.function.extension;

import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.annotation.Comment;
import scu.genius.dummiescrawler.core.annotation.Example;
import scu.genius.dummiescrawler.core.executor.FunctionExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MapFunctionExtension implements FunctionExtension {

    @Override
    public Class<?> support() {
        return Map.class;
    }

    @Comment("将map转换为List")
    @Example("${mapmVar.toList('=')}")
    public static List<String> toList(Map<?, ?> map, String separator) {
        return map.entrySet().stream().map(entry -> entry.getKey() + separator + entry.getValue()).collect(Collectors.toList());
    }
}
