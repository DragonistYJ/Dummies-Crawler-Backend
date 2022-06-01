package scu.genius.dummiescrawler.crawler.executor.function.extension;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import scu.genius.dummiescrawler.core.annotation.Comment;
import scu.genius.dummiescrawler.core.annotation.Example;
import scu.genius.dummiescrawler.core.executor.FunctionExtension;

import java.util.Arrays;
import java.util.List;

@Component
public class ArrayFunctionExtension implements FunctionExtension {

    @Override
    public Class<?> support() {
        return Object[].class;
    }

    @Comment("获取数组的长度")
    @Example("${arrayVar.size()}")
    public static int size(Object[] objs) {
        return objs.length;
    }

    @Comment("将数组拼接起来")
    @Example("${arrayVar.join()}")
    public static String join(Object[] objs, String separator) {
        return StringUtils.join(objs, separator);
    }

    @Comment("将数组用separator拼接起来")
    @Example("${arrayVar.join('-')}")
    public static String join(Object[] objs) {
        return StringUtils.join(objs);
    }

    @Comment("将数组转为List")
    @Example("${arrayVar.toList()}")
    public static List<?> toList(Object[] objs) {
        return Arrays.asList(objs);
    }

}
