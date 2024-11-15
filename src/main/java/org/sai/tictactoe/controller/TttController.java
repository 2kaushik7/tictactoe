package org.sai.tictactoe.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.sai.tictactoe.model.Result;
import org.sai.tictactoe.util.TicTacToeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController("/tictactoe")
public class TttController {

    Result result;

    static Set<Set<Integer>> winCombos = null;
    final Set<Integer> requiredNumbers = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8);

    static {
        String combosFile = "combos.txt";
        winCombos = TicTacToeUtil.loadWinCombosFromFile(combosFile);
    }

    //win combinations = 123,456,789,147,258,369,159,357
    Set<Integer> X = new HashSet<>();
    Set<Integer> O = new HashSet<>();
    Set<Integer> allChoices = new HashSet<>();

    @GetMapping(value = "/{i}/{p}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> insertChoice(@PathVariable int i, @PathVariable char p){
        System.out.println(i+"--"+p);
        switch (p){
            case 'X':
                X.add(i);
                allChoices.add(i);
                break;
            case 'O':
                O.add(i);
                allChoices.add(i);
                break;
            default:
                return ResponseEntity.ok(result);
        }
        if(X.size() >= 3 || O.size() >= 3){
            validatePlayer();
        }
        return ResponseEntity.ok(result != null ? result : new Result(false,""));
    }

    private void validatePlayer() {
        for (Set<Integer> winCombo : winCombos) {
            if(X.containsAll(winCombo)){
                result.setWinner("X");
                result.setResult(true);
                break;
            }
            else if(O.containsAll(winCombo)){
                result.setWinner("O");
                result.setResult(true);
                break;
            } else if (allChoices.containsAll(requiredNumbers)) {
                result.setWinner("draw");
                result.setResult(false);
                break;
            } else {
                result = new Result(false,"");
            }
        }
    }

    @RequestMapping("/r")
    public void reset(){
        result = null;
        X.clear();
        O.clear();
        allChoices.clear();
    }
}
