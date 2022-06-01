package scu.genius.dummiescrawler.crawler.job;

import lombok.extern.slf4j.Slf4j;
import scu.genius.dummiescrawler.core.context.SpiderContext;
import scu.genius.dummiescrawler.core.model.SpiderOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SpiderJobContext extends SpiderContext {

    private static final long serialVersionUID = 9099787449108938453L;
    private OutputStream outputstream;

    private List<SpiderOutput> outputs = new ArrayList<>();

    private boolean output;

    public SpiderJobContext(OutputStream outputstream, boolean output) {
        super();
        this.outputstream = outputstream;
        this.output = output;
    }

    public void close() {
        try {
            this.outputstream.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void addOutput(SpiderOutput output) {
        if (this.output) {
            synchronized (this.outputs) {
                this.outputs.add(output);
            }
        }
    }

    @Override
    public List<SpiderOutput> getOutputs() {
        return outputs;
    }

    public OutputStream getOutputstream() {
        return this.outputstream;
    }

    public static SpiderJobContext create(String directory, Long id, Long taskId, boolean output) {
        OutputStream os = null;
        try {
            File file = new File(new File(directory), id + File.separator + "logs" + File.separator + taskId + ".log");
            File dirFile = file.getParentFile();
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            os = new FileOutputStream(file, true);
        } catch (Exception e) {
            log.error("创建日志文件出错", e);
        }
        SpiderJobContext context = new SpiderJobContext(os, output);
        context.setFlowId(id);
        context.put("taskId", taskId);
        return context;
    }
}
