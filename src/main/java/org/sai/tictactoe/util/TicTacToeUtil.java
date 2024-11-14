package org.sai.tictactoe.util;

import org.sai.tictactoe.controller.TttController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TicTacToeUtil {
    public static Set<Set<Integer>> loadWinCombosFromFile(String fileName){
        Set<Set<Integer>> winCombos = new HashSet<>();
        //Read wincombos from a file
        try(InputStream inputStream = TttController.class.getClassLoader().getResourceAsStream(fileName);
        ) {
            assert inputStream != null;
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));){
                winCombos = reader.lines()
                            .map(TicTacToeUtil::lineToSet)
                            .collect(Collectors.toSet());

            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error while reading file: " + e.getMessage());
        }

        return winCombos;
    }

    private static Set<Integer> lineToSet(String line){
        Set<Integer> set = new HashSet<>();
        for(String num: line.split(",")){
            set.add(Integer.parseInt(num.trim()));
        }
        return set;
    }
}
