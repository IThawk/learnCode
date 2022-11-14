package com.ithawk.demo.thread.one;

/**
 * 责任链
 */
public class ProcessorAppDemo {

    static IRequestProcessor requestProcessor;

    public void setUp() {
        PrintProcessor printProcessor = new PrintProcessor();
        printProcessor.start();
        SaveProcessor saveProcessor = new SaveProcessor(printProcessor);
        saveProcessor.start();

        requestProcessor = new PrevProcessor(saveProcessor);

        //requestProcessor :revProcessor->saveProcessor ->printProcessor
        ((PrevProcessor) requestProcessor).start();
    }


    public static void main(String[] args) {
        ProcessorAppDemo app = new ProcessorAppDemo();
        app.setUp();
        Request request = new Request();
        request.setName("Mic");
        requestProcessor.process(request);
    }

}
