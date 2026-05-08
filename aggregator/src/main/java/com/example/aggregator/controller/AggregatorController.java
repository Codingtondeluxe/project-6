package com.example.aggregator.controller;

import com.example.aggregator.model.Entry;
import com.example.aggregator.service.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AggregatorController {

    private static final Logger logger = LoggerFactory.getLogger(AggregatorController.class);

    private AggregatorService aggregatorService;

    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/")
    public List<Entry> helloWorld() {
        List<Entry> entries = new ArrayList<>();
        entries.add(aggregatorService.getDefinitionFor("hello"));
        entries.add(aggregatorService.getDefinitionFor("world"));
        return entries;
    }

    @GetMapping("/getDefinitionFor/{word}")
    public Entry getDefinitionFor(@PathVariable String word) {
        return aggregatorService.getDefinitionFor(word);
    }

    @GetMapping("/getWordsThatContainSuccessiveLettersAndStarsWith/{chars}")
    public List<Entry> getWordsThatContainSuccessiveLettersAndStarsWith(@PathVariable String chars) {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        List<Entry> entries = aggregatorService.getWordsThatContainSuccessiveLettersAndStartsWith(chars);
        stopWatch.stop();

        long nanoSeconds = stopWatch.getLastTaskTimeNanos();
        String message = new StringBuilder().append("Retrieved entries for words starting with [")
                .append(chars)
                .append("] and containing consecutive letters, containing ")
                .append(entries.size())
                .append(" entries in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();

        logger.info(message);

        return entries;

    }

    @GetMapping("/getWordsThatContainSuccessiveLettersAndContains/{chars}")
    public List<Entry> getWordsThatContainSuccessiveLettersAndContains(@PathVariable String chars) {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        List<Entry> entries = aggregatorService.getWordsThatContainSuccessiveLettersAndContains(chars);
        stopWatch.stop();

        long nanoSeconds = stopWatch.getLastTaskTimeNanos();
        String message = new StringBuilder().append("Retrieved entries for words containing [")
                .append(chars)
                .append("] and containing consecutive letters, containing ")
                .append(entries.size())
                .append(" entries in ")
                .append(nanoSeconds / 1000000.0)
                .append("ms")
                .toString();

        logger.info(message);

        return entries;

    }
}
