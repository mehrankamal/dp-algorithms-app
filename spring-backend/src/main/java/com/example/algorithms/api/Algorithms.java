package com.example.algorithms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.example.algorithms.controllers.AlgorithmsController.*;

@RestController
@RequestMapping(value = "/api/algorithms")
@CrossOrigin(origins="http://localhost:3000")
public class Algorithms {
    private final Map<String, ArrayList<AlgorithmModel>> algorithmsName = new HashMap<>();
    private final Map<String, ArrayList<DatasetModel>> datasets = new HashMap<>();

    private static class ComputeModel {
        private String algorithmCode;
        private String inputDataset;

        public void setAlgorithmCode(String algorithmCode) {
            this.algorithmCode = algorithmCode;
        }

        public void setInputDataset(String inputDataset) {
            this.inputDataset = inputDataset;
        }

        public String getAlgorithmCode() {
            return this.algorithmCode;
        }

        public String getInputDataset() {
            return this.inputDataset;
        }
    }
    private static class AlgorithmModel {
        private String code;
        private String name;

        public AlgorithmModel(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    private static class DatasetModel {
        private String code;
        private String content;
        public DatasetModel(String code, String content){
            this.code = code;
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    private String readDataset(String pathname) {
        try {
            File myObj = new File(pathname);
            Scanner myReader = new Scanner(myObj);
            StringBuilder data = new StringBuilder();
            while (myReader.hasNext()) {
                data.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
            return data.toString();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "Error Occurred!";
        }

    }

    public Algorithms() {

        ArrayList<AlgorithmModel> names = new ArrayList<>();
        names.add(new AlgorithmModel("LCS", "Longest Common Subsequence"));
        names.add(new AlgorithmModel("SCSS", "Shortest Common Super Sequence"));
        names.add(new AlgorithmModel("ED", "Edit Distance"));
        names.add(new AlgorithmModel("LIS", "Longest Increasing Subsequence"));
        names.add(new AlgorithmModel("MCM", "Matrix Chain Multiplication"));
        names.add(new AlgorithmModel("KP", "0-1 Knapsack"));
        names.add(new AlgorithmModel("PP", "Partitioning"));
        names.add(new AlgorithmModel("RCP", "Rod Cutting"));
        names.add(new AlgorithmModel("CCM", "Coin Change"));
        names.add(new AlgorithmModel("WB", "Word Break"));
        this.algorithmsName.put("algorithms", names);

        this.datasets.put("LCS", new ArrayList<>());
        this.datasets.put("SCSS", new ArrayList<>());
        this.datasets.put("ED", new ArrayList<>());
        this.datasets.put("LIS", new ArrayList<>());
        this.datasets.put("MCM", new ArrayList<>());
        this.datasets.put("KP", new ArrayList<>());
        this.datasets.put("PP", new ArrayList<>());
        this.datasets.put("RCP", new ArrayList<>());
        this.datasets.put("CCM", new ArrayList<>());
        this.datasets.put("WB", new ArrayList<>());

        for(String code : this.datasets.keySet()){
            for(int i = 0; i<10; i++){
                String datasetCode = code + i;
                this.datasets.get(code).add(new DatasetModel(datasetCode, readDataset("./src/main/resources/datasets/" + datasetCode + ".txt")));
            }
        }
    }

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAlgorithms() {
        return new ResponseEntity<>(this.algorithmsName.get("algorithms"), HttpStatus.OK);
    }

    @RequestMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDataset(@PathVariable(value = "code") String algorithmCode) {
        ArrayList<DatasetModel> datasetContent = this.datasets.get(algorithmCode);
        for(DatasetModel md : datasetContent){
            System.out.println(md.getContent());
        }
        return new ResponseEntity<>(datasetContent, HttpStatus.OK);
    }

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> computeAlgorithm(@RequestBody ComputeModel algorithmData) {
        String algorithmCode = algorithmData.getAlgorithmCode();
        String inputDataset = algorithmData.getInputDataset();
        String output;
        switch (algorithmCode) {
            case "LCS":
                output = longestCommonSubsequence(inputDataset);
                break;
            case "SCSS":
                output = shortestCommonSuperset(inputDataset);
                break;
            case "ED":
                output = editDistance(inputDataset);
                break;
            case "LIS":
                output = longestIncreasingSubsequence(inputDataset);
                break;
            case "MCM":
                output = matrixChainMul(inputDataset);
                break;
            case "KP":
                output = knapsack01(inputDataset);
                break;
            case "PP":
                output = partitionProblem(inputDataset);
                break;
            case "RCP":
                output = rodCutting(inputDataset);
                break;
            case "CCM":
                output = coinChange(inputDataset);
                break;
            case "WB":
                output = wordBreak(inputDataset);
                break;
            default:
                output = "Please select a valid algorithm and dataset.";
        }

        System.out.println(algorithmCode);
        System.out.println(inputDataset);
        System.out.println(output);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
